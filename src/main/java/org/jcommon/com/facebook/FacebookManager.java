package org.jcommon.com.facebook;

import java.util.Collection;
import java.util.List;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.cache.AppCache;
import org.jcommon.com.facebook.cache.SessionCache;
import org.jcommon.com.facebook.data.App;
import org.jcommon.com.facebook.data.Proxy;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.collections.MapStoreListener;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.jmx.Monitor;
import org.jcommon.com.util.thread.ThreadManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookManager extends Monitor implements  MapStoreListener, HttpListener{
	private Logger logger =  Logger.getLogger(getClass());
	private static FacebookManager instance;
	public static FacebookManager instance() { 
		if(instance==null){
			new FacebookManager();
		}
		return instance; 
	}
	
	public FacebookManager(){
		super("FacebookManager");
		instance = this;
	}
    
    public App getApp(String app_name){
    	return AppCache.instance().getApp(app_name);
    }
    
    public Collection<App> getApps(){
    	return AppCache.instance().getApps();
    }
    
    public FacebookSession addPageSession(PageListener listener, String facebook_id, String access_token){
    	FacebookSession session = SessionCache.instance().removeSession(facebook_id);
        if (session != null) {
          session.logout();
        }
        session = new FacebookSession(facebook_id, access_token, listener);
        session.login(FacebookType.page);
        return session;
    }
    
    public FacebookSession addUserSession(PageListener listener, String user_id, String access_token){
    	FacebookSession session = SessionCache.instance().removeSession(user_id);
        if (session != null) {
          session.logout();
        }
        session = new FacebookSession(user_id, access_token, listener);
        session.login(FacebookType.user);
        return session;
    }
    
    public FacebookSession addPmSession(PageListener listener, String user_id, String access_token){
    	FacebookSession session = SessionCache.instance().removeSession(user_id);
        if (session != null) {
          session.logout();
        }
        session = new FacebookSession(user_id, access_token, listener);
        session.login(FacebookType.message);
        return session;
    }
    
    public FacebookSession getFacebookSession(String id){
    	return SessionCache.instance().getSession(id);
    } 
    
    public List<FacebookSession> getPageSession(){
    	return SessionCache.instance().getPageSession();
    } 
    
    public List<FacebookSession> getFacebookSession() {
	    return SessionCache.instance().getFacebookSession();
    } 
    
    public boolean hasFacebookSession(String id){
    	return SessionCache.instance().getSession(id)!=null;
    }
    
    public void shutdown(){
    	super.shutdown();
    	List<FacebookSession> list = getFacebookSession();
    	for(FacebookSession session : list){
    		session.logout();
    	}
    	list.clear();
    	AppCache.instance().removeMapStoreListener(this);
    	SessionCache.instance().removeMapStoreListener(this);
    }
    
    public void startup(){
    	super.startup();
    	SessionCache.instance().addMapStoreListener(this);
    	AppCache.instance().addMapStoreListener(this);
    }
    
    public boolean isSynchronized(){
    	return true;
    }

	@Override
	public boolean addOne(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		String key = (String) arg0;
		if(arg1 instanceof App){
			key = "app:"+key;
			if(this.hasProperties(key)){
				this.removeProperties(key);
			}
			this.addProperties(key, ((App)arg1).getApi_id()+":"+((App)arg1).getApp_secret());
		}else if(arg1 instanceof FacebookSession){
			key = "FacebookSession:"+key;
			if(this.hasProperties(key)){
				this.removeProperties(key);
			}
			this.addProperties(key, ((FacebookSession)arg1).getAccessToken());
		}
		return false;
	}

	@Override
	public Object removeOne(Object arg0) {
		// TODO Auto-generated method stub
		String key = (String) arg0;
		String key_app = "app:"+key;
		if(this.hasProperties(key_app)){
			this.removeProperties(key_app);
			return null;
		}
		key = "FacebookSession:"+key;
		if(this.hasProperties(key)){
			this.removeProperties(key);
		}else{
			logger.info("can't find key:"+key);
		}
		return null;
	}

	@Override
	public boolean updateOne(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return addOne(arg0,arg1);
	}
	
	@Override
	public void initOperation(){
		addOperation(new MBeanOperationInfo("newTab", "newTab", new MBeanParameterInfo[] {}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("getTab", "getTab", new MBeanParameterInfo[] {}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("deleteTab", "deleteTab", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("tab_id", "java.lang.String", "tab_id")}, 
	    		"void", MBeanOperationInfo.ACTION));	    
	    addOperation(new MBeanOperationInfo("newCallbackCopy", "newCallbackCopy", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("facebook_id", "java.lang.String", "facebook_id"),
	    		new MBeanParameterInfo("callback", "java.lang.String", "callback")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("newFacebookSessionCopy", "newFacebookSessionCopy", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("facebook_id", "java.lang.String", "facebook_id"),
	    		new MBeanParameterInfo("access_token", "java.lang.String", "access_token"),
	    		new MBeanParameterInfo("callback", "java.lang.String", "callback")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("subscriptionPage", "subscriptionPage", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("api_id", "java.lang.String", "api_id"),
	    		new MBeanParameterInfo("page_id", "java.lang.String", "page_id"),
	    		new MBeanParameterInfo("callback_url", "java.lang.String", "callback_url")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("removeSession", "removeSession", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("facebook_id", "java.lang.String", "facebook_id")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("startMonitor", "startMonitor", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("facebook_id", "java.lang.String", "facebook_id")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("stopMonitor", "stopMonitor", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("facebook_id", "java.lang.String", "facebook_id")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("facebookDebug", "facebookDebug", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("debug", "java.lang.Boolean", "debug")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("addApp", "add or update app", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("app_name", "java.lang.String", "facebook app name"), 
	    		new MBeanParameterInfo("app_id", "java.lang.String", "facebook app id"), 
	    		new MBeanParameterInfo("app_secret", "java.lang.String", "facebook app secrect"), 
	    		new MBeanParameterInfo("permissions", "java.lang.String", "facebook app reqeuest permissions") }, 
	    		"void", 1));
	}
	
	public void subscriptionPage(String api_id,String page_id,String callback_url){
		logger.info(String.format("api_id:%s;page_id:%s;callback_url:%s", api_id,page_id,callback_url));
		
		App app = AppCache.instance().getAppByID(api_id);
		if(app==null){
			logger.warn("can't find app by "+api_id);
			return;
		}
		
		HttpRequest re;
		String access_secret = null;
		if(app.getAccess_token()==null){
			re = RequestFactory.createGetAccessSecretReqeust(null, app);
			re.run();
			access_secret = re.getResult();
			
			if(access_secret.indexOf("access_token")!=-1){
				access_secret = access_secret.split("=")[1];
				app.setAccess_token(access_secret);
				logger.info(access_secret);
			}else{
				logger.info(access_secret);
			}
		}else{
			logger.info(app.getAccess_token());
		}
		if(app.getAccess_token()!=null){
			re = RequestFactory.createSubscriptionReqeust(null, app, page_id, callback_url, FacebookType.page);
			re.run();
		    logger.info(re.getResult());
		}else{
			logger.info("app access_token is null");
		}
	}
	
	public void addApp(String app_name, String app_id, String app_secret,
			String permissions) {
		App app = new App(app_id, app_secret);
	    app.setPermissions(permissions);
	    AppCache.instance().addApp(app_name, app);
	}
	
	public void facebookDebug(boolean debug){
		logger.info(String.format("debug:%s", debug));
		FacebookSession.debug = debug;
		addProperties("debug",String.valueOf(debug));
	}
	
	public void newCallbackCopy(String facebook_id, String callback){
		logger.info(String.format("facebook_id:%s ; callback:%s", facebook_id, callback));
		
		facebook_id = facebook_id + "-" + org.jcommon.com.util.BufferUtils.generateRandom(5);
		FacebookSession session = new CallbackCopy(facebook_id,callback);
		session.login(FacebookType.user);
    }
	
	public void newFacebookSessionCopy(String facebook_id, String access_token, String callback){
		logger.info(String.format("page id:%s ; \n access_token:%s ; \n callback:%s", facebook_id,access_token,callback));
		FacebookSession session = new FacebookSessionCopy(facebook_id,access_token,callback);
		session.login(FacebookType.page);
	}
	
	public void removeSession(String facebook_id){
		FacebookSession session = SessionCache.instance().getSession(facebook_id);
		if(session!=null){
			session.logout();
		}else
			logger.warn(String.format("no session(%s) on listener", facebook_id));
	}
	
	public void startMonitor(String facebook_id){
		FacebookSession session = SessionCache.instance().getSession(facebook_id);
		if(session!=null){
			((FacebookSession)session).startMonitor();
		}else
			logger.warn(String.format("no session(%s) on listener", facebook_id));
	}
	
	public void stopMonitor(String facebook_id){
		FacebookSession session = SessionCache.instance().getSession(facebook_id);
		if(session!=null){
			((FacebookSession)session).stopMonitor();
		}else
			logger.warn(String.format("no session(%s) on listener", facebook_id));
	}
	
	public void newTab(){
		  App app = AppCache.instance().getApp();
		  List<FacebookSession> list = SessionCache.instance().getPageSession();
		  FacebookSession session    = list.size()>0?list.get(0):null;
		  String facebook_id; 
		  String access_token;
		  if(session!=null && app!=null){
			  facebook_id = session.getSessionID();
			  access_token = session.getAccessToken();
			  logger.info(String.format("page id:%s ; \n access_token:%s", facebook_id,access_token));
			  HttpRequest request = RequestFactory.createNewTabReqeust(this, facebook_id, access_token, app);
			  ThreadManager.instance().execute(request);
		  }else{
			  logger.warn("session or app is null");
		  }
	  }
	  
	  public void deleteTab(String tab_id){
		  List<FacebookSession> list = SessionCache.instance().getPageSession();
		  FacebookSession session    = list.size()>0?list.get(0):null;
		  HttpRequest request = RequestFactory.createDeleteRequest(this, tab_id, session.getAccessToken());
		  ThreadManager.instance().execute(request);
	  }
	  
	  public void getTab(){
		  App app = AppCache.instance().getApp();
		  List<FacebookSession> list = SessionCache.instance().getPageSession();
		  FacebookSession session    = list.size()>0?list.get(0):null;
		  String facebook_id; 
		  String access_token;
		  if(session!=null && app!=null){
			  facebook_id = session.getSessionID();
			  access_token = session.getAccessToken();
			  logger.info(String.format("page id:%s ; \n access_token:%s", facebook_id,access_token));
			  HttpRequest request = RequestFactory.createGetTabsReqeust(this, facebook_id, access_token, app);
			  ThreadManager.instance().execute(request);
		  }else{
			  logger.warn("session or app is null");
		  }
	  }

	@Override
	public void onException(HttpRequest arg0, Exception arg1) {
		// TODO Auto-generated method stub
		logger.error("", arg1);
	}

	@Override
	public void onFailure(HttpRequest arg0, StringBuilder arg1) {
		// TODO Auto-generated method stub
		logger.error(arg1.toString());
	}

	@Override
	public void onSuccessful(HttpRequest arg0, StringBuilder arg1) {
		// TODO Auto-generated method stub
		logger.info(arg1.toString());
	}

	@Override
	public void onTimeout(HttpRequest arg0) {
		// TODO Auto-generated method stub
		logger.error("timeout");
	}

	public void onProxy(Proxy paramProxy) throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramProxy.getJson());
		String id = paramProxy.getId();
		int type  = paramProxy.getType();
		if(type==0){
			logger.warn("error type!");
			return;
		}
		
		FacebookSession session = SessionCache.instance().getSession(id);
		if(session!=null){
			for(PageListener pl : session.getListener()){
				if(type==Proxy.FEED)
					pl.onPosts(paramProxy.getFeed_());
				else if(type==Proxy.COMMENT)
					pl.onComments(paramProxy.getFeed_(), paramProxy.getComment_());
				else if(type==Proxy.MESSAGE)
					pl.onMessages(paramProxy.getMessage_());
			}
		}else
			logger.warn(String.format("no session(%s) on listener", id));	
	}
	
	public void onCallback(String data){
		JSONObject jsonO = JsonUtils.getJSONObject(data);
		
		try{
			if(!"page".equalsIgnoreCase(jsonO!=null&&jsonO.has("object")?jsonO.getString("object"):null))
				return;
			if(jsonO!=null && jsonO.has("entry")){
				JSONArray jsonA = jsonO.getJSONArray("entry");
				for(int i=0; i<jsonA.length(); i++){
					check(jsonA.getJSONObject(i),data);
				}
			}
		}catch(JSONException e){
			logger.error("", e);
		}
	}

	private void check(JSONObject jsonO, String data) throws JSONException {
		// TODO Auto-generated method stub
	    String id = jsonO.has("id")?jsonO.getString("id"):null;
	    if(id==null)return;
	    
	    boolean done = false;
	    List<FacebookSession> sessions = SessionCache.instance().getFacebookSession();
	    for(FacebookSession session : sessions){
	    	if(session instanceof CallbackCopy || session.getSessionID().equals(id)){
	    		session.onCallback(data);
	    		done = true;
	    	}
	    }
	    if(!done){
	    	logger.warn("session is null or not page session.");
	    }
	}
	
	public boolean appVerify(String hub_challenge,String hub_verify_token,String hub_mode){
		Collection<App> apps = AppCache.instance().getApps();
		for(App app : apps){
			if(app.appVerify(hub_challenge, hub_verify_token, hub_mode))
				return true;
		}
		
		return false;
	}
}
