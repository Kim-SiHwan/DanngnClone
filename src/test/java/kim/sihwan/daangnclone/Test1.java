package kim.sihwan.daangnclone;

import kim.sihwan.daangnclone.config.jwt.JwtTokenProvider;
import kim.sihwan.daangnclone.service.MemberService;
import kim.sihwan.daangnclone.controller.TestBoardController;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.repository.MemberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class Test1 {

    private String token;
    @Autowired
    TestBoardController testBoardController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthenticationManagerBuilder managerBuilder;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Before
    public void beforeTest(){
        System.out.println("BEFORE!");
 /*       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        String token = "Bearer " +tokenProvider.createToken(auth);
        System.out.println(token);*/

        Member member = Member
                .builder()
                .username("test")
                .password(passwordEncoder.encode("testPw"))
                .nickname("testNn")
                .role("ROLE_USER")
                .build();
        memberRepository.save(member);

        Member m = memberRepository.findById(1L).get();
        System.out.println(m.getUsername());
        System.out.println(m.getPassword());
        System.out.println(m.getNickname());


        /*
         * 사실상 토큰으로'만'로그인을 하는것이기 때문에
         * 토큰을 발급받고 그 토큰으로 진행을 해보자고, ! ! 그러면
         * 토큰을 여기 before에서 발급받고 그 토큰으로만 진행하면 될것같음.*//*
         */

        UserDetails userDetails = memberService.loadUserByUsername(m.getUsername());
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getAuthorities());

        UsernamePasswordAuthenticationToken jwt = new UsernamePasswordAuthenticationToken(member.getUsername(),"testPw");
        System.out.println(jwt);

        Authentication auth = managerBuilder.getObject().authenticate(jwt);
        token = tokenProvider.createToken(auth);

        System.out.println(token);

    }




    @Test
    public void 인증이_필요치않는_테스트 () throws Exception {
        //200코드가 나와야함
        System.out.println("??토큰입니다 b"+token);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("This is Test"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void 인증_실패_테스트 () throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("This is Test"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void 변형된토큰으로_요청하는_테스트 () throws Exception{
        //이건 토큰의 문제가 아닌 Bearer??로 시작해서 그럼 원래 Bearer 로 시작해야함.
        //이렇게 작성하면 resolve Token에서 null로 넘기기에 Filter에서 NullException 발생
/*        String jwt = "Bearertgdg "+token;
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v2")
                .header("Authorization",jwt))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Only Authenticated User"))
                .andDo(MockMvcResultHandlers.print());*/

        //401코드 [ Filter에서 InvalidTokenException ]
        token = token.substring(0,token.length()-2);
        String jwt = "Bearer "+token;
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v2")
                        .header("Authorization",jwt))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Only Authenticated User"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void 토큰_인증_테스트 () throws Exception{

        //제대로 된 토큰으로 요청하기에 200 성공
        String jwt = "Bearer "+token;
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v2")
                        .header("Authorization",jwt))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Only Authenticated User"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    @WithMockUser(username = "test",roles = "USER")
    public void 인증_테스트 () throws Exception{
        //Security login 유저 정보가 있다는 것은 토큰을 가지고 이미 인증되었다고 판단하기에 200 성공
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("With Mock User"))
                .andDo(MockMvcResultHandlers.print());

    }

}