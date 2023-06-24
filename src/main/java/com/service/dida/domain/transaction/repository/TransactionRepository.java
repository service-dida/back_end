package com.service.dida.domain.transaction.repository;

import com.service.dida.domain.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select t from Transaction t where t.buyerId = :memberId and (t.type = 'SWAP1' or t.type = 'SWAP2' or t.type = 'SEND_OUT_KLAY')")
    Page<Transaction> findAllSwapHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t FROM Transaction t WHERE (t.buyerId = :memberId OR t.sellerId = :memberId) and t.type = 'DEAL'")
    Page<Transaction> findAllDealingHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t FROM Transaction t WHERE t.buyerId = :memberId AND t.type = 'DEAL'")
    Page<Transaction> findPurchaseDealingHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t FROM Transaction t WHERE t.sellerId = :memberId AND t.type = 'DEAL'")
    Page<Transaction> findSoldDealingHistoryByMemberId(Long memberId, PageRequest pageRequest);
}
