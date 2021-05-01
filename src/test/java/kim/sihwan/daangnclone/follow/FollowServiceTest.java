package kim.sihwan.daangnclone.follow;

import kim.sihwan.daangnclone.domain.Follow;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.dto.follow.FollowRequestDto;
import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.MemberResponseDto;
import kim.sihwan.daangnclone.repository.FollowRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.service.FollowService;
import kim.sihwan.daangnclone.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class FollowServiceTest {

    @Mock
    FollowRepository followRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    FollowRequestDto followRequestDto;

    @InjectMocks
    FollowService followService;

    private Member fromMember = Member.builder()
            .username("fromTest")
            .password("fromTestPw")
            .nickname("fromTestNn")
            .role("ROLE_USER")
            .area("만수3동")
            .build();


    private Member toMember = Member.builder()
            .username("toTest")
            .password("toTestPw")
            .nickname("toTestNn")
            .role("ROLE_USER")
            .area("만수3동")
            .build();

    @Test
    public void 팔로우_테스트 (){
        //given
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(fromMember))
                .willReturn(Optional.of(toMember));

        //when
        Follow follow = followService.follow(followRequestDto);

        //then
        verify(followRepository,times(1)).save(any(Follow.class));
        assertEquals("fromTest",follow.getFromMember().getUsername());
        assertEquals("toTest",follow.getToMember().getUsername());

    }

    @Test
    public void 팔로워_찾기_테스트 (){
        //given

        //when

        //then

    }




  /*  @Autowired
    FollowRepository followRepository;
    @Autowired
    FollowService followService;
    @Autowired
    MemberService memberService;

    public Long join(String username, String password, String nickname){
        JoinRequestDto joinRequestDto = JoinRequestDto
                .builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
        return memberService.join(joinRequestDto);
    }

    public Long follow(Long fromMemberId, Long toMemberId){
        FollowRequestDto followRequestDto = FollowRequestDto
                .builder()
                .fromMemberId(fromMemberId)
                .toMemberId(toMemberId)
                .build();
        return followService.follow(followRequestDto);
    }

    @Test
    public void 팔로우_테스트 (){
        //기댓값 : test -> test2의 팔로우로 fromMember = test, toMember = test2로 통과
        //given
        Long fromMemberId = join("test","testPw","testNn");
        Long toMemberId = join("test2","testPw","testNn2");

        //when
        Long followId = follow(fromMemberId,toMemberId);
        Follow getFollow = followRepository.findById(followId).orElseThrow(NoSuchElementException::new);

        //then
        assertEquals("test",getFollow.getFromMember().getUsername());
        assertEquals("test2",getFollow.getToMember().getUsername());

    }

    @Test
    public void 여러개_팔로우_테스트 (){
        //기댓값 : test -> test2,3,4를 팔로우 test.fromMembers = 0, test.toMembers = test2,3,4로 3개 통과

        //given
        Long memberId = join("test","testPw","testNn");
        Long memberId2 = join("test2","testPw","testNn2");
        Long memberId3 = join("test3","testPw","testNn3");
        Long memberId4 = join("test4","testPw","testNn4");

        //when
        follow(memberId,memberId2);
        follow(memberId,memberId3);
        follow(memberId,memberId4);
        List<MemberResponseDto> fromMemberList = followService.getFromMembers(memberId);
        List<MemberResponseDto> toMemberList = followService.getToMembers(memberId);

        //then
        assertEquals(0,fromMemberList.size());
        assertEquals(3,toMemberList.size());

    }

    @Test
    public void 언팔로우_테스트 (){
        //기댓값 : 팔로우 취소로 member1은 2명의 팔로잉, member2는 1명의 팔로워 보유로 테스트 통과

        //given
        Long memberId = join("test","testPw","testNn");
        Long memberId2 = join("test2","testPw","testNn2");
        Long memberId3 = join("test3","testPw","testNn3");
        Long memberId4 = join("test4","testPw","testNn4");

        //when
        follow(memberId,memberId2);
        follow(memberId,memberId3);
        follow(memberId,memberId4);
        followService.unfollow(memberId,memberId4);

        List<MemberResponseDto> fromMemberList = followService.getFromMembers(memberId2);
        List<MemberResponseDto> toMemberList = followService.getToMembers(memberId);
        System.out.println(fromMemberList.size());
        System.out.println(toMemberList.size());

        //then
        assertEquals(1,fromMemberList.size());
        assertEquals(2,toMemberList.size());
    }
*/
}
