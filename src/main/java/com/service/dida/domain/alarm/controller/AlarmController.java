package com.service.dida.domain.alarm.controller;

import com.service.dida.domain.alarm.dto.AlarmResponseDto.AlarmInfo;
import com.service.dida.domain.alarm.usecase.GetAlarmUseCase;
import com.service.dida.domain.alarm.usecase.UpdateAlarmUseCase;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.config.security.auth.CurrentMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final GetAlarmUseCase getAlarmUseCase;
    private final UpdateAlarmUseCase updateAlarmUseCase;

    /**
     * 알림 페이지 가져오기
     */
    @GetMapping("/common/alarm")
    public ResponseEntity<List<AlarmInfo>> getAllAlarms(@CurrentMember Member member,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(
            getAlarmUseCase.getAllAlarms(member, new PageRequestDto(page, size)), HttpStatus.OK);
    }

    /**
     * 알림 확인하기
     */
    @PatchMapping("/common/alarm/{alarmId}")
    public ResponseEntity<Integer> checkAlarm(@CurrentMember Member member, @PathVariable Long alarmId) {
        updateAlarmUseCase.checkAlarm(member,alarmId);
        return new ResponseEntity<>(200,HttpStatus.OK);
    }

}
