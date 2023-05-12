package com.swd.bike.dto.account.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.trip.TripModel;
import com.swd.bike.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class AccountPageResponse extends BaseResponseData {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String card;
    private Float averagePoint;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private AccountStatus status;
}
