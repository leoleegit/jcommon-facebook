package org.jcommon.com.facebook.object;

public class Profile extends JsonObject {
    private String id;
	private String name;
	
	public Profile(String json) {
		super(json);
		// TODO Auto-generated constructor stub
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
}
