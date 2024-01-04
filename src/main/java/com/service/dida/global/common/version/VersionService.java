package com.service.dida.global.common.version;

import com.service.dida.global.common.version.dto.VersionResponseDto.AppVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService implements VersionUseCase {

    private final VersionRepository versionRepository;

    @Override
    public AppVersion getAppVersion(Long versionId) {
        Version latestVersion = versionRepository.getLatestVersion();
        return new AppVersion(
                latestVersion.getVersionId(),
                versionToString(latestVersion),
                latestVersion.getChanges(),
                checkUpdate(versionId));
    }

    private String versionToString(Version version) {
        return version.getMajor() + "." + version.getMinor() + "." + version.getPatch();
    }

    private boolean checkUpdate(Long versionId) {
        Version version = versionRepository.getVersionByVersionId(versionId);
        return versionRepository.countsByLaterVersion(version.getCreatedAt()) > 0;
    }
}
