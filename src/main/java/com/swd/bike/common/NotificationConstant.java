package com.swd.bike.common;

import org.springframework.stereotype.Component;

@Component
public class NotificationConstant {

    public class Title {
        public static final String VEHICLE_REGISTRATION_CREATE = "Vehicle registration";
        public static final String VEHICLE_REGISTRATION_APPROVE = "Your vehicle registration has been approved";
        public static final String VEHICLE_REGISTRATION_DENIED = "Your vehicle registration has been denied";
        public static final String VEHICLE_UPDATE = "Vehicle Update";
        public static final String POST_NEW_APPLICATION = "Post application";
        public static final String POST_ACCEPT_APPLICATION = "Accept post application";
        public static final String POST_REJECT_APPLICATION = "Reject post application";
        public static final String POST_DELETED = "Post deleted";
        public static final String POST_EXPIRED = "Post expired";
        public static final String IN_COMING_TRIP = "Incoming trip";
        public static final String TRIP_CANCELED = "Trip canceled";
        public static final String TRIP_STARTED = "Trip started";
        public static final String TRIP_FINISHED = "Trip finished";
        public static final String TRIP_FEEDBACK = "Trip feedback";
    }

    public class Body {
        public static final String VEHICLE_REGISTRATION_CREATE = "User %s {ID: %s} has registered their vehicle";
        public static final String VEHICLE_REGISTRATION_APPROVE = "Your vehicle %s {ID: %s} has been approved";
        public static final String VEHICLE_REGISTRATION_DENIED = "Your vehicle %s {ID: %s} has been denied";
        public static final String VEHICLE_UPDATE = "User %s {ID: %s} has updated their vehicle";
        public static final String POST_NEW_APPLICATION = "%s had applied to your post";
        public static final String POST_ACCEPT_APPLICATION = "%s accepted your application to his/her post. Let's track your in-coming trip";
        public static final String POST_REJECT_APPLICATION = "%s rejected your application to his/her post";
        public static final String POST_DELETED = "%s deleted his/her post, which you applied before";
        public static final String POST_EXPIRED = "Your post has been deleted because of expiration";
        public static final String IN_COMING_TRIP = "Your trip with %s from %s to %s is going to start in %d minutes";
        public static final String TRIP_CANCELED = "%s canceled his/her trip with you from %s to %s";
        public static final String TRIP_STARTED = "%s has started his/her trip with you from %s to %s. Let's track your on-going trip";
        public static final String TRIP_FINISHED = "%s finished his/her trip with you from %s to %s";
        public static final String TRIP_FEEDBACK = "%s feedback his/her trip with you from %s to %s";
    }
}
