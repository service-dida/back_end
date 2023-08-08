package com.service.dida.domain.nft.service;

import static com.service.dida.global.config.constants.ServerConstants.MINTING_FEE;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftRequestDto.PostNftRequestDto;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.nft.usecase.RegisterNftUseCase;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.MintingTransactionDto;
import com.service.dida.domain.transaction.dto.TransactionRequestDto.TransactionSetDto;
import com.service.dida.domain.transaction.usecase.RegisterTransactionUseCase;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.common.manage.ManageUseCase;
import com.service.dida.global.config.properties.KasProperties;
import com.service.dida.global.util.usecase.KasUseCase;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
    private final RegisterTransactionUseCase registerTransactionUseCase;
    private final WalletUseCase walletUseCase;

    public void save(Nft nft) {
        nftRepository.save(nft);
    }

    public Nft register(PostNftRequestDto postNftRequestDto, String id, String contracts,
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
        return nft;
    }

    @Override
    public void registerNft(Member member, PostNftRequestDto postNftRequestDto)
        throws IOException, ParseException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Wallet wallet = member.getWallet();
        walletUseCase.checkPayPwd(wallet,postNftRequestDto.getPayPwd());
        walletUseCase.useWallet(wallet);

        // 사용료 납부 부분 없음
        String sendFee = "";
        if (MINTING_FEE != 0D) {
            sendFee = kasUseCase.sendDidaToFeeAccount(wallet, MINTING_FEE);
        }
        String uri = kasUseCase.uploadMetadata(postNftRequestDto);
        String id = Long.toHexString(manageUseCase.getNftIdAndPlusOne());
        String transactionHash = kasUseCase.createNft(wallet.getAddress(), id, uri);
        Nft nft = register(postNftRequestDto, id, kasProperties.getNftContractAddress(),
            transactionHash, false, member);
        registerTransactionUseCase.saveMintingTransaction(
            new MintingTransactionDto(member.getMemberId(), nft,
                new TransactionSetDto("", transactionHash, sendFee)));
    }
}
