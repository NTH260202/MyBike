package com.swd.bike.dto.userTrip.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.BaseResponseData;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Station;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripRole;
import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripResponse extends BaseResponseData {
    private Long id;
    private TripStatus status;
    private String description;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime startAt;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime finishAt;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime cancelAt;
    private Float feedbackPoint;
    private String feedbackContent;
    private Long startStationId;
    private String startStation;
    private Long endStationId;
    private String endStation;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime postedStartTime;
    private String passengerId;
    private String passengerName;
    private String grabberId;
    private String grabberName;

    public TripResponse(Trip trip)  {
        assert trip != null;
        this.id = trip.getId();
        this.status = trip.getStatus();
        this.description = trip.getDescription();
        this.createdAt = trip.getCreatedAt();
        this.startAt = trip.getStartAt();
        this.finishAt = trip.getFinishAt();
        this.cancelAt = trip.getCancelAt();
        this.feedbackPoint = trip.getFeedbackPoint();
        this.feedbackContent = trip.getFeedbackContent();
        this.postedStartTime = trip.getPostedStartTime();

        Station sStation = trip.getStartStation();
        if (sStation != null) {
            startStationId = sStation.getId();
            startStation = sStation.getName();
        }

        Station eStation = trip.getEndStation();
        if (eStation != null) {
            endStationId = eStation.getId();
            endStation = eStation.getName();
        }

        Account grabber = trip.getGrabber();
        if (grabber != null) {
            grabberId = grabber.getId();
            grabberName = grabber.getName();
        }
        Account passenger = trip.getPassenger();
        if (passenger != null) {
            passengerId = passenger.getId();
            passengerName = passenger.getName();
        }
    }
}
