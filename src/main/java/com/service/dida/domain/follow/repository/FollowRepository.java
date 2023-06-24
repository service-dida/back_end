package com.service.dida.domain.follow.repository;

import com.service.dida.domain.follow.Follow;
import com.service.dida.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // NFT 소유자(혹은 보고 프로필의 주인)과 본인과의 팔로우 관계 가져오기
    @Query(value = "SELECT f FROM Follow f WHERE f.followingId = :memberId and f.followerId = :ownerId")
    Optional<Follow> findByMemberWithOwner(Long memberId, Long ownerId);

    // 해당 멤버를 팔로우 하는 사람들의 수
    Integer countByFollowerIdAndStatus(Long memberId, boolean status);

    // 해당 멤버가 팔로잉 하는 사람들의 수
    Integer countByFollowingIdAndStatus(Long memberId, boolean status);
}
