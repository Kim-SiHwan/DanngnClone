package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area,Long> {

    List<Area> findAllByCityLike(String city);

    List<Area> findAllByDongLike(String dong);

    Area findByAddress(String address);

}
