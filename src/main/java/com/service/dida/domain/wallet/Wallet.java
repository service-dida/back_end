package com.service.dida.domain.wallet;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.BaseEntity;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet")
public class Wallet extends BaseEntity {
    @Id
    @Column(name = "wallet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "pay_pwd", nullable = false)
    private String payPwd;

    @Column(name = "wrong_cnt", nullable = false)
    private int wrongCnt;

    @OneToOne(mappedBy = "wallet")
    private Member member;

    public void useWallet() {
        if (Duration.between(this.getUpdatedAt(), LocalDateTime.now()).getSeconds() < 180) {
            throw new BaseException(WalletErrorCode.FAILED_USE_WALLET);
        } else {
            super.updateEntity();
        }
    }
}
