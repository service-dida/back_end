package com.service.dida.domain.like;

import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nft_like")
public class Like {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id")
    private Nft nft;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    public boolean changeStatus() {
        this.status = !this.status;
        return this.status;
    }
}
