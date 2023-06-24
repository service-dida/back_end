package com.service.dida.domain.nft;

import com.service.dida.domain.like.Like;
import com.service.dida.domain.market.Market;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import com.service.dida.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "report_cnt", nullable = false, columnDefinition = "int default 0")
    private int reportCnt;
    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "nft", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Post> posts;

    @OneToOne(mappedBy = "nft", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Market market;

    @OneToMany(mappedBy = "nft", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Like> likes;

    public void setDeleted() {
        this.deleted = true;
    }

    public boolean isMarketed() {
        return this.market != null;
    }

    public String getPrice() {
        String price = "NOT SALE";
        if (this.isMarketed()) {
            price = String.format("%.6f", Double.valueOf(
                this.market.getPrice() * 1000000).longValue() / 1000000f);
        }
        return price;
    }
    public void plusReportCnt() {
        this.reportCnt++;
    }

    public void changeMember(Member member) {
        this.member = member;
    }
}
