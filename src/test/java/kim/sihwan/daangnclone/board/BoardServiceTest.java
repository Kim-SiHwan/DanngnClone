package kim.sihwan.daangnclone.board;

import kim.sihwan.daangnclone.domain.Board;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.dto.board.BoardRequestDto;
import kim.sihwan.daangnclone.dto.board.BoardUpdateRequestDto;
import kim.sihwan.daangnclone.repository.BoardRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.service.BoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class BoardServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    BoardRepository boardRepository;

    @Mock
    BoardRequestDto boardRequestDto;

    @InjectMocks
    BoardService boardService;

    private Board board = Board.builder()
            .title("title")
            .content("content")
            .read(0)
            .createDate(LocalDateTime.now())
            .build();

    private Member member = Member.builder()
            .username("test")
            .password("testPw")
            .nickname("testNn")
            .area("만수3동")
            .role("ROLE_USER")
            .build();

    @Test
    public void 동네생활_생성_테스트() {
        //given
        given(boardRequestDto.toEntity(boardRequestDto)).willReturn(board);
        given(memberRepository.findMemberByNickname(anyString())).willReturn(Optional.of(member));
        //when
        boardService.addBoard(boardRequestDto);
        //then
        verify(boardRepository, times(1)).save(board);

    }

    @Test
    public void 동네생활_조회_테스트() {
        //given
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        //when
        Board getBoard = boardService.findById(anyLong());
        //then
        assertEquals(1,getBoard.getRead());

    }

    @Test(expected = NoSuchElementException.class)
    public void 동네생활_조회실패_테스트() {
        //given
        given(boardRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        Board getBoard = boardService.findById(anyLong());
        //then

    }

    @Test
    public void 동네생활_수정_테스트() {
        //given
        BoardUpdateRequestDto updateRequestDto = new BoardUpdateRequestDto();
        updateRequestDto.setId(1L);
        updateRequestDto.setTitle("updateTitle");
        updateRequestDto.setContent("updateContent");
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        //when
        Board getBoard = boardService.updateBoard(updateRequestDto);
        //then
        assertEquals("updateTitle",getBoard.getTitle());
        assertEquals("updateContent",getBoard.getContent());

    }

    @Test
    public void 동네생활_삭제_테스트() {
        //given
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        //when
        boardService.deleteBoard(anyLong());
        //then
    }
}

