package com.swd.bike.dto.statistic.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swd.bike.core.BaseRequestData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetStatisticRequest extends BaseRequestData {
    @Schema(example = "2023-02-04T00:00:00Z")
    @NotNull
    private String startFrom;

    @Schema(example = "2023-02-04T00:00:00Z")
    @NotNull
    private String startTo;
}
