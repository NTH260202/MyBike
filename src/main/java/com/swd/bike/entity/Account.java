package com.swd.bike.entity;

import com.swd.bike.enums.AccountStatus;
import com.swd.bike.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Table(name = Account.COLLECTION_NAME)
public class Account extends Auditable<String> implements Serializable{

    public static final String COLLECTION_NAME = "account";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String email;
    private String name;
    private String phone;
    private String avatar;
    private String card;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Float averagePoint;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private Boolean isUpdated = false;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "owner")
    private Vehicle vehicle;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExponentPushToken> tokens = new ArrayList<>();
    private String subjectId;
}
