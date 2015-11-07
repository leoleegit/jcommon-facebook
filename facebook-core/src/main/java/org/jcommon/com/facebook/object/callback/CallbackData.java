package org.jcommon.com.facebook.object.callback;

import java.util.ArrayList;
import java.util.List;

import org.jcommon.com.facebook.object.JsonObject;

public class CallbackData extends JsonObject{
	private String object;
	private List<Entry> entry;
	
	public CallbackData(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}
	
	public CallbackData(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}

	public List<Entry> getEntry() {
		return entry;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("entry".equals(arg0)){
			List<Object> list = super.json2Objects(Entry.class, (String) arg1);
			if(list!=null && list.size()>0){
				entry = new ArrayList<Entry>();
				for(Object cu : list){
					entry.add((Entry)cu);
				}
			}
		}
	}
}
