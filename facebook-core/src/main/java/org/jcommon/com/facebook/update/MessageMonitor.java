package org.jcommon.com.facebook.update;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jcommon.com.facebook.FacebookManager;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.object.Conversation;
import org.jcommon.com.facebook.object.Error;
import org.jcommon.com.facebook.object.JsonObject;
import org.jcommon.com.facebook.object.Message;
import org.jcommon.com.facebook.object.update.ConversationsUpdate;
import org.jcommon.com.facebook.utils.FixMap;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;

public class MessageMonitor extends ResponseHandler{
	private int  monitor_lenght    = FacebookManager.instance().getFacebookConfig().getMessage_monitor_lenght();
	private long monitor_frequency = FacebookManager.instance().getFacebookConfig().getMessage_monitor_frequency();
	private long start_time        = FacebookManager.instance().getFacebookConfig().getStart_time() / 1000;
	
	private static final String request_type        = "request_name";
	private static final int message_request        = 1;
	private static final int message_detail_request = 2;
	
	private static final String message_cache_updated = "message_cache_updated";
	
	private String facebook_id;
	private String access_token;
	private MessageMonitorListener listener;
	
	private boolean run;
	private Timer timer_graph = null;
	
	private FixMap<String, Long> conversations_list    = new FixMap<String, Long>(monitor_lenght);
	private LinkedList<String> message_cache  = new LinkedList<String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private final static int fix_size = 300;
		
		public boolean add(String e){
			boolean exist = false;
			if(e==null)
				return true;
			if(super.contains(e)){
				exist = true;
			}
			super.add(e);
			if(super.size()>fix_size)
				super.remove(0);
			return exist;
		}
	};
	
	public MessageMonitor(String facebook_id, String access_token){
		this.setFacebook_id(facebook_id);
		this.setAccess_token(access_token);
	}
	
	public MessageMonitor(String facebook_id, String access_token, MessageMonitorListener listener){
		this.setFacebook_id(facebook_id);
		this.setAccess_token(access_token);
		this.setListener(listener);
	}
	
	public void startup(){
		logger.info(facebook_id);
		run = true;
		timer_graph =  org.jcommon.com.util.thread.TimerTaskManger.instance().schedule("MessageMonitor-Graph", new TimerTask(){
	    	public void run(){
	    		if(!run)return;
	    	      
	    	    HttpRequest re = RequestFactory.messageUpdateReqeust(MessageMonitor.this, facebook_id, access_token, monitor_lenght);
	    	    MessageMonitor.this.addHandlerObject(re, ConversationsUpdate.class);
	    	    re.setAttribute(request_type, message_request);
	    	    ThreadManager.instance().execute(re);
	    	}
	    }, 5000L, monitor_frequency);
	}
	
	public void shutdown() {
		logger.info(facebook_id);
		run = false;
	    if (this.timer_graph != null) {
	        try {
		        this.timer_graph.cancel();
		        this.timer_graph = null;
	        } catch (Exception e) {
	    	    logger.error("", e);
	        }
	    }
	    conversations_list.clear();
	    message_cache.clear();
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
			case message_request:{
				messageHandler(paramHttpRequest,paramObject);
			}
			case message_detail_request:{
				conversationHandler(paramHttpRequest,paramObject);
			}
			default:{
				logger.warn("unknow type");
			}
		}
	}
	
	private void conversationHandler(HttpRequest paramHttpRequest, JsonObject paramObject){
		if(paramObject==null){
			logger.warn("conversation object is null");
			return;
		}
		if(paramObject instanceof Conversation){
			Conversation conversation  = (Conversation) paramObject;			
			long updated_time = (Long) paramHttpRequest.getAttibute(message_cache_updated);
			List<Message> messages = new ArrayList<Message>();
			
			Message message = conversation.getMessages();
			List<Message> data = message!=null?message.getData():null;
			if(data!=null && data.size()>0){	
				for(int i=data.size()-1;i>=0;i--){
					Message msg = data.get(i);
					if(msg.getCreated_time() > updated_time){
						if(!message_cache.add(msg.getId()))
							messages.add(msg);
					}
				}
			}
			
			if(messages.size()>0){
				logger.info("messages size:"+messages.size());
				if(listener!=null)
					listener.onMessage(conversation, messages);	
			}else{
				logger.info("no new messages come");
			}
		}else{
			logger.warn("error class type:"+paramObject);
		}
	}
	
	private void messageHandler(HttpRequest paramHttpRequest, JsonObject paramObject) {
		// TODO Auto-generated method stub
		if(paramObject==null){
			logger.warn("Conversations object is null");
			return;
		}
		
		if(paramObject instanceof ConversationsUpdate){
			ConversationsUpdate conversations = (ConversationsUpdate) paramObject;
			List<ConversationsUpdate> data = conversations.getData();
			if(data!=null && data.size()>0){
				if(conversations_list.size()==0){
					for(int i=data.size()-1;i>=0;i--){
						ConversationsUpdate conversation = data.get(i);
						if(conversation.getId()!=null && conversation.getUpdated_time()!=0){
							conversations_list.put(conversation.getId(), conversation.getUpdated_time());
						}
					}
					logger.info(String.format("conversations size:%s ; conversations list size:%s", data.size(),conversations_list.size()));
				}else{
					for(int i=data.size()-1;i>=0;i--){
						ConversationsUpdate conversation = data.get(i);
						checkConversationsUpdate(conversation);
					}
				}
			}else{
				logger.info("feed data is Empty");
			}
		}else{
			logger.warn("error class type:"+paramObject);
		}
	}
	
	public synchronized void checkConversationsUpdate(ConversationsUpdate conversation){
		if(conversation==null || conversation.getId()==null || conversation.getUpdated_time()==null)
			return;
		long updated_time       = conversation.getUpdated_time();
		long updated_time_cache = start_time;
		String id         = conversation.getId();
		
		if(!conversations_list.containsKey(id)){
			logger.info(String.format("new a conversations : %s", id));
			conversations_list.put(id, updated_time);
		}else{
			updated_time_cache = conversations_list.get(id);
		}
		if(FacebookManager.instance().getFacebookConfig().isDebug())
			logger.info(String.format("updated time:%s;updated_time_cache:%s", updated_time,updated_time_cache));
		
		if(updated_time > updated_time_cache){
			conversations_list.put(id, updated_time);
			logger.info(String.format("new or update one conversations : %s", id));
			HttpRequest re = RequestFactory.messageDetailRequest(this, id, access_token);
    	    addHandlerObject(re, Conversation.class);
    	    re.setAttribute(request_type, message_detail_request);
    	    re.setAttribute(message_cache_updated, updated_time_cache);
    	    ThreadManager.instance().execute(re);
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
	public MessageMonitorListener getListener() {
		return listener;
	}
	public void setListener(MessageMonitorListener listener) {
		this.listener = listener;
	}
}
