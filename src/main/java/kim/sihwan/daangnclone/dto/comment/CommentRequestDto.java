package kim.sihwan.daangnclone.dto.comment;

import kim.sihwan.daangnclone.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDto {
    private Long boardId;
    private String username;
    private String content;

    public Comment toEntity(CommentRequestDto requestDto){
        return Comment.builder()
                .username(requestDto.getUsername())
                .content(requestDto.getContent())
                .build();
    }
}
