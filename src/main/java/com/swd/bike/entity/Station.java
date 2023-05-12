package com.swd.bike.entity;

import com.swd.bike.enums.StationStatus;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@Table(name = Station.COLLECTION_NAME)
@EqualsAndHashCode(callSuper = true)
public class Station extends Auditable<String> implements Serializable {
    public static final String COLLECTION_NAME = "station";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String description;
    private Float longitude;
    private Float latitude;
    @Enumerated(EnumType.STRING)
    private StationStatus status = StationStatus.ACTIVE;
    @ManyToMany
    @JoinTable(
            name = "route",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "next_station_id"))
    private List<Station> nextStation;
}
