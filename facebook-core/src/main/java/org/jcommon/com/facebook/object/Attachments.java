package org.jcommon.com.facebook.object;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @author leo@protelsws.com
 *
{"id":"m_mid.1470305593968:86f8aa9fe4ec6a1a71",
"to":{"data":[{"id":"223201114421192","email":"223201114421192@facebook.com","name":"Sinclair"}]},
"message":"",
"attachments":
{
"data":[
{"id":"1016856278433118",
"mime_type":"text/plain",
"name":"script.txt",
"file_url":"https://cdn.fbsbx.com/v/t59.2708-21/13586959_1016856281766451_1733580613_n.txt/script.txt?oh=fbb60be98653155e66f78e38944e0b5c&oe=57A56D0B&dl=1",
"size":20
}
]
},"from":{"id":"882148571903890","email":"882148571903890@facebook.com","name":"Jun Ch"},"created_time":1470305594}

 */
public class Attachments extends JsonObject {
	private List<Attachments> data;
	private String id;
	private String name;
	private String mime_type;
	private long size;
	private ImageData image_data;
	private String file_url;
	
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

	public void setImage_data(ImageData image_data) {
		this.image_data = image_data;
	}

	public ImageData getImage_data() {
		return image_data;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getFile_url() {
		return file_url;
	}

}
