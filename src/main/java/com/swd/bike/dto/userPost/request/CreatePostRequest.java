package com.swd.bike.dto.userPost.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.BaseRequestData;
import com.swd.bike.enums.TripRole;
import com.swd.bike.util.TrimString;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CreatePostRequest extends BaseRequestData {

    @NotNull
    private Long startStationId;

    @NotNull
    private Long endStationId;

    @NotNull
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = BaseConstant.DEFAULT_DATE_TIME_FORMAT)
    @Schema(description = "UTC Time")
    private LocalDateTime startTime;

    @NotBlank
    @JsonDeserialize(using = TrimString.class)
    @Size(max = 1000)
    private String description;

    @NotNull
    private TripRole role;
}

