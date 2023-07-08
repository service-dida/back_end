package com.service.dida.domain.transaction.repository;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select t from Transaction t where t.buyerId = :memberId and (t.type = 'SWAP1' or t.type = 'SWAP2' or t.type = 'SEND_OUT_KLAY')")
    Page<Transaction> findAllSwapHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t.sellerId FROM Transaction t WHERE t.type='DEAL' " +
            "AND (t.sellerId) NOT IN (SELECT mh.hideMember.memberId FROM MemberHide mh WHERE mh.member=:member) " +
            "AND t.createdAt >:date GROUP BY t.sellerId ORDER BY COUNT(t.sellerId) DESC LIMIT 4")
    Optional<List<Long>> getHotSellersMinusHide(Member member, LocalDateTime date);

    @Query(value = "SELECT t.buyerId FROM Transaction t WHERE t.type='MINTING' " +
            "AND t.createdAt >:date AND COUNT(t.buyerId) >= 10 GROUP BY t.buyerId ORDER BY COUNT(t.buyerId) DESC LIMIT 3")
    Optional<List<Long>> getHotMembers(LocalDateTime date);

    @Query(value = "SELECT t.buyerId FROM Transaction t WHERE t.type='MINTING' " +
            "AND (t.buyerId) NOT IN (SELECT mh.hideMember.memberId FROM MemberHide mh WHERE mh.member=:member) " +
            "AND t.createdAt >:date AND COUNT(t.buyerId) >= 10 GROUP BY t.buyerId ORDER BY COUNT(t.buyerId) DESC LIMIT 3")
    Optional<List<Long>> getHotMembersMinusHide(Member member, LocalDateTime date);
}
