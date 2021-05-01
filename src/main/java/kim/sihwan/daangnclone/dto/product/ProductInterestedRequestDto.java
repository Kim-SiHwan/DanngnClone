package kim.sihwan.daangnclone.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInterestedRequestDto {
    private Long memberId;
    private Long productId;
}
