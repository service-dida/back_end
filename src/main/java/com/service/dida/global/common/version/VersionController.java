package com.service.dida.global.common.version;

import com.service.dida.global.common.version.dto.VersionResponseDto.AppVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VersionController {

    private final VersionUseCase versionUseCase;

    /**
     * 앱 버전 확인 api
     * [GET] /app/version
     */
    @GetMapping("/app/version")
    public ResponseEntity<AppVersion> getAppVersion(@RequestParam(value = "versionId") Long versionId) {
        return new ResponseEntity<>(versionUseCase.getAppVersion(versionId), HttpStatus.OK);
    }
}
