package com.swd.bike.dto.post;

import com.swd.bike.entity.Post;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class QueryPostModel {
    private Long startStationId;
    private Long endStationId;

    private Specification<Post> getPostByStartStationIdOrEndStationId() {
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

    private Specification<Post> getNotDonePostByStatus() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("status"), PostStatus.CREATED));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Specification<Post> getPostByNotDoneStatusAndStartStationIdOrEndStationId() {
        return Specification.where(getPostByStartStationIdOrEndStationId()).and(getNotDonePostByStatus());
    }
}
