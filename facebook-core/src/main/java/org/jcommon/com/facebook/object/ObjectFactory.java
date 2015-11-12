package org.jcommon.com.facebook.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.util.JsonUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjectFactory {
	protected static Logger logger = Logger.getLogger(ResponseHandler.class);
	public static List<? extends JsonObject> newInstanceList(Class<? extends JsonObject> class_, String json){
		
		JSONObject jsonO = JsonUtils.getJSONObject(json);
		if (jsonO != null){
			try {
				List<JsonObject> respObject = new ArrayList<JsonObject>();
			    if (jsonO.has("data")) {
			    	JSONArray arr = JsonUtils.getJSONArray(jsonO.getString("data"));
			    	for (int index = 0; index < arr.length(); index++) {
			    		JsonObject row   = newInstance(class_,arr.getString(index));
			    		respObject.add(row);
		      	    }
		        }else{
		        	JsonObject row   = newInstance(class_,json);
		    		respObject.add(row);
		        }
			    return respObject;
		    }
		    catch (JSONException e) {
		        logger.error("", e);
		    }
		}   
		return null;
	}
	
	public static JsonObject newInstance(Class<? extends JsonObject> class_, String args) {
		try {
			Class<?>[] par = { String.class, boolean.class };
			Constructor<? extends JsonObject> con = class_.getConstructor(par);
			Object[] objs = { args, true };
	        return con.newInstance(objs);
		}
		catch (SecurityException e) {
		      logger.warn(e);
		}
		catch (IllegalArgumentException e) {
		      logger.warn(e);
		}
		catch (NoSuchMethodException e) {
		      logger.warn(e);
		}
		catch (InstantiationException e) {
		      logger.warn(e);
		}
		catch (IllegalAccessException e) {
		      logger.warn(e);
		}
		catch (InvocationTargetException e) {
		      logger.warn(e);
		}
		return null;
	}
}
