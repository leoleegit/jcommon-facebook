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

import org.jcommon.com.util.Json2Object;
import org.jcommon.com.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class User extends BaseUser
{
  public static final String fields = "id,name,link,username,location,work,gender,timezone,locale,picture";
  private String link;
  private BaseObject location;
  private String username;
  private String gender;
  private int timezone;
  private String locale;
  private Picture picture;
  private List<Work> work;

  public User(String data)
  {
    super(data);

    JSONObject jsonO = JsonUtils.getJSONObject(data);
    if (jsonO != null)
      try {
        if (jsonO.has("work")) {
          List<Object> list = Json2Object.json2Objects(Picture.Data.class, jsonO.getString("work"));
          resetWork(list);
        }
      }
      catch (JSONException e) {
        Json2Object.logger.error("", e);
      }
  }

  private void resetWork(List<Object> list)
  {
    if (list == null) return;
    if (this.work == null) this.work = new ArrayList<Work>();
    for (Iterator<?> i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
      this.work.add((Work)o); }
  }

  public String getLink() {
    return this.link;
  }
  public void setLink(String link) {
    this.link = link;
  }
  public BaseObject getLocation() {
    return this.location;
  }
  public void setLocation(BaseObject location) {
    this.location = location;
  }
  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getGender() {
    return this.gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public int getTimezone() {
    return this.timezone;
  }
  public void setTimezone(int timezone) {
    this.timezone = timezone;
  }
  public String getLocale() {
    return this.locale;
  }
  public void setLocale(String locale) {
    this.locale = locale;
  }
  public Picture getPicture() {
    return this.picture;
  }
  public void setPicture(Picture picture) {
    this.picture = picture;
  }
  public List<Work> getWork() {
    return this.work;
  }
  public void setWork(List<Work> work) {
    this.work = work;
  }
  public static String getFields() {
    return "id,name,link,username,location,work,gender,timezone,locale,picture";
  }
}