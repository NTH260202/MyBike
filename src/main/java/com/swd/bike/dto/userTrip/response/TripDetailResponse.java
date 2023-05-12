package com.swd.bike.dto.userTrip.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.account.response.AccountResponse;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.dto.userPost.response.StationResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Station;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TripDetailResponse extends BaseResponseData {
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
    private StationResponse startStation;
    private StationResponse endStation;
    private AccountResponse grabber;
    private AccountResponse passenger;
    private PostResponse post;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime postedStartTime;

    public TripDetailResponse(Trip trip) {
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
            startStation = new StationResponse(sStation);
        }

        Station eStation = trip.getEndStation();
        if (eStation != null) {
            endStation = new StationResponse(eStation);
        }

        Account aGrabber = trip.getGrabber();
        if (aGrabber != null) {
            grabber = new AccountResponse(aGrabber);
        }

        Account aPassenger = trip.getPassenger();
        if (aPassenger != null) {
            passenger = new AccountResponse(aPassenger);
        }

        Post tPost = trip.getPost();
        if (tPost != null) {
            post = new PostResponse(tPost);
        }
    }
}
