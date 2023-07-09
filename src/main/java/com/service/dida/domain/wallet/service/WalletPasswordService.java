package com.service.dida.domain.wallet.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.WalletPasswordUseCase;
import com.service.dida.global.util.usecase.BcryptUseCase;
import com.service.dida.global.util.usecase.MailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletPasswordService implements WalletPasswordUseCase {

    private final MailUseCase mailUseCase;
    private final WalletRepository walletRepository;
    private final BcryptUseCase bcryptUseCase;

    private void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public void setTmpPassword(Member member) {
        Wallet wallet = member.getWallet();
        wallet.changePayPwd(bcryptUseCase.encrypt(mailUseCase.sendPasswordMail(member.getEmail())));
        save(wallet);
    }
}
