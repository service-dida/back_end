package com.service.dida.global.common.version.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VersionResponseDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppVersion {
        private Long versionId;
        private String version;
        private String changes;             // 변경 사항
        private boolean isEssentialUpdate;  // 업데이트 필수 여부
    }
}
