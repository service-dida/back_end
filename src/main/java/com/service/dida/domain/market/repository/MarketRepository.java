package com.service.dida.domain.market.repository;

import com.service.dida.domain.market.Market;
import com.service.dida.domain.nft.Nft;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarketRepository extends JpaRepository<Market, Long> {

    @Query(value = "SELECT m FROM Market m LEFT JOIN FETCH m.nft WHERE m.marketId = :marketId and m.deleted = false")
    Optional<Market> findMarketByMarketIdWithDeleted(Long marketId);

    Optional<Boolean> existsByNft(Nft nft);
}
