package com.service.dida.domain.follow.repository;

import com.service.dida.domain.follow.Follow;
import com.service.dida.domain.member.entity.Member;

import java.util.Optional;

import com.service.dida.domain.nft.Nft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // NFT 소유자(혹은 보고 프로필의 주인)과 본인과의 팔로우 관계 가져오기
    @Query(value = "SELECT f FROM Follow f WHERE f.followingMember = :member and f.followerMember = :owner")
    Optional<Follow> findByMemberWithOwner(Member member, Member owner);

    // 해당 멤버를 팔로우 하는 사람들의 수
    Integer countByFollowerMemberAndStatus(Member member, boolean status);

    // 해당 멤버가 팔로잉 하는 사람들의 수
    Integer countByFollowingMemberAndStatus(Member member, boolean status);

    @Query(value = "SELECT f FROM Follow f WHERE f.followerMember = :member AND f.status = TRUE")
    Page<Follow> findAllFollowerByMember(Member member, PageRequest pageRequest);

    @Query(value = "SELECT f FROM Follow f WHERE f.followingMember = :member AND f.status = TRUE")
    Page<Follow> findAllFollowingByMember(Member member, PageRequest pageRequest);

    void deleteByFollowingMemberAndFollowerMember(Member member, Member owner);
}
