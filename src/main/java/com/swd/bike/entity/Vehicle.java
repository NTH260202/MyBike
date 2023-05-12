package com.swd.bike.entity;


import com.swd.bike.enums.VehicleStatus;
import com.swd.bike.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = Vehicle.COLLECTION_NAME)
@FieldNameConstants
public class Vehicle extends Auditable<String> implements Serializable {

    public static final String COLLECTION_NAME = "vehicle";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String licencePlate; // vehicle number
    private String color;
    private String image;
    private String description;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account owner;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

}
