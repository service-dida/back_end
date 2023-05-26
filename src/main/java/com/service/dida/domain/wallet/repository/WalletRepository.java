package com.service.dida.domain.wallet.repository;

import com.service.dida.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
