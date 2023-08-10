package com.service.dida.domain.alarm.service;

import com.service.dida.domain.alarm.Alarm;
import com.service.dida.domain.alarm.dto.AlarmResponseDto.AlarmInfo;
import com.service.dida.domain.alarm.repository.AlarmRepository;
import com.service.dida.domain.alarm.usecase.GetAlarmUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.util.usecase.UtilUseCase;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAlarmService implements GetAlarmUseCase {

    private final AlarmRepository alarmRepository;
    private final UtilUseCase utilUseCase;


    @Override
    public List<AlarmInfo> getAllAlarms(Member member, PageRequestDto pageRequestDto) {
        Page<Alarm> alarms = alarmRepository.findAllByMember(member,
            PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize(),
                Sort.by(Direction.DESC, "createdAt")));
        List<AlarmInfo> alarmInfos = new ArrayList<>();
        alarms.forEach(
            alarm -> alarmInfos.add(
                new AlarmInfo(alarm,
                    utilUseCase.localDateTimeToAlarmFormatting(alarm.getCreatedAt()))
            )
        );

        return alarmInfos;
    }
}
