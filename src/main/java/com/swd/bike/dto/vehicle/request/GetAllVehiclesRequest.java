package com.swd.bike.dto.vehicle.request;

import com.swd.bike.core.BasePagingAndSortingRequestData;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.enums.VehicleType;
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
@NoArgsConstructor
public class GetAllVehiclesRequest extends BasePagingAndSortingRequestData {
    private String brand;
    private String licencePlate;
    private String color;
    private String description;
    private VehicleType type;
    private VehicleStatus status;

    public Specification<Vehicle> getSpecification() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(brand)) {
                predicates.add(builder.like(root.get(Vehicle.Fields.brand), "%" + brand + "%"));
            }
            if (StringUtils.isNotBlank(licencePlate)) {
                predicates.add(builder.like(root.get(Vehicle.Fields.licencePlate), "%" + licencePlate + "%"));
            }
            if (StringUtils.isNotBlank(color)) {
                predicates.add(builder.like(root.get(Vehicle.Fields.color), "%" + color + "%"));
            }
            if (StringUtils.isNotBlank(description)) {
                predicates.add(builder.like(root.get(Vehicle.Fields.description), "%" + description + "%"));
            }
            if (Objects.nonNull(type)) {
                predicates.add(builder.equal(root.get(Vehicle.Fields.type), type));
            }
            if (Objects.nonNull(status)) {
                predicates.add(builder.equal(root.get(Vehicle.Fields.status), status));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
