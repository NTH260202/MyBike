package com.swd.bike.dto.account.response;

import com.swd.bike.core.BaseResponseData;
import com.swd.bike.entity.Account;
import com.swd.bike.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AccountResponse extends BaseResponseData implements Serializable {
    private String id;
    private String avatar;
    private String card;
    private String name;
    private String email;
    private String phone;
    private Float averagePoint;
    private AccountStatus status;

    public AccountResponse(Account account) {
        assert account != null;
        this.id = account.getId();
        this.avatar = account.getAvatar();
        this.card = account.getCard();
        this.name = account.getName();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.status = account.getStatus();
        this.averagePoint = account.getAveragePoint();
    }

}
