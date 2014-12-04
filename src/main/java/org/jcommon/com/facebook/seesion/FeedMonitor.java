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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.FacebookSession;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.utils.FacebookUtils;
import org.jcommon.com.facebook.utils.FixMap;
import org.jcommon.com.facebook.utils.TempFileCache;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedMonitor extends RequestCallback
{
  private Logger logger = Logger.getLogger(getClass());
  private String page_id;
  private String access_token;
  private PageCallback listener;
  private static final int monitor_lenght = 100;
  private FixMap<String, Long> post_list  = new FixMap<String, Long>(-1);
  private FixMap<String, String> after_list  = new FixMap<String, String>(-1);
  private Map<String, Long> post_cache = new HashMap<String, Long>();
  private Map<HttpRequest, JSONObject> request_cache = new HashMap<HttpRequest, JSONObject>();

  private boolean run = false;
  private Timer timer_graph = null;
  private Timer timer_fql   = null;
  private static String prefix = "jcomconfacebook";
  private static final String suffix = ".feed";

  public FeedMonitor(String page_id, String access_token)
  {
    this.page_id = page_id;
    this.access_token = access_token;
    prefix = "jcomconfacebook" + "-" + page_id;
  }

  public void setAccessToken(String access_token) {
		// TODO Auto-generated method stub
		this.access_token = access_token; 
  }
  public void startup() {
    this.run = true;
    TempFileCache.loadFacebookFixCache(this.post_list, prefix, suffix);
    TempFileCache.loadFacebookFixCache_(this.after_list, prefix, ".comment");
    
    timer_graph =  org.jcommon.com.util.thread.TimerTaskManger.instance().schedule("FeedMonitor-Graph", new TimerTask(){
    	public void run(){
    		if(!run)return;
    	      
    	    HttpRequest re = RequestFactory.createGetFeedUpdateReqeust(FeedMonitor.this, FeedMonitor.this.page_id, FeedMonitor.this.access_token, monitor_lenght);
    	    FeedMonitor.this.addOne(re, "monitorCallback");
    	    ThreadManager.instance().execute(re);
    	}
    }, 5000L, 10000L);
    timer_fql   =  org.jcommon.com.util.thread.TimerTaskManger.instance().schedule("FeedMonitor-Fql", new TimerTask(){
    	public void run(){
    		if(!run)return;
    	      
    	    HttpRequest re = RequestFactory.createGetFeedUpdateReqeustByFql(FeedMonitor.this, FeedMonitor.this.page_id, FeedMonitor.this.access_token, monitor_lenght);
    	    FeedMonitor.this.addOne(re, "monitorCallback");
    	    ThreadManager.instance().execute(re);
    	}
    }, 20000L, 180000L);
    this.logger.info(this.page_id + "running ...");
  }

  public void shutdown() {
    TempFileCache.saveFacebookFixCache(this.post_list, prefix, suffix);
    TempFileCache.saveFacebookFixCache(this.after_list, prefix, ".comment");
    this.run = false;
    if (this.timer_graph != null) {
      try {
        this.timer_graph.cancel();
        this.timer_graph = null;
      } catch (Exception e) {
        this.logger.error("", e);
      }
    }
    if (this.timer_fql != null) {
        try {
          this.timer_fql.cancel();
          this.timer_fql = null;
        } catch (Exception e) {
          this.logger.error("", e);
        }
      }
    this.logger.info(this.page_id + "shutdown ...");
  }

  public void setListener(PageCallback listener) {
    this.listener = listener;
  }

  public PageCallback getListener() {
    return this.listener;
  }

  private boolean checking=false;
  public synchronized void monitorCallback(JSONObject jsonO) {
	if(checking)
		return;
	checking = true;
    if (jsonO == null) {
      this.logger.warn("jsonO is null!");
      return;
    }
    if (jsonO.has("data"))
      try {
        JSONArray data = jsonO.getJSONArray("data");
        if (data == null) {
          this.logger.warn("data is null");
          return;
        }
        if(FacebookSession.debug)
        	logger.info("data size:"+data.length());
        if (this.post_list.size() == 0)
          for (int i = data.length() - 1; i >= 0; i--) {
            JSONObject o = data.getJSONObject(i);
            String id    = o.has("id")?o.getString("id"):(o.has("post_id")?o.getString("post_id"):null);
            if ((o != null) && (id!=null) && (o.has("updated_time"))){
              Long 	updated_time = Long.valueOf(o.getLong("updated_time"));
              Long 	comment_updated_time = getCommentUpatedTime(o);
              updated_time = comment_updated_time>updated_time?comment_updated_time:updated_time;
              this.post_list.put(id, updated_time);
            }
            else
              this.logger.warn("can't find id in jsonObject:" + o.toString());
          }
        else
          for (int i = data.length() - 1; i >= 0; i--) {
            JSONObject o = data.getJSONObject(i);
            String id    = o.has("id")?o.getString("id"):(o.has("post_id")?o.getString("post_id"):null);
            if ((o != null) && (id!=null) && (o.has("updated_time"))) {
              Long 	updated_time = Long.valueOf(o.getLong("updated_time"));
              Long 	comment_updated_time = getCommentUpatedTime(o);
              if(FacebookSession.debug)
            	  logger.info(String.format("post id:%s;update time:%s;comment_updated_time:%s", id, updated_time, comment_updated_time));
              updated_time = comment_updated_time>updated_time?comment_updated_time:updated_time;
              if (!this.post_list.containsKey(id)) {
            	logger.info("catch one post have created:"+id);
                this.post_cache.put(id, Long.valueOf(0L));
                this.post_list.put(id, updated_time);
                HttpRequest re = RequestFactory.createGetDetailRequest(this, id, null, this.access_token);
                addOne(re, "postCallback");
                ThreadManager.instance().execute(re);

                re = RequestFactory.createGetDetailRequest(this, id, null, this.access_token);
                addOne(re, "commentCallback");
                ThreadManager.instance().execute(re);
              }
              else if (updated_time != ((Long)this.post_list.get(id)).longValue()) {
            	logger.info("catch one post have updated:	"+id);
                this.post_cache.put(id, this.post_list.get(id));
                this.post_list.put(id, updated_time);
                HttpRequest re = RequestFactory.createGetDetailRequest(this, id, null, this.access_token);
                addOne(re, "commentCallback");
                ThreadManager.instance().execute(re);
              }
            }
            else {
              this.logger.warn("can't find id in jsonObject:" + o.toString());
            }
          }
      }
      catch (JSONException e) {
        this.logger.error("", e);
      }
    else
      this.logger.warn("not a correct json:" + jsonO.toString());
    checking = false;
  }

  private Long getCommentUpatedTime(JSONObject jsonO) throws JSONException {
	// TODO Auto-generated method stub
	if(jsonO.has("comments")) {
		JSONObject comments = jsonO.has("comments")?jsonO.getJSONObject("comments"):null;
		JSONArray data = comments.has("data") ? comments.getJSONArray("data") : null;
        if (data == null)
        {
          return 0L;
        }
        for (int i = 0; i < data.length(); i++) {
            JSONObject o = data.getJSONObject(i);
            if(o.has("created_time")){
            	return o.getLong("created_time");
            }
        }
	}
	return 0L;
}

public void postCallback(JSONObject jsonO) {
    if ((jsonO != null) && (!jsonO.has("message"))) {
//      try {
//        String id = jsonO.has("id") ? jsonO.getString("id") : null;
//        if (id != null)
//          this.post_cache.remove(id);
//      }
//      catch (JSONException e) {
//        this.logger.error("", e);
//      }
    	logger.warn("jsonO can't find message key:"+jsonO.toString());
    }
    else if ((this.listener != null) && (jsonO != null))
      this.listener.onPosts(jsonO);
  }

  public void setcomment(JSONObject post, JSONObject comment){
	  if(post!=null && comment!=null){
		  try {
			  String id = post.has("id")?post.getString("id"):null;
			  long   updated_time = post.has("updated_time")?Long.valueOf(post.getLong("updated_time")):0;
			  if(!post_list.containsKey(id) || post_list.get(id)< updated_time){
				  this.post_list.put(id, updated_time);
				  JSONObject[] comm = {comment};
				  this.listener.onComments(post, Arrays.asList(comm));
			  }	  
		  }
	      catch (JSONException e)
	      {
	        this.logger.error("", e);
	      }
	  }
  }
  
  public void commentCallback(JSONObject jsonO) {
    if (jsonO != null)
      try {
        String post_id = jsonO.has("id") ? jsonO.getString("id") : null;
        if (post_id == null) {
          this.logger.warn("can't find id in jsonObject:" + jsonO.toString());
          return;
        }
        if(!this.post_cache.containsKey(post_id)){
        	this.logger.warn("can't find id in post_cache:" + post_id);
        	return;
        }
        long update_time = ((Long)this.post_cache.remove(post_id)).longValue();
        JSONObject comments = jsonO.has("comments")?jsonO.getJSONObject("comments"):null;
        if (comments == null) {
          this.logger.warn("can't find comments in jsonObject:" + jsonO.toString());
          return;
        }

        JSONArray data = comments.has("data") ? comments.getJSONArray("data") : null;
        if (data == null)
        {
          return;
        }
        int count = comments.has("count") ? comments.getInt("count") : 0;
        int offset = 0;
        String after_id = after_list.containsKey(post_id)?after_list.get(post_id):null;
        if (count > 25) {
          offset = count - 25;
        }
       
        jsonO.accumulate("since_update_time", Long.valueOf(update_time));
        if(FacebookSession.debug)
        	logger.info(String.format("post id:%s,since update time:%s;after id:%s", post_id,update_time,after_id));
        HttpRequest re = after_id!=null?RequestFactory.createGetCommentsReqeust(this, post_id, this.access_token, null, after_id):
        	RequestFactory.createGetCommentsReqeust(this, post_id, this.access_token, null, offset);
        this.request_cache.put(re, jsonO);
        addOne(re, "commentDetailCallback");
        ThreadManager.instance().execute(re);
      }
      catch (JSONException e)
      {
        this.logger.error("", e);
      }
  }

  public void commentDetailCallback(JSONObject post, JSONObject comments)
  {
    //logger.info("comments' detail :\n"+comments.toString());	 
    try {
      long since_update_time = post.getLong("since_update_time");
      JSONArray data = comments.has("data") ? comments.getJSONArray("data") : null;

      List<JSONObject> update_comments = new ArrayList<JSONObject>();
      for (int i = 0; i < data.length(); i++) {
        JSONObject o = data.getJSONObject(i);
        long create_time = o.has("created_time") ? o.getLong("created_time") : 0L;
        String comment_id = o.has("id")?o.getString("id"):null;
        if(FacebookSession.debug)
        	logger.info(String.format("comment id:%s;create time:%s;since update time:%s", comment_id,create_time,since_update_time));
        if (create_time > since_update_time)
          update_comments.add(o);
      }
      if ((update_comments.size() != 0) && (this.listener != null)){
    	List<JSONObject> sort_comments  = FacebookUtils.sortComments(update_comments);
    	JSONObject o = sort_comments.get(sort_comments.size()-1);
    	if(o!=null && o.has("id") && post.has("id")){
    		after_list.put(post.getString("id"), o.getString("id"));
    	}
        this.listener.onComments(post, sort_comments);
      }
    }
    catch (JSONException e)
    {
      this.logger.error("", e);
    }
  }
  
  public void onFailure(HttpRequest reqeust, StringBuilder sResult)
  {
    super.onFailure(reqeust, sResult);
    logger.warn(sResult.toString());
  }

  public void onSuccessful(HttpRequest reqeust, StringBuilder sResult)
  {
    String str = sResult.toString();
    String method = null;
    JSONObject jsonO = null;

    if (super.hasKey(reqeust)) {
      method = (String)super.getOne(reqeust);
      super.removeOne(reqeust);
    }
    try
    {
      if (!str.startsWith("{")) {
        this.logger.warn("request fail:" + str);
        return;
      }
      jsonO = JsonUtils.getJSONObject(str);
      if ((jsonO != null) && (jsonO.has("error"))) {
        //this.error = true;
        this.logger.warn("request error:" + jsonO.getString("error"));
        return;
      }

      if ((method != null) && (jsonO != null)) {
        if (method.equals("commentDetailCallback")) {
          JSONObject post = (JSONObject)this.request_cache.remove(reqeust);
          commentDetailCallback(post, jsonO);
          return;
        }
        Method m = JsonUtils.getMethod(getClass(), method);
        if (m != null)
          m.invoke(this, new Object[] { jsonO });
        else
          this.logger.warn("can't found map method:" + method);
      }
    }
    catch (Throwable t) {
      this.logger.error("", t);
    }
  }
}