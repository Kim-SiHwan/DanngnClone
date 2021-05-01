package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Product;
import kim.sihwan.daangnclone.domain.ProductTag;
import kim.sihwan.daangnclone.domain.Tag;
import kim.sihwan.daangnclone.repository.ProductTagRepository;
import kim.sihwan.daangnclone.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductTagService {
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;

    public List<ProductTag> getProductIdsByTagId(Long tagId){
        return productTagRepository.findAllByTagId(tagId);
    }

    @Transactional
    public void addProductTag(Product product, Set<String> tags){
        System.out.println("태그 : "+tags);
        tags.forEach(t ->{
            ProductTag productTag = new ProductTag();
            Tag tag = saveTag(t);
            System.out.println(tag.getTag());
            productTag.addProduct(product);
            productTag.addTag(tag);
        });
    }

    @Transactional
    public Tag saveTag(String tag){
        Tag getTag = tagRepository.findTagByTag(tag)
                .orElse(Tag.builder().tag(tag).build());
        return tagRepository.save(getTag);
    }
}
