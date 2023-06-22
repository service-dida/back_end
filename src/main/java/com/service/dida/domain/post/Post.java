package com.service.dida.domain.post;

import com.service.dida.domain.comment.Comment;
import com.service.dida.global.common.BaseEntity;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.member.entity.Member;
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
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title", nullable = false, length = 20)
    private String title;
    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "report_cnt", nullable = false, columnDefinition = "int default 0")
    private int reportCnt;
    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id")
    private Nft nft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Comment> comments;

    public void editPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void setDeleted() {
        this.deleted = true;
    }
    public void plusReportCnt() {
        this.reportCnt++;
    }
}
