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

import org.jcommon.com.facebook.utils.DataType;
import org.jcommon.com.util.JsonObject;
import org.jcommon.com.util.collections.MapStore;

public class DataCache extends MapStore
{
  private static DataCache instance = new DataCache();

  public static DataCache instance() { return instance; }

  public void addCache(DataType type, JsonObject json) {
    if (super.hasKey(type))
      super.updateOne(type, json);
    else
      super.addOne(type, json);
  }

  public JsonObject getCache(DataType type) {
    if (super.hasKey(type))
      return (JsonObject)super.getOne(type);
    return null;
  }

  public JsonObject removeCache(DataType type) {
    if (super.hasKey(type))
      return (JsonObject)super.removeOne(type);
    return null;
  }
  
  public boolean addCache(Object key, Object value){
	  if(super.hasKey(key))
		 return super.updateOne(key, value);
	  return super.addOne(key, value);
  }
  
  public Object getCache(Object key){
	  return super.getOne(key);
  }
  
  public Object removeCache(Object key){
	  return super.removeOne(key);
  }
  
}