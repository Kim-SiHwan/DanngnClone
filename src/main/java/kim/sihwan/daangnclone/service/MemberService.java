package kim.sihwan.daangnclone.service;


import kim.sihwan.daangnclone.config.jwt.JwtTokenProvider;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginResponseDto;
import kim.sihwan.daangnclone.dto.member.MemberResponseDto;
import kim.sihwan.daangnclone.exception.customException.UsernameDuplicatedException;
import kim.sihwan.daangnclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
        String jwt = "Bearer "+tokenProvider.createToken(auth);
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


}
