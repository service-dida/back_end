package com.service.dida.domain.user;

import com.service.dida.global.common.BaseEntity;
import com.service.dida.domain.like.Like;
import com.service.dida.domain.market.Market;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "nickname",nullable = false)
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

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Nft> nfts;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Market> markets;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Like> likes;

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
