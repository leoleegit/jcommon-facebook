package org.jcommon.com.facebook.object;

import java.util.List;

public class User extends Profile {
	private String category;
	private String link;
	private String gender;
	private String timezone;
	private String locale;
	private String languages;
	private Picture picture;
	private String email;
	
	public User(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}
	
	public User(String json) {
		super(json,true);
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public Picture getPicture() {
		if(picture!=null && picture.getData()!=null && picture.getData().size()>0)
			return picture.getData().get(0);
		return picture;
	}
	
	public List<Picture> getPictures() {
		return picture!=null?picture.getData():null;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
