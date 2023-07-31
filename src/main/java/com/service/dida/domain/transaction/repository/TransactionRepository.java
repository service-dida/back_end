package com.service.dida.domain.transaction.repository;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select t from Transaction t where t.buyerId = :memberId and (t.type = 'SWAP1' or t.type = 'SWAP2' or t.type = 'SEND_OUT_KLAY')")
    Page<Transaction> findAllSwapHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t FROM Transaction t WHERE (t.buyerId = :memberId OR t.sellerId = :memberId) and t.type = 'DEAL'")
    Page<Transaction> findAllDealingHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t FROM Transaction t WHERE t.buyerId = :memberId AND t.type = 'DEAL'")
    Page<Transaction> findPurchaseDealingHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t FROM Transaction t WHERE t.sellerId = :memberId AND t.type = 'DEAL'")
    Page<Transaction> findSoldDealingHistoryByMemberId(Long memberId, PageRequest pageRequest);

    @Query(value = "SELECT t.sellerId FROM Transaction t WHERE t.type='DEAL' " +
            "AND (t.sellerId) NOT IN (SELECT mh.hideMember.memberId FROM MemberHide mh WHERE mh.member=:member) " +
            "AND t.createdAt >:date GROUP BY t.sellerId ORDER BY COUNT(t.sellerId) DESC")
    Page<Long> getHotSellersWithoutHide(Member member, LocalDateTime date, PageRequest pageRequest);

    @Query(value = "SELECT t.sellerId FROM Transaction t WHERE t.type='DEAL' " +
            "AND t.createdAt >:date GROUP BY t.sellerId ORDER BY COUNT(t.sellerId) DESC LIMIT 4")
    Page<Long> getHotSellers(LocalDateTime date, PageRequest pageRequest);

    @Query(value = "SELECT t.buyerId FROM Transaction t WHERE t.type='MINTING' " +
            "AND t.createdAt >:date GROUP BY t.buyerId HAVING COUNT(t.buyerId) >= 10 ORDER BY COUNT(t.buyerId) DESC")
    Page<Long> getHotMembers(LocalDateTime date, PageRequest pageRequest);

    @Query(value = "SELECT t.buyerId FROM Transaction t WHERE t.type='MINTING' " +
            "AND (t.buyerId) NOT IN (SELECT mh.hideMember.memberId FROM MemberHide mh WHERE mh.member=:member) " +
            "AND t.createdAt >:date GROUP BY t.buyerId HAVING COUNT(t.buyerId) >= 10 ORDER BY COUNT(t.buyerId) DESC")
    Page<Long> getHotMembersWithoutHide(Member member, LocalDateTime date, PageRequest pageRequest);

    @Query(value = "SELECT t.nft FROM Transaction t WHERE t.type='DEAL' " +
            "AND (t.sellerId) NOT IN (SELECT mh.hideMember.memberId FROM MemberHide mh WHERE mh.member=:member) " +
            "AND (t.nft) NOT IN (SELECT nh.nft FROM NftHide nh WHERE nh.member=:member) " +
            "AND t.createdAt> :date ORDER BY t.payAmount DESC ")
    Page<Nft> getSoldOutWithoutHide(Member member, LocalDateTime date, PageRequest pageRequest);

    @Query(value = "SELECT t.nft FROM Transaction t WHERE t.type='DEAL' " +
            "AND t.createdAt> :date ORDER BY t.payAmount DESC ")
    Page<Nft> getSoldOut(LocalDateTime date, PageRequest pageRequest);
}
