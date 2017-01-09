package com.github.utils.mycollect.configserver.log;

import java.io.File;

public class LocalConfigInfo {
	private static final String JM_LOCAL_PATH;
	private static final String CC_ROOT_PATH;
	static final boolean IsWindows = System.getProperty("os.name")
			.toUpperCase().indexOf("WINDOWS") >= 0;
	public static final String SNAPSHOT_ROOT;
	public static final String LOG_PATH;
	public static final String REMOTING_LOG_PATH;
	public static final String FAILOVER_DATA_PATH;
	public static final String FAILOVER_TRIGGER_PATH;

	static {
		JM_LOCAL_PATH = System.getProperty("JM.LOG.PATH",
				System.getProperty("user.home"));

		CC_ROOT_PATH = JM_LOCAL_PATH + File.separator + "configclient";
		SNAPSHOT_ROOT = CC_ROOT_PATH + File.separator + "snapshot";
		LOG_PATH = CC_ROOT_PATH + File.separator + "logs" + File.separator
				+ "config.client.log";

		REMOTING_LOG_PATH = CC_ROOT_PATH + File.separator + "logs"
				+ File.separator + "remoting.log";

		FAILOVER_DATA_PATH = CC_ROOT_PATH + File.separator + "data";
		FAILOVER_TRIGGER_PATH = CC_ROOT_PATH + File.separator + "failover";
	}
}
