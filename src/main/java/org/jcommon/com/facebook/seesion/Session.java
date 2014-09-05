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
import java.util.List;

import org.jcommon.com.facebook.PageListener;
import org.jcommon.com.facebook.RequestCallback;
import org.jcommon.com.facebook.data.BaseUser;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.http.HttpRequest;

public abstract interface Session
{
  public abstract void login(FacebookType type);

  public abstract void logout();

  public abstract String getSessionID();
  
  public abstract void setSessionID(String session);

  public abstract String getAccessToken();

  public abstract FacebookType getType();
  
  public HttpRequest postPhoto2Wall(RequestCallback callback, File file, String message, boolean start_upload);
  
  public HttpRequest postVideo2Wall(RequestCallback callback, File file, String title, String description,  boolean start_upload);
  
  public HttpRequest postFeed2Wall(RequestCallback callback, String message, String link, String picture, String name, String caption, String description);

  public HttpRequest postComment2Wall(RequestCallback callback, String post_id, String message);

  public HttpRequest deletePost4Wall(RequestCallback callback, String post_id) ;

  public HttpRequest deleteComment4Wall(RequestCallback callback, String comment_id) ;
  
  public HttpRequest replayMessage(RequestCallback callback, String message_id, String message);
  
  public void addListnener(PageListener listener);
  
  public void removeListnener(PageListener listener);
  
  public List<PageListener> getListener();
  
  public BaseUser getMe();
}

