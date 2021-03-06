package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.*;
import kim.sihwan.daangnclone.dto.product.ProductInterestedRequestDto;
import kim.sihwan.daangnclone.dto.product.ProductListResponseDto;
import kim.sihwan.daangnclone.dto.product.ProductRequestDto;
import kim.sihwan.daangnclone.dto.product.ProductResponseDto;
import kim.sihwan.daangnclone.repository.InterestedRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.ProductRepository;
import kim.sihwan.daangnclone.repository.SelectedAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

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
    private final ProductInterestedService interestedService;
    private final RedisTemplate<String, String> redisTemplate;

    //달린 태그를 통해서 카프카에 보낼 멤버 찾기.
    @Transactional
    public Long addProduct(ProductRequestDto productRequestDto) {
        Product product = productAlbumService.addProductAlbums(productRequestDto);
        Member member = memberRepository.findMemberByNickname(productRequestDto.getNickname()).orElseThrow(NoSuchElementException::new);
        product.addMember(member);
        productRepository.save(product);
        tagService.addProductTag(product, productRequestDto.getTags());
        return product.getId();
    }

    public Product findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        addRead(productId);
        return product;
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<ProductListResponseDto> findAllProductsByTagId(Long tagId) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        System.out.println("서비스왔어");
        Member member = memberRepository.findMemberByUsername(username);
        System.out.println(member.getUsername());
        SelectedArea selectedArea = selectedAreaRepository.findByMemberId(member.getId());
        System.out.println(selectedArea.getArea().getAddress());
        System.out.println(selectedArea.getArea().getDong());
        ListOperations<String, String> vo = redisTemplate.opsForList();
        System.out.println(vo.range(selectedArea.getArea().getAddress() + "::List", 0L, -1L));


        Long memberId = member.getId();
        List<ProductListResponseDto> tempResult = new ArrayList<>();
        List<String> al = vo.range(selectedArea.getArea().getAddress() + "::List", 0L, -1L);

        System.out.println(al);
        //태그 나중에

/*        al.forEach(dong->{
            result.addAll(productRepository.findAllByArea(dong)
                    .stream()
                    .map(ProductListResponseDto::toDto)
                    .sorted(Comparator.comparing(ProductListResponseDto::getId, Comparator.reverseOrder()))
                    .collect(Collectors.toList()));
        });*/

        List<ProductInterested> interestedList = interestedService.gg();


        assert al != null;
        al.forEach(dong -> {
            tempResult.addAll(
                    productRepository.findAllByArea(dong)
                            .stream()
                            .map(m -> {
                                if (isInterested(interestedList, memberId, m.getId())) {
                                    return ProductListResponseDto.toDto(m, true);
                                }
                                return ProductListResponseDto.toDto(m, false); //false

                            })
                            .sorted(Comparator.comparing(ProductListResponseDto::getId, Comparator.reverseOrder()))
                            .collect(Collectors.toList()));

        });
        List<ProductListResponseDto> result = tempResult
                .stream()
                .sorted(Comparator.comparing(ProductListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        System.out.println("결과:" + result.size());

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        if (tagId == 0) {



/*            List<String> finalAl = al;
            return productRepository.findAll()
                    .stream()
                    .map(ProductListResponseDto::toDto)
                    .sorted(Comparator.comparing(ProductListResponseDto::getId,Comparator.reverseOrder()))
                    .collect(Collectors.toList());
*/
            return result;
        }


        al.forEach(dong -> {
            result.addAll(tagService.getProductIdsByTagId(tagId)
                    .stream()
                    .map(productTag -> ProductListResponseDto.toDto(productTag.getProduct(), true))
                    .sorted(Comparator.comparing(ProductListResponseDto::getId, Comparator.reverseOrder()))
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
    public void addRead(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        product.addRead();
    }

    public boolean isInterested(List<ProductInterested> interestedList, Long memberId, Long productId) {
        boolean flag = false;
        for (ProductInterested interested : interestedList) {
            if (interested.getMember().getId().equals(memberId) && interested.getProduct().getId().equals(productId)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
