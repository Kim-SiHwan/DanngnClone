package kim.sihwan.daangnclone.dto.product.productAlbum;

import kim.sihwan.daangnclone.domain.ProductAlbum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAlbumRequestDto {
    private Long productId;
    private String url;
    private String filename;

    public ProductAlbum toEntity(ProductAlbumRequestDto requestDto){
        return ProductAlbum.builder()
                .url(requestDto.getUrl())
                .filename(requestDto.getFilename())
                .build();
    }
}
