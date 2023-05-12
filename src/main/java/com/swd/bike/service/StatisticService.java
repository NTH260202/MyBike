package com.swd.bike.service;

import com.swd.bike.dto.statistic.DailyPost;
import com.swd.bike.dto.statistic.StatisticModel;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.Role;
import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.repository.PostRepository;
import com.swd.bike.repository.TripRepository;
import com.swd.bike.repository.VehicleRepository;
import com.swd.bike.service.interfaces.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final AccountRepository accountRepository;
    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;
    private final PostRepository postRepository;
    @Override
    public StatisticModel getStatistics(LocalDateTime from, LocalDateTime to) {
        List<Account> accounts = accountRepository.findDistinctTop5ByRoleAndCreatedAtBetweenOrderByAveragePointDesc(Role.USER, from, to);
        int numOfWaitingVehicle = vehicleRepository.countByStatusAndCreatedAtBetween(VehicleStatus.WAITING, from, to);
        int numOfTrip = tripRepository.countByCreatedAtBetween(from, to);
        List<Post> posts = postRepository.findAllByCreatedAtBetween(from, to);
        int numOfNewAccount = accountRepository.countAccountsByIsUpdatedAndRoleAndCreatedAtBetween(true, Role.USER, from, to);
        StatisticModel model = StatisticModel.builder()
                .numOfTrip(numOfTrip)
                .numOfNewUser(numOfNewAccount)
                .top5Users(accounts)
                .numOfWaitingVehicle(numOfWaitingVehicle)
                .build();
        Map<LocalDate, Integer> dailyPost = new HashMap<>();
        for (Post post : posts) {
            LocalDate currentKey = post.getCreatedAt().toLocalDate();
            if (dailyPost.containsKey(currentKey)) {
                dailyPost.put(currentKey, dailyPost.get(currentKey) + 1);
            } else {
                dailyPost.put(currentKey, 1);
            }

        }
        return model.toBuilder()
                .dailyPost(dailyPost)
                .build();
    }
}
