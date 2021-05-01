package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
