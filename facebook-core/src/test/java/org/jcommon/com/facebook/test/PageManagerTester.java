package org.jcommon.com.facebook.test;

import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.FacebookManager;
import org.jcommon.com.facebook.FacebookSession;
import org.jcommon.com.facebook.FacebookSessionListener;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.manager.PageManager;
import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Error;
import org.jcommon.com.facebook.object.Feed;
import org.jcommon.com.facebook.object.JsonObject;
import org.jcommon.com.facebook.object.Photo;
import org.jcommon.com.facebook.object.Text;
import org.jcommon.com.facebook.object.UrlObject;
import org.jcommon.com.facebook.object.Vedio;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.system.SystemListener;

public class PageManagerTester extends ResponseHandler implements FacebookSessionListener, SystemListener{
	private static Logger logger = Logger.getLogger(Tester.class);
	
	public static URL init_file_is = Tester.class.getResource("/facebook-log4j.xml");
	static{
	    if (init_file_is != null)
	      DOMConfigurator.configure(init_file_is);
	}
	
	private static FacebookSession session;
	private static PageManagerTester listener;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
//		FacebookManager.instance().getFacebookConfig().setDebug(true);
//		FacebookManager.instance().getFacebookConfig().setFeedMonitorEnable(false);
//		FacebookManager.instance().getFacebookConfig().setStart_time(0L);
//		FacebookManager.instance().getFacebookConfig().setMonitor_lenght(5);
		
		new PageManagerTester().startup();
		Thread.sleep(11000L);
		//postText(session.getPageManager());
		//postPhoto(session.getPageManager());
		//postVedio(session.getPageManager());
		
		String data = "{\"object\":\"page\",\"entry\":[{\"id\":\"271039552948235\",\"time\":1446829106,\"changes\":[{\"field\":\"feed\",\"value\":{\"item\":\"comment\",\"verb\":\"add\",\"comment_id\":\"928837367168447_1065235330195316\",\"post_id\":\"271039552948235_1065271373525045\",\"parent_id\":\"271039552948235_928837383835112\",\"sender_id\":271039552948235,\"created_time\":1446829106,\"message\":\"hi men\",\"sender_name\":\"Newton\"}}]}]}";
		FacebookManager.instance().onCallback(data);
	}
	
	public static void postText(PageManager page){
		page.publishText(new Text("hello text!"), listener);
	}
	
	public static void postComment(PageManager page){
		page.publishComment("271039552948235_1065058233546359",new Text("hello 271039552948235_1065058233546359!"), listener);
	}
	
	public static void postPhoto(PageManager page){
		String photo_object = "{\"url\": \"%2F634546cf1fbb14c2a8abc986dba3da6e%2F%E5%9B%BE%E7%89%871.jpg\"}";
		UrlObject url = new UrlObject(photo_object);
		System.out.print(url.getUrl());

		Photo photo = new Photo("hello photo",url.getUrl());
		page.publishPhoto(photo, listener);
	}
	
	public static void postVedio(PageManager page){
		String photo_object = "{\"url\": \"%2F7069100727988ae093e0fc635f606548%2F1190239_592640700788117_6454_n.mp4\"}";
		UrlObject url = new UrlObject(photo_object);
		System.out.print(url.getUrl());

		Vedio vedio = new Vedio("hello vedio","description",url.getUrl());
		page.publishVedio(vedio, listener);
	}
	
	@Override
	public void onFeed(Feed feed) {
		// TODO Auto-generated method stub
		logger.info(String.format("new feed (type:%s) : %s ",feed.getFeedType(), feed.toJson()));
		//logger.info(feed.getPicture());
		//logger.info(feed.getLink());
	}
	
	@Override
	public void onComment(Feed feed, Comment comment) {
		// TODO Auto-generated method stub
		logger.info(String.format("feed %s --> %s", feed.getId(),comment.toJson()));
	}

	@Override
	public void onError(HttpRequest paramHttpRequest, Error paramError) {
		// TODO Auto-generated method stub
		logger.info(paramError.toJson());
	}

	@Override
	public void onOk(HttpRequest paramHttpRequest, JsonObject paramObject) {
		// TODO Auto-generated method stub
		logger.info(paramObject.toJson());
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		session.shutdown();
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		String facebook_id   = "271039552948235";
		String access_token  = "CAACpWxUfDVwBAJhKoNZATZBvhLqa25vTnmrqXkxMuGmNdrEYdkQlNs2i9o3WyZBMPZAatxii0Mj9VDQvqkVgkrZBIpOZA9UlqZAtVrStTyhvLzULmkKD1PIu9ZB1wX1QaiPZCLNDqX22vQwerU8xPLMGt5KB0DNFO00NYQAIEi8e5UotcZATxoAt0d";
		listener= new PageManagerTester();
		session = new FacebookSession(listener, facebook_id, access_token, FacebookType.page);
		session.startup();
		
	}

	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
