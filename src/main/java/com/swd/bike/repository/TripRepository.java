package com.swd.bike.repository;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Station;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>,
        JpaSpecificationExecutor<Trip> {
    @Query("SELECT trip " +
            "FROM Trip trip " +
            "WHERE (trip.passenger.id = ?1 OR trip.grabber.id = ?1) " +
            "       AND trip.status = ?2")
    Optional<Trip> findFirstByStatus(String accountId, TripStatus status);

    @Query("SELECT trip " +
            "FROM Trip trip " +
            "WHERE (trip.passenger.id = ?1 OR trip.grabber.id = ?1) " +
            "       AND trip.status = ?2 " +
            "ORDER BY trip.startAt DESC")
    List<Trip> findByAccountIdAndStatus(String accountId, TripStatus status);

    Integer countByGrabberIdAndFeedbackPointNotNull(String accountId);
    List<Trip> findTripsByGrabber(Account account);
    List<Trip> findTripsByPassenger(Account account);

    @Override
    @Query( "SELECT t FROM Trip t " +
            "LEFT JOIN FETCH t.passenger " +
            "LEFT JOIN FETCH t.grabber " +
            "LEFT JOIN FETCH t.startStation " +
            "LEFT JOIN FETCH t.endStation " +
            "LEFT JOIN FETCH t.post " +
            "WHERE t.id = :id")
    Optional<Trip> findById(@Param("id") Long id);

    int countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "grabber",
                    "passenger",
                    "startStation",
                    "endStation",
                    "post"
            }
    )
    Page<Trip> findAll(Specification<Trip> specification, Pageable pageable);
}