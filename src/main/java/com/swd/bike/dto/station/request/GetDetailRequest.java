package com.swd.bike.dto.station.request;

import com.swd.bike.core.BaseRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor()
public class GetDetailRequest extends BaseRequestData {
    private long id;
}
