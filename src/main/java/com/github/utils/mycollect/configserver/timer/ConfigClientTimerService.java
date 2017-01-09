package com.github.utils.mycollect.configserver.timer;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Timer线程 此线程是一个定时器工作线程。
 * 
 */
public class ConfigClientTimerService {
	static public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                            long initialDelay, long delay, TimeUnit unit) {
		return timer.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}

	public static final ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(
			1, new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true);
					t.setName("com.xxx.config.client.timer");
					return t;
				}
			});
}
