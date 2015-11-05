package org.jcommon.com.facebook;

import org.jcommon.com.facebook.config.FacebookConfig;

public class FacebookManager {
	private FacebookConfig facebookConfig;
	private long start_time = System.currentTimeMillis();
	
	private static FacebookManager instance;
	public static FacebookManager instance() { 
		if(instance==null){
			new FacebookManager();
		}
		return instance; 
	}

	public FacebookConfig getFacebookConfig() {
		if(facebookConfig==null){
			facebookConfig = new FacebookConfig();
			facebookConfig.setStart_time(start_time);
		}
		return facebookConfig;
	}

	public void setFacebookConfig(FacebookConfig facebookConfig) {
		this.facebookConfig = facebookConfig;
		this.facebookConfig.setStart_time(start_time);
	}
}
