package com.swd.bike.service;

import com.swd.bike.scheduler.ScheduledTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskSchedulerService {

    private final TaskScheduler taskScheduler;

    public void schedule(ScheduledTask task) {
        try {
            taskScheduler.schedule(task, task.getTime());
        } catch (Exception ex) {
            log.error("[SCHEDULING]: Scheduled task failed. {}", ex.getMessage());
        }
    }
}
