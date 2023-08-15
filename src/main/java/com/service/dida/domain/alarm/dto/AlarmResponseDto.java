package com.service.dida.domain.alarm.dto;


import com.service.dida.domain.alarm.Alarm;
import com.service.dida.domain.alarm.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AlarmResponseDto {

    @Getter
    @AllArgsConstructor
    public static class AlarmInfo {
        private Long alarmId;
        private AlarmType type;
        private Long id;
        private boolean watched;
        private String date;

        public AlarmInfo(Alarm alarm, String date) {
            this.alarmId = alarm.getAlarmId();
            this.type = alarm.getType();
            this.id = alarm.getId();
            this.watched = alarm.isWatched();
            this.date = date;
        }
    }
}
