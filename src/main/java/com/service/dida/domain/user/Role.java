package com.service.dida.domain.user;

public enum Role {

    ROLE_VISITOR("로그인 회원"),
    ROLE_USER("지갑발행 일반 회원"),
    ROLE_MANAGER("관리자");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
