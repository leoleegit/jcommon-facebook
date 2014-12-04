package org.jcommon.com.facebook.test;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.data.App;
import org.jcommon.com.facebook.servlet.GetAccessToken;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;

public class SubscriptionTest extends GetAccessToken implements HttpListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass());
	static SubscriptionTest ft = new SubscriptionTest();
	App app = new App("186208661474652","0bddb8fc5c19a89c4d65264161a20031");
	public static void main(String[] args) {
		ft.getAccessSecret();
	}
	
	public void getAccessSecret(){
		
		String permissions = "read_stream,manage_pages,publish_stream,offline_access,read_mailbox,read_page_mailboxes";
		app.setPermissions(permissions);
		HttpRequest re = RequestFactory.createGetAccessSecretReqeust(ft, app);
		ThreadManager.instance().execute(re);
	
	}
	
	public void subscription(String access_secret){
		String callback_url = "http://mg.protel.com.hk/jcommon-facebook/facebook.callback";
		String page_id = "271039552948235";
		app.setAccess_token(access_secret);
		HttpRequest re = RequestFactory.createSubscriptionReqeust(ft, app, page_id, callback_url, FacebookType.page);
		ThreadManager.instance().execute(re);
	}
	
	public void delSubscription(String access_secret){
		String page_id = "271039552948235";
		HttpRequest re = RequestFactory.createDelSubscriptionReqeust(ft, access_secret, page_id, FacebookType.page);
		ThreadManager.instance().execute(re);
	}
	
	@Override
	public void onSuccessful(HttpRequest reqeust, StringBuilder sResult) {
		// TODO Auto-generated method stub
		logger.info(sResult.toString());
		String access_secret = sResult.toString();
		if(access_secret.indexOf("access_token")!=-1){
			access_secret = sResult.toString().split("=")[1];
			logger.info(access_secret);
			subscription(access_secret);
			//delSubscription(access_secret);
		}else{
			logger.info(access_secret);
		}
		
	}

	@Override
	public void onFailure(HttpRequest reqeust, StringBuilder sResult) {
		// TODO Auto-generated method stub
		logger.info(sResult.toString());
	}

	@Override
	public void onTimeout(HttpRequest reqeust) {
		// TODO Auto-generated method stub
		logger.info("onTimeout");
	}

	@Override
	public void onException(HttpRequest reqeust, Exception e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
	}

}
