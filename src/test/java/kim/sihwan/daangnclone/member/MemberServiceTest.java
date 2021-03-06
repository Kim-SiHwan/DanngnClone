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
            .area("??????3???")
            .role("ROLE_USER")
            .build();

    private LoginRequestDto loginRequestDto = LoginRequestDto
            .builder()
            .username("test")
            .password("testPw")
            .build();

    @Test
    @DisplayName("??????????????? ???????????? ?????????")
    public void ????????????_?????????() {
        // given ( A ).willReturn ( B )??? ??? A ???????????? ??????????????? B??? ????????? ????????? ????????????
        // when C C??? ??????????????? ???
        // then D D??? ????????? ????????? ??????.

        //joinRequestDto.toEntity(joinRequestDto)??? ???????????? ??? member ????????? ???????????? ????????? ??????
        //memberService.join(joinRequestDto)??? ???????????? ???
        //verify(memberRepository.times(1)).save(member) memberRepository.save() ??? ??????????????? member ????????? ????????????, 1??? ????????????????????? ??????

        //given
//        given(joinRequestDto.toEntity(joinRequestDto,encoder)).willReturn(member);
        given(joinRequestDto.toEntity(joinRequestDto,encoder)).willReturn(member);

        //when
        //join ????????? ??????????????? ???????????? ?????? member ??????????????
        //join ???????????? ?????? member ????????????

        memberService.join(joinRequestDto);

        //then
        verify(memberRepository,times(1)).save(member);

    }

    @Test(expected = UsernameDuplicatedException.class)
    public void ????????????_??????_????????? (){
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
    public void ?????????_????????? (){


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
    public void ????????????_????????? (){
        //????????? : ???????????? ?????????????????? ??????

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
    public void ????????????_??????_????????? (){
        //????????? : ????????? username?????? UsernameDuplicatedException ???????????? ??????

        //given
        Long memberId = join("test","testPw","testNn");

        //when
        Long memberId2 = join("test","testPw","testNn");

        //then

    }

    @Test
    public void ?????????_????????? (){
        //????????? : ?????? ???????????? ?????? jwt ?????? ??????

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
    public void ?????????_??????_????????? (){
        //????????? : ????????? ????????? BadCredentialException ????????? ????????? ??????

        //given
        Long memberId = join("test","testPw","testNn");

        //when
        LoginResponseDto loginResponseDto = login("test","wrongPw");

        //then

    }*/
}
