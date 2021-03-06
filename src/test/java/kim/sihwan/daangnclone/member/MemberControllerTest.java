package kim.sihwan.daangnclone.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginRequestDto;
import kim.sihwan.daangnclone.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class MemberControllerTest {

    private String token;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;


    public JoinRequestDto join(String username, String password, String nickname) {
        return JoinRequestDto
                .builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
    }

    public LoginRequestDto login(String username, String password) {
        return LoginRequestDto
                .builder()
                .username(username)
                .password(password)
                .build();
    }

    public <T> String toJson(T obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void ????????????_????????????_?????????() throws Exception {
        //given
        JoinRequestDto joinRequestDto = join("test", "testPw", "testNn");

        //when
        final ResultActions result = mockMvc
                .perform(post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(joinRequestDto)));
        //then
        result
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void ?????????_????????????_?????????() throws Exception {
        //given
        memberService.join(join("test", "testPw", "testNn"));
        LoginRequestDto loginRequestDto = login("test", "testPw");

        //when
        final ResultActions result = mockMvc
                .perform(post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(loginRequestDto)));
        //then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").exists())
                .andExpect(jsonPath("username").value("test"));

    }

    @Test
    public void ??????_??????_?????????() throws Exception {

        //????????? ??? ???????????? ??????????????? 200 ??????
        String jwt = "Bearer " + token;
        mockMvc
                .perform(get("/test/v2")
                        .header("Authorization", jwt))
                .andExpect(status().isOk())
                .andExpect(content().string("Only Authenticated User"))
                .andDo(print());

    }
}
