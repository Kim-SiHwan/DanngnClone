package kim.sihwan.daangnclone.service;


import kim.sihwan.daangnclone.config.jwt.JwtTokenProvider;
import kim.sihwan.daangnclone.domain.Area;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.SelectedArea;
import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginResponseDto;
import kim.sihwan.daangnclone.dto.member.MemberResponseDto;
import kim.sihwan.daangnclone.exception.customException.UsernameDuplicatedException;
import kim.sihwan.daangnclone.repository.AreaRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.SelectedAreaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final AreaRepository areaRepository;
    private final SelectedAreaRepository selectedAreaRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    private Boolean isValidateDuplicateMember(Member member) {
        Member getMember = memberRepository.findMemberByUsername(member.getUsername());
        if(getMember ==null){
            return true;
        }
        return false;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        System.out.println("로그인 서비스 ");
        System.out.println(loginRequestDto.getUsername()+" "+loginRequestDto.getPassword());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        Authentication auth = managerBuilder.getObject().authenticate(token);

        Member member = memberRepository.findMemberByUsername(auth.getName());
        String jwt = tokenProvider.createToken(auth);


        return new LoginResponseDto(member.getId(),jwt, member.getUsername(), member.getNickname());
    }

    @Transactional
    public Long join(JoinRequestDto joinRequestDto){
        System.out.println("?먼데");
        System.out.println(joinRequestDto.getUsername());
        //얘가 Given? ??
        Member member = joinRequestDto.toEntity(joinRequestDto,passwordEncoder);
        System.out.println(member.getUsername());
        if (!isValidateDuplicateMember(member)) {
            throw new UsernameDuplicatedException();
        }
        memberRepository.save(member);

        Area area = areaRepository.findByAddress(joinRequestDto.getArea());
        SelectedArea selectedArea = new SelectedArea();
        selectedArea.addMember(member);
        selectedArea.addArea(area);
        selectedAreaRepository.save(selectedArea);

        String[] splitStr= joinRequestDto.getArea().split(" ");
        String city = splitStr[0];
        List<Area> list = areaRepository.findAllByCityLike("%"+city+"%");
        System.out.println(list.size());

        Area getArea = areaRepository.findByAddress(joinRequestDto.getArea());

        List<String> nearArea = new ArrayList<>();
        list.forEach(l->{
            double distanceMeter =
                    distance(Double.parseDouble(getArea.getLat()), Double.parseDouble(getArea.getLng()), Double.parseDouble(l.getLat()), Double.parseDouble(l.getLng()), "meter");
            if(distanceMeter<=3000) {
                nearArea.add(l.getDong());
                System.out.println(getArea.getDong() + "부터 " + l.getDong() + "까지의 거리는 : " + distanceMeter);
            }
        });
        ListOperations<String,String> vo = redisTemplate.opsForList();
        if(!vo.getOperations().hasKey(getArea.getAddress()+"::List")) {
            vo.leftPushAll(getArea.getAddress() + "::List", nearArea);
        }
        System.out.println(getArea.getAddress());
        System.out.println("레디스! ");
        System.out.println(vo.range(getArea.getAddress()+"::List",0L,-1L));




        return member.getId();
    }

    public MemberResponseDto findById(Long memberId){
        return new MemberResponseDto(memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new));

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username);
        return new User(member.getUsername(), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
    }


    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}
