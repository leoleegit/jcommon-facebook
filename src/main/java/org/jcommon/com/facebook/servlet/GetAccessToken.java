// ========================================================================
// Copyright 2012 leolee<workspaceleo@gmail.com>
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
package org.jcommon.com.facebook.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.cache.AppCache;
import org.jcommon.com.facebook.data.AccessToken;
import org.jcommon.com.facebook.data.App;
import org.jcommon.com.facebook.data.BaseUser;
import org.jcommon.com.facebook.data.Error;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.http.ResourceServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetAccessToken extends ResourceServlet
{
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger(GetAccessToken.class);
  public static URL init_file_is = null;//GetAccessToken.class.getResource("/facebook-log4j.xml");

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
    logger.info("JcommonFacebook running ...");
  }
  
  final String[] files = {"help.PNG","access_token.html"};
  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
	String file_name = null;
	if(super.isAcessSource(request, files[0])){
		response.setContentType("image/jpeg");
		file_name = files[0];
	}else if(super.isAcessSource(request, files[1])){
		response.setContentType("text/html; charset=utf-8");
		file_name = files[1];
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
	  
    String app_name = request.getParameter("app");
    App app   = app_name!=null?AppCache.instance().getApp(app_name):AppCache.instance().getApp();
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String all = request.getParameter("all");
    logger.info(String.format("id:%s ,name:%s", new Object[] { id, name }));
    try {
      Error error = new Error();
      error.setType("get_access_token");
      if (app == null) {
        error.setMessage("can find facebook app : " + app_name);
        response.getWriter().println(error.toJsonStr());
        return;
      }

      String redirect_uri = getRequestURL(request);
      logger.info("Request URL:" + redirect_uri);

      String code = request.getParameter("code");
      if (code == null) {
        String url = RequestFactory.createGetAccessCodeUrl(redirect_uri, app.getPermissions(), app);
        response.sendRedirect(url);
      } else {
        HttpRequest facebook_request = RequestFactory.createGetAccessTokenReqeust(null, code, redirect_uri, app);
        facebook_request.run();
        String access_token = facebook_request.getResult();
        if ((access_token != null) && (access_token.startsWith("access_token="))) {
          access_token = access_token.replaceAll("access_token=", "");
        } else {
          error.setMessage("get access_token error:" + access_token);
          response.getWriter().println(error.toJsonStr());
          return;
        }
        logger.info(access_token);
        if ((access_token != null) && (access_token.indexOf("&") != -1)) {
          access_token = access_token.substring(0, access_token.indexOf("&"));
        }
        if ((id == null) && (name == null) && (all == null)) {
        	 AccessToken at = new AccessToken();
        	 facebook_request = RequestFactory.createGetAboutMeReqeust(null, access_token);
             facebook_request.run();
             BaseUser user    = new BaseUser(facebook_request.getResult());
             at.setId(user.getId());
             at.setName(user.getName());
             at.setAccess_token(access_token);
             response.getWriter().println(at.toJsonStr());
             return;
        }
        facebook_request = RequestFactory.createGetAllAccessTokenReqeust(null, access_token);
        facebook_request.run();
        String data = facebook_request.getResult();
        JSONObject jsonO = JsonUtils.getJSONObject(data);
        if (jsonO == null) {
          error.setMessage("get access_token error:" + data);
          response.getWriter().println(error.toJsonStr());
          return;
        }
        try {
          JSONArray arr = jsonO.has("data") ? jsonO.getJSONArray("data") : null;
          if (arr == null) {
            error.setMessage("get access_token error:" + data);
            response.getWriter().println(error.toJsonStr());
            return;
          }

          response.getWriter().println("[");
          for (int i = 0; i < arr.length(); i++) {
            String access_token_data = arr.getString(i);
            AccessToken access_token_app = new AccessToken(access_token_data);
            logger.info(access_token_app.getId() + ":" + access_token_app.getAccess_token());
            if (all != null) {
              response.getWriter().println(access_token_app.getJsonData());
              if(i!=(arr.length()-1))
            	  response.getWriter().println(",");
            }
            else if (((id != null) && (id.equals(access_token_app.getId()))) || ((name != null) && (id.equals(access_token_app.getName())))) {
              response.getWriter().println(access_token_app.getJsonData());
              break;
            }
          }
          response.getWriter().println("]");
          if (all == null && id==null) {
            error.setMessage("get access_token error: can't find map access_token for " + id);
            response.getWriter().println(error.toJsonStr());
          }
        }
        catch (JSONException e) {
          logger.error("", e);
          error.setMessage("get access_token error:" + e.getMessage());
          response.getWriter().println(error.toJsonStr());
        }
      }
    }
    catch (IOException e) {
      logger.error("", e);
    }
  }

  private String getRequestURL(HttpServletRequest request)
  {
    StringBuffer sb = request.getRequestURL();
    sb.append("?");
    Enumeration<?> names = request.getParameterNames();
    while (names.hasMoreElements()) {
      String name = (String)names.nextElement();
      sb.append(name).append("=").append(request.getParameter(name)).append("&");
    }

    if ((sb.lastIndexOf("&") == sb.length() - 1) && (sb.length() > 0))
      sb.deleteCharAt(sb.length() - 1);
    if ((sb.lastIndexOf("?") == sb.length() - 1) && (sb.length() > 0))
      sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    doGet(request, response);
  }

  static
  {
    if (init_file_is != null)
      DOMConfigurator.configure(init_file_is);
  }
}