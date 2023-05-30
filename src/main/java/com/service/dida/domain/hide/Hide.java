package com.service.dida.domain.hide;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "nft_hide")
public class Hide {
    @Id
    @Column(name = "hide_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hideId;

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
