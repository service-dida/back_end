package com.service.dida.domain.nft.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftRequestDto.SendNftRequestDto;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.nft.usecase.UpdateNftUseCase;
import com.service.dida.domain.wallet.repository.WalletRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.util.usecase.KasUseCase;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateNftService implements UpdateNftUseCase {

    private final NftRepository nftRepository;
    private final WalletRepository walletRepository;
    private final KasUseCase kasUseCase;

    @Override
    public void deleteNft(Member member, Long nftId) {
        Nft nft = nftRepository.findByNftIdWithDeletedAndMember(member, nftId)
            .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        nft.changeDeleted(true);
    }

    @Override
    public void sendNftOutside(Member member, SendNftRequestDto sendNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        checkSendNftOutside(member, sendNftRequestDto);
        member.getWallet().useWallet();
        Nft nft = nftRepository.findByNftIdWithDeletedAndMember(member,
                sendNftRequestDto.getNftId())
            .orElseThrow(() -> new BaseException(NftErrorCode.EMPTY_NFT));
        kasUseCase.sendNftOutside(member.getWallet().getAddress(), sendNftRequestDto.getAddress(),
            nft.getId());
        nft.changeDeleted(true);
    }

    public void checkSendNftOutside(Member member, SendNftRequestDto sendNftRequestDto) {
        if (walletRepository.existsWalletByAddress(sendNftRequestDto.getAddress()).orElse(false)) {
            throw new BaseException(WalletErrorCode.IN_MEMBER_ADDRESS);
        }
        if (!sendNftRequestDto.getPayPwd().equals(member.getWallet().getPayPwd())) {
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
    }
}
