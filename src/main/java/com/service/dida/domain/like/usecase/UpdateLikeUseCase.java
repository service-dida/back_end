package com.service.dida.domain.like.usecase;

import com.service.dida.domain.like.Like;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;

public interface UpdateLikeUseCase {

    void checkAndDeleteLike(Member member, Nft nft);
}
