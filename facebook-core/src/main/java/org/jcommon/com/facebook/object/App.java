package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.List;

import org.jcommon.com.facebook.permission.Permission;

public class App extends JsonObject {
	private List<App> data;
	private String app_id;
	private String app_name;
	private String app_secret;
	private String app_link;
	private Permission[] permissions;
	private String permission_str;
	private String access_token;
	private String verify_token;
	private final String verfiy_token_default = "jcommon-facebook";
	
	public App(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}
	
	public App(String json) {
		super(json,true);
		// TODO Auto-generated constructor stub
	}
	
	public App(String app_id, String app_secret, String verify_token) {
		super(null,true);
		// TODO Auto-generated constructor stub
		setApp_id(app_id);
		setApp_secret(app_secret);
		if(verify_token!=null && !"".equals(verify_token.trim()))
			setVerify_token(verify_token);
	}
	
	public App(String api_id, String app_secret) {
		this(api_id,app_secret,null);
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
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
		if(verify_token==null)
			return verfiy_token_default;	
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
	
	private String getPermissionsStr(){
		StringBuilder sb = new StringBuilder();
	    for (Permission p : permissions) {
	        sb.append(p.toString()).append(",");
	    }

	    if ((sb.lastIndexOf(",") == sb.length() - 1) && (sb.length() > 0))
	        sb.deleteCharAt(sb.length() - 1);
	    return sb.toString();
	}
	
	public boolean appVerify(String hub_challenge,String hub_verify_token,String hub_mode){
	    if("subscribe".equals(hub_mode) && (verify_token!=null && verify_token.equals(hub_verify_token)))
		  return true;
	    return false;
    }

	public String getPermission_str() {
		if(permission_str==null)
			permission_str = getPermissionsStr();
		return permission_str;
	}

	public void setPermission_str(String permission_str) {
		this.permission_str = permission_str;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setData(List<App> data) {
		this.data = data;
	}

	public List<App> getData() {
		return data;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<App>();
				for(Object cu : list){
					data.add((App)cu);
				}
			}
		}
	}
}
