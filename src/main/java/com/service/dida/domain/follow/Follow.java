package com.service.dida.domain.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "follow")
public class Follow {
    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @Column(name = "following_id", nullable = false)
    private Long followingId;   // 팔로우를 하는 사람

    @Column(name = "follower_id", nullable = false)
    private Long followerId;    // 팔로우를 당한 사람

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    public boolean changeStatus() {
        this.status = !this.status;
        return this.status;
    }
}
