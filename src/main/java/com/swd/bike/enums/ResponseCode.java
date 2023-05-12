package com.swd.bike.enums;

import com.swd.bike.common.BaseConstant;

public enum ResponseCode {
    // Common
    SUCCESS(0, "Success"),
    FAILED(1, "Failed"),
    COMMON_ERROR(2, "Common Error"),
    INVALID_PARAM(3, "Invalid param"),
    INVALID_SESSION(4, "Invalid session"),
    UNHANDLED_REQUEST(5, "Unhandled request"),
    THIRD_PARTY_ERROR(6, "Error when calling third party"),
    JSON_PROCESSING_ERROR(7, "Error when processing JSON"),
    RESPONSE_CODE_INVALID(8, "Invalid response code"),
    CONFLICT(9, "Key is conflict"),

    // Auth
    INVALID_USERNAME_OR_PASSWORD(10, "Invalid username or password"),
    GOOGLE_AUTH_ERROR(11, "Error occurs when login with Google"),
    REFRESH_TOKEN_INVALID(12, "Invalid refresh token"),
    AUTHENTICATION_FAILED(13, "Authentication failed"),
    AUTHENTICATION_FAILED_OUTSIDE_EMAIL(14, "Authentication failed: Outside email"),
    CODE_INVALID(15, "Invalid code"),

    UNAUTHORIZED_REQUEST(11, "Unauthorized"),

    // POST
    POST_ERROR_INVALID_TIME(20, "Start time must be greater than now"),
    POST_ERROR_CONFLICT_TIME(22, "Conflict time, There are one or more active post(s) at this start time (threshold: " + BaseConstant.POST_THRESHOLD_IN_MINUTES + " minutes)"),
    POST_ERROR_INVALID_STATION(23, "Invalid station. Start station and end station must be different"),
    POST_ERROR_STATION_NOT_FOUND(24, "Station not found"),
    POST_ERROR_NOT_FOUND(25, "Post not found or closed"),
    POST_ERROR_INVALID_STATUS(26, "Can't update post. Post is current being applied"),
    POST_ERROR_SELF_APPLY(27, "Can't self apply for your post"),
    POST_ERROR_EXISTED_APPLIER(28, "Existed applier"),
    POST_ERROR_NOT_EXISTED_APPLIER(29, "Not existed applier"),
    POST_ERROR_INVALID_END_STATION(30, "End station can't be accessed from start station"),

    POST_ERROR_UNREGISTERED_VEHICLE(31, "Account has not registered the vehicle yet"),
    POST_ERROR_UNUPDATED_ACCOUNT(32, "Account has not updated information yet"),

    // User
    USER_NOT_FOUND(30, "User not found"),
    USER_MISSING_FIELD(31, "User missing required field"),
    USER_HAS_REGISTERED_VEHICLE(32, "User has registered vehicle"),

    // Trip
    TRIP_ERROR_NOT_FOUND(40, "Trip not found"),
    TRIP_ERROR_INVALID_STATUS(41, "Trip's current status is invalid for this action"),
    TRIP_ERROR_INVALID_ACCESS(42, "Current logged in user has no access on the trip in this action"),
    TRIP_ERROR_ON_GOING_TRIP(43, "User is busy. There is an on going trip"),
    TRIP_ERROR_EXISTED_FEEDBACK(44, "Existed feedback"),
    TRIP_ERROR_CAN_NOT_CANCEL_TRIP(45, "You must cancel the trip at least " + BaseConstant.TRIP_CANCEL_THRESHOLD_IN_MINUTES + " minutes before the start time"),


    //Station
    STATION_NOT_FOUND(51, "Station is not found"),
    STATION_IS_USED(52, "Station is used"),
    STATION_IS_INACTIVE(53, "Station is inactive"),

    //Station
    VEHICLE_NOT_FOUND(61, "Vehicle is not found"),

    //Account
    ACCOUNT_NOT_FOUND(71, "Account is not found"),
    ACCOUNT_INACTIVE(72, "Account is inactive"),

    //Account
    NOTIFICATION_NOT_FOUND(81, "Notification is not found"),

    ;

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
