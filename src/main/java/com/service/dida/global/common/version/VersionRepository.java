package com.service.dida.global.common.version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VersionRepository extends JpaRepository<Version, Long> {

    @Query(value = "SELECT v FROM version v WHERE v.versionId=:versionId")
    Version getVersionByVersionId(Long versionId);
}
