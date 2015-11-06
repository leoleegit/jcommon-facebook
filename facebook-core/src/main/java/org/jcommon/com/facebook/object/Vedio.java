package org.jcommon.com.facebook.object;

import java.io.File;

public class Vedio {
	private String title;
	private String description;
	private File media;
	
	public Vedio(String title, String description, File media){
		this.title       = title;
		this.description = description;
		this.media       = media;
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

	public File getMedia() {
		return media;
	}

	public void setMedia(File media) {
		this.media = media;
	}
}
