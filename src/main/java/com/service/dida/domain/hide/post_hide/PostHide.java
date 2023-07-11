package com.service.dida.domain.hide.post_hide;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import com.service.dida.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post_hide")
public class PostHide extends BaseEntity {
    @Id
    @Column(name = "post_hide_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postHideId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    public boolean changeStatus() {
        this.status = !this.status;
        return this.status;
    }
}
