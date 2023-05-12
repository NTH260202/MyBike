package com.swd.bike.dto.account.response;

import com.swd.bike.core.BaseRequestData;
import com.swd.bike.core.BaseResponseData;
import com.swd.bike.enums.AccountStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor()
@SuperBuilder(toBuilder = true)
public class UpdateAccountStatusResponse extends BaseResponseData {
    private boolean success;
}
