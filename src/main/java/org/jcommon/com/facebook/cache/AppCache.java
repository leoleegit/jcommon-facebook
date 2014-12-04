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
package org.jcommon.com.facebook.cache;

import java.util.ArrayList;
import java.util.Collection;

import org.jcommon.com.facebook.data.App;
import org.jcommon.com.util.collections.MapStore;

public class AppCache extends MapStore
{
  private static AppCache instance = new AppCache();

  public static AppCache instance() { return instance; }

  public void addApp(String key, App app) {
    if (super.hasKey(key))
      super.updateOne(key, app);
    else
      super.addOne(key, app);
  }

  public App getApp(String key) {
    if (super.hasKey(key)) {
      return (App)super.getOne(key);
    }
    return null;
  }
  
  public App getAppByID(String api_id) {
	  if(api_id==null)return null;
	  Collection<Object> apps = super.getAll().values();
	  while(!apps.isEmpty()){
		  App app =  (App) apps.iterator().next();
		  if(api_id.equals(app.getApi_id()))
			  return app;
	  }
      return null;
  }
  
  public App getApp(){
	  Collection<Object> apps = super.getAll().values();
	  if(!apps.isEmpty())
		  return (App) apps.iterator().next();
	  return null;
  }

  public App removeApp(String key) {
    return (App)super.removeOne(key);
  }

  public Collection<App> getApps() {
	Collection<App> apps_ = new ArrayList<App>();
	Collection<Object> apps = super.getAll().values();
	while(!apps.isEmpty())
		 apps_.add((App) apps.iterator().next()) ;
	return apps_;
  }
}