package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findTagByTag(String tag);
}
