package org.jcommon.com.facebook.object;

public class DefaultResponse extends JsonObject {
	private String response;
	private String id;
	private String post_id;
	
	public DefaultResponse(String response){
		super(response);
		this.response = response;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
}
