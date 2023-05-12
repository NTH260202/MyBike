package com.swd.bike.entity;

import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.TripRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = Post.COLLECTION_NAME)
public class Post extends Auditable<String> implements Serializable {

    public static final String COLLECTION_NAME = "post";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Account author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_station_id")
    private Station startStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_station_id")
    private Station endStation;

    private LocalDateTime startTime;

    private String description;

    @Enumerated(EnumType.STRING)
    private TripRole role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_application",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id")
    )
    private List<Account> applications;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.CREATED;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "post")
    private Trip trip;
}

