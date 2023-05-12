package com.swd.bike.dto.userTrip.request;

import com.swd.bike.dto.common.PagingFilterRequest;
import com.swd.bike.entity.Account_;
import com.swd.bike.entity.Station_;
import com.swd.bike.entity.Trip;
import com.swd.bike.entity.Trip_;
import com.swd.bike.enums.TripStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GetAllTripsRequest extends PagingFilterRequest {

    private String query;

    private String startStationId;

    private String endStationId;

    private TripStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(example = "2023-02-04T00:00:00Z")
    private LocalDateTime startFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(example = "2023-02-04T00:00:00Z")
    private LocalDateTime startTo;

    public Specification<Trip> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(query)) {
                predicates.add(cb.like(cb.lower(root.get(Trip_.DESCRIPTION)), "%" + query.trim().toLowerCase() + "%"));
            }

            if (Objects.nonNull(startStationId)) {
                predicates.add(cb.equal(root.join(Trip_.START_STATION).get(Station_.ID), startStationId));
            }

            if (Objects.nonNull(endStationId)) {
                predicates.add(cb.equal(root.join(Trip_.END_STATION).get(Station_.ID), endStationId));
            }

            if (Objects.nonNull(startFrom)) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(Trip_.START_AT), startFrom));
            }

            if (Objects.nonNull(startTo)) {
                predicates.add(cb.lessThanOrEqualTo(root.get(Trip_.START_AT), startTo));
            }

            if (Objects.nonNull(status)) {
                predicates.add(cb.equal(root.get(Trip_.STATUS), status));
            }

            Predicate filterPredicate = cb.and(predicates.toArray(new Predicate[0]));

            String userId = this.getUserId();
            List<Predicate> orPredicates = new ArrayList<>();
            if (Objects.nonNull(userId)) {
                orPredicates.add(cb.equal(root.join(Trip_.PASSENGER).get(Account_.ID), userId));
                orPredicates.add(cb.equal(root.join(Trip_.GRABBER).get(Account_.ID), userId));
                Predicate userPredicate = cb.or(orPredicates.toArray(new Predicate[0]));
                return cb.and(filterPredicate, userPredicate);
            }

            return filterPredicate;
        };
    }

    @Override
    public String toString() {
        return "GetAllTripsRequest{" +
                "query='" + query + '\'' +
                ", startStationId='" + startStationId + '\'' +
                ", endStationId='" + endStationId + '\'' +
                ", status=" + status +
                ", startFrom=" + startFrom +
                ", startTo=" + startTo +
                ", pageNumber=" + getPage()+
                ", pageSize=" + getPageSize() +
                ", sortBy=" + getSortBy() +
                ", sortDir=" + getSortDir().toString() +
                ", userId=" + getUserId() +
                '}';
    }
}
