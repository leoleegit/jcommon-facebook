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

public class App
{
  private String api_id;
  private String app_secret;
  private String app_link;
  private String permissions;
  private String access_token;
  private String verify_token = "jcommon-facebook";

  public App(String api_id, String app_secret)
  {
    this.api_id = api_id;
    this.app_secret = app_secret;
  }

  public String getApi_id() {
    return this.api_id;
  }

  public void setApi_id(String api_id) {
    this.api_id = api_id;
  }

  public String getApp_secret() {
    return this.app_secret;
  }

  public void setApp_secret(String app_secret) {
    this.app_secret = app_secret;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  public String getPermissions() {
    return this.permissions;
  }

  public void setApp_link(String app_link) {
	this.app_link = app_link;
  }
	
  public String getApp_link() {
	return app_link;
  }

  public void setAccess_token(String access_token) {
	this.access_token = access_token;
  }

  public String getAccess_token() {
	return access_token;
  }

  public void setVerify_token(String verify_token) {
	this.verify_token = verify_token;
  }

  public String getVerify_token() {
	return verify_token;
  }
  
  public boolean appVerify(String hub_challenge,String hub_verify_token,String hub_mode){
	  if("subscribe".equals(hub_mode) && (verify_token!=null && verify_token.equals(hub_verify_token)))
		  return true;
	  return false;
  }
}