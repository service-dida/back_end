package com.service.dida.domain.follow.usecase;

import com.service.dida.domain.member.entity.Member;
import java.io.IOException;

public interface RegisterFollowUseCase {
    void registerFollow(Member member,Long otherId) throws IOException;
}
