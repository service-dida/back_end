package com.service.dida.domain.alarm.usecase;

import com.service.dida.domain.member.entity.Member;
import java.io.IOException;

public interface RegisterAlarmUseCase {

    void registerSaleAlarm(Member member, Long nftId) throws IOException;

    void registerLikeAlarm(Member member, Long nftId) throws IOException;

    void registerFollowAlarm(Member member, Long followerId) throws IOException;

    void registerCommentAlarm(Member member, Long commentId) throws IOException;
}
