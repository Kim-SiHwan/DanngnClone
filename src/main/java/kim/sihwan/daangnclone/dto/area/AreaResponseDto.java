package kim.sihwan.daangnclone.dto.area;

import kim.sihwan.daangnclone.domain.Area;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AreaResponseDto {
    private Long id;
    private String address;
    private String dong;

    public static AreaResponseDto toDto (Area area){
        return AreaResponseDto.builder()
                .id(area.getId())
                .address(area.getAddress())
                .dong(area.getDong())
                .build();
    }
}
