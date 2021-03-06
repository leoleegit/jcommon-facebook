package org.jcommon.com.facebook.object.update;

import java.util.ArrayList;
import java.util.List;

import org.jcommon.com.facebook.object.JsonObject;

public class CommentUpdate extends JsonObject{
	private List<CommentUpdate> data;
	private Long created_time;
	private String id;
	
	public CommentUpdate(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}
	
	public CommentUpdate(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}

	public Long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Long created_time) {
		this.created_time = created_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CommentUpdate> getData() {
		return data;
	}

	public void setData(List<CommentUpdate> data) {
		this.data = data;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<CommentUpdate>();
				for(Object cu : list){
					data.add((CommentUpdate)cu);
				}
			}
		}
	}
}
