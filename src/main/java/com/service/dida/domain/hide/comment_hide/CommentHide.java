package com.service.dida.domain.hide.comment_hide;

import com.service.dida.domain.comment.Comment;
import com.service.dida.domain.member.entity.Member;
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
@Table(name = "comment_hide")
public class CommentHide extends BaseEntity {
    @Id
    @Column(name = "comment_hide_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentHideId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    public boolean changeStatus() {
        this.status = !this.status;
        return this.status;
    }
}
