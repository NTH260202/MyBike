package com.swd.bike.dto.user.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swd.bike.common.TrimString;
import com.swd.bike.core.BaseRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest extends BaseRequestData {
    @JsonDeserialize(using = TrimString.class)
    private String name;
    @JsonDeserialize(using = TrimString.class)
    @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{8})$")
    private String phone;
    @JsonDeserialize(using = TrimString.class)
    private String avatar;
    @JsonDeserialize(using = TrimString.class)
    private String card;
}
