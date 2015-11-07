package org.jcommon.com.facebook.test;

import java.net.URL;

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
		String user_token = "CAACpWxUfDVwBAKIuybcYH1AClMnmbIPO29Hk4mC5qVdgXvEMqPtf2Eo1AvoHxZAHJZCzoooDPRsXhWZA01qF0b33SNBqIIP6JSqGweETvAP1M3SR54mEInrmb3xnvnbgoqRMghSEa86fuZBgRsEujvdNxUksiYypoyVh2DB2qEZAsQ3JwxrZCuNrZCV0dVpnVcZD";
		
		AppManager.aboutMe(new AccessToken("",user_token));
//		App app = AppManager.instance().getDefaultApp();
//		String redirect_uri = "http://spotlightx.protel.com.hk:8080/facebook-core/fb.accesstoken";
//		String code = "AQA1_IhsjlbCekeWHbgQNtU8HsfLWqztg7NwSsDonelcX4oOr0jfRUr_RL2o3FZaB44_eqmqjtnSPakFRk5vPxLn9gT1nEQ5vs4YKxAk7MrtFkvjkIKHbzJZk6NXY8NJymIe3hzEtBMK5ozoV329gs-_ZSA6y9_pp9d2_rTTzC4Q4A8q3zFgxtWSOS-n8a7xK64D0-nBHRa2vjVmPP7WwvH0sLco-mZiSqyiF0kwscrRs8410wRLfiPR7IaKhhY0UFI509q-WYEJkqvV6EECgTBtl31dL1baVWIMzZBcSDwVd5mn0qhTfF9HCv1Gg7uHNuuPRq9L-fPAX5HXe1umAXoB";
//		AccessToken token = AppManager.getAccessToken(app, redirect_uri, code);
//		logger.info(token.getError().toJson());
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
		String app_id       = "186208661474652";
		String app_secret   = "0bddb8fc5c19a89c4d65264161a20031";
		String verify_token = "verify_token";
		Permission[] permissions  = new Permission[]{
				ExtendedPermissionV_2_5.email,
				ExtendedPermissionV_2_5.read_page_mailboxes,
				ExtendedPermissionV_2_5.publish_actions,
				ExtendedPermissionV_2_5.publish_pages,
				ExtendedPermissionV_2_5.manage_pages};
		App app = new App(app_id, app_secret, verify_token);
	    app.setPermissions(permissions);
	    logger.info(app.toJson());
	    logger.info("add successful:"+AppManager.instance().addApp(app));
	}

}
