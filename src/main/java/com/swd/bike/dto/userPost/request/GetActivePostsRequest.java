package com.swd.bike.dto.userPost.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swd.bike.common.BaseConstant;
import com.swd.bike.dto.common.PagingFilterRequest;
import com.swd.bike.entity.Account_;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Post_;
import com.swd.bike.entity.Station_;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.TripRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetActivePostsRequest extends PagingFilterRequest {
    private String query;

    private Long startStationId;

    private Long endStationId;

    private String authorEmail;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(example = "2023-02-04T12:34:56Z")
    private LocalDateTime startFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(example = "2023-02-04T12:34:56Z")
    private LocalDateTime startTo;

    private TripRole role;

    @JsonIgnore
    private String exceptUserId;

    @JsonIgnore
    private PostStatus status = PostStatus.CREATED;

    public Specification<Post> getSpecification() {
        return (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(query)) {
                predicates.add(cb.like(cb.lower(root.get(Post_.DESCRIPTION)), "%" + query.trim().toLowerCase() + "%"));
            }

            if (Objects.nonNull(startStationId)) {
                predicates.add(cb.equal(root.join(Post_.START_STATION).get(Station_.ID), startStationId));
            }

            if (Objects.nonNull(endStationId)) {
                predicates.add(cb.equal(root.join(Post_.END_STATION).get(Station_.ID), endStationId));
            }

            if (Objects.nonNull(authorEmail)) {
                predicates.add(cb.like(root.join(Post_.AUTHOR).get(Account_.EMAIL), "%" + authorEmail.trim() + "%"));
            }

            if (Objects.nonNull(startFrom)) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(Post_.START_TIME), startFrom));
            }

            if (Objects.nonNull(startTo)) {
                predicates.add(cb.lessThanOrEqualTo(root.get(Post_.START_TIME), startTo));
            }

            if (Objects.nonNull(role)) {
                predicates.add(cb.equal(root.get(Post_.ROLE), role));
            }

            if (Objects.nonNull(exceptUserId))  {
                predicates.add(cb.notEqual(root.join(Post_.AUTHOR).get(Account_.ID), exceptUserId));
            }

            String currentUserId = this.getUserId();
            if (Objects.nonNull(currentUserId)) {
                predicates.add(cb.equal(root.join(Post_.AUTHOR).get(Account_.ID), currentUserId));
            }

            if (Objects.nonNull(status)) {
                predicates.add(cb.equal(root.get(Post_.STATUS), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public String toString() {
        return "GetActivePostsRequest{" +
                "query='" + query + '\'' +
                ", startStationId=" + startStationId +
                ", endStationId=" + endStationId +
                ", authorEmail='" + authorEmail + '\'' +
                ", startFrom=" + startFrom +
                ", startTo=" + startTo +
                ", role=" + role +
                ", exceptUserId='" + exceptUserId + '\'' +
                ", status=" + status +
                ", pageNumber=" + getPage() +
                ", pageSize=" + getPageSize() +
                ", sortBy=" + getSortBy() +
                ", sortDirection=" + getSortDir().toString() +
                ", userId=" + getUserId() +
                '}';
    }
}
