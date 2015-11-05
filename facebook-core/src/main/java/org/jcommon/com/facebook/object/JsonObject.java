package org.jcommon.com.facebook.object;

import org.apache.log4j.Logger;

public class JsonObject extends org.jcommon.com.util.JsonObject{
	protected static Logger logger = Logger.getLogger(JsonObject.class);
	
	public JsonObject(String json){
		super(json);
	}

	@Override
	public void setListObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
