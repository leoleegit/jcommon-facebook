package org.jcommon.com.facebook.object;

import java.io.File;

public class Photo {
	private String message;
	private File media;
	
	public Photo(String message, File media){
		this.media  = media;
		this.message= message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getMedia() {
		return media;
	}

	public void setMedia(File media) {
		this.media = media;
	}
}
