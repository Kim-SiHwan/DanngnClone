package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.Qna;
import kim.sihwan.daangnclone.dto.qna.QnaRequestDto;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {
    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addQna(QnaRequestDto qnaRequestDto){
        Qna qna = qnaRequestDto.toEntity(qnaRequestDto);
        Member member = memberRepository.findById(qnaRequestDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        qna.addMember(member);
        qnaRepository.save(qna);
        return qna.getId();
    }

    public Qna findById(Long qnaId){
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(NoSuchElementException::new);
        qna.visitedQna();
        return qna;
    }
}
