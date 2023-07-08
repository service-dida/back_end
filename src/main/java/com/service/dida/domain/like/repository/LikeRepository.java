package com.service.dida.domain.like.repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import com.service.dida.domain.like.Like;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "SELECT l.nft FROM Like l " +
            "WHERE (l.nft) NOT IN (SELECT nh.nft FROM NftHide nh WHERE nh.member=:member) " +
            "AND (l.nft.member) NOT IN (SELECT mh.hideMember FROM MemberHide mh WHERE mh.member=:member) " +
            "AND l.status = true GROUP BY l.nft ORDER BY COUNT(l.nft) DESC LIMIT 6")
    Optional<List<Nft>> getHotItems(Member member);

    @Query(value = "SELECT COUNT(l) FROM Like l WHERE l.nft = :nft AND l.status = true")
    Optional<Long> getLikeCountsByNftId(Nft nft);

    Optional<Like> findByMemberAndNft(Member member, Nft nft);

    void deleteByMemberAndNft(Member member, Nft nft);

}
