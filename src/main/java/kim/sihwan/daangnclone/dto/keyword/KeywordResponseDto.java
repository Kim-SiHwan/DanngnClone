package kim.sihwan.daangnclone.dto.keyword;

import kim.sihwan.daangnclone.domain.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class KeywordResponseDto {
    private Long id;
    private String keyword;

    public static KeywordResponseDto toDto(Keyword keyword){
        return KeywordResponseDto.builder()
                .id(keyword.getId())
                .keyword(keyword.getKeyword())
                .build();
    }
}
