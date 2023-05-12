package com.swd.bike.entity;


import com.swd.bike.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = Trip.COLLECTION_NAME)
public class Trip extends Auditable<String> implements Serializable {

    public static final String COLLECTION_NAME = "trip";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private Account passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grabber_id")
    private Account grabber;

    @Enumerated(EnumType.STRING)
    private TripStatus status = TripStatus.CREATED;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    private LocalDateTime cancelAt;

    private Float feedbackPoint;

    private String feedbackContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_station_id")
    private Station startStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_station_id")
    private Station endStation;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime postedStartTime;

}
