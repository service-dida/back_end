package com.service.dida.domain.nft.repository;

import com.service.dida.domain.nft.Nft;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {

    Optional<Nft> findByNftId(Long nftId);
}
