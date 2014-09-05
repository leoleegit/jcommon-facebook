package org.jcommon.com.facebook;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.data.Proxy;

public class ProxyCallback extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ProxyCallback.class);
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
	    try {
	      request.setCharacterEncoding("UTF-8");
	      response.setCharacterEncoding("UTF-8");
	      StringBuilder xml = new StringBuilder();
	      BufferedReader reader = request.getReader();
	      String line;
	      while ((line = reader.readLine()) != null) {
	        xml.append(line);
	      }
	      reader.close();

	      String post_data = xml.toString();
	      post_data = org.jcommon.com.util.CoderUtils.decode(post_data);
	      logger.info(post_data);

	      Proxy proxy = new Proxy(post_data);
	      FacebookManager.instance().onProxy(proxy);
	    } catch (IOException e) {
	      logger.error("", e);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
	    	logger.error("", e);
		}
	  }
}
