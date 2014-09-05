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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.cache.AppCache;
import org.jcommon.com.facebook.data.*;
import org.jcommon.com.facebook.data.Error;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JcommonFacebook extends HttpServlet
{
  private static Logger logger = Logger.getLogger(JcommonFacebook.class);
  private static final long serialVersionUID = 1L;
  private static final String cmd_about_me = "cmd_about_me";
  private static final String cmd_access_token = "cmd_access_token";
  private static final String FACEBOOK_APP = "facebook_app";
  private String facebook_app_key;

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
    this.facebook_app_key = getInitParameter(FACEBOOK_APP);
    logger.info("JcommonFacebook running ...");
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
	
    String cmd = request.getParameter("cmd");
    String code = request.getParameter("code");
    try {
      String path = request.getPathInfo();
      if ((path != null) && (path.length() != 0)) {
        if (path.startsWith("/")) path = path.substring(1);
        String[] parts = path.split("/");
        if ("jcommon-facebook.js".equals(parts[0])) {
          App app = AppCache.instance().getApp(this.facebook_app_key);

          response.setContentType("text/javascript");
          InputStream is = getClass().getClassLoader().getResourceAsStream("jcommon-facebook.js");
          OutputStream os = response.getOutputStream();
          if (app == null) {
            os.write("//can't find facebook app".getBytes());
          } else {
            String request_code_url = RequestFactory.createGetAccessCodeUrl(null, app.getPermissions(), app);
            request_code_url = "var request_code_url = '" + request_code_url + "';\n";
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
          return;
        }
      }
      if (cmd == null) {
        error(response, "request cmd is null");
        return;
      }
      if (code == null) {
        error(response, "code is null");
        return;
      }
      App app = AppCache.instance().getApp(this.facebook_app_key);
      if (app == null) {
        error(response, "can find facebook app : " + this.facebook_app_key);
        return;
      }
      int retry = 3;

      String redirect_uri = request.getParameter("redirect_uri");
      StringBuffer sb = request.getRequestURL();
      if (redirect_uri == null)
        redirect_uri = sb.toString();
      HttpRequest facebook_request = RequestFactory.createGetAccessTokenReqeust(null, code, redirect_uri, app);
      String access_token = null;
      for (int i = 0; i < retry; i++) {
        facebook_request.run();
        access_token = facebook_request.getResult();
        if (!"".equals(access_token))
          break;
      }
      if ((access_token != null) && (access_token.startsWith("access_token="))) {
        access_token = access_token.replaceAll("access_token=", "");
      } else {
        error(response, access_token);
        return;
      }
      if ((access_token != null) && (access_token.indexOf("&") != -1)) {
        access_token = access_token.substring(0, access_token.indexOf("&"));
      }
      if (cmd_about_me.equals(cmd)) {
        String about_me = null;
        facebook_request = RequestFactory.createGetAboutMeReqeust(null, access_token);
        for (int i = 0; i < retry; i++) {
          facebook_request.run();
          about_me = facebook_request.getResult();
          if (!"".equals(about_me)) break;
        }
        if (about_me == null) {
          about_me = "{}";
        }
        response.getWriter().println(about_me);
        return;
      }if (cmd_access_token.equals(cmd)) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String all = request.getParameter("all");
        if ((id == null) && (name == null) && (all == null)) {
          response.getWriter().println(access_token);
          return;
        }
        facebook_request = RequestFactory.createGetAllAccessTokenReqeust(null, access_token);
        facebook_request.run();
        String data = facebook_request.getResult();
        JSONObject jsonO = JsonUtils.getJSONObject(data);
        if (jsonO == null) {
          error(response, "get access_token error:" + data);
          return;
        }
        try {
          JSONArray arr = jsonO.has("data") ? jsonO.getJSONArray("data") : null;
          if (arr == null) {
            error(response, "get access_token error:" + data);
            return;
          }
          if (all != null) {
            response.getWriter().println(arr.toString());
            return;
          }
          for (int i = 0; i < arr.length(); i++) {
            String access_token_data = arr.getString(i);
            AccessToken access_token_app = new AccessToken(access_token_data);
            logger.info(access_token_app.getId() + ":" + access_token_app.getAccess_token());
            if (((id != null) && (id.equals(access_token_app.getId()))) || ((name != null) && (id.equals(access_token_app.getName())))) {
              response.getWriter().println(access_token_app.getJsonData());
              return;
            }
          }
        }
        catch (JSONException e) {
          logger.error("", e);
          error(response, "get access_token error:" + e.getMessage());
        }
      }
    }
    catch (IOException e) {
      logger.error("", e);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    doGet(request, response);
  }
  //"get+access_token+error%3A%7B%22error%22%3A%7B%22message%22%3A%22This+authorization+code+has+expired.%22%2C%22type%22%3A%22OAuthException%22%2C%22code%22%3A100%7D%7D"
//13/06/28 23:21:24:187 org.jcommon.com.util.http.HttpRequest-run [URL][response][failure]{"error":{"message":"This authorizati
//  on code has expired.","type":"OAuthException","code":100}}
  private void error(HttpServletResponse response, String msg) throws IOException {
	JSONObject jsonO = JsonUtils.getJSONObject(msg);  
    Error error      = new Error();
	try {
		if(jsonO!=null)
			error = new Error(jsonO.getString("error"));
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		logger.error("", e);
	}
    if(error.getType()==null){
    	 error.setType("jcommonfacebook");
    	 error.setMessage(msg);
    } 
    response.getWriter().println(error.toJsonStr());
  }
}