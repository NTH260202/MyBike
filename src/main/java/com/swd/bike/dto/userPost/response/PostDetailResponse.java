package com.swd.bike.dto.userPost.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.BaseResponseData;
import com.swd.bike.dto.account.response.AccountResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Station;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.TripRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResponse extends BaseResponseData implements Serializable {
    private Long id;
    private TripRole role;
    private String description;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime startTime;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = BaseConstant.UTC_TIMEZONE_FORMAT)
    private LocalDateTime modifiedAt;
    private PostStatus status;
    private AccountResponse author;
    private StationResponse startStation;
    private StationResponse endStation;
    private List<AccountResponse> applications;

    public PostDetailResponse(Post post) {

        this.id = post.getId();
        this.role = post.getRole();
        this.status = post.getStatus();
        this.description = post.getDescription();
        this.startTime = post.getStartTime();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

        Account auth = post.getAuthor();
        if (auth != null) {
            author = new AccountResponse(auth);
        }

        Station sStation = post.getStartStation();
        if (sStation != null) {
            startStation = new StationResponse(sStation);
        }

        Station eStation = post.getEndStation();
        if (eStation != null) {
            endStation = new StationResponse(eStation);
        }

        List<Account> appliedAccounts = post.getApplications();
        if (appliedAccounts != null) {
            applications = appliedAccounts
                    .stream()
                    .map(AccountResponse::new)
                    .collect(Collectors.toList());
        }
    }

}
