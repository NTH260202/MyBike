package com.swd.bike.dto.statistic.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.account.AccountModel;
import com.swd.bike.dto.statistic.DailyPost;
import com.swd.bike.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class GetStatisticResponse extends BaseResponseData {
    private int numOfTrip;
    private int numOfNewUser;
    private int numOfWaitingVehicle;
    private List<AccountModel> top5Users;
    private Map<LocalDate, Integer> dailyPost;
}
