package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Board;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.Product;
import kim.sihwan.daangnclone.dto.board.BoardRequestDto;
import kim.sihwan.daangnclone.dto.board.BoardUpdateRequestDto;
import kim.sihwan.daangnclone.repository.BoardRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addBoard(BoardRequestDto boardRequestDto){
        Board board = boardRequestDto.toEntity(boardRequestDto);
        Member member = memberRepository.findMemberByNickname(boardRequestDto.getNickname()).orElseThrow(NoSuchElementException::new);;
        board.addMember(member);
        boardRepository.save(board);
        return board.getId();
    }

    public Board findById(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
        board.addRead();
        return board;
    }

    @Transactional
    public void deleteBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
        boardRepository.delete(board);
    }

    @Transactional
    public Board updateBoard(BoardUpdateRequestDto boardUpdateRequestDto){
        Board board = boardRepository.findById(boardUpdateRequestDto.getId()).orElseThrow(NoSuchElementException::new);
        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        return board;
    }
}
