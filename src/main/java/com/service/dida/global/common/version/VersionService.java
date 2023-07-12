package com.service.dida.global.common.version;

import com.service.dida.global.common.version.dto.VersionResponseDto.AppVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService implements VersionUseCase{

    private final VersionRepository versionRepository;

    public AppVersion getAppVersion(Long versionId) {
        return new AppVersion(versionRepository.findAppVersionByVersionId(versionId).orElse(0L));
    }

}
