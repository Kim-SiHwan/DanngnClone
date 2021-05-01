package kim.sihwan.daangnclone.dto.tag;

import kim.sihwan.daangnclone.domain.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponseDto {
    private Long id;
    private String tag;

    public TagResponseDto(Tag tag){
        this.id = tag.getId();
        this.tag = tag.getTag();
    }
}
