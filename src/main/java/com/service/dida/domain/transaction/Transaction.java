package com.service.dida.domain.transaction;

import com.service.dida.domain.transaction.dto.TransactionRequestDto.TransactionSetDto;
import com.service.dida.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction extends BaseEntity {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;
    @Column(name = "seller_id")
    private Long sellerId;
    @Column(name = "pay_amount", nullable = false)
    private Double payAmount;
    @Column(name = "pay_back_amount")
    private Double payBackAmount;
    @Column(name = "nft_id")
    private Long nftId;
    @Column(name = "pay_transaction")
    private String payTransaction;
    @Column(name = "pay_back_transaction")
    private String payBackTransaction;
    @Column(name = "ree_transaction")
    private String feeTransaction;

    public void setTransactionSet(TransactionSetDto transactionSet) {
        this.payTransaction = transactionSet.getPayTransaction();
        this.payBackTransaction = transactionSet.getPayBackTransaction();
        this.feeTransaction = transactionSet.getFeeTransaction();
    }
}