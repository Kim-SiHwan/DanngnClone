package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    List<Follow> findAllByFromMemberId(Long id);
    List<Follow> findAllByToMemberId(Long id);
    void deleteByFromMemberIdAndToMemberId(Long fromId, Long toId);
}
