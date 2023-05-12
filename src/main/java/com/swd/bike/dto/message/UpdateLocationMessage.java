package com.swd.bike.dto.message;

import com.swd.bike.dto.kafka.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateLocationMessage extends KafkaMessage {
    private String accountId;
    private Long tripId;
    private Float latitude;
    private Float longitude;
}
