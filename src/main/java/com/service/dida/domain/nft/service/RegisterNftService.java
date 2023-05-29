package com.service.dida.domain.nft.service;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.nft.usecase.RegisterNftUseCase;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.TransactionSetDto;
import com.service.dida.domain.transaction.usecase.TransactionUseCase;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.global.common.manage.ManageUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.config.properties.KasProperties;
import com.service.dida.global.util.usecase.KasUseCase;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterNftService implements RegisterNftUseCase {

    private final NftRepository nftRepository;
    private final KasUseCase kasUseCase;
    private final ManageUseCase manageUseCase;
    private final KasProperties kasProperties;
    private final TransactionUseCase transactionUseCase;

    public void save(Nft nft) {
        nftRepository.save(nft);
    }

    public Long register(PostNftRequestDto postNftRequestDto, String id, String contracts,
        String txHash, boolean isAi, Member member) {
        Nft nft = Nft.builder()
            .member(member)
            .id(id)
            .title(postNftRequestDto.getTitle())
            .description(postNftRequestDto.getDescription())
            .contracts(contracts)
            .txHash(txHash)
            .imgUrl(postNftRequestDto.getImage())
            .build();
        save(nft);
        return nft.getNftId();
    }

    @Override
    public void registerNft(Member member, PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException {
        Wallet wallet = member.getWallet();
        if (!wallet.getPayPwd().equals(postNftRequestDto.getPayPwd())) {
            throw new BaseException(WalletErrorCode.WRONG_PWD);
        }
        wallet.useWallet();

        // 사용료 납부 부분 없음

        String uri = kasUseCase.uploadMetadata(postNftRequestDto);
        String id = Long.toHexString(manageUseCase.getNftIdAndPlusOne());
        String transactionHash = kasUseCase.createNft(wallet.getAddress(), id, uri);
        Long nftId = register(postNftRequestDto, id, kasProperties.getNftContractAddress(),
            transactionHash, false, member);
        transactionUseCase.saveMintingTransaction(
            new MintingTransactionDto(member.getMemberId(), nftId,
                new TransactionSetDto("", transactionHash, null))); // 사용료 정해져야함
    }
}
