package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.SelectedArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedAreaRepository extends JpaRepository<SelectedArea,Long> {
    SelectedArea findByMemberId(Long memberId);
}
