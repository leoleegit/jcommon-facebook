package org.jcommon.com.facebook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
	private AccessToken access_token;
	private FacebookType type;
	private FacebookSessionListener listener;
	
	private FeedMonitor feedMonitor;
	
	private Properties properties = new Properties();
	private Map<Object, Object> attributes = new HashMap<Object, Object>();
	
	private AlbumManager albumManager;
	private PageManager  pageManager;
	
	public FacebookSession(String facebook_id,String access_token,FacebookType type){
		this.facebook_id  = facebook_id;
		this.type         = type;
		
		setAccess_token(new AccessToken(facebook_id,access_token));
		setAlbumManager(new AlbumManager(this));
		setPageManager(new PageManager(this));
		setFeedMonitor(new FeedMonitor(facebook_id,access_token,this));
	}
	
	public FacebookSession(FacebookSessionListener listener, String facebook_id,String access_token,FacebookType type){
		this(facebook_id,access_token,type);
		this.setListener(listener);
	}
	
	public void startup(){
		logger.info(facebook_id);
		if(feedMonitor!=null && FacebookType.page == type)
			feedMonitor.startup();
		if(albumManager!=null)
			albumManager.getAlbums();
	}
	
	public void shutdown(){
		logger.info(facebook_id);
		if(feedMonitor!=null && FacebookType.page == type)
			feedMonitor.shutdown();
	}
	
	@Override
	public void onFeed(Feed feed) {
		// TODO Auto-generated method stub
		logger.info(String.format("new feed %s coming", feed.getId()));
		if(listener!=null){
			listener.onFeed(feed);
		}
	}
	
	@Override
	public void onComment(Feed feed, List<Comment> comments) {
		// TODO Auto-generated method stub
		logger.info(String.format("feed %s get update comment size : ", feed.getId(), comments.size()));
		if(listener!=null){
			for(Comment comment : comments){
				listener.onComment(feed, comment);
			}
		}
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
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

	public void setAccess_token(AccessToken access_token) {
		this.access_token = access_token;
	}

	public AccessToken getAccess_token() {
		return access_token;
	}

	public void setListener(FacebookSessionListener listener) {
		this.listener = listener;
	}

	public FacebookSessionListener getListener() {
		return listener;
	}

	public void setAttribute(Object key, Object value){
		attributes.put(key, value);
	}
	
	public Object getAttribute(Object key){
		if(attributes.containsKey(key))
			return attributes.get(key);
		return null;
	}
	
	public Object removeAttribute(String key){
		if(properties.containsKey(key))
			return attributes.remove(key);
		return null;
	}
	
	public void setProperty(String key, String value){
		properties.put(key, value);
	}
	
	public String getProperty(String key){
		if(properties.containsKey(key))
			return properties.getProperty(key);
		return null;
	}
	
	public String removeProperty(String key){
		if(properties.containsKey(key))
			return (String) properties.remove(key);
		return null;
	}
}
