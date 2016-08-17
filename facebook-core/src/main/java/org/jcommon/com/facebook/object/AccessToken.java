package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.jcommon.com.util.DateUtils;

public class AccessToken extends JsonObject{
	private List<AccessToken> data;
	private String id;
	private String name;
	
	private App app;
	private User user;
	private String scopes;
	
	private String access_token;
	private String token_type;
	private String category;
	private String perms;
	private String expired;
	private String expires_in;
	  
	public AccessToken(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}
	
	public AccessToken(String json) {
		super(json,true);
		// TODO Auto-generated constructor stub
	}
	
	public AccessToken(String id, String access_token) {
		super(null,true);
		// TODO Auto-generated constructor stub
		this.id  = id;
		this.access_token = access_token;
	}

	public String getId() {
		if(id==null && getUser()!=null)
			return getUser().getId();
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public List<AccessToken> getData() {
		return data;
	}

	public void setData(List<AccessToken> data) {
		this.data = data;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<AccessToken>();
				for(Object cu : list){
					data.add((AccessToken)cu);
				}
			}
		}
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public App getApp() {
		return app;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public String getScopes() {
		return scopes;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setExpires_in(String expires_in) {
		if(expires_in!=null && isNumeric(expires_in)){
			long expire = Long.valueOf(expires_in) * 1000l;
			long now    = new Date().getTime();
			expires_in  = DateUtils.getNowSinceYear(new Date(now+expire));
		}
		this.expires_in = expires_in;
	}

	public String getExpires_in() {
		return expires_in==null?"Never":expires_in;
	}
	
	public static boolean isNumeric(String str){ 
		if(str==null)return false;
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	}
}
