package com.service.dida.domain.like.usecase;

import com.service.dida.domain.member.entity.Member;

public interface RegisterLikeUseCase {
    boolean pushLike(Member member, Long nftId);
}
