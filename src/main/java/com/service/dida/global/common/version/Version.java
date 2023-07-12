package com.service.dida.global.common.version;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "version")
public class Version {
    @Id
    @Column(name = "version_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;
    @Column(name = "app_version")
    private Long appVersion;

    public void upVersion() {
        this.appVersion = this.appVersion + 1;
    }
}
