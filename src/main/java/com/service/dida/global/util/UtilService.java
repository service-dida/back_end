package com.service.dida.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilService {

    public String doubleToString(double d) {
        return String.format("%.6f", Double.valueOf(d * 1000000).longValue() / 1000000f);
    }
}
