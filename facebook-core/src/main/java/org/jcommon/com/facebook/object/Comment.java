package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.List;

public class Comment extends JsonObject {
	private List<Comment> data;
	private Comment comments;
	private String id;
	private String message;
	private long created_time;
	private User from;
	
	public Comment(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}
	
	public Comment(String json) {
		super(json,true);
		// TODO Auto-generated constructor stub
	}

	public List<Comment> getData() {
		return data;
	}

	public void setData(List<Comment> data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<Comment>();
				for(Object cu : list){
					data.add((Comment)cu);
				}
			}
		}
	}

	public void setComments(Comment comments) {
		this.comments = comments;
	}

	public Comment getComments() {
		return comments;
	}
}
