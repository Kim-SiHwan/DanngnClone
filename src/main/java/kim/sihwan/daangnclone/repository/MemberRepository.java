package kim.sihwan.daangnclone.repository;

import kim.sihwan.daangnclone.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findMemberByUsername(String username);


    Optional<Member> findMemberByNickname(String nickname);

}
