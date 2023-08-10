package com.service.dida.domain.alarm;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "alarm")
public class Alarm extends BaseEntity {

    @Id
    @Column(name = "alarm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AlarmType type; // 판매, 좋아요, 팔로우, 댓글

    @Column(name = "id")
    private Long id;    // type에 따라 판매글은 판매된 nft id, 좋아요 눌린 nft id, 팔로우를 한 유저의 id, 댓글의 id 저장

    @Column(name = "is_watched", nullable = false, columnDefinition = "boolean default false")
    private boolean watched;

    public void setWatched() {
        this.watched = true;
    }
}
