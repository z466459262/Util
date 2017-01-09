package com.github.utils.mycollect.configserver.event;

import com.github.utils.mycollect.configserver.log.ConfigClientLogger;
import com.github.utils.mycollect.configserver.log.LocalConfigInfo;
import com.github.utils.mycollect.configserver.timer.ConfigClientTimerService;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 利用定时器，定时检测状态，发现状态不对，立刻进行事件触发，让相应监听器来做事情
 * 
 */
public class RunMode {
	private static final Logger log = ConfigClientLogger.logger();
	public static final int NORMAL = 1;
	public static final int FAILOVER = 2;
	private static volatile int runMode = 1;

	public static boolean isNormalMode() {
		return (1 == runMode);
	}

	public static boolean isFailoverMode() {
		return (2 == runMode);
	}

	static void checkRunMode() {
		boolean isFailOver = new File(LocalConfigInfo.FAILOVER_TRIGGER_PATH)
				.exists();
		if (isFailoverMode() == isFailOver)
			return;
		runMode = (isFailOver) ? 2 : 1;

		EventDispatcher.fireEvent(new Events.RunModeChangeEvent());
	}

	static {
		new FailoverModeListener();

		ConfigClientTimerService.timer.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				try {
					RunMode.checkRunMode();
				} catch (Throwable ex) {
					RunMode.log.error(
							"error when check run mode: " + ex.toString(), ex);
				}
			}
		}, 0L, 5L, TimeUnit.SECONDS);
	}
}
