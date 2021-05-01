package kim.sihwan.daangnclone.member;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginResponseDto;
import kim.sihwan.daangnclone.dto.member.MemberResponseDto;
import kim.sihwan.daangnclone.exception.customException.UsernameDuplicatedException;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MemberServiceTest {


    @Mock
    MemberRepository memberRepository;

    @Mock
    LoginResponseDto loginDto;

    @Mock
    JoinRequestDto joinRequestDto;

    @Mock
    PasswordEncoder encoder;


    @InjectMocks
    MemberService memberService;


    private Member member = Member
            .builder()
            .username("test")
            .password("testPw")
            .nickname("testNn")
            .area("만수3동")
            .role("ROLE_USER")
            .build();

    private LoginRequestDto loginRequestDto = LoginRequestDto
            .builder()
            .username("test")
            .password("testPw")
            .build();

    @Test
    @DisplayName("정상적으로 가입하는 테스트")
    public void 회원가입_테스트() {
        // given ( A ).willReturn ( B )일 때 A 메소드를 호출함으로 B를 가지고 있다고 가정한다
        // when C C를 실행시켰을 때
        // then D D의 결과가 나와야 한다.

        //joinRequestDto.toEntity(joinRequestDto)를 실행했을 때 member 무조건 반환되는 상황을 가정
        //memberService.join(joinRequestDto)을 실행했을 때
        //verify(memberRepository.times(1)).save(member) memberRepository.save() 의 파라미터가 member 인지를 검사하고, 1번 호출되었는지를 검사

        //given
//        given(joinRequestDto.toEntity(joinRequestDto,encoder)).willReturn(member);
        given(joinRequestDto.toEntity(joinRequestDto,encoder)).willReturn(member);

        //when
        //join 제대로 돌아가는지 검증하기 위해 member 필요하다??
        //join 수행하기 위해 member 받아논다

        memberService.join(joinRequestDto);

        //then
        verify(memberRepository,times(1)).save(member);

    }

    @Test(expected = UsernameDuplicatedException.class)
    public void 회원가입_중복_테스트 (){
        //given
        given(memberRepository.findMemberByUsername(anyString())).willReturn(member);
        given(joinRequestDto.toEntity(joinRequestDto,encoder)).willReturn(member);
        //when
        memberService.join(joinRequestDto);

        //then


    }

    @Mock
    UsernamePasswordAuthenticationToken token;


    @Test
    public void 로그인_테스트 (){


    }

  /*  @Value("${jwt.secret}")
    private String secret;

    @Mock
    Member member;

    @Mock
    JoinRequestDto joinRequestDto;

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder encoder;




    public Long join(String username, String password, String nickname){
        System.out.println("JOIN!");
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();

        return memberService.join(joinRequestDto);
    }

    public LoginResponseDto login(String username, String password){
        System.out.println("LOGIN!");
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username(username)
                .password(password)
                .build();
        return memberService.login(loginRequestDto);
    }
    @Test
    public void 회원가입_테스트 (){
        //기댓값 : 정상적인 회원가입으로 통과

        //given
        Long memberId = join("test","testPw","testNn");
        //when
        MemberResponseDto getMember = memberService.findById(memberId);
        //then
        assertEquals(memberId,getMember.getId());
        assertEquals("test",getMember.getUsername());
        assertEquals("testNn",getMember.getNickname());

    }

    @Test(expected = UsernameDuplicatedException.class)
    public void 회원가입_중복_테스트 (){
        //기댓값 : 중복된 username으로 UsernameDuplicatedException 발생으로 통과

        //given
        Long memberId = join("test","testPw","testNn");

        //when
        Long memberId2 = join("test","testPw","testNn");

        //then

    }

    @Test
    public void 로그인_테스트 (){
        //기댓값 : 정상 로그인을 통한 jwt 토큰 발급

        //given
        Long memberId = join("test","testPw","testNn");

        //when
        LoginResponseDto loginResponseDto = login("test","testPw");
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(loginResponseDto.getToken().substring(7))
                .getBody();
        //then
        System.out.println(loginResponseDto.getUsername());
        System.out.println(loginResponseDto.getToken());
        assertEquals("test",loginResponseDto.getUsername());
        assertEquals("testNn",loginResponseDto.getNickname());
        assertTrue(loginResponseDto.getToken().length()>10);
        assertTrue(loginResponseDto.getToken().startsWith("Bearer "));
        assertEquals(loginResponseDto.getUsername(),claims.getSubject());
    }

    @Test(expected = BadCredentialsException.class)
    public void 로그인_실패_테스트 (){
        //기댓값 : 로그인 실패로 BadCredentialException 발생해 테스트 통과

        //given
        Long memberId = join("test","testPw","testNn");

        //when
        LoginResponseDto loginResponseDto = login("test","wrongPw");

        //then

    }*/
}
