package com.service.dida.nft;

import com.service.dida.common.BaseEntity;
import com.service.dida.like.Like;
import com.service.dida.market.Market;
import com.service.dida.post.Post;
import com.service.dida.user.User;
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
@Table(name = "nft")
public class Nft extends BaseEntity {
    @Id
    @Column(name = "nft_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nftId;

    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "contracts", nullable = false)
    private String contracts;
    @Column(name = "tx_hash", nullable = false)
    private String txHash;
    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "is_ai", nullable = false, columnDefinition = "boolean default false")
    private boolean isAi;
    @Column(name= "marketed", nullable = false, columnDefinition = "boolean default false")
    private boolean marketed;

    @Column(name = "report_cnt", nullable = false, columnDefinition = "int default 0")
    private int reportCnt;
    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "nft", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "nft", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Market> markets;

    @OneToMany(mappedBy = "nft", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Like> likes;

    public boolean isValidated() {
        return !this.isDeleted();
    }
}
