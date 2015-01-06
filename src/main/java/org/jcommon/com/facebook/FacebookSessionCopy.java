package org.jcommon.com.facebook;

import org.jcommon.com.facebook.data.Comment;
import org.jcommon.com.facebook.data.Feed;
import org.jcommon.com.facebook.data.Message;
import org.jcommon.com.facebook.data.Proxy;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;

public class FacebookSessionCopy extends FacebookSession implements PageListener{
	private String callback;
	public final static String copy = "Facebook.Session.Copy";
	
	public FacebookSessionCopy(String facebook_id, String access_token, String callback) {
		super(facebook_id, access_token, null);
		// TODO Auto-generated constructor stub
		addListnener(this);
		this.callback = callback;
		logger.info(callback);
	}

	@Override
	public void onPosts(Feed paramFeed) throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramFeed.getJson());
		Proxy proxy = new Proxy(facebook_id,paramFeed,null,null);
		callback(proxy.toJson());
	}

	@Override
	public void onComments(Feed paramFeed, Comment paramComment)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Feed:" + paramFeed.getJson() + "\nComment:"
				+ paramComment.getJson());
		Proxy proxy = new Proxy(facebook_id,paramFeed,paramComment,null);
		callback(proxy.toJson());
	}

	@Override
	public void onMessages(Message paramMessage) throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramMessage.getJson());
		Proxy proxy = new Proxy(facebook_id,null,null,paramMessage);
		callback(proxy.toJson());
	}
	
	private void callback(String xml){
		logger.info(xml);
		if(callback!=null && xml!=null){
			String[] keys   = { "hub.mode"};
			String[] values = { copy };
			String    url   = JsonUtils.toRequestURL(callback, keys, values);
			HttpRequest request = new HttpRequest(url,org.jcommon.com.util.CoderUtils.encode(xml),"POST",this);
			ThreadManager.instance().execute(request);
		}
	}
	
	public void onSuccessful(HttpRequest reqeust, StringBuilder sResult){
		logger.info(sResult);
	}
	
	public void onFailure(HttpRequest reqeust, StringBuilder sResult){
		logger.info(sResult);
	}
	
    public void onTimeout(HttpRequest request){
    	logger.error(callback);
    }

    public void onException(HttpRequest request, Exception e){
    	logger.error(callback, e);
    }
}
