package com.swd.bike.scheduler;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class ScheduledTask implements Runnable {

    private Instant time;

    public ScheduledTask(Instant time) {
        this.time = time;
    }

}
