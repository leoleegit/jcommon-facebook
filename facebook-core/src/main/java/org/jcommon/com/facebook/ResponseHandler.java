package org.jcommon.com.facebook;

import java.util.*;

import org.apache.log4j.Logger;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.collections.MapStore;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.facebook.config.FacebookConfig;
import org.jcommon.com.facebook.object.DefaultResponse;
import org.jcommon.com.facebook.object.Error;
import org.jcommon.com.facebook.object.JsonObject;
import org.jcommon.com.facebook.object.ObjectFactory;
import org.json.JSONObject;

public abstract class ResponseHandler extends MapStore implements HttpListener{
	private Map<HttpRequest,Class<? extends JsonObject>> responseObjects = new HashMap<HttpRequest,Class<? extends JsonObject>>();
	protected static Logger logger = Logger.getLogger(ResponseHandler.class);
	
	public abstract void onError(HttpRequest paramHttpRequest, Error paramError);
	
	public abstract void onOk(HttpRequest paramHttpRequest, JsonObject paramObject);
	
	public void addHandlerObject(HttpRequest request, Class<? extends JsonObject> type) {
		responseObjects.put(request, type);
	}
	
	private Class<? extends JsonObject> getResponseObject(HttpRequest request){
	    return responseObjects.remove(request);
	}
	
	public void onSuccessful(HttpRequest reqeust, StringBuilder sResult){
		FacebookConfig config = FacebookManager.instance().getFacebookConfig();
		if(config.isDebug())
			logger.info(sResult.toString());
	    String result = sResult.toString();

	    Class<? extends JsonObject> type = getResponseObject(reqeust);
	    if(config.isDebug())
	    	logger.info("responseObject:"+type);
	    Error error_e = new Error(-1,"spotlight system error");
	    if (result != null) {
	        if (type!=null){
		        try {
		        	JsonObject args = ObjectFactory.newInstance(type, result);
		        	if(args.getError()!=null)
		        		onError(reqeust, args.getError());
		        	else
		        		onOk(reqeust, args);
		            return;
		        }
		        catch (SecurityException e) {
		            logger.warn(e);
		            error_e.setMessage(e.getMessage());
		        }
		        catch (IllegalArgumentException e) {
		            logger.warn(e);
		            error_e.setMessage(e.getMessage());
		        }
	        }
	        else {
	        	onOk(reqeust, new DefaultResponse(result));
	        	return;
	        }
	    }
	    else{
	    	onOk(reqeust, new DefaultResponse(result));
	        return;
	    }
	    onError(reqeust, error_e);
	}

	public void onFailure(HttpRequest reqeust, StringBuilder sResult){
	    logger.warn(sResult.toString());
	    String result = sResult.toString();
	    getResponseObject(reqeust);
	    JSONObject reObj = JsonUtils.getJSONObject(result);
	    Error error_e    = new Error(-1,result);
	    if (reObj != null) {
	    	if (reObj.has("error")) {
	    		error_e = new Error(result);
		        onError(reqeust, error_e);
		        return;
	        }
	    }
	    onError(reqeust, error_e);
	}

	public void onTimeout(HttpRequest reqeust){
	    logger.error("timeout");
	    getResponseObject(reqeust);
	    Error error_e    = new Error(-1,"spotlight system error --> timeout");
	    onError(reqeust, error_e);
	}

	public void onException(HttpRequest reqeust, Exception e){
	    logger.error("", e);
	    getResponseObject(reqeust);
	    Error error_e    = new Error(-1,e.getMessage());
	    onError(reqeust, error_e);
	}
}
