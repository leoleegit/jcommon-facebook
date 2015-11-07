package org.jcommon.com.facebook.object.update;

import java.util.ArrayList;
import java.util.List;

import org.jcommon.com.facebook.object.JsonObject;

public class ConversationsUpdate extends JsonObject{
	private List<ConversationsUpdate> data;
	private MessageUpdate messages;
	private Long updated_time;
	private String id;
	
	public ConversationsUpdate(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}

	public ConversationsUpdate(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}
	
	public MessageUpdate getMessages() {
		return messages;
	}

	public void setMessages(MessageUpdate messages) {
		this.messages = messages;
	}

	public Long getUpdated_time() {
		if(messages!=null && messages.getData().size()>0){
			MessageUpdate comment = messages.getData().get(0);
			if(comment!=null && comment.getCreated_time() > updated_time){
				updated_time = comment.getCreated_time();
			}
		}
		return updated_time;
	}

	public void setUpdated_time(Long updated_time) {
		this.updated_time = updated_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(List<ConversationsUpdate> data) {
		this.data = data;
	}

	public List<ConversationsUpdate> getData() {
		return data;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<ConversationsUpdate>();
				for(Object cu : list){
					data.add((ConversationsUpdate)cu);
				}
			}
		}
	}
}
