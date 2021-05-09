package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.ProductInterested;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestedRepository extends JpaRepository<ProductInterested,Long> {
    ProductInterested findByMemberIdAndProductId(Long memberId, Long productId);
    ProductInterested findByMemberUsernameAndProductId(String username, Long productId);
}
