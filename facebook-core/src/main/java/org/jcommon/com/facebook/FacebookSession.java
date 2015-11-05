package org.jcommon.com.facebook;

import java.util.List;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.manager.AlbumManager;
import org.jcommon.com.facebook.manager.PageManager;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Feed;
import org.jcommon.com.facebook.update.FeedMonitor;
import org.jcommon.com.facebook.update.FeedMonitorListener;
import org.jcommon.com.facebook.utils.FacebookType;

public class FacebookSession implements FeedMonitorListener{
	protected Logger logger = Logger.getLogger(getClass());
	
	private String facebook_id;
	private String access_token;
	private FacebookType type;
	
	private FeedMonitor feedMonitor;
	
	private AlbumManager albumManager;
	private PageManager  pageManager;
	
	public FacebookSession(String facebook_id,String access_token,FacebookType type){
		this.facebook_id  = facebook_id;
		this.access_token = access_token;
		this.type         = type;
		
		AccessToken token = new AccessToken(facebook_id,access_token);
		
		setAlbumManager(new AlbumManager(facebook_id,token));
		setPageManager(new PageManager(facebook_id,token));
		setFeedMonitor(new FeedMonitor(facebook_id,access_token,this));
	}
	
	public void startup(){
		logger.info(facebook_id);
		if(feedMonitor!=null && FacebookType.page == type)
			feedMonitor.startup();
	}
	
	public void shutdown(){
		logger.info(facebook_id);
		if(feedMonitor!=null && FacebookType.page == type)
			feedMonitor.shutdown();
	}
	
	@Override
	public void onFeed(Feed feed) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onComment(Feed feed, List<Comment> comments) {
		// TODO Auto-generated method stub
		
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public FacebookType getType() {
		return type;
	}

	public void setType(FacebookType type) {
		this.type = type;
	}

	public AlbumManager getAlbumManager() {
		return albumManager;
	}

	public void setAlbumManager(AlbumManager albumManager) {
		this.albumManager = albumManager;
	}

	public PageManager getPageManager() {
		return pageManager;
	}

	public void setPageManager(PageManager pageManager) {
		this.pageManager = pageManager;
	}

	public FeedMonitor getFeedMonitor() {
		return feedMonitor;
	}

	public void setFeedMonitor(FeedMonitor feedMonitor) {
		this.feedMonitor = feedMonitor;
	}

}
