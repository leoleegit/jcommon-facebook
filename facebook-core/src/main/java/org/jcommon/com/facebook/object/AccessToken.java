package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.List;

public class AccessToken extends JsonObject{
	private List<AccessToken> data;
	private String id;
	private String name;
	
	private String access_token;
	private String token_type;
	private String category;
	private String perms;
	private String expired;
	  
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
}
