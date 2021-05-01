package kim.sihwan.daangnclone.qna;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.Qna;
import kim.sihwan.daangnclone.dto.qna.QnaRequestDto;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.QnaRepository;
import kim.sihwan.daangnclone.service.QnaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class QnaServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    QnaRepository qnaRepository;

    @Mock
    QnaRequestDto qnaRequestDto;

    @InjectMocks
    QnaService qnaService;

    private Qna qna = Qna.builder()
            .title("title")
            .content("content")
            .answer(false)
            .visited(false)
            .build();
    private Member member = Member.builder()
            .username("test")
                .password("testPw")
                .nickname("testNn")
                .area("만수3동")
                .role("ROLE_USER")
                .build();

    @Test
    public void 문의사항_생성_테스트 (){
        //given
        given(qnaRequestDto.toEntity(qnaRequestDto)).willReturn(qna);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        //when
        qnaService.addQna(qnaRequestDto);
        //then
        verify(qnaRepository,times(1)).save(any(Qna.class));
    }

    @Test
    public void 문의사항_조회_테스트 (){
        //given
        given(qnaRepository.findById(anyLong())).willReturn(Optional.of(qna));
        //when
        Qna getQna = qnaService.findById(anyLong());
        //then
        assertFalse(getQna.isAnswer());
        assertTrue(getQna.isVisited());

    }

    @Test(expected = NoSuchElementException.class)
    public void 문의사항_조회_실패_테스트 (){
        //given
        given(qnaRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        Qna getQna = qnaService.findById(anyLong());
        //then
    }
}
