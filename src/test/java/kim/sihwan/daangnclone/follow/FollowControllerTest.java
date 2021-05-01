package kim.sihwan.daangnclone.follow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kim.sihwan.daangnclone.dto.follow.FollowRequestDto;
import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginResponseDto;
import kim.sihwan.daangnclone.service.FollowService;
import kim.sihwan.daangnclone.service.MemberService;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class FollowControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;
    @Autowired
    FollowService followService;

    public void join(String username, String password, String nickname) {
        JoinRequestDto joinRequestDto = JoinRequestDto
                .builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();

        memberService.join(joinRequestDto);
    }

    public LoginResponseDto login(String username, String password) {
        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .username(username)
                .password(password)
                .build();

        return memberService.login(loginRequestDto);
    }

    public FollowRequestDto follow(Long fromMemberId, Long toMemberId){
        return FollowRequestDto
                .builder()
                .fromMemberId(fromMemberId)
                .toMemberId(toMemberId)
                .build();
    }

    public <T> String toJson(T obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void 팔로우_컨트롤러_테스트 () throws Exception {
        //given
        join("test","testPw","testNn");
        join("test2","testPw","testNn2");
        join("test3","testPw","testNn3");

        LoginResponseDto loginResponseDto = login("test","testPw");
        //when
        final ResultActions result = mockMvc
                .perform(post("/api/follow")
                .header("Authorization",loginResponseDto.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(follow(1L,2L))));
        //then
        result
                .andDo(print())
                .andExpect(status().isOk()) ;
    }

    @Test
    public void 팔로워찾기_컨트롤러_테스트 () throws Exception {
        //given
        join("test","testPw","testNn");
        join("test2","testPw","testNn2");
        join("test3","testPw","testNn3");
        join("test4","testPw","testNn4");
        join("test5","testPw","testNn5");
        FollowRequestDto requestDto = follow(1L,2L);
        FollowRequestDto requestDto2 = follow(1L,3L);
        FollowRequestDto requestDto3 = follow(1L,4L);
        FollowRequestDto requestDto4 = follow(1L,5L);

        followService.follow(requestDto);
        followService.follow(requestDto2);
        followService.follow(requestDto3);
        followService.follow(requestDto4);


        LoginResponseDto loginResponseDto = login("test","testPw");

        //when
        final ResultActions result = mockMvc
                .perform(get("/api/follow/1")
                        .header("Authorization",loginResponseDto.getToken()));
        //then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("followers",hasSize(0)))
                .andExpect(jsonPath("followings", hasSize(4)));

    }

    @Test
    public void 언팔로우_컨트롤러_테스트 () throws Exception {
        //given
        join("test","testPw","testNn");
        join("test2","testPw","testNn2");
        join("test3","testPw","testNn3");

        FollowRequestDto requestDto = follow(1L,2L);
        FollowRequestDto requestDto2 = follow(1L,3L);

        followService.follow(requestDto);
        followService.follow(requestDto2);

        LoginResponseDto loginResponseDto = login("test","testPw");

        //when
        final ResultActions result = mockMvc
                .perform(delete("/api/follow")
                .header("Authorization",loginResponseDto.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(requestDto)));
        //then
        result
                .andDo(print())
                .andExpect(status().isOk());

    }


}
