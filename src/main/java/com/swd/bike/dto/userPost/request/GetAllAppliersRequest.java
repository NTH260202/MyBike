package com.swd.bike.dto.userPost.request;

import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllAppliersRequest extends BaseRequestData {
    private Long id;
}
