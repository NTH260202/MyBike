package com.swd.bike.dto.userTrip.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swd.bike.core.BaseRequestData;
import com.swd.bike.util.TrimString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FeedbackTripRequest extends BaseRequestData {
    @JsonIgnore
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private Float point;

    @NotBlank
    @JsonDeserialize(using = TrimString.class)
    @Size(max = 500)
    private String content;
}
