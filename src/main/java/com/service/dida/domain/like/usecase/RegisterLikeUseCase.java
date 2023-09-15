package com.service.dida.domain.like.usecase;

import com.service.dida.domain.like.dto.LikeRequestDto;
import com.service.dida.domain.member.entity.Member;

import java.io.IOException;

public interface RegisterLikeUseCase {
    boolean pushLike(Member member, LikeRequestDto.NftLikeRequestDto nftLikeRequestDto) throws IOException;
}
