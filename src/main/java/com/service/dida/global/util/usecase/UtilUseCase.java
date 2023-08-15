package com.service.dida.global.util.usecase;

import java.time.LocalDateTime;

public interface UtilUseCase {

    String doubleToString(double d);

    String pebToDecimal(String peb);

    String decimalToPeb(double decimal);

    String localDateTimeFormatting(LocalDateTime localDateTime,LocalDateTime now);

    String localDateTimeToDateFormatting(LocalDateTime localDateTime);

    String localDateTimeToAlarmFormatting(LocalDateTime localDateTime);
}
