package com.swd.bike.dto.application.response;

import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Trip;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AppliedPostResponse extends PostResponse {

    private boolean accepted = false;

    public AppliedPostResponse(Post post, Account applier) {
        super(post);
        Trip trip = post.getTrip();
        if (trip != null) {
            Account grabber = trip.getGrabber();
            Account passenger = trip.getPassenger();
            if ((grabber != null && Objects.equals(grabber.getId(), applier.getId()))
                    || (passenger != null && Objects.equals(passenger.getId(), applier.getId()))) {
               accepted = true;
            }
        }
    }
}
