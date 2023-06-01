package com.service.dida.domain.wallet;

import com.service.dida.global.common.BaseEntity;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    public void checkPayPwd(String pwd) {
        if (this.wrongCnt == 5) {
            throw new BaseException(WalletErrorCode.FIVE_ERRORS_FOR_PWD);
        }
        if (this.getPayPwd().equals(pwd)) {
            this.wrongCnt = 0;
        } else {
            this.wrongCnt += 1;
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
    }
}
