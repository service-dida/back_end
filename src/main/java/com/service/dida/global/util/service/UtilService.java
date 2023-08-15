package com.service.dida.global.util.service;

import com.service.dida.global.util.usecase.UtilUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UtilService implements UtilUseCase {

    public String doubleToString(double d) {
        return String.format("%.6f", Double.valueOf(d * 1000000).longValue() / 1000000f);
    }

    public String pebToDecimal(String peb) {
        DecimalFormat format = new DecimalFormat();
        format.applyLocalizedPattern("0.000000");
        String hexKlay = peb.substring(2);      // 앞 2글자에서 코드 자르기 ex) 0x0 -> 0
        BigInteger bigInteger = new BigInteger(hexKlay, 16);     // 16진수 -> 10진수 bigInteger
        BigInteger naun = new BigInteger("1000000000000");      // 10의 12제곱 bigInteger
        long re = bigInteger.divide(naun).longValue();              // 클레이 나누기 10의 12제곱
        return format.format(Math.floor((double) re) / 1000000.0);
    }

    public String decimalToPeb(double decimal) {
        long pay = Double.valueOf(decimal * 1000000).longValue();         // 소수점 6째까지 받아서 곱해서 넘김
        String py = Long.toString(pay);
        BigInteger bigInteger = new BigInteger(py);                  // 보내려는 클레이 정수의 BigInteger
        BigInteger gob = new BigInteger("1000000000000");      // 10의 12제곱 BigInteger
        String hex = bigInteger.multiply(gob).toString(16);     // 정수 곱하기 10의 16제곱 을 16진수로 표현
        return "0x" + hex;
    }

    public String localDateTimeFormatting(LocalDateTime localDateTime, LocalDateTime now) {
        if (localDateTime.getYear() != now.getYear()) {
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return localDateTime.format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
        }
    }

    public String localDateTimeToDateFormatting(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy. MM. dd"));
    }

    public String localDateTimeToAlarmFormatting(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if(localDateTime.getYear() == now.getYear() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else  {
            return localDateTimeToDateFormatting(localDateTime);
        }
    }
}
