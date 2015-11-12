package org.jcommon.com.facebook.object;

import java.io.File;

public class Photo extends Media{
	private String message;
	
	public Photo(String message, File media){
		super(null,true);
		setMessage(message);
		setMedia(media);
	}
	
	public Photo(String message, String url){
		super(null,true);
		setMessage(message);
		initMedia(url);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
