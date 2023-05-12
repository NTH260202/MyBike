package com.swd.bike.dto.common;

import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagingFilterRequest extends BaseRequestData {
    @Positive
    private Integer page = 1;

    @Positive
    @Max(200)
    private Integer pageSize = 20;

    private Sort.Direction sortDir = Sort.Direction.DESC;

    private String sortBy = "createdAt";

    public Pageable getPageable() {
        return page != null
                ? PageRequest.of(page - 1, pageSize, Sort.by(sortDir, sortBy))
                : Pageable.unpaged();
    }
}
