package com.swd.bike.dto.station.request;

import com.swd.bike.core.BaseRequestData;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor()
public class CreateStationRequest extends BaseRequestData {
    @NotNull
    private String address;
    @NotNull
    private String name;
    @NotNull
    private List<Long> nextStationIds = new ArrayList<>();
    private String description;
    private Float longitude;
    private Float latitude;
}
