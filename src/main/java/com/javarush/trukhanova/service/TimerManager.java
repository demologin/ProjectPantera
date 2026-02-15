package com.javarush.trukhanova.service;

import java.util.concurrent.*;
import java.util.Map;

public class TimerManager {
    private static TimerManager instance;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private final Map<String, ScheduledFuture<?>> activeTimers = new ConcurrentHashMap<>();

    private TimerManager() {}

    public static synchronized TimerManager getInstance() {
        if (instance == null) {
            instance = new TimerManager();
        }
        return instance;
    }

    public void startTimer(String sessionId, int seconds, Runnable onTimeout) {
        stopTimer(sessionId);
        ScheduledFuture<?> future = scheduler.schedule(onTimeout, seconds, TimeUnit.SECONDS);
        activeTimers.put(sessionId, future);
    }

    public void stopTimer(String sessionId) {
        ScheduledFuture<?> future = activeTimers.remove(sessionId);
        if (future != null && !future.isDone()) {
            future.cancel(false);
        }
    }
}