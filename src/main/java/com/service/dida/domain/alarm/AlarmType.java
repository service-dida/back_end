package com.service.dida.domain.alarm;

public enum AlarmType {
    SALE("판매 완료"),
    LIKE("좋아요"),
    FOLLOW("팔로우"),
    COMMENT("댓글");

    private final String name;

    AlarmType(String name) {
        this.name = name;
    }
}
