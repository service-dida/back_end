package com.service.dida.domain.alarm.repository;

import com.service.dida.domain.alarm.Alarm;
import com.service.dida.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findAllByMember(Member member, PageRequest pageRequest);

    Optional<Alarm> findByAlarmId(Long alarmId);
}
