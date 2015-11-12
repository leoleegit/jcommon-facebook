package org.jcommon.com.facebook.object;


public class UrlObject extends JsonObject{
	
	private String url;
	public UrlObject(String url, boolean decode){
		super(url,decode);
		if(this.url==null)
			this.url = url;
	}
	
	public UrlObject(String url){
		super(url,true);
		if(this.url==null)
			this.url = url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
} 
