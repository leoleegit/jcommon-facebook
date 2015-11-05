package org.jcommon.com.facebook.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jcommon.com.facebook.FacebookManager;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Error;
import org.jcommon.com.facebook.object.Feed;
import org.jcommon.com.facebook.object.JsonObject;
import org.jcommon.com.facebook.object.update.FeedUpdate;
import org.jcommon.com.facebook.utils.FixMap;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;

public class FeedMonitor extends ResponseHandler{
	private static final int  monitor_lenght    = 100;
	private static final long monitor_frequency = 10000L;
	
	private static final String request_type      = "request_name";
	private static final int feed_request         = 1;
	private static final int feed_detail_request  = 2;
	private static final int commnet_request      = 3;
	
	private static final String comment_cache_updated = "comment_cache_updated";
	private String facebook_id;
	private String access_token;
	private FeedMonitorListener listener;
	
	private boolean run;
	private Timer timer_graph = null;
	private long start_time   = FacebookManager.instance().getFacebookConfig().getStart_time();
	private FixMap<String, Long> post_list    = new FixMap<String, Long>(monitor_lenght);
	
	public FeedMonitor(String facebook_id, String access_token){
		this.setFacebook_id(facebook_id);
		this.setAccess_token(access_token);
	}
	
	public FeedMonitor(String facebook_id, String access_token, FeedMonitorListener listener){
		this.setFacebook_id(facebook_id);
		this.setAccess_token(access_token);
		this.setListener(listener);
	}
	
	public void startup(){
		logger.info(facebook_id);
		timer_graph =  org.jcommon.com.util.thread.TimerTaskManger.instance().schedule("FeedMonitor-Graph", new TimerTask(){
	    	public void run(){
	    		if(!run)return;
	    	      
	    	    HttpRequest re = RequestFactory.feedUpdateReqeust(FeedMonitor.this, facebook_id, access_token, monitor_lenght);
	    	    FeedMonitor.this.addHandlerObject(re, FeedUpdate.class);
	    	    re.setAttribute(request_type, feed_request);
	    	    ThreadManager.instance().execute(re);
	    	}
	    }, 5000L, monitor_frequency);
	}
	
	public void shutdown() {
		logger.info(facebook_id);
		this.run = false;
	    if (this.timer_graph != null) {
	        try {
		        this.timer_graph.cancel();
		        this.timer_graph = null;
	        } catch (Exception e) {
	    	    logger.error("", e);
	        }
	    }
	}
	
	@Override
	public void onError(HttpRequest paramHttpRequest, Error paramError) {
		// TODO Auto-generated method stub
		logger.error(paramError.toJson());
	}

	@Override
	public void onOk(HttpRequest paramHttpRequest, JsonObject paramObject) {
		// TODO Auto-generated method stub
		if(!run){
			logger.info("this monitor have been shutdown.");
			return;
		}
		int type = (Integer) paramHttpRequest.getAttibute(request_type);
		switch(type){
			case feed_request:{
				feedHandler(paramHttpRequest,paramObject);
				break;
			}case feed_detail_request:{
				if(paramObject==null){
					logger.warn("feed object is null");
					break;
				}
				if(paramObject instanceof Feed){
					Feed feed = (Feed) paramObject;
					if(feed!=null && listener!=null){
						listener.onFeed(feed);
					}
				}else{
					logger.warn("error class type:"+paramObject);
				}
				break;
			}
			case commnet_request:{
				commentHandler(paramHttpRequest,paramObject);
				break;
			}
			default:{
				logger.warn("unknow type");
			}
		}
	}
	
	private void commentHandler(HttpRequest paramHttpRequest, JsonObject paramObject){
		if(paramObject==null){
			logger.warn("comment object is null");
			return;
		}
		if(paramObject instanceof Feed){
			Feed feed  = (Feed) paramObject;
			
			List<Comment> data= feed.getCommentList();
			
			if(data!=null && data.size()>0){
				List<Comment> comments = new ArrayList<Comment>();
				long updated_time = (Long) paramHttpRequest.getAttibute(comment_cache_updated);
				for(int i=data.size()-1;i>=0;i--){
					Comment comment = data.get(i);
					if(comment.getCreated_time() > updated_time)
						comments.add(comment);
				}
				if(comments.size()>0){
					logger.info("comments size:"+comments.size());
					if(listener!=null)
						listener.onComment(feed, comments);
				}else{
					logger.info("no new comment come");
				}
			}else{
				logger.info("feed data is Empty");
			}
		}else{
			logger.warn("error class type:"+paramObject);
		}
	}
	
	private void feedHandler(HttpRequest paramHttpRequest, JsonObject paramObject){
		if(paramObject==null){
			logger.warn("feed object is null");
			return;
		}
		if(paramObject instanceof FeedUpdate){
			FeedUpdate feeds = (FeedUpdate) paramObject;
			List<FeedUpdate> data = feeds.getData();
			if(data!=null && data.size()>0){
				if(post_list.size()==0){
					for(int i=data.size()-1;i>=0;i--){
						FeedUpdate feed = data.get(i);
						if(feed.getId()!=null && feed.getUpdated_time()!=0){
							post_list.put(feed.getId(), feed.getUpdated_time());
						}
					}
					logger.info(String.format("feed size:%s ; post list size:%s", data.size(),post_list.size()));
				}else{
					for(int i=data.size()-1;i>=0;i--){
						FeedUpdate feed = data.get(i);
						long updated_time       = feed.getUpdated_time();
						long updated_time_cache = start_time;
						String id         = feed.getId();
						if(!post_list.containsKey(id)){
							logger.info(String.format("new a feed : %s", id));
							HttpRequest re = RequestFactory.getFeedDetailRequest(this, id, access_token);
				    	    FeedMonitor.this.addHandlerObject(re, Feed.class);
				    	    re.setAttribute(request_type, feed_detail_request);
				    	    ThreadManager.instance().execute(re);
						}else{
							updated_time_cache = post_list.get(id);
						}
						
						if(updated_time > updated_time_cache){
							post_list.put(id, updated_time);
							logger.info(String.format("new or update one feed : %s", id));
							HttpRequest re = RequestFactory.getCommentsRequest(this, id, access_token);
				    	    FeedMonitor.this.addHandlerObject(re, Feed.class);
				    	    re.setAttribute(request_type, commnet_request);
				    	    re.setAttribute(comment_cache_updated, updated_time_cache);
				    	    ThreadManager.instance().execute(re);
						}
					}
				}
			}else{
				logger.info("feed data is Empty");
			}
		}else{
			logger.warn("error class type:"+paramObject);
		}
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

	public FeedMonitorListener getListener() {
		return listener;
	}

	public void setListener(FeedMonitorListener listener) {
		this.listener = listener;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

}
