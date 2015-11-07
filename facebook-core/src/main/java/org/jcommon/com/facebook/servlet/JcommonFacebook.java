package org.jcommon.com.facebook.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.AppManager;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.App;
import org.jcommon.com.facebook.object.User;
import org.jcommon.com.util.http.ResourceServlet;

public class JcommonFacebook extends ResourceServlet{
	private static Logger logger = Logger.getLogger(JcommonFacebook.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String cmd_about_me = "cmd_about_me";
	
    public void init(ServletConfig config)throws ServletException{
	    super.init(config);
	    logger.info("GetAccessToken running ...");
    }
    
    final String[] files = {"jcommon-facebook.js"};
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cmd    = request.getParameter("cmd");
        String code   = request.getParameter("code");
        String app_id = request.getParameter("id");
        
        logger.info(String.format("cmd:%s;app id :%s; \n code:%s", cmd,app_id,code));
        org.jcommon.com.facebook.object.Error error = new org.jcommon.com.facebook.object.Error(null,true);
        error.setType("jcommonfacebook");
        if(super.isAcessSource(request, files[0])){
        	response.setContentType("text/javascript");
            InputStream is = getClass().getClassLoader().getResourceAsStream(files[0]);
            OutputStream os = response.getOutputStream();
            
            App app   = app_id!=null?AppManager.instance().getApp(app_id):AppManager.instance().getDefaultApp();
    	  	logger.info(String.format("app:%s,id%s", app,app_id));
    	  	if(app==null){
    	  		error.setMessage("//can't find facebook app");
    	  		os.write(error.toJson().getBytes());
    	  	}else{
    	  		String request_code_url =  AppManager.getAccessCodeUrl(app, null);
    	  		request_code_url        = "var request_code_url = '" + request_code_url + "';\n";
    	  		String request_jcommonfacebook_url = request.getRequestURL().toString();
                request_jcommonfacebook_url = request_jcommonfacebook_url.replaceAll("jcommon-facebook.js", "");
                request_jcommonfacebook_url = "var request_jcommonfacebook_url = '" + request_jcommonfacebook_url + "';\n";
                os.write(request_code_url.getBytes());
                os.write(request_jcommonfacebook_url.getBytes());
                String facebook_app_url = app.getApp_link();
                if(facebook_app_url!=null)
                	 os.write(("var facebook_app_url = '" + facebook_app_url + "';\n").getBytes());
                byte[] buffer = new byte[65536];
                int len;
                while ((len = is.read(buffer)) >= 0)
                  os.write(buffer, 0, len);
    	  	}
    	  	is.close();
    	  	os.close();
    	  	return;
        }
        if (cmd == null) {
        	error.setMessage("request cmd can not be null");
        	response.getWriter().println(error.toJson());
        	return;
        }
        if (code == null) {
        	error.setMessage("code can not be null");
        	response.getWriter().println(error.toJson());
        	return;
        }
        String redirect_uri = request.getParameter("redirect_uri");
        redirect_uri = redirect_uri==null?getRequestURL(request):redirect_uri;
	  	logger.info(String.format("redirect_uri:%s \ncode:%s", redirect_uri,code));
	  	
        App app   = app_id!=null?AppManager.instance().getApp(app_id):AppManager.instance().getDefaultApp();
	  	logger.info(String.format("app:%s,id%s", app,app_id));
	  	
	  	if(app==null){
	  		error.setMessage("//can't find facebook app");
	  		response.getWriter().println(error.toJson());
        	return;
	  	}
        
	  	int retry = 3;
	  	AccessToken token = null;
	  	for (int i = 0; i < retry; i++) {
	  		token = AppManager.getAccessToken(app, redirect_uri, code);
	  		if(token.getAccess_token()!=null){
	  			break;
	  		}else if(token.getError()!=null){
		  		response.getWriter().println(token.getError().toJson());
	        	return;
	  		}
	  	}
	  	if(token.getAccess_token()!=null){
	  		 if (cmd_about_me.equals(cmd)) {
	  			User user = AppManager.aboutMe(token);
	  			if(user==null){
	  				error.setMessage("get about me fail.");
	  	        	response.getWriter().println(error.toJson());
		        	return;
	  			}else{
	  				response.getWriter().println(user.toJson());
	  		        return;
	  			}
	  		 }
	  	}else if(token.getError()!=null){
	  		response.getWriter().println(token.getError().toJson());
        	return;
	  	}else{
	  		logger.info("access token is null");
	  	}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
    
    private String getRequestURL(HttpServletRequest request){
        StringBuffer sb = request.getRequestURL();
        sb.append("?");
        Enumeration<?> names = request.getParameterNames();
        while (names.hasMoreElements()) {
	        String name = (String)names.nextElement();
	        if(!"code".equalsIgnoreCase(name))
	      	    sb.append(name).append("=").append(request.getParameter(name)).append("&");
        }

        if ((sb.lastIndexOf("&") == sb.length() - 1) && (sb.length() > 0))
        	sb.deleteCharAt(sb.length() - 1);
        if ((sb.lastIndexOf("?") == sb.length() - 1) && (sb.length() > 0))
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
