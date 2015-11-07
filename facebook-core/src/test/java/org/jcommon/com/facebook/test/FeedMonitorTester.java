package org.jcommon.com.facebook.test;

import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.FacebookManager;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Feed;
import org.jcommon.com.facebook.object.update.FeedUpdate;
import org.jcommon.com.facebook.update.FeedMonitor;
import org.jcommon.com.facebook.update.FeedMonitorListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.system.SystemListener;

public class FeedMonitorTester implements SystemListener, FeedMonitorListener{
private static Logger logger = Logger.getLogger(Tester.class);
	
	public static URL init_file_is = Tester.class.getResource("/facebook-log4j.xml");
	static{
	    if (init_file_is != null)
	      DOMConfigurator.configure(init_file_is);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(1446793604834L / 1000);
		//new FacebookManager().getFacebookConfig().setDebug(true);
		String facebook_id   = "271039552948235";
		String access_token  = "CAACpWxUfDVwBAJhKoNZATZBvhLqa25vTnmrqXkxMuGmNdrEYdkQlNs2i9o3WyZBMPZAatxii0Mj9VDQvqkVgkrZBIpOZA9UlqZAtVrStTyhvLzULmkKD1PIu9ZB1wX1QaiPZCLNDqX22vQwerU8xPLMGt5KB0DNFO00NYQAIEi8e5UotcZATxoAt0d";
		new FeedMonitor(facebook_id,access_token,new FeedMonitorTester()).startup();
		
		//feedUpdateTest();
	}
	
	public static void feedUpdateTest(){
		String feed_id   = "271039552948235_1065075213544661";
		String access_token  = "CAACpWxUfDVwBAJhKoNZATZBvhLqa25vTnmrqXkxMuGmNdrEYdkQlNs2i9o3WyZBMPZAatxii0Mj9VDQvqkVgkrZBIpOZA9UlqZAtVrStTyhvLzULmkKD1PIu9ZB1wX1QaiPZCLNDqX22vQwerU8xPLMGt5KB0DNFO00NYQAIEi8e5UotcZATxoAt0d";
		HttpRequest re = RequestFactory.feedUpdateReqeust(null, feed_id, access_token);
		re.run();
		
		String str = re.getResult();
		logger.info(str);
		FeedUpdate update = new FeedUpdate(str);
		logger.info(update.getComments().getData());
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
		
	}

	@Override
	public void onFeed(Feed feed) {
		// TODO Auto-generated method stub
		logger.info(String.format("new feed (type:%s) : %s  coming",feed.getFeedType(), feed.toJson()));
		logger.info(feed.getPicture());
		logger.info(feed.getLink());
	}
	
	@Override
	public void onComment(Feed feed, List<Comment> comments) {
		// TODO Auto-generated method stub
		logger.info(String.format("feed %s get update comment size : %s", feed.getId(), comments.size()));
		for(Comment comment : comments){
			logger.info(comment.toJson());
		}
	}

}
