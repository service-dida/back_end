package com.service.dida.global.common.version;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, Long> {

    Optional<Long> findAppVersionByVersionId(Long versionId);
}
