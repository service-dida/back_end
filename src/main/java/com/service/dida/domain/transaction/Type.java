package com.service.dida.domain.transaction;

public enum Type {
    MINTING("NFT 발행"),
    SWAP1("클레이를 디다로 스왑"),
    SWAP2("디다를 클레이로 스왑"),
    DEAL("거래");

    private final String name;

    Type(String name) {
        this.name = name;
    }
}
