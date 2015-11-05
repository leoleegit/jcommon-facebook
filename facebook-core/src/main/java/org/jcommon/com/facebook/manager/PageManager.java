package org.jcommon.com.facebook.manager;

import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.Error;
import org.jcommon.com.facebook.object.JsonObject;
import org.jcommon.com.util.http.HttpRequest;

public class PageManager extends ResponseHandler{
	private String page_id;
	private AccessToken access_token;
	
	public PageManager(String page_id, AccessToken access_token){
		this.page_id = page_id;
		this.access_token = access_token;
	}

	public String getPage_id() {
		return page_id;
	}

	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}

	public AccessToken getAccess_token() {
		return access_token;
	}

	public void setAccess_token(AccessToken access_token) {
		this.access_token = access_token;
	}
	
	

	@Override
	public void onError(HttpRequest paramHttpRequest, Error paramError) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOk(HttpRequest paramHttpRequest, JsonObject paramObject) {
		// TODO Auto-generated method stub
		
	}
}
