package kim.sihwan.daangnclone.dto.qna;

import kim.sihwan.daangnclone.domain.Qna;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QnaRequestDto {
    private String title;
    private String content;
    private Long memberId;

    public Qna toEntity(QnaRequestDto requestDto){
        return Qna.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .visited(false)
                .answer(false)
                .build();

    }
}
