package org.jcommon.com.facebook.object;

import org.jcommon.com.facebook.permission.Permission;

public class App extends JsonObject {
	private String api_id;
	private String app_secret;
	private String app_link;
	private Permission[] permissions;
	private String permission_str;
	private String access_token;
	private String verify_token = "jcommon-facebook";
	
	public App(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}
	
	public App(String api_id, String app_secret, String verify_token) {
		super(null,true);
		// TODO Auto-generated constructor stub
		setApi_id(api_id);
		setApp_secret(app_secret);
		if(verify_token!=null && !"".equals(verify_token.trim()))
			setVerify_token(verify_token);
	}
	
	public App(String api_id, String app_secret) {
		this(api_id,app_secret,null);
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
		if(getPermission_str()!=null)return getPermission_str();
		StringBuilder sb = new StringBuilder();
	    for (Permission p : permissions) {
	        sb.append(p.toString()).append(",");
	    }

	    if ((sb.lastIndexOf(",") == sb.length() - 1) && (sb.length() > 0))
	        sb.deleteCharAt(sb.length() - 1);
	    setPermission_str(sb.toString());
	    return getPermission_str();
	}
	
	public boolean appVerify(String hub_challenge,String hub_verify_token,String hub_mode){
	    if("subscribe".equals(hub_mode) && (verify_token!=null && verify_token.equals(hub_verify_token)))
		  return true;
	    return false;
    }

	public String getPermission_str() {
		return permission_str;
	}

	public void setPermission_str(String permission_str) {
		this.permission_str = permission_str;
	}
}
