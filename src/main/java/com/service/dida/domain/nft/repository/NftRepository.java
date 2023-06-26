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
}
