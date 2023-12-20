package com.service.dida.global.common.version;

import com.service.dida.global.common.version.dto.VersionResponseDto.AppVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService implements VersionUseCase{

    private final VersionRepository versionRepository;

    @Override
    public AppVersion getAppVersion(Long versionId) {
        Version version = versionRepository.getVersionByVersionId(versionId);
        return new AppVersion(versionToString(version));
    }

    private String versionToString(Version version) {
        return version.getMajor() + "." + version.getMinor() + "." + version.getPatch();
    }

}
