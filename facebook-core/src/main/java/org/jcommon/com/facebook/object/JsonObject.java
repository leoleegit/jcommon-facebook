package org.jcommon.com.facebook.object;

import org.apache.log4j.Logger;

public class JsonObject extends org.jcommon.com.util.JsonObject{
	protected static Logger logger = Logger.getLogger(JsonObject.class);
	private Error  error;
	
	public JsonObject(String json, boolean decode){
		super(json,decode);
	}

	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}
