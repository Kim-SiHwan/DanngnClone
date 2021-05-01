package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Board;
import kim.sihwan.daangnclone.domain.Comment;
import kim.sihwan.daangnclone.dto.comment.CommentRequestDto;
import kim.sihwan.daangnclone.repository.BoardRepository;
import kim.sihwan.daangnclone.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long addComment(CommentRequestDto commentRequestDto){
        Comment comment = commentRequestDto.toEntity(commentRequestDto);
        Board board = boardRepository.findById(commentRequestDto.getBoardId()).orElseThrow(NoSuchElementException::new);
        comment.addBoard(board);
        commentRepository.save(comment);
        return comment.getId();
    }

    public List<Comment> getCommentsByBoardId(Long boardId){
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return comments;
    }
}
