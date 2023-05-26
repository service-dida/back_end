package com.service.dida.domain.nft.repository;

import com.service.dida.domain.nft.Nft;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {

    @Query(value = "select n from Nft n where n.nftId = :nftId and n.deleted = false")
    Optional<Nft> findByNftIdWithDeleted(Long nftId);

    Optional<Nft> findByNftId(Long nftId);
}
