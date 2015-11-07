package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.List;

public class Message extends JsonObject {
	private List<Message> data;
	private Conversation conversation;
	private long created_time;
	private String id;
	private User from;
	private String message;
	private List<User> to;
	
	public Message(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}

	public Message(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<Message>();
				for(Object cu : list){
					data.add((Message)cu);
				}
			}
		}else if("to".equals(arg0)){
			List<Object> list = super.json2Objects(User.class, (String) arg1);
			if(list!=null && list.size()>0){
				to = new ArrayList<User>();
				for(Object cu : list){
					to.add((User)cu);
				}
			}
		}
	}

	public List<Message> getData() {
		return data;
	}

	public void setData(List<Message> data) {
		this.data = data;
	}
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<User> getTo() {
		return to;
	}

	public void setTo(List<User> to) {
		this.to = to;
	}
}
