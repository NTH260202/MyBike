package com.swd.bike.dto.token.request;

import com.swd.bike.core.BaseRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor()
public class AddExpoTokenRequest extends BaseRequestData {
    @NotBlank
    private String token;
}
