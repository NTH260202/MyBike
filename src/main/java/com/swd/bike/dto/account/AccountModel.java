package com.swd.bike.dto.account;

import com.swd.bike.dto.trip.TripModel;
import com.swd.bike.enums.AccountStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AccountModel {
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
