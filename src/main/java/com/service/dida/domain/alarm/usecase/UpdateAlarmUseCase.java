package com.service.dida.domain.alarm.usecase;

import com.service.dida.domain.member.entity.Member;

public interface UpdateAlarmUseCase {

    void checkAlarm(Member member,Long alarmId);
}
