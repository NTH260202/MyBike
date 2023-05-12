package com.swd.bike.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomException {
    @JsonProperty
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
