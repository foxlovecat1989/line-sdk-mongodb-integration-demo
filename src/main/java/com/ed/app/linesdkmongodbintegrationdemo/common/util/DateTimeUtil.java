package com.ed.app.linesdkmongodbintegrationdemo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class DateTimeUtil {
    private DateTimeUtil() {
    }

    /**
     * <pre>
     * java.util.Date 轉 java.time.LocalDateTime
     *
     * @param date - 欲轉換的 Date物件
     * @return 轉換後的 LocalDateTime
     *
     * </pre>
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Objects.requireNonNull(date);

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
