package com.service.dida.domain.alarm.usecase;

import com.service.dida.domain.alarm.dto.AlarmResponseDto.AlarmInfo;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import java.util.List;

public interface GetAlarmUseCase {

    List<AlarmInfo> getAllAlarms(Member member, PageRequestDto pageRequestDto);
}
