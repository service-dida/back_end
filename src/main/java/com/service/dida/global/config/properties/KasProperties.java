package com.service.dida.global.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class KasProperties {
    @Value("${kas.authorization}")
    private String authorization;
    @Value("${kas.version}")
    private String version;
    @Value("${kas.nft-contract}")
    private String nftContract;
    @Value("${kas.nft-contract-address}")
    private String nftContractAddress;
    @Value("${kas.ft-contract}")
    private String ftContract;
    @Value("${kas.fee-account}")
    private String feeAccount;                  // 수수료를 받아놓는 계좌
    @Value("${kas.fee-payer-account}")
    private String feePayerAccount;             // KAS에 수수료를 대납해주는 계좌
    @Value("${kas.liquid-pool-account}")
    private String liquidPoolAccount;           // 유동성 풀 계좌
}
