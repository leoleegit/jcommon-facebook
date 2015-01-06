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
package org.jcommon.com.facebook.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jcommon.com.util.JsonObject;
import org.jcommon.com.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Feed extends JsonObject
{
  private String id;
  private BaseUser from;
  private List<BaseUser> to;
  private String message;
  private String created_time;
  private String updated_time;
  private String type;
  private List<Link> actions;
  private String picture;
  private String source;
  private Privacy privacy;
  private String link;
  private String icon;
  private String status_type;
  private String object_id;

  public Feed(String data)
  {
    super(data);

    JSONObject jsonO = JsonUtils.getJSONObject(data);
    if (jsonO != null)
      try {
        if (jsonO.has("to")) {
          String to = jsonO.getString("to");
          List<Object> list = null;
          if ((to != null) && (to.startsWith("["))) {
            list = json2Objects(BaseUser.class, jsonO.getString("to"));
          } else if ((to != null) && (to.startsWith("{"))) {
            JSONObject o = JsonUtils.getJSONObject(to);
            if (o.has("data")) {
              list = json2Objects(BaseUser.class, o.getString("data"));
            }
          }
          resetTo(list);
        }
        if (jsonO.has("actions")) {
          List<Object> list = json2Objects(Link.class, jsonO.getString("actions"));
          resetLink(list);
        }
      }
      catch (JSONException e) {
        logger.error("", e);
      }
  }

  private void resetLink(List<Object> list)
  {
    if (list == null) return;
    if (this.actions == null) this.actions = new ArrayList<Link>();
    for (Iterator<?> i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
      this.actions.add((Link)o); }
  }

  private void resetTo(List<Object> list)
  {
    if (list == null) return;
    if (this.to == null) this.to = new ArrayList<BaseUser>();
    for (Iterator<?> i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
      this.to.add((BaseUser)o); }
  }

  public String getPicture()
  {
    return this.picture;
  }

  public void setPicture(String picture)
  {
    this.picture = picture;
  }

  public String getSource() {
    return this.source;
  }

  public void setSource(String source)
  {
    this.source = source;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BaseUser getFrom() {
    return this.from;
  }

  public void setFrom(BaseUser from) {
    this.from = from;
  }

  public List<BaseUser> getTo() {
    return this.to;
  }

  public void setTo(List<BaseUser> to) {
    this.to = to;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCreated_time() {
    return this.created_time;
  }

  public void setCreated_time(String created_time) {
    this.created_time = created_time;
  }

  public String getUpdated_time() {
    return this.updated_time;
  }

  public void setUpdated_time(String updated_time) {
    this.updated_time = updated_time;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<Link> getActions() {
    return this.actions;
  }

  public void setActions(List<Link> actions) {
    this.actions = actions;
  }

  public void setPrivacy(Privacy privacy) {
    this.privacy = privacy;
  }

  public Privacy getPrivacy() {
    return this.privacy;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getIcon() {
    return this.icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getStatus_type() {
    return this.status_type;
  }

  public void setStatus_type(String status_type) {
    this.status_type = status_type;
  }

  public String getObject_id() {
    return this.object_id;
  }

  public void setObject_id(String object_id) {
    this.object_id = object_id;
  }
}