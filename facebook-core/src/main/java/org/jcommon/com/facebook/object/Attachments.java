package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.List;

public class Attachments extends JsonObject {
	private List<Attachments> data;
	private String id;
	private String name;
	private String mime_type;
	private long size;
	
	public Attachments(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}
	
	public Attachments(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<Attachments>();
				for(Object cu : list){
					data.add((Attachments)cu);
				}
			}
		}
	}

	public List<Attachments> getData() {
		return data;
	}

	public void setData(List<Attachments> data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMime_type() {
		return mime_type;
	}

	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
