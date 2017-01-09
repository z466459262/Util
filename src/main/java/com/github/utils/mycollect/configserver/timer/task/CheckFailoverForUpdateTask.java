package com.github.utils.mycollect.configserver.timer.task;

import com.github.utils.mycollect.configserver.timer.ConfigClientTimerService;

import java.util.concurrent.TimeUnit;

class CheckFailoverForUpdateTask implements Runnable {
	// private static final Logger log = ConfigClientLogger.logger();
	public static final long INTERVAL_SECONDS = 5L;
	// final DefaultSubscriber subscriber;
	long lastModified = -1L;

	/**
	 * 创建一个定时任务，定时任务的实现依托于timer
	 */
	public static void createAndSchedule(/** Subscriber sub **/
	) {
		CheckFailoverForUpdateTask task = null;// new
												// CheckFailoverForUpdateTask(sub);
		ConfigClientTimerService.timer.scheduleWithFixedDelay(task, 0L, 5L,
				TimeUnit.SECONDS);
	}

	/**
	 * task里面注册一个真正做事情的类
	 */
	private CheckFailoverForUpdateTask(/** Subscriber sub **/
	) {
		// this.subscriber = ((DefaultSubscriber) subscriber);
	}

	public void run() {
		// do something

	}
}
