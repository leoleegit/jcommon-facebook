package org.jcommon.com.facebook.test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.FacebookManager;
import org.jcommon.com.facebook.FacebookSession;
import org.jcommon.com.facebook.FacebookSessionListener;
import org.jcommon.com.facebook.MediaManager;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.manager.MessageManager;
import org.jcommon.com.facebook.manager.PageManager;
import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Conversation;
import org.jcommon.com.facebook.object.Error;
import org.jcommon.com.facebook.object.Feed;
import org.jcommon.com.facebook.object.JsonObject;
import org.jcommon.com.facebook.object.Media;
import org.jcommon.com.facebook.object.Message;
import org.jcommon.com.facebook.object.Photo;
import org.jcommon.com.facebook.object.Text;
import org.jcommon.com.facebook.object.UrlObject;
import org.jcommon.com.facebook.object.Vedio;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.facebook.utils.FeedType;
import org.jcommon.com.util.http.ContentType;
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
//		String file_name     =  "图片1.jpg";
//		String file_id       = org.jcommon.com.util.BufferUtils.generateRandom(20);
//		String content_type  = ContentType.png.name;
//		
//		Media media = new Media(null);
//		media.setContent_type(content_type);
//		media.setMedia_id(file_id);
//		media.setMedia_name(file_name);
//		
//		java.io.File file  = MediaManager.instance().getMedia_factory().createEmptyFile(media);
//		try {
//			file.createNewFile();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		media.setMedia(file);
//		
//		String url = MediaManager.instance().getMedia_factory().createUrl(media).getUrl();
//		logger.info(url);
//		UrlObject obj = new UrlObject(url);
//		logger.info(new Photo("", obj.getUrl()).getMedia());
		
		// TODO Auto-generated method stub
		//FacebookManager.instance().getFacebookConfig().setDebug(true);
		//FacebookManager.instance().getFacebookConfig().setFeedMonitorEnable(false);
		//FacebookManager.instance().getFacebookConfig().setMessageMonitorEnable(false);
		//FacebookManager.instance().getFacebookConfig().setStart_time(0L);
		//FacebookManager.instance().getFacebookConfig().setFeed_monitor_lenght(5);
		
		new PageManagerTester().startup();
		//Thread.sleep(11000L);
		//postText(session.getPageManager());
		//postPhoto(session.getPageManager());
		//postVedio(session.getPageManager());
		
		//postMessage(session.getMessageManager());
		
//		String data = "{\"object\":\"page\",\"entry\":[{\"id\":\"271039552948235\",\"time\":1446829106,\"changes\":[{\"field\":\"feed\",\"value\":{\"item\":\"comment\",\"verb\":\"add\",\"comment_id\":\"928837367168447_1065235330195316\",\"post_id\":\"271039552948235_1065271373525045\",\"parent_id\":\"271039552948235_928837383835112\",\"sender_id\":271039552948235,\"created_time\":1446829106,\"message\":\"hi men\",\"sender_name\":\"Newton\"}}]}]}";
//		FacebookManager.instance().onCallback(data);
	}
	
	public static void postMessage(MessageManager page){
		String conversation_id = "t_id.312177318835636";
		page.sendMessage(conversation_id, new Text("hello text!"), listener);
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
		if(feed.getFeedType()==FeedType.photo)
			logger.info(feed.getPicture());
		logger.info(feed.getLink());
		logger.info(feed.getActions());
	}
	
	@Override
	public void onComment(Feed feed, Comment comment) {
		// TODO Auto-generated method stub
		logger.info(String.format("feed %s --> %s", feed.getId(),comment.toJson()));
	}
	
	@Override
	public void onMessage(Conversation conversation, Message messages) {
		// TODO Auto-generated method stub
		logger.info(String.format("conversation %s --> %s", conversation.getId(),messages.toJson()));
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
		String facebook_id   = "882148571903890";
		String access_token  = "EAACZAGu9RCzsBAGkiA5cMyEJuCrF0DPWAMhva4PtePa8egZCnFQfDtdrQJkhqRNtmgWh7P9gE0C1rRmjg6m8CBIMcECLNUJmtN0ZAQkTd2TRrQgApZCWVvFRJ1rEu08lrv999zEgw3IdZAVtqqYsjKGMebiOI3l3kTa2SRjZAHrwZDZD";
		listener= new PageManagerTester();
		session = new FacebookSession(listener, facebook_id, access_token, FacebookType.message);
		session.startup();
		
	}

	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return false;
	}

}
