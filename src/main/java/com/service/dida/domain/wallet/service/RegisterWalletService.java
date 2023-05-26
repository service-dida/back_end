package com.service.dida.domain.wallet.service;

import com.service.dida.domain.member.Role;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.RegisterWalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.util.KasService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterWalletService implements RegisterWalletUseCase {

    private final MemberRepository memberRepository;
    private final WalletRepository walletRepository;
    private final KasService kasService;

    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    public void register(Member member, String payPwd)
        throws IOException, ParseException, InterruptedException {
        String address = kasService.createAccount();
        Wallet wallet = Wallet.builder()
            .address(address)
            .payPwd(payPwd)
            .wrongCnt(0)
            .member(member)
            .build();
        save(wallet);
        member.changeRole(Role.ROLE_USER);
    }

    @Override
    public void registerWallet(Long memberId, WalletRequestDto walletRequestDto)
        throws IOException, ParseException, InterruptedException {
        Member member = memberRepository.findByMemberId(memberId).orElse(null);
        if (member == null || member.isDeleted()) {
            throw new BaseException(MemberErrorCode.EMPTY_MEMBER);
        }
        if (checkPassword(walletRequestDto)) {
            register(member, walletRequestDto.getPayPwd());
        } else {
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
    }

    public boolean checkPassword(WalletRequestDto walletRequestDto) {
        return walletRequestDto.getPayPwd().equals(walletRequestDto.getCheckPwd());
    }
}
