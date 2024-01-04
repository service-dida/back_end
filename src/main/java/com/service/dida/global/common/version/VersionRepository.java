package com.service.dida.global.common.version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface VersionRepository extends JpaRepository<Version, Long> {

    @Query(value = "SELECT v FROM version v ORDER BY v.versionId DESC")
    Version getLatestVersion();

    @Query(value = "SELECT v FROM version v WHERE v.versionId=:versionId")
    Version getVersionByVersionId(Long versionId);

    @Query(value = "SELECT COUNT(v) FROM version v WHERE v.createdAt >:time AND v.isEssentialUpdate = true")
    int countsByLaterVersion(@Param("time") LocalDateTime time);
}
