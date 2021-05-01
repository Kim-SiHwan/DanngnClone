package kim.sihwan.daangnclone.dto.product;

import kim.sihwan.daangnclone.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductRequestDto {

    private String area;
    private String title;
    private String content;
    private String username;
    private String nickname;
    private List<MultipartFile> files;
    private Set<String> tags;

    public Product toEntity(ProductRequestDto requestDto){
        return Product.builder()
                .area(requestDto.getArea())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .build();
    }


}
