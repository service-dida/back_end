package com.service.dida.domain.transaction;

public enum TransactionType {
    MINTING("NFT 발행"),
    SWAP1("클레이를 디다로 스왑"),
    SWAP2("디다를 클레이로 스왑"),
    SEND_OUT_KLAY("클레이 외부 전송"),
    DEAL("거래");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }
}
