package org.jcommon.com.facebook.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.AppManager;
import org.jcommon.com.facebook.FacebookManager;
import org.jcommon.com.facebook.FacebookSession;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.App;
import org.jcommon.com.facebook.object.User;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.http.ResourceServlet;

public class GetAccessToken extends ResourceServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GetAccessToken.class);
	public static URL init_file_is = null;// GetAccessToken.class.getResource("/facebook-log4j.xml");

    public void init(ServletConfig config)throws ServletException{
	    super.init(config);
	    logger.info("GetAccessToken running ...");
    }
    
    final String[] files = {"help.PNG","access_token.html","jquery-min-1.11.0.js"};
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	  	String file_name = null;
	  	org.jcommon.com.facebook.object.Error error = new org.jcommon.com.facebook.object.Error(null,true);
	  	error.setType("get_access_token");
	  	if(super.isAcessSource(request, files[0])){
	  		response.setContentType("image/jpeg");
	  		file_name = files[0];
	  	}else if(super.isAcessSource(request, files[1])){
	  		response.setContentType("text/html; charset=utf-8");
	  		file_name = files[1];
	  	}else if(super.isAcessSource(request, "ls")){
	  		response.setContentType("text/html; charset=utf-8");
	  		file_name = files[1];
	  	}else if(super.isAcessSource(request, files[2])){
	  		response.setContentType("text/html; charset=utf-8");
	  		file_name = files[2];
	  	}else if(super.isAcessSource(request, "apps")){
	  		response.setContentType("text/html; charset=utf-8");
	  		ArrayList<App> apps = (ArrayList<App>) AppManager.instance().getApps();
	  		String jsons = org.jcommon.com.util.JsonObject.list2Json(apps);
	  		if(jsons!=null){
	  			response.getWriter().println(jsons);
	  			return;
	  		}else{
	  			error.setMessage("apps data is null.");
		  		response.getWriter().println(error.toJson());
		  		return;
	  		}
	  	}
	  	if(file_name!=null){
	  		logger.info("file_name:"+file_name);
	  		InputStream is = getClass().getClassLoader().getResourceAsStream(file_name);
	        try {
	  			OutputStream os = response.getOutputStream();
	  			byte[] buffer = new byte[65536];
	  	        int len;
	  	        while ((len = is.read(buffer)) >= 0)
	  	           os.write(buffer, 0, len);
	  		} catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			logger.error("", e);
	  		}
	  		return;
	  	}
	  	String code         = request.getParameter("code");
	  	String token_type   = request.getParameter("type");
	  	String app_id       = request.getParameter("app_id");
	  	String all          = request.getParameter("all"); 	  	
	  	
	  	String redirect_uri = request.getParameter("redirect_uri");
	  	App app   = app_id!=null?AppManager.instance().getApp(app_id):AppManager.instance().getDefaultApp();
	  	logger.info(String.format("app:%s;app_id:%s;all:%s;id:%s", app,app_id,all,token_type));
	  	
	  	if(app==null){
	  		error.setMessage("can find facebook app by :"+app_id);
	  		response.getWriter().println(error.toJson());
	  		return;
	  	}
	  	redirect_uri = redirect_uri==null?getRequestURL(request):redirect_uri;
	  	logger.info(String.format("redirect_uri:%s \ncode:%s", redirect_uri,code));
	  	
	  	if (code == null) {
	  		String url = AppManager.getAccessCodeUrl(app, redirect_uri);
	  		logger.info(url);
	  		response.sendRedirect(url);
	  	}else{
	  		AccessToken token = AppManager.getAccessToken(app, redirect_uri, code);
	  		if(token!=null && token.getError()!=null){
		  		response.getWriter().println(token.getError().toJson());
	        	return;
	  		}
	  		else if(token==null || token.getAccess_token()==null){
	  			error.setMessage("get access token fail, please try again later.");
		  		response.getWriter().println(error.toJson());
		  		return;
	  		}
	  		if(FacebookType.user.name().equals(token_type)){
	  			User user = AppManager.aboutMe(token);
	  			if(user!=null && user.getId()!=null && user.getName()!=null){
	  				token.setId(user.getId());
	  				token.setName(user.getName());
	  			}
	  			logger.info(token.toJson());
	  			response.getWriter().println(token.toJson());
	  		}else{
	  			List<AccessToken> tokens = AppManager.getAccessTokenList(token);
	  			if(tokens==null){
	  				error.setMessage("get access token list fail, please try again later.");
			  		response.getWriter().println(error.toJson());
			  		return;
	  			}
	  			if(all!=null){
	  				String json              = AccessToken.list2Json(tokens);
		  			logger.info(json);
		  			response.getWriter().println(json);
	  			}else{
	  				FacebookSession session = FacebookManager.instance().getDefaultFacebookSessions(FacebookType.page);
	  				if(session!=null){
	  					String facebook_id = session.getFacebook_id();
	  					for(AccessToken tk : tokens){
	  						if(facebook_id!=null && facebook_id.equals(tk.getId())){
	  				  			logger.info(tk.toJson());
	  				  			response.getWriter().println(tk.toJson());
	  				  			break;
	  						}
	  					}
	  				}
	  			}
	  		}
	  	}
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    static{
      if (init_file_is != null)
        DOMConfigurator.configure(init_file_is);
    }
}
