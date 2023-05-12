package com.swd.bike.dto.notification.request;

import com.swd.bike.dto.common.PagingFilterRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetNotificationsRequest extends PagingFilterRequest {
    public Specification<Notification> getSpecification() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(this.getUserId())) {
                predicates.add(builder.equal(root.get(Notification.Fields.account).get(Account.Fields.id), this.getUserId()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
