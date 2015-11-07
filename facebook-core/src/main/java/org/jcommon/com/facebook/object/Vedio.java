package org.jcommon.com.facebook.object;

import java.io.File;

public class Vedio extends Media{
	private String title;
	private String description;
	
	public Vedio(String title, String description, File media){
		super(null,true);
		setTitle(title);
		setDescription(description);
		setMedia(media);
	}
	
	public Vedio(String title, String description, String url){
		super(null,true);
		setTitle(title);
		setDescription(description);
		setUrl(url);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
