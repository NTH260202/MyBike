package com.swd.bike.dto.trip.request;

import com.swd.bike.core.BaseRequestData;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetTripDetailRequest extends BaseRequestData {
    private Long id;
}
