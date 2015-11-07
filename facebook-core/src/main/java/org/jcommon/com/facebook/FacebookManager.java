package org.jcommon.com.facebook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.config.FacebookConfig;
import org.jcommon.com.facebook.object.App;
import org.jcommon.com.facebook.object.callback.CallbackData;
import org.jcommon.com.facebook.object.callback.Changes;
import org.jcommon.com.facebook.object.callback.Entry;
import org.jcommon.com.facebook.object.callback.Value;
import org.jcommon.com.facebook.utils.CallbackType;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.jmx.Monitor;

public class FacebookManager extends Monitor{
	private Logger logger = Logger.getLogger(getClass());
	private FacebookConfig facebookConfig;
	private Map<String,FacebookSession> facebookSessions = new HashMap<String,FacebookSession>();
	private long start_time = System.currentTimeMillis();
	
	private static FacebookManager instance;
	public static FacebookManager instance() { 
		if(instance==null){
			new FacebookManager();
		}
		return instance; 
	}
	
	public FacebookManager() {
		super("FacebookManager");
		// TODO Auto-generated constructor stub
		instance = this;
	}
	
	@Override
	public void initOperation(){
		 addOperation(new MBeanOperationInfo("addApp", "add or update app", new MBeanParameterInfo[] { 
		    		new MBeanParameterInfo("app_id", "java.lang.String", "facebook app_id"), 
		    		new MBeanParameterInfo("app_secret", "java.lang.String", "facebook app_secret"), 
		    		new MBeanParameterInfo("verify_token", "java.lang.String", "facebook app verify_token"), 
		    		new MBeanParameterInfo("permissions", "java.lang.String", "facebook app reqeuest permissions") }, 
		    		"void", 1));
	}

	public FacebookConfig getFacebookConfig() {
		if(facebookConfig==null){
			facebookConfig = new FacebookConfig();
			facebookConfig.setStart_time(start_time);
		}
		return facebookConfig;
	}

	public void setFacebookConfig(FacebookConfig facebookConfig) {
		this.facebookConfig = facebookConfig;
		this.facebookConfig.setStart_time(start_time);
	}
	
	public void addApp(String app_id, String app_secret,String verify_token,
			String permissions) {
		logger.info(String.format("app_id:%s;app_secret:%s;verify_token:%s\n permissions:%s", app_id,app_secret,verify_token,permissions));
		App app = new App(app_id, app_secret, verify_token);
	    app.setPermission_str(permissions);
	    logger.info("add successful:"+AppManager.instance().addApp(app));
	}
	
	public void removeApp(String app_id) {
		logger.info("remove successful:"+AppManager.instance().removeApp(app_id));
	}
	
	public void onCallback(String data){
		CallbackData callback = new CallbackData(data,true);
		if(CallbackType.page.name().equalsIgnoreCase(callback.getObject())){
			List<Entry> entrys = callback.getEntry();
			if(entrys!=null && entrys.size()>0){
				for(Entry entry : entrys){
					List<Changes> changes = entry.getChanges();
					String entry_id      = entry.getId();
					if(changes!=null && changes.size()>0){
						for(Changes change : changes){
							Value value = change.getValue();
							if(value!=null){
								String post_id = value.getPost_id();
								String item    = value.getItem();
								logger.info(String.format("update itme %s post_id:%s", item,post_id));
								if(post_id!=null && "comment".equalsIgnoreCase(item)){
									for(FacebookSession session : facebookSessions.values()){
										if(session.getFeedMonitor()!=null && entry_id.equals(session.getFacebook_id()))
											session.getFeedMonitor().checkFeedUpdate(post_id);
									}
								}
							}else{
								logger.info("value is null");
							}
						}
					}else{
						logger.info("changes is null or size is 0");
					}
				}
			}else{
				logger.info("entrys is null or size is 0");
			}
		}else{
			logger.info("ignore not page update");
		}
	}
	
	public boolean appVerify(String hub_challenge,String hub_verify_token,String hub_mode){
		Collection<App> apps = AppManager.instance().getApps();
		for(App app : apps){
			if(app.appVerify(hub_challenge, hub_verify_token, hub_mode))
				return true;
		}
		
		return false;
	}

	public Map<String,FacebookSession> getFacebookSessions() {
		return facebookSessions;
	}
	
	public List<FacebookSession> getFacebookSessions(FacebookType type){
		List<FacebookSession> list = new ArrayList<FacebookSession>();
		for(FacebookSession session : getFacebookSessions().values()){
			if(session.getType()!=null && session.getType()==type){
				list.add(session);
			}
		}
		return list;
	}
	
	public FacebookSession getDefaultFacebookSessions(FacebookType type){
		List<FacebookSession> list = getFacebookSessions(type);
		return list.size()>0?list.get(0):null;
	}
	
	public void putFacebookSession(FacebookSession session){
		if(session!=null && session.getFacebook_id()!=null){
			FacebookSession session_ = getFacebookSession(session.getFacebook_id());
			if(session_!=null && session_!=session){
				session_.shutdown();
				removeFacebookSession(session_);
			}
			facebookSessions.put(session.getFacebook_id(), session);
		}
	}
	
	public FacebookSession getFacebookSession(String facebook_id){
		if(facebook_id!=null && facebookSessions.containsKey(facebook_id))
			return facebookSessions.get(facebook_id);
		return null;
	}
	
	public FacebookSession removeFacebookSession(FacebookSession session){
		if(session!=null && session.getFacebook_id()!=null)
			removeFacebookSession(session.getFacebook_id());
		return null;
	}
	
	public FacebookSession removeFacebookSession(String facebook_id){
		if(facebook_id!=null && facebookSessions.containsKey(facebook_id))
			return facebookSessions.remove(facebook_id);
		return null;
	}
}
