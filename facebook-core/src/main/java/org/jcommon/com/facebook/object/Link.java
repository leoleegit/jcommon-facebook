package org.jcommon.com.facebook.object;

public class Link extends JsonObject {
	private String name;
	private String link;
	
	public Link(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}

	public Link(String json) {
		super(json,true);
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
