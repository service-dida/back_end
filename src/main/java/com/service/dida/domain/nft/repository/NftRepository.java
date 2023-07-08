package com.service.dida.domain.nft.repository;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {

    @Query(value = "select n from Nft n LEFT JOIN FETCH n.market where n.nftId = :nftId and n.deleted = false")
    Optional<Nft> findByNftIdWithDeleted(Long nftId);

    @Query(value = "select n from Nft n LEFT JOIN FETCH n.market where n.nftId = :nftId and n.member = :member and n.deleted = false")
    Optional<Nft> findByNftIdWithDeletedAndMember(Member member, Long nftId);

    Integer countByMemberAndDeleted(Member member, boolean deleted);

    @Query(value = "SELECT n FROM Nft n WHERE n.member = :member AND n.deleted = false")
    Page<Nft> findAllNftsByMember(Member member, PageRequest pageRequest);

    @Query(value = "SELECT n.imgUrl FROM Nft n " +
            "WHERE n NOT IN (SELECT nh.nft FROM NftHide nh WHERE nh.member=:member) " +
            "AND n.member=:owner ORDER BY n.createdAt DESC LIMIT 1")
    Optional<String> getRecentNftImgUrlMinusHide(Member member, Member owner);

    @Query(value = "SELECT n.imgUrl FROM Nft n WHERE n.member=:owner ORDER BY n.createdAt DESC LIMIT 1")
    Optional<String> getRecentNftImgUrl(Member owner);

    @Query(value = "SELECT n FROM Nft n " +
            "WHERE (n) NOT IN (SELECT nh.nft FROM NftHide nh WHERE nh.member=:member) " +
            "AND (n.member) NOT IN (SELECT mh.hideMember FROM MemberHide mh WHERE mh.member=:member) " +
            "AND n.deleted=false ORDER BY n.createdAt DESC ")
    Page<Nft> getRecentNftsMinusHide(Member member, PageRequest pageRequest);

    @Query(value = "SELECT n FROM Nft n WHERE n.deleted=false ORDER BY n.createdAt DESC ")
    Page<Nft> getRecentNfts(PageRequest pageRequest);

    @Query(value = "SELECT COUNT(n) FROM Nft n WHERE n.deleted=false AND n.member=:member")
    Optional<Long> countByMemberWithDeleted(Member member);
}
