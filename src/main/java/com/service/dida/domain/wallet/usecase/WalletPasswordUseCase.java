package com.service.dida.domain.wallet.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.wallet.dto.WalletRequestDto.ChangePwd;
import com.service.dida.domain.wallet.dto.WalletRequestDto.CheckPwdForNft;
import com.service.dida.domain.wallet.dto.WalletResponseDto.WrongCnt;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface WalletPasswordUseCase {

    void changePassword(Member member, ChangePwd changePwd)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;

    WrongCnt checkPassword(Member member, CheckPwdForNft checkPwdForNft)
        throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException;
}
