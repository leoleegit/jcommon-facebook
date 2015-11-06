package org.jcommon.com.facebook.config;

public class FacebookConfig {
	public  final static String version = "v2.5";
	private boolean debug;
	private long start_time;
	
	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public long getStart_time() {
		return start_time;
	}

	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
}
