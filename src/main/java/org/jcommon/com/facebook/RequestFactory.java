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
package org.jcommon.com.facebook;

import java.io.File;
import org.jcommon.com.facebook.data.App;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.FileRequest;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;

public class RequestFactory
{																														
  public static final String graph_url = "https://graph.facebook.com/";
  public  static boolean trusted = true;
  public  static String  version = "v2.0";

  public static HttpRequest createGetFeedReqeust(HttpListener listener, String page_id, String access_token, String fields, int limit)
  {
    if (limit == 0) limit = 25;
    String[] keys = { "access_token", "method", "date_format", "limit", "fields" };

    String[] values = { access_token, HttpRequest.GET, "U", String.valueOf(limit), fields };

    String url = graph_url + version + "/" + page_id + "/feed";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }
  
  public static HttpRequest createReqeust(HttpListener listener, String access_token, String url)
  {
    String[] keys = { "access_token", "method"};
    String[] values = { access_token, HttpRequest.GET};
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  //271039552948235/feed?fields=updated_time,comments.limit(1).date_format(U).fields(created_time)&date_format=U
  public static HttpRequest createGetFeedUpdateReqeust(HttpListener listener, String page_id, String access_token, int limit) {
    String fields = "updated_time,comments.limit(1).date_format(U).fields(created_time)";
    return createGetFeedReqeust(listener, page_id, access_token, fields, limit);
  }
  
  public static HttpRequest createGetFeedUpdateReqeustByFql(HttpListener listener, String page_id, String access_token, int limit) {
	    String fql    = "SELECT post_id, updated_time FROM stream WHERE source_id="+page_id+" ORDER BY updated_time DESC LIMIT "+limit;
	    return createGetReqeustByFql(listener,access_token,fql);
  }

  public static HttpRequest createGetCommentsReqeust(HttpListener listener, String post_id, String access_token, String fields, int offset) {
    String[] keys = { "limit", "access_token", "method", "date_format", "offset", "fields", "filter"};
    String[] values = { String.valueOf(1000), access_token, HttpRequest.GET, "U", String.valueOf(offset), fields, "stream"};

    String url = graph_url + version + "/" + post_id + "/comments";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }
  
  public static HttpRequest createGetCommentsReqeust(HttpListener listener, String post_id, String access_token, String fields, String after_id) {
    String[] keys = { "limit", "access_token", "method", "date_format", "__after_id", "fields", "filter"};
    String[] values = { String.valueOf(1000), access_token, HttpRequest.GET, "U", after_id, fields, "stream" };

    String url = graph_url + version + "/" + post_id + "/comments";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
   }

  public static HttpRequest createGetMessageReqeust(HttpListener listener, String page_id, String access_token, String fields) {
    String[] keys = { "access_token", "method", "date_format", "fields" };

    String[] values = { access_token, HttpRequest.GET, "U", fields };

    String url = graph_url + version + "/" + page_id + "/conversations";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createGetAlbumReqeust(HttpListener listener, String page_id, String access_token, String fields) {
    String[] keys = { "access_token", "method", "date_format", "fields" };

    String[] values = { access_token, HttpRequest.GET, "U", fields };

    String url = graph_url + version + "/" + page_id + "/albums";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createGetAboutMeReqeust(HttpListener listener, String access_token)
  {
    return createGetDetailRequest(listener, "me", "id,name,link,location,work,gender,timezone,locale,picture", access_token);
    //return createGetDetailRequest(listener, "me", "id,name,link,username,location,work,gender,timezone,locale,picture", access_token);
  }

  public static HttpRequest createGetAccessTokenReqeust(HttpListener listener, String code, String redirect_uri, App app) {
    String[] keys = { "client_id", "client_secret", "code", "redirect_uri" };

    String[] values = { app.getApi_id(), app.getApp_secret(), code, redirect_uri };

    String url = graph_url + version + "/" + "oauth/access_token";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createGetAllAccessTokenReqeust(HttpListener listener, String access_token) {
    String url = graph_url +"me/accounts";
    String[] keys = { "access_token", "method" };
    String[] values = { access_token, HttpRequest.GET };

    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createGetAccessSecretReqeust(HttpListener listener, App app) {
    String url = graph_url + "oauth/access_token?client_id=" + app.getApi_id();
    String[] keys = { "client_id", "client_secret", "scope", "response_type", "type" };

    String[] values = { app.getApi_id(), app.getApp_secret(), app.getPermissions(), "token", "client_cred" };

    String content = JsonUtils.toParameter(keys, values);
    return new HttpRequest(url, content, HttpRequest.POST, listener, trusted);
  }

  public static String createGetAccessCodeUrl(String redirect_uri, String permissions, App app) {
    String url = "http://www.facebook.com/dialog/oauth?client_id=" + app.getApi_id();
    String[] keys = { "redirect_uri", "scope", "response_type" };

    String[] values = { redirect_uri, permissions, "code" };

    return JsonUtils.toRequestURL(url, keys, values);
  }

  public static HttpRequest createGetMessageUpdateReqeust(HttpListener listener, String page_id, String access_token) {
    String fields = "updated_time";
    return createGetMessageReqeust(listener, page_id, access_token, fields);
  }

  public static HttpRequest createGetMessageByIdReqeust(HttpListener listener, String message_id, String access_token) {
    String[] keys = { "access_token", "method", "date_format" };

    String[] values = { access_token, HttpRequest.GET, "U" };

    String url = graph_url + version + "/" + message_id + "/messages";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createGetDetailRequest(HttpListener listener, String id, String fields, String access_token) {
    String[] keys = { "access_token", "method", "date_format", "fields" };

    String[] values = { access_token, HttpRequest.GET, "U", fields };

    String url = graph_url + version + "/" + id;
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createPostPhotoRequest(HttpListener listener, String album_id, File file, String message, String access_token) {
    String[] keys = { "access_token", "method", "message" };

    String[] values = { access_token, HttpRequest.POST, message };

    String url = graph_url + version + "/" + album_id + "/photos";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new FileRequest(url,HttpRequest.POST,listener,trusted,file);
  }

  public static HttpRequest createPostVideoRequest(HttpListener listener, String page_id, File file, String title, String description, String access_token) {
    String[] keys = { "access_token", "method", "title", "description" };

    String[] values = { access_token, HttpRequest.POST, title, description };

    String url = graph_url + version + "/" + page_id + "/videos";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new FileRequest(url,HttpRequest.POST,listener,trusted,file);
  }

  public static HttpRequest createPostRequest(HttpListener listener, String page_id, String message, String link, String picture, String name, String caption, String description, String access_token)
  {
    String[] keys = { "access_token", "method", "message", "link", "name", "caption", "description", "picture" };

    String[] values = { access_token, HttpRequest.POST, message, link, name, caption, description, picture };

    String url = graph_url + version + "/" + page_id + "/feed";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createDeleteRequest(HttpListener listener, String id, String access_token) {
    String[] keys = { "access_token", "method" };
    String[] values = { access_token, "DELETE" };
    String url = graph_url + version + "/" + id;
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createPostCommentRequest(HttpListener listener, String post_id, String message, String access_token) {
    String[] keys = { "access_token", "method", "message" };
    String[] values = { access_token, HttpRequest.POST, message };
    String url = graph_url + version + "/" + post_id + "/comments";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }

  public static HttpRequest createReplyMessageRequest(HttpListener listener, String message_id, String message, String access_token) {
    String[] keys = { "access_token", "method", "message" };
    String[] values = { access_token, HttpRequest.POST, message };
    String url = graph_url + version + "/" + message_id + "/messages";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }
  
  public static HttpRequest createNewTabReqeust(HttpListener listener,String page_id, String access_token, App app) {
    String[] keys = { "app_id", "access_token", "method"};
    String[] values = {app.getApi_id(), access_token, HttpRequest.POST};
    String url = graph_url + version + "/" + page_id + "/tabs";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }
  
  public static HttpRequest createGetTabsReqeust(HttpListener listener,String page_id, String access_token, App app) {
    String[] keys = { "app_id", "access_token", "method"};
    String[] values = {app.getApi_id(), access_token, HttpRequest.GET};
    String url = graph_url + version + "/" + page_id + "/tabs";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }
  
  public static HttpRequest createSubscriptionReqeust(HttpListener listener, App app, String page_id, String callback_url,FacebookType type){
	String[] keys   = { "access_token","callback_url","object","fields","verify_token","active","method"};
    String[] values = { app.getAccess_token(), callback_url, type.toString(), "feed", app.getVerify_token(), "true",HttpRequest.POST };
    String url = graph_url + version + "/" + page_id + "/subscriptions";
    url = JsonUtils.toRequestURL(url, keys, values);
    return new HttpRequest(url, listener, trusted);
  }
  
  public static HttpRequest createDelSubscriptionReqeust(HttpListener listener, String app_access_token, String page_id, FacebookType type){
		String[] keys   = { "access_token","object","fields","method"};
	    String[] values = { app_access_token,  type.toString(), "feed",HttpRequest.DELETE };
	    String url = graph_url + version + "/" + page_id + "/subscriptions";
	    url = JsonUtils.toRequestURL(url, keys, values);
	    return new HttpRequest(url, listener, trusted);
  }
  
  public static HttpRequest createGetReqeustByFql(HttpListener listener, String access_token, String fql) {	   
	    String[] keys = { "access_token", "method", "q"};
	    String[] values = { access_token, HttpRequest.GET, fql};
	    String url = graph_url+ version  + "/fql";
	    url = JsonUtils.toRequestURL(url, keys, values);
	    return new HttpRequest(url, listener, trusted);
  }
  
  //SELECT id, text, time, fromid FROM comment WHERE post_id='271039552948235_581811248537729' and time > '1373947325'
  /**
   * {'aa':'SELECT name, id FROM profile WHERE id IN (SELECT fromid FROM comment WHERE post_id="271039552948235_581811248537729")',
   *  'bb':'SELECT id, text, time, fromid FROM comment WHERE post_id="271039552948235_581811248537729"'}
   */
}