package org.jcommon.com.facebook;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.util.collections.MapStore;

public class AccessTokenManager extends MapStore{
	private static Logger logger = Logger.getLogger(AppManager.class.getClass());
	private static AccessTokenManager instance = new AccessTokenManager();

	public static AccessTokenManager instance() { return instance; }
	
	public boolean addAccessToken(AccessToken app){
		if(app==null){
			logger.info("can not install a null AccessToken");
			return false;
		}
		String id = app.getId();
		if(id==null){
			logger.info("can not install a AccessToken with null ID");
			return false;
		}
		if(super.hasKey(id))
			return super.updateOne(id, app);
		return super.addOne(id, app);
	}
	
	public AccessToken getAccessToken(String token_id){
		if(token_id==null){
			logger.info("AccessToken ID can not be null");
			return null;
		}
		if(super.hasKey(token_id))
			return (AccessToken) super.getOne(token_id);
		return null;
	}
	
	public AccessToken removeAccessToken(String token_id){
		if(token_id==null){
			logger.info("AccessToken ID can not be null");
			return null;
		}
		if(super.hasKey(token_id))
			return (AccessToken) super.removeOne(token_id);
		return null;
	}

	public Collection<AccessToken> getAccessTokens() {
		Collection<AccessToken> apps_   = new ArrayList<AccessToken>();
		Collection<Object> apps = super.getAll().values();
		for(Object app : apps)
			apps_.add((AccessToken) app);
			 
		return apps_;
	}
}
