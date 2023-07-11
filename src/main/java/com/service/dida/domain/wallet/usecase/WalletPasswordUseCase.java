package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangePwd;

public interface WalletPasswordUseCase {

    void setTmpPassword(Member member);

    void changePassword(Member member, ChangePwd changePwd);
}
