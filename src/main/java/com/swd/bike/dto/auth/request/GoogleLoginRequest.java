package com.swd.bike.dto.auth.request;

import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLoginRequest extends BaseRequestData {
    @NotBlank
    private String code;
    @NotBlank
    private String redirectUri;
}
