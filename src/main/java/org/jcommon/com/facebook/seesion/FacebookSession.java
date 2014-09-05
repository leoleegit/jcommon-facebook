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
package org.jcommon.com.facebook.seesion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jcommon.com.facebook.PageListener;
import org.jcommon.com.facebook.RequestCallback;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.cache.CallbackCache;
import org.jcommon.com.facebook.cache.DataCache;
import org.jcommon.com.facebook.cache.SessionCache;
import org.jcommon.com.facebook.data.Album;
import org.jcommon.com.facebook.data.BaseUser;
import org.jcommon.com.facebook.data.Comment;
import org.jcommon.com.facebook.data.Error;
import org.jcommon.com.facebook.data.Feed;
import org.jcommon.com.facebook.data.Message;
import org.jcommon.com.facebook.utils.DataType;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.facebook.utils.FacebookUtils;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookSession
  implements PageCallback, HttpListener, Session
{
  protected Logger logger = Logger.getLogger(getClass());
  public static boolean debug;
  private String page_id;
  private String access_token;
  private FacebookType type;
  private MessageMonitor message_monitor;
  private FeedMonitor feed_monitor;
  private List<PageListener> listeners = new ArrayList<PageListener>();
  private String wall_album_id;

  private BaseUser me;
  private boolean monitoring;

  public FacebookSession(String page_id, String access_token, PageListener listnener)
  {
    this.page_id = page_id;
    this.access_token = access_token;
    addListnener(listnener);
  }

  public void login(FacebookType type) {
	  setType(type);
	  startMonitor();
      SessionCache.instance().addSession(this.page_id, this);
  }

  public void logout() {
	  stopMonitor();
      SessionCache.instance().removeSession(this.page_id);
  }
  
  public void stopMonitor(){
	  if(FacebookType.page == type && monitoring){  
		    this.message_monitor.shutdown();
		    this.feed_monitor.shutdown();
		    this.message_monitor.setListener(null);
		    this.feed_monitor.setListener(null);
		    this.message_monitor = null;
		    this.feed_monitor = null;
	  }
	  monitoring = false;
  }
  
  public void startMonitor(){
	  if(FacebookType.page == type && !monitoring){
		   this.message_monitor = new MessageMonitor(this.page_id, this.access_token);
		   this.feed_monitor = new FeedMonitor(this.page_id, this.access_token);

		   this.message_monitor.startup();
		   this.feed_monitor.startup(); 
		   this.message_monitor.setListener(this);
		   this.feed_monitor.setListener(this);
		   monitoring = true;
	  }
	  logger.info(String.format("FacebookType:%s;id:%s;access_token:%s",type,page_id,access_token));
  }

  public HttpRequest postPhoto2Wall(RequestCallback callback, File file, String message, boolean start_upload) {
    return postPhoto2Wall(callback, file, message, this.access_token, start_upload);
  }

  public HttpRequest postVideo2Wall(RequestCallback callback, File file, String title, String description,  boolean start_upload) {
    return postVideo2Wall(callback, file, title, description, this.access_token, start_upload);
  }

  public HttpRequest postFeed2Wall(RequestCallback callback, String message, String link, String picture, String name, String caption, String description) {
    return postFeed2Wall(callback, message, link, picture, name, caption, description, this.access_token);
  }

  public HttpRequest postComment2Wall(RequestCallback callback, String post_id, String message) {
    return postComment2Wall(callback, post_id, message, this.access_token);
  }

  public HttpRequest postPhoto2Wall(RequestCallback callback, File file, String message, String access_token, boolean start_upload)
  {
    if (this.wall_album_id == null) {
      Album wall_album = getWallAlbum();
      if (wall_album != null)
        this.wall_album_id = wall_album.getId();
      if ((this.wall_album_id == null) && (callback != null)) {
        Error error = new Error();
        if (error.getMessage() == null)
          error.setMessage("reqeust fail request can't find wall album id");
        callback.requestFailure(null, error);
        this.logger.warn(error.toJsonStr());
        return null;
      }
    }
    HttpRequest request = RequestFactory.createPostPhotoRequest(this, this.wall_album_id, file, message, access_token);
    CallbackCache.instance().addCallback(request, callback);
    if (start_upload)
      ThreadManager.instance().execute(request);
    return request;
  }

  public HttpRequest postVideo2Wall(RequestCallback callback, File file, String title, String description,  String access_token, boolean start_upload) {
    HttpRequest request = RequestFactory.createPostVideoRequest(this, this.page_id, file, title, description,  access_token);
    CallbackCache.instance().addCallback(request, callback);
    if (start_upload)
      ThreadManager.instance().execute(request);
    return request;
  }

  public HttpRequest postFeed2Wall(RequestCallback callback, String message, String link, String picture, String name, String caption, String description, String access_token) {
    HttpRequest request = RequestFactory.createPostRequest(this, this.page_id, message, link, picture, name, caption, description, access_token);
    CallbackCache.instance().addCallback(request, callback);
    ThreadManager.instance().execute(request);
    return request;
  }

  public HttpRequest postComment2Wall(RequestCallback callback, String post_id, String message, String access_token) {
    HttpRequest request = RequestFactory.createPostCommentRequest(this, post_id, message, access_token);
    CallbackCache.instance().addCallback(request, callback);
    ThreadManager.instance().execute(request);
    return request;
  }

  public HttpRequest deletePost4Wall(RequestCallback callback, String post_id) {
    return deleteRequest(callback, post_id);
  }

  public HttpRequest deleteComment4Wall(RequestCallback callback, String comment_id) {
    return deleteRequest(callback, comment_id);
  }

  private HttpRequest deleteRequest(RequestCallback callback, String id) {
    HttpRequest request = RequestFactory.createDeleteRequest(this, id, this.access_token);
    CallbackCache.instance().addCallback(request, callback);
    ThreadManager.instance().execute(request);
    return request;
  }

  public HttpRequest replayMessage(RequestCallback callback, String message_id, String message) {
    if ((message_id != null) && (message_id.indexOf("_m_id") != -1)) {
      message_id = message_id.substring(0, message_id.indexOf("_m_id"));
    }
    HttpRequest request = RequestFactory.createReplyMessageRequest(this, message_id, message, this.access_token);
    CallbackCache.instance().addCallback(request, callback);
    ThreadManager.instance().execute(request);
    return request;
  }

  public Album getWallAlbum() {
    HttpRequest request = RequestFactory.createGetAlbumReqeust(null, this.page_id, this.access_token, null);
    request.run();
    String data = request.getResult();
    JSONObject jsonO = JsonUtils.getJSONObject(data);
    Album album = null;
    if (jsonO == null) {
      this.logger.warn("can't find wall album:" + data);
      return null;
    }
    try {
      JSONArray jsonA = jsonO.has("data") ? jsonO.getJSONArray("data") : null;
      if (jsonA == null) {
        this.logger.warn("can't find wall album:" + data);
        return null;
      }

      for (int i = 0; i < jsonA.length(); i++) {
        jsonO = jsonA.getJSONObject(i);
        if ((jsonO.has("id")) && (jsonO.has("type")) && 
          ("wall".equals(jsonO.getString("type")))) {
          album = new Album(jsonO.toString());
          DataCache.instance().addCache(DataType.wall_album, album);
        }
      }
      
      if(album==null){
    	  for (int i = 0; i < jsonA.length(); i++) {
              jsonO = jsonA.getJSONObject(i);
              if ((jsonO.has("id")) && (jsonO.has("can_upload")) && 
                ("true".equals(jsonO.getString("can_upload"))) &&
                (jsonO.has("name")) && ("Wall Photos".equals(jsonO.getString("name")))) {
                album = new Album(jsonO.toString());
                DataCache.instance().addCache(DataType.wall_album, album);
              }
           }
      }
     
      if(album==null){
    	  for (int i = 0; i < jsonA.length(); i++) {
              jsonO = jsonA.getJSONObject(i);
              if ((jsonO.has("id")) && (jsonO.has("can_upload")) && 
                ("true".equals(jsonO.getString("can_upload")))) {
                album = new Album(jsonO.toString());
                DataCache.instance().addCache(DataType.wall_album, album);
              }
           }
      }

    }
    catch (JSONException e)
    {
      this.logger.error("", e);
    }

    return album;
  }

  public void onPosts(JSONObject posts)
  {
    try
    {
      if (this.listeners.size() != 0) {
        Feed feed = new Feed(posts.toString());
        if ((feed.getPicture() != null) && (feed.getSource() == null)) {
          feed.setSource(FacebookUtils.getNormalSource(feed.getPicture()));
        }
        for(PageListener listener : listeners)
        	listener.onPosts(feed);
      }else{
    	  logger.warn("listener is null");
      }
    } catch (Exception e) {
      this.logger.error("", e);
    }
  }

  public void onComments(JSONObject post, List<JSONObject> comments)
  {
    if (this.listeners.size() == 0){
  	  logger.warn("listener is null");
  	  return;
    }
    try
    {
      Feed feed = new Feed(post.toString());
      for (JSONObject jsonO : comments){
    	  for(PageListener listener : listeners)
    		  listener.onComments(feed, new Comment(jsonO.toString()));
      }
    }
    catch (Exception e)
    {
      this.logger.error("", e);
    }
  }
  
  public void onComment(JSONObject post, JSONObject comment)
  {
	  if(feed_monitor!=null)
		  feed_monitor.setcomment(post, comment);
  }

  public void onMessages(List<JSONObject> messages)
  {
    if (this.listeners.size() == 0) {
	  logger.warn("listener is null");
  	  return;
    }
    try
    {
      for (JSONObject jsonO : messages)
    	  for(PageListener listener : listeners)
    		  listener.onMessages(new Message(jsonO.toString()));
    }
    catch (Exception e) {
      this.logger.error("", e);
    }
  }

  public void addListnener(PageListener listener) {
	if(listener==null)return;
    if(!listeners.contains(listener))
    	listeners.add(listener);
  }
  
  public void removeListnener(PageListener listener) {
		if(listener==null)return;
	    if(listeners.contains(listener))
	    	listeners.remove(listener);
  }

  public List<PageListener> getListener() {
    return this.listeners;
  }

  public void onSuccessful(HttpRequest request, StringBuilder sResult)
  {
    RequestCallback callback = CallbackCache.instance().removeCallback(request);
    if (callback != null)
      callback.reqeustSuccessful(request, sResult);
  }

  public void onFailure(HttpRequest request, StringBuilder sResult)
  {
    Error error = new Error(sResult.toString());
    if (error.getMessage() == null) {
      error.setMessage("reqeust fail " + sResult);
      error.setType("post request");
    }
    RequestCallback callback = CallbackCache.instance().removeCallback(request);
    if (callback != null)
      callback.requestFailure(request, error);
  }

  public void onTimeout(HttpRequest request)
  {
    Error error = new Error();
    error.setMessage("reqeust fail request timeout");
    error.setType("post request");
    RequestCallback callback = CallbackCache.instance().removeCallback(request);
    if (callback != null)
      callback.requestFailure(request, error);
  }

  public void onException(HttpRequest request, Exception e)
  {
    Error error = new Error();
    error.setMessage("reqeust fail request exception " + e.getMessage());
    error.setType("post request");
    RequestCallback callback = CallbackCache.instance().removeCallback(request);
    if (callback != null)
      callback.requestFailure(request, error);
  }

  public String getSessionID()
  {
    return this.page_id;
  }
  
  public void setSessionID(String session)
  {
    this.page_id = session;
  }

  public String getAccessToken()
  {
    return this.access_token;
  }

  private void setType(FacebookType type) {
    this.type = type;
  }

  public FacebookType getType() {
    return this.type;
  }

  public void setMe(BaseUser me) {
	this.me = me;
  }

  public BaseUser getMe() {
	if(me==null){
		 String about_me = null;
		 HttpRequest facebook_request = RequestFactory.createGetAboutMeReqeust(null, access_token);
         for (int i = 0; i < 3; i++) {
	          facebook_request.run();
	          about_me = facebook_request.getResult();
	          if (!"".equals(about_me)) break;
	     }
	     if (about_me == null) {
	          about_me = "{}";
	     }
	     setMe(new BaseUser(about_me));
	}
	return me;
  }
  public void setWall_album_id(String wall_album_id) {
		this.wall_album_id = wall_album_id;
  }
}