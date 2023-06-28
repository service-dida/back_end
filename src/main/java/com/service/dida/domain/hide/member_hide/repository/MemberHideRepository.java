package com.service.dida.domain.hide.member_hide.repository;

import com.service.dida.domain.hide.member_hide.MemberHide;
import com.service.dida.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberHideRepository extends JpaRepository<MemberHide, Long> {

    Optional<MemberHide> findByMemberAndHideMember(Member member, Member hideMember);
}
