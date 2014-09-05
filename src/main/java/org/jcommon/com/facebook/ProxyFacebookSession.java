package org.jcommon.com.facebook;

import org.jcommon.com.facebook.cache.SessionCache;
import org.jcommon.com.facebook.data.Comment;
import org.jcommon.com.facebook.data.Feed;
import org.jcommon.com.facebook.data.Message;
import org.jcommon.com.facebook.data.Proxy;
import org.jcommon.com.facebook.seesion.FacebookSession;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;

public class ProxyFacebookSession extends FacebookSession implements PageListener{
	private String callback;
	private String id;
	public ProxyFacebookSession(String page_id, String access_token,
			PageListener listnener) {
		super(page_id, access_token, listnener);
		// TODO Auto-generated constructor stub
	}
	
	public ProxyFacebookSession(String page_id, String access_token, String callback) {
		super(page_id, access_token, null);
		// TODO Auto-generated constructor stub
		this.id = page_id;
		this.callback = callback;
		addListnener(this);
		if(SessionCache.instance().getSession(page_id)!=null){
			SessionCache.instance().getSession(page_id).addListnener(this);
			String id = page_id + "-" + org.jcommon.com.util.BufferUtils.generateRandom(8);
			setSessionID(id);
			login(FacebookType.user);
		}
		else
			login(FacebookType.page);		
	}
	
	public void logout() {
		super.logout();
		logger.info("logout proxy session:"+id);
		if(SessionCache.instance().getSession(id)!=null)
			SessionCache.instance().getSession(id).removeListnener(this);		
	}

	@Override
	public void onPosts(Feed paramFeed) throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramFeed.getJsonData());
		Proxy proxy = new Proxy(id,paramFeed,null,null);
		callback(proxy.toJsonStr());
	}

	@Override
	public void onComments(Feed paramFeed, Comment paramComment)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Feed:"+paramFeed.getJsonData() +"\nComment:"+paramComment.getJsonData());
		Proxy proxy = new Proxy(id,paramFeed,paramComment,null);
		callback(proxy.toJsonStr());
	}

	@Override
	public void onMessages(Message paramMessage) throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramMessage.getJsonData());
		Proxy proxy = new Proxy(id,null,null,paramMessage);
		callback(proxy.toJsonStr());
	}
	
	private void callback(String xml){
		logger.info(xml);
		if(callback!=null){
			HttpRequest request = new HttpRequest(callback,xml,"POST",this);
			ThreadManager.instance().execute(request);
		}
	}

}
