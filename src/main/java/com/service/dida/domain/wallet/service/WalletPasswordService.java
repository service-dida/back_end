package com.service.dida.domain.wallet.service;

import static com.service.dida.global.config.constants.ServerConstants.PRIVATE_KEY;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangePwd;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwdForNft;
import com.service.dida.domain.wallet.dto.WalletResponseDto.WrongCnt;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.WalletPasswordUseCase;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.util.usecase.BcryptUseCase;
import com.service.dida.global.util.usecase.RsaUseCase;
import jakarta.transaction.Transactional;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletPasswordService implements WalletPasswordUseCase {

    private final WalletRepository walletRepository;
    private final BcryptUseCase bcryptUseCase;
    private final RsaUseCase rsaUseCase;
    private final WalletUseCase walletUseCase;

    private void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public void changePassword(Member member, ChangePwd changePwd)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Wallet wallet = member.getWallet();
        wallet.changePayPwd(
            bcryptUseCase.encrypt(rsaUseCase.rsaDecode(changePwd.getChangePwd(), PRIVATE_KEY)));
        wallet.initWrongCnt();
        save(wallet);
    }

    @Override
    public WrongCnt checkPassword(Member member, CheckPwdForNft checkPwdForNft)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return walletUseCase.checkPayPwd(member.getWallet(), checkPwdForNft.getPayPwd());
    }
}
