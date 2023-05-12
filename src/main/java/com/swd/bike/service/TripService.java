package com.swd.bike.service;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.enums.TripStatus;
import com.swd.bike.exception.InternalException;
import com.swd.bike.repository.AccountRepository;
import com.swd.bike.repository.TripRepository;
import com.swd.bike.scheduler.RemindComingTripTask;
import com.swd.bike.service.interfaces.ITripService;
import com.swd.bike.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService implements ITripService {
    private final TripRepository tripRepository;
    private final AccountRepository accountRepository;
    private final TaskSchedulerService schedulerService;

    @Override
    public List<Trip> getTripByFilter(Specification<Trip> specification) {
        List<Trip> trips = tripRepository.findAll(specification);
        return trips != null ? trips : new ArrayList<>();
    }

    @Override
    public List<Trip> getTripByMemberId(String id, boolean isGrabber) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.ACCOUNT_NOT_FOUND));
        if (isGrabber) {
            return tripRepository.findTripsByGrabber(account);
        } else {
            return tripRepository.findTripsByPassenger(account);
        }
    }

    @Override
    public boolean checkExists(Specification<Trip> specification) {
        return tripRepository.exists(specification);
    }

    @Override
    @Cacheable(value = "trip", key = "#id", condition = "#id != null")
    public Trip getDetailById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new InternalException(ResponseCode.TRIP_ERROR_NOT_FOUND));
    }

    @Override
    public Page<Trip> getTripPageByFilter(Specification<Trip> specification, Pageable pageable) {
        return tripRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Trip> getAllTrip(Specification<Trip> spec, Pageable pageable) {
        if (pageable == null) {
            return new PageImpl<>(new ArrayList<>());
        }
        return tripRepository.findAll(spec, pageable);
    }

    @Override
    public Trip getCurrentTrip(Account account) {
        if (account == null) {
            return null;
        }
        List<Trip> onGoingTrips = tripRepository.findByAccountIdAndStatus(account.getId(), TripStatus.ON_GOING);
        return onGoingTrips != null && !onGoingTrips.isEmpty()
                ? onGoingTrips.get(0)
                : null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "pageTrips", allEntries = true),
            @CacheEvict(value = "trip", key = "#trip.id", condition = "#trip != null && #trip.id != null")
    })
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    @Cacheable(value = "trip", key = "#id", condition = "#id != null")
    public Trip getTrip(Long id) {
        if (id == null) {
            return null;
        }
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public Integer countFeedbackedTrip(Account account) {
        if (account == null) {
            return 0;
        }
        return tripRepository.countByGrabberIdAndFeedbackPointNotNull(account.getId());
    }

    @Override
    public void scheduleRemindComingTrip(Trip trip) {
        Instant remindTime = TimeUtils
                .convertLocalDateTimeToInstant(trip.getPostedStartTime())
                .minusSeconds(BaseConstant.REMIND_TRIP_BEFORE_IN_MINUTES * 60);

        RemindComingTripTask task = new RemindComingTripTask(trip.getId(), remindTime);

        schedulerService.schedule(task);
    }
}
