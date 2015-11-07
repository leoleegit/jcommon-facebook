package org.jcommon.com.facebook.object.callback;

import org.jcommon.com.facebook.object.JsonObject;

public class Changes extends JsonObject {
	private String field;
	private Value value;
	
	public Changes(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}
	
	public Changes(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
