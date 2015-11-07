package org.jcommon.com.facebook.test;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.FacebookManager;

public class onCallbackTester {
private static Logger logger = Logger.getLogger(Tester.class);
	
	public static URL init_file_is = Tester.class.getResource("/facebook-log4j.xml");
	static{
	    if (init_file_is != null)
	      DOMConfigurator.configure(init_file_is);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data = "{\"object\":\"page\",\"entry\":[{\"id\":\"271039552948235\",\"time\":1446829106,\"changes\":[{\"field\":\"feed\",\"value\":{\"item\":\"comment\",\"verb\":\"add\",\"comment_id\":\"928837367168447_1065235330195316\",\"post_id\":\"271039552948235_928837367168447\",\"parent_id\":\"271039552948235_928837383835112\",\"sender_id\":271039552948235,\"created_time\":1446829106,\"message\":\"hi men\",\"sender_name\":\"Newton\"}}]}]}";
		FacebookManager.instance().onCallback(data);
	}

}
