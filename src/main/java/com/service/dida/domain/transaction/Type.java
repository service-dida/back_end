package com.service.dida.domain.transaction;

public enum Type {
    MINTING("NFT 발행"),
    SWAP("스왑"),
    DEAL("거래");

    private final String name;

    Type(String name) {
        this.name = name;
    }
}
