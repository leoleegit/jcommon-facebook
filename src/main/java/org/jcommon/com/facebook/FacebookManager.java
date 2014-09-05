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
import org.jcommon.com.facebook.seesion.Session;
import org.jcommon.com.facebook.seesion.FacebookSession;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.collections.MapStoreListener;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.jmx.Monitor;
import org.jcommon.com.util.thread.ThreadManager;

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
	
    public void addApp(String app_name, String api_id, String app_secret, String permissions){
    	App app = new App(api_id, app_secret);
        app.setPermissions(permissions);
        AppCache.instance().addApp(app_name, app);
    }
    
    public App getApp(String app_name){
    	return AppCache.instance().getApp(app_name);
    }
    
    public Collection<App> getApps(){
    	return AppCache.instance().getApps();
    }
    
    public Session addPageSession(PageListener listener, String page_id, String access_token){
    	Session session = SessionCache.instance().removeSession(page_id);
        if (session != null) {
          session.logout();
        }
        session = new FacebookSession(page_id, access_token, listener);
        session.login(FacebookType.page);
        return session;
    }
    
    public Session addUserSession(PageListener listener, String user_id, String access_token){
    	Session session = SessionCache.instance().removeSession(user_id);
        if (session != null) {
          session.logout();
        }
        session = new FacebookSession(user_id, access_token, listener);
        session.login(FacebookType.user);
        return session;
    }
    
    public Session getFacebookSession(String id){
    	return SessionCache.instance().getSession(id);
    } 
    
    public List<Session> getPageSession(){
    	return SessionCache.instance().getPageSession();
    } 
    
    public List<Session> getFacebookSession() {
	    return SessionCache.instance().getFacebookSession();
    } 
    
    public boolean hasFacebookSession(String id){
    	return SessionCache.instance().getSession(id)!=null;
    }
    
    public void shutdown(){
    	super.shutdown();
    	List<Session> list = getFacebookSession();
    	for(Session session : list){
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
	    
	    addOperation(new MBeanOperationInfo("newProxySession", "newProxySession", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("page_id", "java.lang.String", "page_id"),
	    		new MBeanParameterInfo("access_token", "java.lang.String", "access_token"),
	    		new MBeanParameterInfo("callback", "java.lang.String", "callback")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("removeSession", "removeSession", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("page_id", "java.lang.String", "page_id")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("startMonitor", "startMonitor", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("page_id", "java.lang.String", "page_id")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("stopMonitor", "stopMonitor", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("page_id", "java.lang.String", "page_id")}, 
	    		"void", MBeanOperationInfo.ACTION));
	    addOperation(new MBeanOperationInfo("facebookDebug", "facebookDebug", new MBeanParameterInfo[] { 
	    		new MBeanParameterInfo("debug", "java.lang.Boolean", "debug")}, 
	    		"void", MBeanOperationInfo.ACTION));
	}
	
	public void facebookDebug(boolean debug){
		logger.info(String.format("debug:%s", debug));
		FacebookSession.debug = debug;
		addProperties("debug",String.valueOf(debug));
	}
	
	public void newProxySession(String page_id, String access_token, String callback){
		logger.info(String.format("page id:%s ; \n access_token:%s ; \n callback:%s", page_id,access_token,callback));
		new ProxyFacebookSession(page_id,access_token,callback);
	}
	
	public void removeSession(String page_id){
		Session session = SessionCache.instance().getSession(page_id);
		if(session!=null){
			session.logout();
		}else
			logger.warn(String.format("no session(%s) on listener", page_id));
	}
	
	public void startMonitor(String page_id){
		Session session = SessionCache.instance().getSession(page_id);
		if(session!=null){
			((FacebookSession)session).startMonitor();
		}else
			logger.warn(String.format("no session(%s) on listener", page_id));
	}
	
	public void stopMonitor(String page_id){
		Session session = SessionCache.instance().getSession(page_id);
		if(session!=null){
			((FacebookSession)session).stopMonitor();
		}else
			logger.warn(String.format("no session(%s) on listener", page_id));
	}
	
	public void newTab(){
		  App app = AppCache.instance().getApp();
		  List<Session> list = SessionCache.instance().getPageSession();
		  Session session    = list.size()>0?list.get(0):null;
		  String page_id; 
		  String access_token;
		  if(session!=null && app!=null){
			  page_id = session.getSessionID();
			  access_token = session.getAccessToken();
			  logger.info(String.format("page id:%s ; \n access_token:%s", page_id,access_token));
			  HttpRequest request = RequestFactory.createNewTabReqeust(this, page_id, access_token, app);
			  ThreadManager.instance().execute(request);
		  }else{
			  logger.warn("session or app is null");
		  }
	  }
	  
	  public void deleteTab(String tab_id){
		  List<Session> list = SessionCache.instance().getPageSession();
		  Session session    = list.size()>0?list.get(0):null;
		  HttpRequest request = RequestFactory.createDeleteRequest(this, tab_id, session.getAccessToken());
		  ThreadManager.instance().execute(request);
	  }
	  
	  public void getTab(){
		  App app = AppCache.instance().getApp();
		  List<Session> list = SessionCache.instance().getPageSession();
		  Session session    = list.size()>0?list.get(0):null;
		  String page_id; 
		  String access_token;
		  if(session!=null && app!=null){
			  page_id = session.getSessionID();
			  access_token = session.getAccessToken();
			  logger.info(String.format("page id:%s ; \n access_token:%s", page_id,access_token));
			  HttpRequest request = RequestFactory.createGetTabsReqeust(this, page_id, access_token, app);
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
		logger.info(paramProxy.getJsonData());
		String id = paramProxy.getId();
		int type  = paramProxy.getType();
		if(type==0){
			logger.warn("error type!");
			return;
		}
		
		Session session = SessionCache.instance().getSession(id);
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
}
