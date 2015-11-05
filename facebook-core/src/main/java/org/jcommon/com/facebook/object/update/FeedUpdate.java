package org.jcommon.com.facebook.object.update;

import java.util.ArrayList;
import java.util.List;

import org.jcommon.com.facebook.object.JsonObject;


public class FeedUpdate extends JsonObject{
	private List<FeedUpdate> data;
	private Long updated_time;
	private String id;
	private CommentUpdate comments;
	
	public FeedUpdate(String json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUpdated_time() {
		if(comments!=null && comments.getData().size()>0){
			CommentUpdate comment = comments.getData().get(0);
			if(comment!=null && comment.getCreated_time() > updated_time){
				updated_time = comment.getCreated_time();
			}
		}
		return updated_time;
	}

	public void setUpdated_time(Long updated_time) {
		this.updated_time = updated_time;
	}

	public CommentUpdate getComments() {
		return comments;
	}

	public void setComment(CommentUpdate comments) {
		this.comments = comments;
	}

	public List<FeedUpdate> getData() {
		return data;
	}

	public void setData(List<FeedUpdate> data) {
		this.data = data;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<FeedUpdate>();
				for(Object cu : list){
					data.add((FeedUpdate)cu);
				}
			}
		}
	}
}
