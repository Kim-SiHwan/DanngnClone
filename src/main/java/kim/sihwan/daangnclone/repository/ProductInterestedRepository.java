package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.ProductInterested;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInterestedRepository extends JpaRepository<ProductInterested,Long> {
    List<ProductInterested> findByMemberId(Long memberId);



}
