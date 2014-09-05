package org.jcommon.com.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.cache.SessionCache;
import org.jcommon.com.facebook.seesion.Session;
import org.jcommon.com.facebook.seesion.FacebookSession;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookMonitor extends HttpServlet {
	private Logger logger = Logger.getLogger(this.getClass()); 
    public static final String monitor_key = "spotlight_verify";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		logger.info("subscription verify");
		Map<?,?> map  = request.getParameterMap();
		for(Object key : map.keySet()){
			for(String value : (String[])map.get(key)){
				logger.info(key+"	:"+value);
			}
		}
		if(map.containsKey("hub.challenge")){
			String verify_token = request.getParameter("hub.verify_token");
			PrintWriter  servletOutput = response.getWriter();
			response.setContentType("text/html");
			if(monitor_key.equals(verify_token)){
				servletOutput.println(request.getParameter("hub.challenge"));
				logger.info("SubscriptionSuccessful:"+verify_token);
			}else{
				servletOutput.println("subscription error");
				logger.info("Subscription Error:"+verify_token);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder xml = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			xml.append(line);
		}
		reader.close();
		logger.info("callback data:"+xml.toString());
		handleCallback(xml.toString());
	}
	
	/**
	 * {
	 * "object":"user",
	 * "entry":
	 * [
	 * {
	 * "id":"223201114421192",
	 * "time":1373531914,
	 * "changes":
	 * [
	 * {
	 * "field":"feed",
	 * "value":
	 * {
	 * "item":"comment",
	 * "verb":"add",
	 * "comment_id":"479317935476174_3446504",
	 * "parent_id":479317935476174,
	 * "sender_id":100002338172555,
	 * "created_time":1373531914
	 * }
	 * }
	 * ]
	 * }
	 * ]
	 * }
	 * @param data
	 */
	private void handleCallback(String data){
		JSONObject jsonO = JsonUtils.getJSONObject(data);
		try{
			if(jsonO!=null && jsonO.has("entry")){
				JSONArray jsonA = jsonO.getJSONArray("entry");
				for(int i=0; i<jsonA.length(); i++){
					check(jsonA.getJSONObject(i));
				}
			}
		}catch(JSONException e){
			logger.error("", e);
		}
	}

	private void check(JSONObject jsonO) throws JSONException {
		// TODO Auto-generated method stub
	    String id = jsonO.has("id")?jsonO.getString("id"):null;
	    if(id==null)return;
	    
	    Session session = SessionCache.instance().getSession(id);
	    if(session!=null && session instanceof FacebookSession){
	    	JSONArray jsonA = jsonO.has("changes")?jsonO.getJSONArray("changes"):null;
	    	if(jsonA==null)return;
	    	for(int i=0; i<jsonA.length(); i++){
	    		jsonO = jsonA.getJSONObject(i);
	    		//check comment update
	    		if(!jsonO.has("item") || (jsonO.has("item")&&!"comment".equals(jsonO.getString("item")))){
	    			continue;
	    		}
	    		String comment_id = jsonO.has("comment_id")?jsonO.getString("comment_id"):null;
	    		String post_id    = jsonO.has("parent_id")?jsonO.getString("parent_id"):null;
	    		if(comment_id!=null && post_id!=null){
	    			String access_token = session.getAccessToken();
	    			HttpRequest request = RequestFactory.createGetDetailRequest(null, post_id, null, access_token);
	    			request.run();
	    			JSONObject json_post = JsonUtils.getJSONObject(request.getResult());
	    			
	    			request = RequestFactory.createGetDetailRequest(null, comment_id, null, access_token);
	    			request.run();
	    			JSONObject json_comment = JsonUtils.getJSONObject(request.getResult());
	    			((FacebookSession)session).onComment(json_post, json_comment);
	    		}
			}
	    }else{
	    	logger.warn("session is null or not page session.");
	    }
	}
}
