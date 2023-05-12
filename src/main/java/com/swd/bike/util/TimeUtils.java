package com.swd.bike.util;


import lombok.extern.slf4j.Slf4j;

import java.time.*;

@Slf4j
public class TimeUtils {
    public static final Instant convertLocalDateTimeToInstant(LocalDateTime time) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = time.atZone(zoneId);
        return zonedDateTime.toInstant();
    }
}
