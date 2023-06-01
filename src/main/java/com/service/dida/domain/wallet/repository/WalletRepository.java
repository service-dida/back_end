package com.service.dida.domain.wallet.repository;

import com.service.dida.domain.wallet.Wallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Boolean> existsWalletByAddress(String address);
}
