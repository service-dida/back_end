package com.service.dida.domain.wallet.service;

import com.service.dida.domain.member.Role;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.dto.WalletRequestDto;
import com.service.dida.domain.wallet.dto.WalletResponseDto;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
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
public class WalletService implements WalletUseCase {

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
        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));
        if (checkPassword(walletRequestDto)) {
            register(member, walletRequestDto.getPayPwd());
        } else {
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
    }

    @Override
    public WalletResponseDto isExistWallet(Long memberId) {
        return new WalletResponseDto(
            walletRepository.existsWalletByMember(memberRepository.findByMemberId(memberId).get())
                .orElse(false));
    }

    public boolean checkPassword(WalletRequestDto walletRequestDto) {
        return walletRequestDto.getPayPwd().equals(walletRequestDto.getCheckPwd());
    }
}
