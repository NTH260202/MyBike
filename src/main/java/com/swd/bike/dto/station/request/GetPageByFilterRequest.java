package com.swd.bike.dto.station.request;

import com.swd.bike.core.BasePagingAndSortingRequestData;
import com.swd.bike.entity.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetPageByFilterRequest extends BasePagingAndSortingRequestData {
    private String partialName;

    public Specification<Station> getSpecification() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(partialName)) {
                predicates.add(builder.like(root.get("name"), "%" + partialName + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Override
    public String toString() {
        return "GetPageByFilterRequest: " +
                this.partialName + "" +
                this.getPageSize() + "" +
                this.getPageNumber() + "" +
                this.getSortBy() + "" +
                this.getSortDirection();
    }
}
