package org.jcommon.com.facebook.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.FacebookSession;
import org.jcommon.com.facebook.PageListener;
import org.jcommon.com.facebook.RequestCallback;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.cache.SessionCache;
import org.jcommon.com.facebook.data.Comment;
import org.jcommon.com.facebook.data.Error;
import org.jcommon.com.facebook.data.Feed;
import org.jcommon.com.facebook.data.Message;
import org.jcommon.com.facebook.seesion.FeedMonitor;
import org.jcommon.com.facebook.servlet.GetAccessToken;
import org.jcommon.com.facebook.utils.FacebookUtils;
import org.jcommon.com.util.http.FileRequest;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;
import org.json.JSONException;

public class FacebookUtilsTest extends GetAccessToken implements PageListener, RequestCallback, HttpListener{
	private Logger logger = Logger.getLogger(this.getClass());
	static FacebookUtilsTest ft = new FacebookUtilsTest();
	public static void main(String[] args) throws JSONException, FileNotFoundException{
//		String str = "{\"data\": [{\"name\": \"aa\",\"fql_result_set\": [{\"name\": \"Newton\",\"id\": 271039552948235},{\"name\": \"Row Ti\",\"id\": 100003226166163}]},{\"name\": \"bb\",\"fql_result_set\": [{\"id\": \"581811248537729_95954866\",\"text\": \"661\",\"time\": 1373947325,\"fromid\": 271039552948235},"+
//            "{\"id\": \"581811248537729_95955421\",\"text\": \"fsadf\",\"time\": 1373964268,\"fromid\": 100003226166163}]}]}";
//		System.out.println(FacebookUtils.mergeFqlResult(str, "id"));
		new GetAccessToken();
		
//		String page_id = "271039552948235";
//		String access_token = "CAACpWxUfDVwBAIYoneAOh7I4V0e9N00l5H7vmcSuNBDTlI6CQbLii3a3gNZAGQcITWjH8nDJXUnRY1iBS77sLySTHiVPHYd0YkYRCDD6M4H0SAIKdRxDUyZAwUYzjUwz4x1kPSE0ZBhpHMY2r5fvzpfwtqv0hjQe1pLsUnDEedc9U7pDnN1";
		String page_id = "271039552948235";
		String access_token = "CAACEdEose0cBAKFD9pe3XfRVXIZC5EY84p5dQn4rauOATfbL0dG8qajrMH0M3lmMzjt0NUwqd6HNVSIw0nclC5RY8zLjCKmZBVtirIjIDC1HnrCODCDBHR3HMr1UZCCQZBMC6ccNM8Oy2XFZCfChB8NrrvJBDTT5Rn0PqegyNnVc2uJ4zGGzfa6PMycouoIQUre8VNBYlPvS8fhAa9fPH";

		//FacebookSession session = new FacebookSession(page_id, access_token, ft);
		//session.setWall_album_id("466553496730172");
		//File file = new File("C:/Users/Administrator/Downloads/glasswtmk.jpg");
		//session.postPhoto2Wall(ft, file, "test upload pic",  true);
		//System.out.println("========"+session.getWallAlbum().getJsonData());
//		//String url = "https://graph.facebook.com/466553496730172/photos";
//		String message = "description1";
//		
//		//url = url + "?access_token="+access_token+"&message="+message;
//		//new FileRequest(url,HttpRequest.POST,ft,true,file).run();
//		RequestFactory.createPostPhotoRequest(ft, "466553496730172", file, message, access_token).run();
		
		
//		File file = new File("C:/Users/Administrator/Downloads/1190239_592640700788117_6454_n.mp4");
//		String description = "description1";
//		String title = "title";
//		RequestFactory.createPostVideoRequest(ft, page_id, file, title, description, access_token).run();
		
//		HttpRequest re = RequestFactory.createGetFeedUpdateReqeustByFql(new FacebookUtilsTest(), page_id, access_token, 100);
//	    ThreadManager.instance().execute(re);
		
	}

	@Override
	public void onPosts(Feed paramFeed) throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramFeed);
	}
	@Override
	public void onComments(Feed paramFeed, Comment paramComment)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramFeed);
	}

	@Override
	public void onMessages(Message paramMessage) throws Exception {
		// TODO Auto-generated method stub
		logger.info(paramMessage);
	}

	@Override
	public void reqeustSuccessful(HttpRequest paramHttpRequest,
			StringBuilder paramStringBuilder) {
		// TODO Auto-generated method stub
		logger.info(paramStringBuilder.toString());
	}

	@Override
	public void requestFailure(HttpRequest paramHttpRequest, Error paramError) {
		// TODO Auto-generated method stub
		logger.info(paramError.getJsonData());
	}

	@Override
	public void onSuccessful(HttpRequest reqeust, StringBuilder sResult) {
		// TODO Auto-generated method stub
		logger.info(sResult.toString());
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
