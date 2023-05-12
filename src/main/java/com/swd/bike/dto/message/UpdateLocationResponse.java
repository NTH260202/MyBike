package com.swd.bike.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationResponse {
    private String accountId;
    private Float latitude;
    private Float longitude;

    public UpdateLocationResponse(UpdateLocationMessage message) {
        this.accountId = message.getAccountId();
        this.latitude = message.getLatitude();
        this.longitude = message.getLongitude();
    }
}