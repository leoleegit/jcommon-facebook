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

public class Page extends JsonObject
{
  private String name;
  private String is_published;
  private String has_added_app;
  private String username;
  private String about;
  private boolean can_post;
  private int talking_about_count;
  private int unread_notif_count;
  private int unread_message_count;
  private int unseen_message_count;
  private String promotion_ineligible_reason;
  private String category;
  private String id;
  private String link;
  private int likes;
  private Cover cover;

  public Page(String data)
  {
    super(data);
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIs_published() {
    return this.is_published;
  }

  public void setIs_published(String is_published) {
    this.is_published = is_published;
  }

  public String getHas_added_app() {
    return this.has_added_app;
  }

  public void setHas_added_app(String has_added_app) {
    this.has_added_app = has_added_app;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAbout() {
    return this.about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public boolean isCan_post() {
    return this.can_post;
  }

  public void setCan_post(boolean can_post) {
    this.can_post = can_post;
  }

  public int getTalking_about_count() {
    return this.talking_about_count;
  }

  public void setTalking_about_count(int talking_about_count) {
    this.talking_about_count = talking_about_count;
  }

  public int getUnread_notif_count() {
    return this.unread_notif_count;
  }

  public void setUnread_notif_count(int unread_notif_count) {
    this.unread_notif_count = unread_notif_count;
  }

  public int getUnread_message_count() {
    return this.unread_message_count;
  }

  public void setUnread_message_count(int unread_message_count) {
    this.unread_message_count = unread_message_count;
  }

  public int getUnseen_message_count() {
    return this.unseen_message_count;
  }

  public void setUnseen_message_count(int unseen_message_count) {
    this.unseen_message_count = unseen_message_count;
  }

  public String getPromotion_ineligible_reason() {
    return this.promotion_ineligible_reason;
  }

  public void setPromotion_ineligible_reason(String promotion_ineligible_reason) {
    this.promotion_ineligible_reason = promotion_ineligible_reason;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLink() {
    return this.link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public int getLikes() {
    return this.likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public Cover getCover() {
    return this.cover;
  }

  public void setCover(Cover cover) {
    this.cover = cover;
  }
}