package com.swd.bike.dto.tempAuth;

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
public class TempLoginRequest extends BaseRequestData {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}