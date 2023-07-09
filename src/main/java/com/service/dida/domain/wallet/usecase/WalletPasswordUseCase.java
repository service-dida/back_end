package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.member.entity.Member;

public interface WalletPasswordUseCase {

    void setTmpPassword(Member member);
}
