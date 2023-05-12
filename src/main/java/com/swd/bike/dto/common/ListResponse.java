package com.swd.bike.dto.common;

import com.swd.bike.core.BaseResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListResponse<T> extends BaseResponseData {
    private List<T> items;
}
