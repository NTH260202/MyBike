package com.swd.bike.dto.userPost.response;

import com.swd.bike.entity.Station;
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
public class StationResponse implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Float longitude;
    private Float latitude;

    public StationResponse(Station station) {
       assert station != null;
       this.id = station.getId();
       this.name = station.getName();
       this.address = station.getAddress();
       this.description = station.getDescription();
       this.longitude = station.getLongitude();
       this.latitude = station.getLatitude();
    }

}
