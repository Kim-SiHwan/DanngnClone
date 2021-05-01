package kim.sihwan.daangnclone.dto.board;

import kim.sihwan.daangnclone.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardRequestDto {
    private String nickname;
    private String username;
    private String title;
    private String content;

    public Board toEntity(BoardRequestDto requestDto){
        return Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createDate(LocalDateTime.now())
                .build();
    }

}
