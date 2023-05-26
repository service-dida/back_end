package com.service.dida.domain.member.entity;

import com.service.dida.domain.like.Like;
import com.service.dida.domain.market.Market;
import com.service.dida.domain.member.Role;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.wallet.Wallet;
import com.service.dida.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @Column(name = "member_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "description")
    private String description;
    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
    @Column(name = "device_token", nullable = false)
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "report_cnt", nullable = false, columnDefinition = "int default 0")
    private int reportCnt;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Nft> nfts;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Market> markets;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Like> likes;

    public boolean isValidated() {
        return !this.isDeleted();
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void changeDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void changeDeleted(boolean flag) {
        this.deleted = flag;
    }
}
