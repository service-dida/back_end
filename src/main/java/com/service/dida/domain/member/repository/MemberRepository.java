package com.service.dida.domain.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.service.dida.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select m from Member m where m.memberId = :memberId and m.deleted = false")
    Optional<Member> findByMemberIdWithDeleted(Long memberId);

    Optional<Member> findByEmail(String email);

    Optional<Boolean> existsByEmail(String email);

    Optional<Boolean> existsByNickname(String nickname);
}
