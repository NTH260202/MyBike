package com.swd.bike.dto.account.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.trip.TripModel;
import com.swd.bike.dto.vehicle.VehicleModel;
import com.swd.bike.enums.AccountStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class GetAccountDetailResponse extends BaseResponseData {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String card;
    private Float averagePoint;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<TripModel> grabberOfTrips;
    private List<TripModel> passengerOfTrips;
    private AccountStatus status;
    private VehicleModel vehicle;
}
