package com.service.dida.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.service.dida.domain.user.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByEmail(String email);

    Optional<Boolean> existsByEmail(String email);

    Optional<Boolean> existsByNickname(String nickname);
}
