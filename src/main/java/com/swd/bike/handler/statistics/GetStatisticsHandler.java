package com.swd.bike.handler.statistics;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.account.AccountModel;
import com.swd.bike.dto.statistic.StatisticModel;
import com.swd.bike.dto.statistic.request.GetStatisticRequest;
import com.swd.bike.dto.statistic.response.GetStatisticResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.service.interfaces.IStatisticService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class GetStatisticsHandler extends RequestHandler<GetStatisticRequest, GetStatisticResponse> {
    private IStatisticService service;

    public GetStatisticsHandler(IStatisticService service) {
        this.service = service;
    }

    @Override
    public GetStatisticResponse handle(GetStatisticRequest request) {
        StatisticModel model = service.getStatistics(LocalDateTime.parse(request.getStartFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")), LocalDateTime.parse(request.getStartTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
        GetStatisticResponse response = GetStatisticResponse.builder()
                .numOfWaitingVehicle(model.getNumOfWaitingVehicle())
                .top5Users(model.getTop5Users().stream().map(this::convertEntityToModel).collect(Collectors.toList()))
                .numOfNewUser(model.getNumOfNewUser())
                .numOfTrip(model.getNumOfTrip())
                .dailyPost(model.getDailyPost())
                .build();
        return response;
    }

    private AccountModel convertEntityToModel(Account account) {
        return AccountModel.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .phone(account.getPhone())
                .averagePoint(account.getAveragePoint())
                .card(account.getCard())
                .avatar(account.getAvatar())
                .modifiedAt(account.getModifiedAt())
                .createdAt(account.getCreatedAt())
                .status(account.getStatus())
                .build();
    }
}
