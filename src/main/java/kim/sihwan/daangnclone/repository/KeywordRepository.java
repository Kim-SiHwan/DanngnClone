package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {

    List<Keyword> findAllByMemberUsername(String username);

    List<Keyword> findAllByKeyword(String keyword);
}
