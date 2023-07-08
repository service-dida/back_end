package com.service.dida.domain.nft.repository;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {

    @Query(value = "select n from Nft n LEFT JOIN FETCH n.market where n.nftId = :nftId and n.deleted = false")
    Optional<Nft> findByNftIdWithDeleted(Long nftId);

    @Query(value = "select n from Nft n LEFT JOIN FETCH n.market where n.nftId = :nftId and n.member = :member and n.deleted = false")
    Optional<Nft> findByNftIdWithDeletedAndMember(Member member, Long nftId);

    @Query(value = "SELECT n.imgUrl FROM Nft n " +
            "WHERE n NOT IN (SELECT nh.nft FROM NftHide nh WHERE nh.member=:member)" +
            "AND n.member=:member ORDER BY n.createdAt DESC LIMIT 1")
    Optional<String> findRecentNftImgUrlMinusHide(Member member);
}
