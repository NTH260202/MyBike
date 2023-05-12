package com.swd.bike.dto.userPost.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swd.bike.core.BaseRequestData;
import com.swd.bike.enums.TripRole;
import com.swd.bike.util.TrimString;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpdatePostRequest extends BaseRequestData {
    @JsonIgnore
    private Long id;

    @JsonDeserialize(using = TrimString.class)
    private String title;

    private Long startStationId;

    private Long endStationId;

    @Schema(description = "UTC Time")
    private LocalDateTime startTime;

    @JsonDeserialize(using = TrimString.class)
    private String description;

    private TripRole role;

}

