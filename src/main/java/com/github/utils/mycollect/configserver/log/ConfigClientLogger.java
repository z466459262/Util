package com.github.utils.mycollect.configserver.log;

import org.apache.log4j.*;

import java.io.File;

public class ConfigClientLogger {
	static final Logger logger;

	public static Logger logger() {
		return logger;
	}

	static {
		System.out.println("config client log path : "
				+ new File(LocalConfigInfo.LOG_PATH).getAbsolutePath());

		FileAppender appender = new DailyRollingFileAppender();
		appender.setAppend(true);
		appender.setEncoding("GBK");
		appender.setFile(LocalConfigInfo.LOG_PATH);
		appender.setLayout(new PatternLayout("%d %-5p - %m%n%n"));
		appender.activateOptions();

		logger = Logger.getLogger("com.taobao.config.client");
		logger.setLevel(Level.INFO);
		logger.setAdditivity(false);
		logger.addAppender(appender);

		System.out
				.println("com.taobao.remoting : "
						+ new File(LocalConfigInfo.REMOTING_LOG_PATH)
								.getAbsolutePath());

		appender = new DailyRollingFileAppender();
		appender.setAppend(true);
		appender.setEncoding("GBK");
		appender.setFile(LocalConfigInfo.REMOTING_LOG_PATH);
		appender.setLayout(new PatternLayout("%n%d %-5p %c{2} - %n%m%n"));
		appender.activateOptions();

		Logger logRemoting = Logger.getLogger("com.taobao.remoting");
		logRemoting.setLevel(Level.INFO);
		logRemoting.setAdditivity(false);
		logRemoting.addAppender(appender);

		Logger logMina = Logger.getLogger("org.apache.mina");
		logMina.setLevel(Level.INFO);
		logMina.setAdditivity(false);
		logMina.addAppender(appender);
	}
}
