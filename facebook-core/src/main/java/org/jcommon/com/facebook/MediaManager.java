package org.jcommon.com.facebook;

import org.jcommon.com.facebook.media.DefaultMediaFactory;
import org.jcommon.com.facebook.media.MediaFactory;


public class MediaManager {
	private MediaFactory media_factory;
	private static MediaManager instance = new MediaManager();
	public static MediaManager instance() { return instance; }
	
	public MediaFactory getMedia_factory() {
		if(media_factory==null)
			media_factory = new DefaultMediaFactory();
		return media_factory;
	}
	
	public void setMedia_factory(MediaFactory media_factory) {
		this.media_factory = media_factory;
	}
}
