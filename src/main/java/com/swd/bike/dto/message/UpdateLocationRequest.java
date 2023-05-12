package com.swd.bike.dto.message;

import com.swd.bike.dto.kafka.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationRequest extends KafkaMessage {
    private Long tripId;
    private Float latitude;
    private Float longitude;
}
