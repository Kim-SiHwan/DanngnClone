package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.Product;
import kim.sihwan.daangnclone.domain.ProductInterested;
import kim.sihwan.daangnclone.dto.intrerested.InterestedRequestDto;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.ProductInterestedRepository;
import kim.sihwan.daangnclone.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductInterestedService {
    private final ProductInterestedRepository productInterestedRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long addInterested(InterestedRequestDto interestedRequestDto){
        Member member = memberRepository.findById(interestedRequestDto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Product product = productRepository.findById(interestedRequestDto.getProductId()).orElseThrow(NoSuchElementException::new);
        ProductInterested productInterested = new ProductInterested();
        productInterested.addMember(member);
        productInterested.addProduct(product);
        productInterestedRepository.save(productInterested);
        return productInterested.getId();

    }




}
