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

public class Comment extends JsonObject
{
  private String id;
  private String message;
  private String created_time;
  private BaseUser from;

  public Comment(String data)
  {
    super(data);
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
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

  public BaseUser getFrom() {
    return this.from;
  }

  public void setFrom(BaseUser from) {
    this.from = from;
  }
}