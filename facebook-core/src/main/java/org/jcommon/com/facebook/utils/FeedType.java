package org.jcommon.com.facebook.utils;


public enum FeedType {
	link, status, photo, video, offer;
	
	public static FeedType getType(String name) {
	    for(FeedType et : FeedType.class.getEnumConstants()){
	    	if(et.name().equalsIgnoreCase(name))
	    		return et;
	    }
	    return FeedType.status;
	}
}
