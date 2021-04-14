package kim.sihwan.daangnclone;

import kim.sihwan.daangnclone.controller.TestBoardController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    TestBoardController testBoardController;
    @Autowired
    MockMvc mockMvc;

/*

    @WithMockUser(username = "test",roles = "USER")
    public void beforeTest(){
        System.out.println("BEFORE!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        String token = "Bearer " +tokenProvider.createToken(auth);
        System.out.println(token);
*/
/*
        Member member = Member
                .builder()
                .username("test")
                .password(passwordEncoder.encode("testPw"))
                .nickname("testNn")
                .role("USER")
                .build();
        memberRepository.save(member);

        *//*
*/
/*
        * 사실상 토큰으로'만'로그인을 하는것이기 때문에
        * 토큰을 발급받고 그 토큰으로 진행을 해보자고, ! ! 그러면
        * 토큰을 여기 before에서 발급받고 그 토큰으로만 진행하면 될것같음.*//*
*/
/*

        UsernamePasswordAuthenticationToken jwt = new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword());
        Authentication auth = managerBuilder.getObject().authenticate(jwt);
        token = tokenProvider.createToken(auth);

        System.out.println(token);*//*

    }

*/



    @Test
    public void 인증이_필요치않는_테스트 () throws Exception {
        //200코드가 나와야함
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("This is Test"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void 인증없이_요청하는_테스트 () throws Exception{
        //403코드가 나와야함.
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Only Authenticated User"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser(username = "test",roles = "USER")
    public void 인증_테스트 () throws Exception{
        //200 코드가 나와야함
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/v3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("With Mock User"))
                .andDo(MockMvcResultHandlers.print());

    }

}
