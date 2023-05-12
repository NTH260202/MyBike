package com.swd.bike.handler.account;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.account.request.GetDetailRequest;
import com.swd.bike.dto.account.response.GetAccountDetailResponse;
import com.swd.bike.dto.trip.QueryTripModel;
import com.swd.bike.dto.trip.TripModel;
import com.swd.bike.dto.vehicle.VehicleModel;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.ExponentPushToken;
import com.swd.bike.entity.Trip;
import com.swd.bike.entity.Vehicle;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.Role;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.AccountService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetDetailAccountHandler extends RequestHandler<GetDetailRequest, GetAccountDetailResponse> {
    private final ITripService tripService;
    private final AccountService accountService;

    @Override
    public GetAccountDetailResponse handle(GetDetailRequest request) {
        // call service method get account role USER
        Account account = accountService.getById(request.getId());
        if (account == null || !account.getRole().equals(Role.USER)) {
            throw new InternalException(ResponseCode.ACCOUNT_NOT_FOUND);
        }
        List<ExponentPushToken> exponentPushToken = account.getTokens();
        // call service method get trip
        QueryTripModel queryTripModel = QueryTripModel.builder()
                .grabberId(account.getId())
                .passengerId(account.getId())
                .build();
        List<Trip> grabberTrips = tripService.getTripByMemberId(account.getId(), true);
        List<Trip> passengerTrips = tripService.getTripByMemberId(account.getId(), false);

        Vehicle vehicle = account.getVehicle();
        return GetAccountDetailResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .card(account.getCard())
                .phone(account.getPhone())
                .avatar(account.getAvatar())
                .averagePoint(account.getAveragePoint())
                .createdAt(account.getCreatedAt())
                .modifiedAt(account.getModifiedAt())
                .passengerOfTrips(passengerTrips.stream()
                        .map(trip -> convertTripToTripModel(trip))
                        .collect(Collectors.toList()))
                .grabberOfTrips(grabberTrips.stream()
                        .map(trip -> convertTripToTripModel(trip))
                        .collect(Collectors.toList()))

                .vehicle(account.getVehicle() == null
                        ? null
                        : VehicleModel.builder()
                        .id(vehicle.getId())
                        .brand(vehicle.getBrand())
                        .licencePlate(vehicle.getLicencePlate()) // vehicle number
                        .color(vehicle.getColor())
                        .image(vehicle.getImage())
                        .description(vehicle.getDescription())
                        .type(vehicle.getType())
                        .status(vehicle.getStatus())
                        .build())
                .status(account.getStatus())
                .build();
    }

    private TripModel convertTripToTripModel(Trip trip) {
        return TripModel.builder()
                .id(trip.getId())
                .startStationId(trip.getStartStation().getId())
                .startStationName(trip.getStartStation().getName())
                .endStationId(trip.getEndStation().getId())
                .endStationName(trip.getEndStation().getName())
                .feedbackContent(trip.getFeedbackContent())
                .feedbackPoint(trip.getFeedbackPoint())
                .build();
    }
}
