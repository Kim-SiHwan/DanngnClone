package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.Product;
import kim.sihwan.daangnclone.domain.ProductTag;
import kim.sihwan.daangnclone.domain.SelectedArea;
import kim.sihwan.daangnclone.dto.product.ProductInterestedRequestDto;
import kim.sihwan.daangnclone.dto.product.ProductListResponseDto;
import kim.sihwan.daangnclone.dto.product.ProductRequestDto;
import kim.sihwan.daangnclone.dto.product.ProductResponseDto;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.ProductRepository;
import kim.sihwan.daangnclone.repository.SelectedAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductAlbumService productAlbumService;
    private final MemberRepository memberRepository;
    private final ProductTagService tagService;
    private final SelectedAreaRepository selectedAreaRepository;
    private final RedisTemplate redisTemplate;


    @Transactional
    public Long addProduct(ProductRequestDto productRequestDto){
        Product product = productAlbumService.addProductAlbums(productRequestDto);
        Member member = memberRepository.findMemberByNickname(productRequestDto.getNickname()).orElseThrow(NoSuchElementException::new);
        product.addMember(member);
        productRepository.save(product);
        tagService.addProductTag(product,productRequestDto.getTags());
        return product.getId();
    }

    public Product findById(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        addRead(productId);
        return product;
    }

    public String tt(Long memberId, Long productId){
        SetOperations<String,Long> setOperations = redisTemplate.opsForSet();
        String msg= "존재하지 않는 값이라 추가했음";
        if(checkSet(productId,memberId)){
            setOperations.remove("test::interested::product::"+productId,memberId);
            msg="이미 존재하는 값이라 삭제했음";
            return msg;
        }
        setOperations.add("test::interested::product::"+productId,memberId);
        return msg;
    }

    public boolean checkSet(Long productId, Long memberId){
        SetOperations<String,Long> setOperations = redisTemplate.opsForSet();

        return setOperations.isMember("test::interested::product::"+productId,memberId);
    }



    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<ProductListResponseDto> findAllProductsByTagId(Long tagId){
        System.out.println("서비스왔어");
        Member member = memberRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        System.out.println(member.getUsername());
        SelectedArea selectedArea = selectedAreaRepository.findByMemberId(member.getId());
        System.out.println(selectedArea.getArea().getAddress());
        System.out.println(selectedArea.getArea().getDong());
        ValueOperations<String,List<String>> vo = redisTemplate.opsForValue();
        System.out.println(vo.get("a"));
        List<ProductListResponseDto> result = new ArrayList<>();
        List<String> al = new ArrayList<>();


        al= vo.get(selectedArea.getArea().getDong()+"::List");
        SetOperations<String,Long> setOperations = redisTemplate.opsForSet();

        //태그 나중에

/*        al.forEach(dong->{
            result.addAll(productRepository.findAllByArea(dong)
                    .stream()
                    .map(ProductListResponseDto::toDto)
                    .sorted(Comparator.comparing(ProductListResponseDto::getId, Comparator.reverseOrder()))
                    .collect(Collectors.toList()));
        });*/

        al.forEach(dong->{
            result.addAll(productRepository.findAllByArea(dong)
                    .stream()
                    .map(m-> {
                        if(setOperations.isMember("test::interested::product::"+m.getId(),1L)){
                            return ProductListResponseDto.toDto(m,true); //true
                        }
                        return ProductListResponseDto.toDto(m,false); //false

                    })
                    .sorted(Comparator.comparing(ProductListResponseDto::getId, Comparator.reverseOrder()))
                    .collect(Collectors.toList()));
        });
        if(tagId == 0){



/*            List<String> finalAl = al;
            return productRepository.findAll()
                    .stream()
                    .map(ProductListResponseDto::toDto)
                    .sorted(Comparator.comparing(ProductListResponseDto::getId,Comparator.reverseOrder()))
                    .collect(Collectors.toList());
*/
            return result;
        }


        al.forEach(dong->{
            result.addAll(tagService.getProductIdsByTagId(tagId)
                    .stream()
                    .map(productTag -> ProductListResponseDto.toDto(productTag.getProduct(),true))
                    .sorted(Comparator.comparing(ProductListResponseDto::getId,Comparator.reverseOrder()))
                    .collect(Collectors.toList()));
        });

        return result;

/*        return tagService.getProductIdsByTagId(tagId)
                .stream()
                .map(productTag -> ProductListResponseDto.toDto(productTag.getProduct()))
                .sorted(Comparator.comparing(ProductListResponseDto::getId,Comparator.reverseOrder()))
                .collect(Collectors.toList());*/

    }



    @Transactional
    public void addRead(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        product.addRead();
    }

}
