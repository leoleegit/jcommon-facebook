package org.jcommon.com.facebook.test;

import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.AppManager;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.App;
import org.jcommon.com.facebook.permission.ExtendedPermissionV_2_5;
import org.jcommon.com.facebook.permission.Permission;
import org.jcommon.com.util.system.SystemListener;

public class GetAccessTokenTester implements SystemListener{
	private static Logger logger = Logger.getLogger(Tester.class);
	
	public static URL init_file_is = Tester.class.getResource("/facebook-log4j.xml");
	static{
	    if (init_file_is != null)
	      DOMConfigurator.configure(init_file_is);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//GetAccessTokenTester tester = new GetAccessTokenTester();
		//tester.startup();
		//String user_token = "CAACpWxUfDVwBAKIuybcYH1AClMnmbIPO29Hk4mC5qVdgXvEMqPtf2Eo1AvoHxZAHJZCzoooDPRsXhWZA01qF0b33SNBqIIP6JSqGweETvAP1M3SR54mEInrmb3xnvnbgoqRMghSEa86fuZBgRsEujvdNxUksiYypoyVh2DB2qEZAsQ3JwxrZCuNrZCV0dVpnVcZD";
		
		//AppManager.aboutMe(new AccessToken("",user_token));
//		App app = AppManager.instance().getDefaultApp();
//		String redirect_uri = "http://spotlightx.protel.com.hk:8080/facebook-core/fb.accesstoken";
//		String code = "AQA1_IhsjlbCekeWHbgQNtU8HsfLWqztg7NwSsDonelcX4oOr0jfRUr_RL2o3FZaB44_eqmqjtnSPakFRk5vPxLn9gT1nEQ5vs4YKxAk7MrtFkvjkIKHbzJZk6NXY8NJymIe3hzEtBMK5ozoV329gs-_ZSA6y9_pp9d2_rTTzC4Q4A8q3zFgxtWSOS-n8a7xK64D0-nBHRa2vjVmPP7WwvH0sLco-mZiSqyiF0kwscrRs8410wRLfiPR7IaKhhY0UFI509q-WYEJkqvV6EECgTBtl31dL1baVWIMzZBcSDwVd5mn0qhTfF9HCv1Gg7uHNuuPRq9L-fPAX5HXe1umAXoB";
//		AccessToken token = AppManager.getAccessToken(app, redirect_uri, code);
//		logger.info(token.getError().toJson());
		
		//String user_token = "CAAFInQP7JggBAEWKAQXJxcWt2GqSdt2OalN3TOxsbYZC2rbEkX1Q0RQTfURZA5qUHYDamJ4vAlmGkeXQWHzXyok4SMsfTdx9IFAKzTZBOIsPiLjmOXlxwVMR75IPSKZA4wZBjVNCCNuiAxctC7OiErLCTo0cLI1OnteaQM3msHCnvZCZBFZCvkkzWoi03cgBOtVX8PlTsU0sUgZDZD";
		//AccessToken token = new AccessToken("id",user_token);
		//List<AccessToken> tokens = AppManager.getAccessTokenList(token);
		
	
		
	}
	
	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		String app_id       = "228137200677950";
		String app_secret   = "9065df1533466afb8f14006dfbaf90dd";
		String verify_token = "verify_token";
		App app = generateApp(app_id,app_secret,verify_token,"SocialMM");
	    logger.info("add successful:"+AppManager.instance().addApp(app));
	    
	    String app_id2       = "1651604651754860";
		String app_secret2   = "7b8f0ad86bc151cc787a6dd3083a73ec";
		String verify_token2 = "verify_token";
		App app2 = generateApp(app_id2,app_secret2,verify_token2,"NowTV");
	    logger.info("add successful:"+AppManager.instance().addApp(app2));
	}
	
	public App generateApp(String app_id,String app_secret,String verify_token,String app_name){
		Permission[] permissions  = new Permission[]{
				ExtendedPermissionV_2_5.read_page_mailboxes,
				ExtendedPermissionV_2_5.publish_actions,
				ExtendedPermissionV_2_5.publish_pages,
				ExtendedPermissionV_2_5.manage_pages,
				ExtendedPermissionV_2_5.email};
		App app = new App(app_id, app_secret, verify_token);
		app.setApp_name(app_name);
	    app.setPermissions(permissions);
	    logger.info(app.toJson());
	    return app;
	}
}
