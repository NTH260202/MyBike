package com.swd.bike.common;

import org.springframework.data.domain.Sort;

public class PagingAndSortConstant {
    public static final Integer DEFAULT_PAGE_SIZE = 5;
    public static final Integer DEFAULT_PAGE_NUMBER = 1;
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
}
