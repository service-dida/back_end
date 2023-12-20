package com.service.dida.global.common.version;

import com.service.dida.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "version")
public class Version extends BaseEntity {
    @Id
    @Column(name = "version_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;

    @Column(name = "major", nullable = false)
    private Long major;
    @Column(name = "minor", nullable = false)
    private Long minor;
    @Column(name = "patch", nullable = false)
    private Long patch;

    @Column(name = "is_essential_update", nullable = false)
    private boolean isEssentialUpdate;

    @Column(name = "reason", nullable = false)
    private String changes;
}
