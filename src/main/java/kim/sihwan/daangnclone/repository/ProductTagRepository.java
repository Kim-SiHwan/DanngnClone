package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag,Long> {

    List<ProductTag> findAllByTagId(Long id);
}
