package kim.sihwan.daangnclone.product;

import kim.sihwan.daangnclone.common.AreaInit;
import kim.sihwan.daangnclone.common.MemberFunction;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.Product;
import kim.sihwan.daangnclone.dto.member.LoginResponseDto;
import kim.sihwan.daangnclone.dto.member.MemberResponseDto;
import kim.sihwan.daangnclone.dto.product.ProductRequestDto;
import kim.sihwan.daangnclone.dto.product.ProductResponseDto;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.ProductRepository;
import kim.sihwan.daangnclone.service.MemberService;
import kim.sihwan.daangnclone.service.ProductAlbumService;
import kim.sihwan.daangnclone.service.ProductService;
import kim.sihwan.daangnclone.service.ProductTagService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ProductServiceTest {
    /*    @Autowired
        MemberFunction memberFunction;
        @Autowired
        AreaInit areaInit;
        @Autowired
        MemberService memberService;

        @Autowired
        ProductService productService;

        @Autowired
        ProductRepository productRepository;

        LoginResponseDto login;
        MemberResponseDto member;

        @Before
        public void beforeTest() {
            memberFunction.join("test", "testPw", "testNn");
            login = memberFunction.login("test", "testPw");
            member = memberService.findById(login.getId());
            areaInit.init();
        }

        public ProductRequestDto product(String area, String nickname, String title, String content, Set<String> tags) {
            return ProductRequestDto.builder()
                    .area(area)
                    .nickname(nickname)
                    .title(title)
                    .content(content)
                    .tags(tags)
                    .build();

        }
        */
    @Mock
    Member member;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ProductAlbumService productAlbumService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductTagService tagService;

    @Mock
    ProductRequestDto productRequestDto;

    @InjectMocks
    ProductService productService;


    private Product product = Product.builder()
            .username("test")
            .nickname("testNn")
            .area("만수3동")
            .content("팝니다")
            .title("파라요")
            .build();


    @Test
    public void 상품등록_테스트() {
        //memberrepo로 멤버 만들고 수행..?
        //given
        member = Member.builder()
                .username("test")
                .password("testPw")
                .nickname("testNn")
                .area("만수3동")
                .role("ROLE_USER")
                .build();

        given(productAlbumService.addProductAlbums(productRequestDto)).willReturn(product);
        given(memberRepository.findMemberByNickname(null)).willReturn(Optional.of(member));
        //when


        productService.addProduct(productRequestDto);
        //then
        verify(memberRepository, times(1)).findMemberByNickname(null);
        verify(productRepository, times(1)).save(product);
        verify(tagService, times(1)).addProductTag(eq(product),anySet());


    }

    @Test
    public void 단일상품_조회_테스트 (){

        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
        //when
        Product getProduct = productService.findById(anyLong());
        //then
        verify(productRepository,times(1)).findById(anyLong());
        assertEquals(product.getNickname(),getProduct.getNickname());


    }

    @Test(expected = NoSuchElementException.class)
    public void 없는상품_조회_테스트(){
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        Product getProduct = productService.findById(anyLong());
        //then

    }

    @Test
    public void 상품_조회수_테스트 (){
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        //when
        Product getProduct = productService.findById(anyLong());
        //then
        assertEquals(1,getProduct.getRead());
    }

    @Test
    public void 전체상품_조회_테스트 (){
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product);

        //given
        given(productRepository.findAll()).willReturn(productList);
        //when
        List<Product> result = productService.findAll();
        //then
        verify(productRepository,times(1)).findAll();
        assertEquals(result.size(), 2);
    }

/*
    @Test
    public void 상품등록_테스트() {
        //given
        Set<String> tags = new HashSet<>();
        tags.add("태그1");
        tags.add("태그2");
        ProductRequestDto productRequestDto = product(member.getArea(), member.getNickname(), "sampleTitle", "sampleContent", tags);


        //when
        Long productId = productService.addProduct(productRequestDto);
        ProductResponseDto productResponseDto = productService.findById(productId);
        //then
        assertEquals("만수3동",productResponseDto.getArea());
        assertEquals("testNn",productResponseDto.getNickname());
        assertTrue(productResponseDto.getTags().size()==2);

    }
*/


}
