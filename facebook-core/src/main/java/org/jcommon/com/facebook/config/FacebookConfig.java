package org.jcommon.com.facebook.config;

public class FacebookConfig {
	public  static final String version = "v2.5";
	private static final int  default_feed_monitor_lenght    = 100;
	private static final long default_feed_monitor_frequency = 10000L;
	
	private static final int  default_message_monitor_lenght    = 25;
	private static final long default_message_monitor_frequency = 10000L;
	
	private boolean debug;
	private long start_time;
	private int  feed_monitor_lenght    = default_feed_monitor_lenght;
	private long feed_monitor_frequency = default_feed_monitor_frequency;
	
	private int  message_monitor_lenght    = default_message_monitor_lenght;
	private long message_monitor_frequency = default_message_monitor_frequency;
	
	private boolean feedMonitorEnable = true;
	private boolean messageMonitorEnable = true;
	
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

	public int getFeed_monitor_lenght() {
		return feed_monitor_lenght;
	}

	public int getMessage_monitor_lenght() {
		return message_monitor_lenght;
	}

	public void setMessage_monitor_lenght(int message_monitor_lenght) {
		this.message_monitor_lenght = message_monitor_lenght;
	}

	public long getMessage_monitor_frequency() {
		return message_monitor_frequency;
	}

	public void setMessage_monitor_frequency(long message_monitor_frequency) {
		this.message_monitor_frequency = message_monitor_frequency;
	}

	public void setFeed_monitor_lenght(int feed_monitor_lenght) {
		this.feed_monitor_lenght = feed_monitor_lenght;
	}

	public long getFeed_monitor_frequency() {
		return feed_monitor_frequency;
	}

	public void setFeed_monitor_frequency(long feed_monitor_frequency) {
		this.feed_monitor_frequency = feed_monitor_frequency;
	}

	public void setFeedMonitorEnable(boolean feedMonitorEnable) {
		this.feedMonitorEnable = feedMonitorEnable;
	}

	public boolean isFeedMonitorEnable() {
		return feedMonitorEnable;
	}

	public void setMessageMonitorEnable(boolean messageMonitorEnable) {
		this.messageMonitorEnable = messageMonitorEnable;
	}

	public boolean isMessageMonitorEnable() {
		return messageMonitorEnable;
	}
}
