package kim.sihwan.daangnclone.comment;


import kim.sihwan.daangnclone.domain.Board;
import kim.sihwan.daangnclone.domain.Comment;
import kim.sihwan.daangnclone.dto.comment.CommentRequestDto;
import kim.sihwan.daangnclone.repository.BoardRepository;
import kim.sihwan.daangnclone.repository.CommentRepository;
import kim.sihwan.daangnclone.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class CommentServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentRequestDto commentRequestDto;

    @InjectMocks
    CommentService commentService;

    private final Comment comment = Comment
            .builder()
            .username("test")
            .content("testComment")
            .build();

    private final Board board = Board
            .builder()
            .title("testTitle")
            .content("testContent")
            .read(0)
            .createDate(LocalDateTime.now())
            .build();

    @Test
    public void 댓글생성_테스트 (){
        //given
        given(commentRequestDto.toEntity(commentRequestDto)).willReturn(comment);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        //when
        commentService.addComment(commentRequestDto);

        //then
        verify(commentRepository,times(1)).save(any(comment.getClass()));

    }

    @Test
    public void 댓글조회_테스트 (){
        //given
        List<Comment> list = new ArrayList<>();
        list.add(comment);
        list.add(comment);
        given(commentRepository.findAllByBoardId(anyLong())).willReturn(list);

        //when
        List<Comment> result = commentService.getCommentsByBoardId(anyLong());

        //then
        verify(commentRepository,times(1)).findAllByBoardId(anyLong());
        assertEquals(result.size(), 2);
    }
}
