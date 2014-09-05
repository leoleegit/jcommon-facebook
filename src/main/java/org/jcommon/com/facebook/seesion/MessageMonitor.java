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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.utils.FixMap;
import org.jcommon.com.facebook.utils.TempFileCache;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageMonitor extends RequestCallback
{
  private Logger logger = Logger.getLogger(getClass());
  private String page_id;
  private String access_token;
  private PageCallback listener;
  private static final int monitor_lenght = 100;
  private FixMap<String, Long> message_list = new FixMap<String, Long>(monitor_lenght);
  private Map<String, Long> message_cache = new HashMap<String, Long>();

  private boolean run = false;
  //private boolean error = false;
  private MessageMonitor.Task task;
  private Timer timer = new Timer();

  private static String prefix = "jcomconfacebook";
  private static final String suffix = ".pm";

  public MessageMonitor(String page_id, String access_token)
  {
    this.page_id = page_id;
    this.access_token = access_token;
    prefix = "jcomconfacebook" + "-" + page_id;
  }

  public void startup() {
    this.run = true;
    this.task = new MessageMonitor.Task();
    TempFileCache.loadFacebookFixCache(this.message_list, prefix, suffix);
    this.timer.schedule(this.task, 5000L, 20000L);
    this.logger.info(this.page_id + "running ...");
  }

  public void shutdown() {
    TempFileCache.saveFacebookFixCache(this.message_list, prefix, suffix);
    if (this.timer != null) {
      try {
        this.run = false;
        this.timer.cancel();
        this.task = null;
        this.timer = null;
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

  public void monitorCallback(JSONObject jsonO) {
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
        if (this.message_list.size() == 0)
          for (int i = 0; i < data.length(); i++) {
            JSONObject o = data.getJSONObject(i);
            if ((o != null) && (o.has("id")) && (o.has("updated_time")))
              this.message_list.put(o.getString("id"), Long.valueOf(o.getLong("updated_time")));
            else
              this.logger.warn("can't find id in jsonObject:" + o.toString());
          }
        else
          for (int i = data.length() - 1; i >= 0; i--) {
            JSONObject o = data.getJSONObject(i);
            if ((o != null) && (o.has("id")) && (o.has("updated_time"))) {
              String id = o.getString("id");
              
              if (!this.message_list.containsKey(id)) {
            	logger.info("catch one Message have created:"+id);
                this.message_list.put(id, Long.valueOf(o.getLong("updated_time")));
                this.message_cache.put(id, Long.valueOf(0L));
                HttpRequest re = RequestFactory.createGetMessageByIdReqeust(this, id, this.access_token);
                addOne(re, "messageCallback");
                ThreadManager.instance().execute(re);
              }
              else if (o.getLong("updated_time") != ((Long)this.message_list.get(id)).longValue()) {
            	logger.info("catch one Message have updated:	"+id);
                this.message_cache.put(id, this.message_list.get(id));
                this.message_list.put(id, Long.valueOf(o.getLong("updated_time")));
                HttpRequest re = RequestFactory.createGetMessageByIdReqeust(this, id, this.access_token);
                addOne(re, "messageCallback");
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
  }

  public void messageCallback(String message_id, JSONObject jsonO) {
    if (jsonO != null)
      try {
    	//logger.info(message_id+":\n"+jsonO.toString());
        long update_time = this.message_cache.remove(message_id);
        
        List<JSONObject> update_messages = new ArrayList<JSONObject>();
        JSONArray data = jsonO.getJSONArray("data");
        for (int i = data.length() - 1; i >= 0; i--) {
          JSONObject o = data.getJSONObject(i);
          long create_time = o.has("created_time") ? o.getLong("created_time") : 0L;
          if (create_time > update_time) {
        	logger.info(create_time+":\n"+o.toString()); 
            try {
              Object temp_id = o.remove("id");
              if (temp_id != null)
                o.accumulate("id", message_id + "_" + temp_id);
            }
            catch (JSONException e) {
              this.logger.error("", e);
            }
            update_messages.add(o);
          }
        }
        if ((update_messages.size() != 0) && (this.listener != null))
          this.listener.onMessages(update_messages);
      }
      catch (JSONException e) {
        this.logger.error("", e);
      }
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
        if (method.equals("messageCallback")) {
          String url = reqeust.getUrl();
          String message_id = getMessage_id(url);
          messageCallback(message_id, jsonO);
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
  
  public void onFailure(HttpRequest reqeust, StringBuilder sResult)
  {
    super.onFailure(reqeust, sResult);
    logger.warn(sResult.toString());
  }

  private String getMessage_id(String url)
  {
    url = url.replace(RequestFactory.graph_url, "");
    url = url.substring(RequestFactory.version.length()+1, url.indexOf("/",5));
    return url;
  }
  
  class Task extends TimerTask
  {
    public void run()
    {
      if(!run)return;
      HttpRequest re = RequestFactory.createGetMessageUpdateReqeust(MessageMonitor.this, MessageMonitor.this.page_id, MessageMonitor.this.access_token);
      MessageMonitor.this.addOne(re, "monitorCallback");
      ThreadManager.instance().execute(re);
    }
  }
}