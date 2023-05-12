package com.swd.bike.dto.notification.response;

import com.swd.bike.core.BaseResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NumOfUnreadNotificationsResponse extends BaseResponseData {
    private Long numOfUnread;
}
