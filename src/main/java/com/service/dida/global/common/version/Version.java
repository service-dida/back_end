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

    @Column(name = "major", nullable = false)
    private Long major;
    @Column(name = "minor", nullable = false)
    private Long minor;
    @Column(name = "patch", nullable = false)
    private Long patch;

    public void upMajor() {
        this.major = this.major + 1;
    }
    public void upMinor() {
        this.minor = this.minor + 1;
    }
    public void upPatch() {
        this.patch = this.patch + 1;
    }
}
