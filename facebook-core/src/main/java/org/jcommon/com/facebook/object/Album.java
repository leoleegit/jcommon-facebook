package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.List;

public class Album extends JsonObject {
	private List<Album> data;
	private String id;
	private User from;
	private String name;
	private String description;
	private String link;
    private String cover_photo;
	private String privacy;
	private int count;
	private String type;
	private String created_time;
	private String updated_time;
	private boolean can_upload;
	  
	public Album(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}
	
	public Album(String json) {
		super(json,true);
		// TODO Auto-generated constructor stub
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCover_photo() {
		return cover_photo;
	}

	public void setCover_photo(String cover_photo) {
		this.cover_photo = cover_photo;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public boolean isCan_upload() {
		return can_upload;
	}

	public void setCan_upload(boolean can_upload) {
		this.can_upload = can_upload;
	}

	public List<Album> getData() {
		return data;
	}

	public void setData(List<Album> data) {
		this.data = data;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<Album>();
				for(Object cu : list){
					data.add((Album)cu);
				}
			}
		}
	}
}
