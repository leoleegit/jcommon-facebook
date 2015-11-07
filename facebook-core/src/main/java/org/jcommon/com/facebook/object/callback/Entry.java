package org.jcommon.com.facebook.object.callback;

import java.util.ArrayList;
import java.util.List;

import org.jcommon.com.facebook.object.JsonObject;

public class Entry extends JsonObject {
	private String id;
	private long time;
	private List<Changes> changes;
	
	public Entry(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}
	
	public Entry(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setChanges(List<Changes> changes) {
		this.changes = changes;
	}

	public List<Changes> getChanges() {
		return changes;
	}
	
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("changes".equals(arg0)){
			List<Object> list = super.json2Objects(Changes.class, (String) arg1);
			if(list!=null && list.size()>0){
				changes = new ArrayList<Changes>();
				for(Object cu : list){
					changes.add((Changes)cu);
				}
			}
		}
	}
}
