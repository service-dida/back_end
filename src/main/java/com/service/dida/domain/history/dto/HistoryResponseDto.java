package com.service.dida.domain.history.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HistoryResponseDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NftOwnData {
        private LocalDateTime ownerDate;
        private Long ownerId;
        private String ownerName;
        private double price;
    }
}
