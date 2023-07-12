package com.service.dida.global.common.version;

import com.service.dida.global.common.version.dto.VersionResponseDto;

public interface VersionUseCase {
    VersionResponseDto.AppVersion getAppVersion(Long versionId);
}
