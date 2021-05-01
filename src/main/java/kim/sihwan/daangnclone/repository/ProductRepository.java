package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @EntityGraph(attributePaths = {"member","productTags","productTags.tag","productAlbums","productInteresteds"})
    List<Product> findAllByArea(String area);

}
