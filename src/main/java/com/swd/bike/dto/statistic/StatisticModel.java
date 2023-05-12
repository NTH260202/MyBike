package com.swd.bike.dto.statistic;

import com.swd.bike.entity.Account;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StatisticModel {
    private int numOfTrip;
    private int numOfNewUser;
    private int numOfWaitingVehicle;
    private List<Account> top5Users;
    private Map<LocalDate, Integer> dailyPost;
}
