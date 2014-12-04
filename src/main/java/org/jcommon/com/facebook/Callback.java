// ========================================================================
// Copyright 2014 leolee<workspaceleo@gmail.com>
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//     http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ========================================================================
package org.jcommon.com.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.data.Proxy;

public class Callback extends HttpServlet {
	private Logger logger = Logger.getLogger(this.getClass()); 
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
		
		String hub_challenge = request.getParameter("hub.challenge");
	    String hub_verify_token = request.getParameter("hub.verify_token");
	    String hub_mode = request.getParameter("hub.mode");
	    
	    if (FacebookManager.instance().appVerify(hub_challenge, hub_verify_token, hub_mode)) {
	        PrintWriter servletOutput = response.getWriter();
	        response.setContentType("text/html");
	        servletOutput.println(hub_challenge);
	    } else {
	        logger.warn("request verify failure!");
	    }
	}
	
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        
		StringBuilder xml = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			xml.append(line);
		}
		reader.close();
		logger.info("callback data:"+xml.toString());
		
		Map map = request.getParameterMap();
	      for (Iterator<?> i$ = map.keySet().iterator(); i$.hasNext(); ) { 
	    	Object key = i$.next();
	        for (String value : (String[])map.get(key)) {
	          logger.info(new StringBuilder().append(key).append("\t:").append(value).toString());
	        }
	    }
	  	/** xml.toString()
		   {
			   "object":"page",
			   "entry":[
				   {
				   "id":"271039552948235",
				   "time":1417656261,
				   "changes":[
					   {
						   "field":"feed",
						   "value":
						   {
							   "item":"comment",
							   "verb":"add",
							   "comment_id":"856823764369808_863976023654582",
							   "parent_id":"271039552948235_856823771036474",
							   "sender_id":100003226166163,
							   "created_time":1417656261
						   }
					   }
				   ]
				   }
			   ]
		   }

		 * 
		 */  
	    String hub_mode = request.getParameter("hub.mode");  
	    if(FacebookSessionCopy.copy.equals(hub_mode)){
	    	String data = org.jcommon.com.util.CoderUtils.decode(xml.toString());
	    	Proxy proxy = new Proxy(data);
	    	try {
				FacebookManager.instance().onProxy(proxy);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
	    	return;
	    }
	    FacebookManager.instance().onCallback(xml.toString());  
	}
	

}
