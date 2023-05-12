package com.swd.bike.dto.user.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserResponse extends BaseResponseData {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String avatar;
    private String card;
    private Float averagePoint;
    private Boolean isUpdated;

    public static UserResponse convert(Account account) {
        if (account == null) {
            return null;
        }
        return new UserResponse()
                .setId(account.getId())
                .setEmail(account.getEmail())
                .setName(account.getName())
                .setPhone(account.getPhone())
                .setAvatar(account.getAvatar())
                .setCard(account.getCard())
                .setAveragePoint(account.getAveragePoint())
                .setIsUpdated(account.getIsUpdated());
    }
}
