package com.swd.bike.dto.index;

import com.swd.bike.core.BaseRequestData;
import com.swd.bike.entity.Station;
import com.swd.bike.entity.Station_;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetStationsRequest extends BaseRequestData {
    private String query;
    private Long fromStationId;

    public Specification<Station> getSpecifications() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> filterPredicates = new ArrayList<>();
            if (Objects.nonNull(fromStationId)) {
                // Get Access
            }

            if (StringUtils.isNotBlank(query)) {
                List<Predicate> queryPredicates = new ArrayList<>();
                queryPredicates.add(cb.like(cb.lower(root.get(Station_.NAME)), "%" + query.trim().toLowerCase() + "%"));
                queryPredicates.add(cb.like(cb.lower(root.get(Station_.ADDRESS)), "%" + query.trim().toLowerCase() + "%"));
                queryPredicates.add(cb.like(cb.lower(root.get(Station_.DESCRIPTION)), "%" + query.trim().toLowerCase() + "%"));

                return cb.and(
                        cb.and(filterPredicates.toArray(new Predicate[0])),
                        cb.or(queryPredicates.toArray(new Predicate[0]))
                );
            }

            return cb.and(filterPredicates.toArray(new Predicate[0]));
        };
    }
}
