package com.service.dida.domain.transaction.repository;

import com.service.dida.domain.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select t from Transaction t where t.buyerId = :memberId and (t.type = 'SWAP1' or t.type = 'SWAP2')")
    Page<Transaction> findAllSwapHistoryByMemberId(Long memberId, PageRequest pageRequest);
}
