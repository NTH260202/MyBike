package com.swd.bike.dto.trip.request;

import com.swd.bike.core.BasePagingAndSortingRequestData;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GetPageByFilterRequest extends BasePagingAndSortingRequestData {
    private String passengerName;
    private String grabberName;
    private TripStatus status;
    private String startStationName;
    private String endStationName;

    public Specification<Trip> getSpecification() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) {
                predicates.add(builder.equal(root.get("status"), status));
            }
            if (passengerName != null) {
                predicates.add(builder.like(root.join("passenger").get("name"), "%" + passengerName + "%"));
            }
            if (grabberName != null) {
                predicates.add(builder.like(root.join("grabber").get("name"), "%" + grabberName + "%"));
            }
            if (startStationName != null) {
                predicates.add(builder.equal(root.get("startStation").get("name"), startStationName));
            }
            if (endStationName != null) {
                predicates.add(builder.equal(root.get("endStation").get("name"), endStationName));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
