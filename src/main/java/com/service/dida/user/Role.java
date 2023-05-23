package com.service.dida.user;

public enum Role {

    GENERAL("일반 회원"),
    MANAGER("관리자");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
