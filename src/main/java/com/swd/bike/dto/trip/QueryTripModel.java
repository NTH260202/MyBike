package com.swd.bike.dto.trip;

import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QueryTripModel {
    private String passengerId;
    private String grabberId;
    private TripStatus status;
    private Long startStationId;
    private Long endStationId;

    private Specification<Trip> getTripByRiderIdOrGrabberId() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (passengerId != null) {
                predicates.add(builder.equal(root.get("passenger").get("id"), passengerId));
            }
            if (grabberId != null) {
                predicates.add(builder.equal(root.get("grabber").get("id"), grabberId));
            }
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private Specification<Trip> getTripByStartStationIdOrEndStationId() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (startStationId != null) {
                predicates.add(builder.equal(root.get("startStation").get("id"), startStationId));
            }
            if (endStationId != null) {
                predicates.add(builder.equal(root.get("endStation").get("id"), endStationId));
            }
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private Specification<Trip> getDoneTripByStatus() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("status"), TripStatus.FINISHED));
            predicates.add(builder.equal(root.get("status"), TripStatus.CANCELED));
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private Specification<Trip> getNotDoneTripByStatus() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("status"), TripStatus.ON_GOING));
            predicates.add(builder.equal(root.get("status"), TripStatus.CREATED));
            return builder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Specification<Trip> getTripByDoneStatusAndGrabberIdOrRiderId() {
        return Specification.where(getTripByRiderIdOrGrabberId()).and(getDoneTripByStatus());
    }

    public Specification<Trip> getTripByNotDoneStatusAndStartStationIdOrEndStationId() {
        return Specification.where(getTripByStartStationIdOrEndStationId()).and(getNotDoneTripByStatus());
    }
}
