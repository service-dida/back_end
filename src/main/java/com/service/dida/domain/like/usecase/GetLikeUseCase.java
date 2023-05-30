package com.service.dida.domain.like.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;

public interface GetLikeUseCase {
    boolean checkIsLiked(Member member, Nft nft);
}
