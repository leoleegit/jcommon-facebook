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

public class AccessToken extends BaseObject
{
  private String access_token;
  private String category;
  private String perms;
  private String expired;

  public AccessToken(String data)
  {
    super(data);
  }

  public AccessToken() {
	// TODO Auto-generated constructor stub
	  super(null);
  }

  public String getAccess_token() {
    return this.access_token;
  }
  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
  public String getCategory() {
    return this.category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public String getPerms() {
    return this.perms;
  }
  public void setPerms(String perms) {
    this.perms = perms;
  }
  public void setExpired(String expired) {
    this.expired = expired;
  }
  public String getExpired() {
    return this.expired;
  }
}