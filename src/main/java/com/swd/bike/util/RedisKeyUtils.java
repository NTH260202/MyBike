package com.swd.bike.util;

public class RedisKeyUtils {

    private static final String REDIS_PREFIX_USER_SUBJECT = "USER_SUBJECT_";

    public static String getUserSubjectKey(String subjectId) {
        return REDIS_PREFIX_USER_SUBJECT + subjectId;
    }
}
