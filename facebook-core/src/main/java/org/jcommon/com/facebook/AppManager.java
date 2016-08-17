package org.jcommon.com.facebook;

import java.util.*;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.App;
import org.jcommon.com.facebook.object.User;
import org.jcommon.com.facebook.permission.ExtendedPermissionV_2_5;
import org.jcommon.com.util.collections.MapStore;
import org.jcommon.com.util.http.HttpRequest;

public class AppManager extends MapStore{
	private static Logger logger = Logger.getLogger(AppManager.class.getClass());
	private static AppManager instance = new AppManager();

	public static AppManager instance() { return instance; }
	
	public boolean addApp(App app){
		if(app==null){
			logger.info("can not install a null app");
			return false;
		}
		String id = app.getApp_id();
		if(id==null){
			logger.info("can not install a app with null ID");
			return false;
		}
		return super.addOne(id, app);
	}
	
	public App getApp(String app_id){
		if(app_id==null){
			logger.info("app ID can not be null");
			return null;
		}
		if(super.hasKey(app_id))
			return (App) super.getOne(app_id);
		return null;
	}
	
	public App getAppByName(String app_name){
		if(app_name==null){
			logger.info("app name can not be null");
			return null;
		}
		for(App app : getApps()){
			if(app_name.equals(app.getApp_name()))
				return app;
		}
		return null;
	}
	
	public App getDefaultApp(){
		Collection<Object> apps = super.getAll().values();
		if(!apps.isEmpty())
			return (App) apps.iterator().next();
		return null;
	}
	
	public App removeApp(String app_id){
		if(app_id==null){
			logger.info("app ID can not be null");
			return null;
		}
		if(super.hasKey(app_id))
			return (App) super.removeOne(app_id);
		return null;
	}
	
	public Collection<App> getApps() {
		Collection<App> apps_   = new ArrayList<App>();
		Collection<Object> apps = super.getAll().values();
		for(Object app : apps)
			apps_.add((App) app);
			 
		return apps_;
	}
	
	public static User aboutMe(AccessToken access_token){
        return aboutMe(null,access_token);
	}
	
	public static User aboutMe(String id, AccessToken access_token){
		if(access_token==null){
			logger.info("access_token not be null");
			return null;
		}
		if(id==null)
			id = "me";
		logger.info(access_token.getAccess_token());
		HttpRequest facebook_request = RequestFactory.getAboutMeReqeust(null, id, access_token.getAccess_token());
        facebook_request.run();
        logger.info("Result:" + facebook_request.getResult());
        return new User(facebook_request.getResult());
	}
	
	public static String getAccessCodeUrl(App app, String redirect_uri){
		if(app==null){
			logger.info("app not be null");
			return null;
		}
		return RequestFactory.getAccessCodeUrl(redirect_uri, app.getPermission_str(), app.getApp_id());
	}
	
	public static String getPageAccessCodeUrl(App app, String redirect_uri){
		if(app==null){
			logger.info("app not be null");
			return null;
		}
		return RequestFactory.getAccessCodeUrl(redirect_uri, ExtendedPermissionV_2_5.manage_pages.name(), app.getApp_id());
	}
	
	public static List<AccessToken> getAccessTokenList(AccessToken access_token){
		if(access_token==null){
			logger.info("access_token not be null");
			return null;
		}
		logger.info(access_token.getAccess_token());
		HttpRequest facebook_request = RequestFactory.getAllAccessTokenReqeust(null, access_token.getAccess_token());
	    facebook_request.run();
	    String data = facebook_request.getResult();
	    logger.info("Result:" + data);
	    return new AccessToken(data).getData();
	}
	
	public static AccessToken getAccessToken(App app, String redirect_uri, String code){
		logger.info(String.format("app:%s;redirect_uri:%s;code:%s", app,redirect_uri,code));
		if(app==null){
			logger.info("app not be null");
			return null;
		}
		
		HttpRequest facebook_request = RequestFactory.getAccessTokenReqeust(null,app.getApp_id(),app.getApp_secret(),code,redirect_uri);
        facebook_request.run();
        String access_token_ = facebook_request.getResult();
        logger.info("Result:" + access_token_);
        AccessToken at = new AccessToken(access_token_);
        return at;
	}
}
