package org.jcommon.com.facebook.object;

import org.jcommon.com.facebook.permission.Permission;

public class App extends JsonObject {
	private String api_id;
	private String app_secret;
	private String app_link;
	private Permission[] permissions;
	private String access_token;
	private String verify_token = "jcommon-facebook";
	
	public App(String json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public String getApi_id() {
		return api_id;
	}

	public void setApi_id(String api_id) {
		this.api_id = api_id;
	}

	public String getApp_secret() {
		return app_secret;
	}

	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}

	public String getApp_link() {
		return app_link;
	}

	public void setApp_link(String app_link) {
		this.app_link = app_link;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getVerify_token() {
		return verify_token;
	}

	public void setVerify_token(String verify_token) {
		this.verify_token = verify_token;
	}
	
	public Permission[] getPermissions() {
		return permissions;
	}

	public void setPermissions(Permission[] permissions) {
		this.permissions = permissions;
	}
	
	public String getPermissionsStr(){
		StringBuilder sb = new StringBuilder();
	    for (Permission p : permissions) {
	        sb.append(p.toString()).append(",");
	    }

	    if ((sb.lastIndexOf(",") == sb.length() - 1) && (sb.length() > 0))
	        sb.deleteCharAt(sb.length() - 1);
	    return sb.toString();
	}
}