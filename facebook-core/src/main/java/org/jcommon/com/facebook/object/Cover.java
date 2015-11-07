package org.jcommon.com.facebook.object;

public class Cover extends JsonObject {
	private String cover_id;
	private String source;
	private String offset_y;
	
	public Cover(String json, boolean decode) {
		super(json,decode);
		// TODO Auto-generated constructor stub
	}

	public String getCover_id() {
		return cover_id;
	}

	public void setCover_id(String cover_id) {
		this.cover_id = cover_id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOffset_y() {
		return offset_y;
	}

	public void setOffset_y(String offset_y) {
		this.offset_y = offset_y;
	}
}
