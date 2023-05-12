package com.swd.bike.service.interfaces;

import com.swd.bike.dto.statistic.StatisticModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


public interface IStatisticService {
    StatisticModel getStatistics(LocalDateTime from, LocalDateTime to);
}
