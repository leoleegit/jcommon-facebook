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

import org.jcommon.com.util.JsonObject;

public class Album extends JsonObject
{
  private String id;
  private BaseUser from;
  private String name;
  private String description;
  private String link;
  private String cover_photo;
  private String privacy;
  private int count;
  private String type;
  private String created_time;
  private String updated_time;
  private boolean can_upload;

  public Album(String data)
  {
    super(data);
  }

  public String getId() {
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

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getCover_photo() {
    return this.cover_photo;
  }

  public void setCover_photo(String cover_photo) {
    this.cover_photo = cover_photo;
  }

  public String getPrivacy() {
    return this.privacy;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  public int getCount() {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
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

  public boolean isCan_upload() {
    return this.can_upload;
  }

  public void setCan_upload(boolean can_upload) {
    this.can_upload = can_upload;
  }
}