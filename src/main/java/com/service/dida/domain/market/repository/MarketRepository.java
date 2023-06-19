package com.service.dida.domain.market.repository;

import com.service.dida.domain.market.Market;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarketRepository extends JpaRepository<Market, Long> {

    @Query(value = "SELECT m FROM Market m JOIN FETCH m.nft WHERE m.marketId = :marketId")
    Optional<Market> findMarketByMarketId(Long marketId);
}
