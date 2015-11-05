package org.jcommon.com.facebook.object;

import java.util.ArrayList;
import java.util.List;

public class Picture extends JsonObject {
	private List<Picture> data;
	private boolean is_silhouette;
	private String url;
	
	public Picture(String json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public List<Picture> getData() {
		return data;
	}

	public void setData(List<Picture> data) {
		this.data = data;
	}

	public boolean isIs_silhouette() {
		return is_silhouette;
	}

	public void setIs_silhouette(boolean is_silhouette) {
		this.is_silhouette = is_silhouette;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		if("data".equals(arg0)){
			List<Object> list = super.json2Objects(getClass(), (String) arg1);
			if(list!=null && list.size()>0){
				data = new ArrayList<Picture>();
				for(Object cu : list){
					data.add((Picture)cu);
				}
			}
		}
	}
}
