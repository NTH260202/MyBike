package com.swd.bike.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseConstant {

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static final String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String KAFKA_GROUP_ID = "bike-kafka";
    public static final String KAFKA_CHANNEL_PUBLIC = "NOTIFICATION_CHANNEL_PUBLIC";
    public static final String KAFKA_CHANNEL_USER = "NOTIFICATION_CHANNEL_USER";
    public static final String KAFKA_CHANNEL_TRIP = "LOCATION_CHANNEL_TRIP";
    public static Long POST_THRESHOLD_IN_MINUTES;

    @Value("${post-threshold:30}")
    public void setPostThresholdInMinutes(Long noMinutes) {
        POST_THRESHOLD_IN_MINUTES = noMinutes;
    }

    public static Long TRIP_CANCEL_THRESHOLD_IN_MINUTES;

    @Value("${trip_cancel_threshold:30}")
    public void setTripCancelThresholdInMinutes(Long noMinutes) {
        TRIP_CANCEL_THRESHOLD_IN_MINUTES = noMinutes;
    }

    public static Long REMIND_TRIP_BEFORE_IN_MINUTES;

    @Value("${trip_remind_time:5}")
    public void setRemindTripBeforeInMinutes(Long minutes) {
        REMIND_TRIP_BEFORE_IN_MINUTES = minutes;
    }

    public static final String UTC_TIMEZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}
