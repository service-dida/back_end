package com.service.dida.domain.alarm.usecase;

import com.service.dida.domain.alarm.dto.AlarmResponseDto.AlarmInfo;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import java.util.List;

public interface GetAlarmUseCase {

    PageResponseDto<List<AlarmInfo>> getAllAlarms(Member member, PageRequestDto pageRequestDto);
}
