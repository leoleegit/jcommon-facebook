package org.jcommon.com.facebook.object;

public class Privacy extends JsonObject{
	private String description;
	private String value;
	
	public Privacy(String json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
