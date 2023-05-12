package com.swd.bike.dto.account.request;

import com.swd.bike.core.BaseRequestData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
public class GetDetailRequest extends BaseRequestData {
    private String id;
}
