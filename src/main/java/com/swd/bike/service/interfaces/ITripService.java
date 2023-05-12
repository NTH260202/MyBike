package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
public interface ITripService {
    List<Trip> getTripByFilter(Specification<Trip> specification);
    List<Trip> getTripByMemberId(String id, boolean isGrabber);
    boolean checkExists(Specification<Trip> specification);
    Trip getDetailById(Long id);
    Page<Trip> getTripPageByFilter(Specification<Trip> specification, Pageable pageable);
    Page<Trip> getAllTrip(Specification<Trip> spec, Pageable pageable);
    Trip getCurrentTrip(Account account);
    Trip save(Trip trip);

    Trip getTrip(Long id);

    Integer countFeedbackedTrip(Account account);

    void scheduleRemindComingTrip(Trip trip);
}
