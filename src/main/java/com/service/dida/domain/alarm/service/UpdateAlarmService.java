package com.service.dida.domain.alarm.service;

import com.service.dida.domain.alarm.Alarm;
import com.service.dida.domain.alarm.repository.AlarmRepository;
import com.service.dida.domain.alarm.usecase.UpdateAlarmUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.AlarmErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UpdateAlarmService implements UpdateAlarmUseCase {

    private final AlarmRepository alarmRepository;

    @Override
    public void checkAlarm(Member member, Long alarmId) {
        Alarm alarm = alarmRepository.findByAlarmId(alarmId)
            .orElseThrow(() -> new BaseException(AlarmErrorCode.INVALID_ID));
        alarm.setWatched();
    }
}
