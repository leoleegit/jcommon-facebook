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

import org.jcommon.com.facebook.RequestCallback;
import org.jcommon.com.util.collections.MapStore;
import org.jcommon.com.util.http.HttpRequest;

public class CallbackCache extends MapStore
{
  private static CallbackCache instance = new CallbackCache();

  public static CallbackCache instance() { return instance; }

  public void addCallback(HttpRequest request, RequestCallback callback) {
    if (super.hasKey(request))
      super.updateOne(request, callback);
    else
      super.addOne(request, callback);
  }

  public RequestCallback getCallback(HttpRequest reqeust) {
    if (super.hasKey(reqeust))
      return (RequestCallback)super.getOne(reqeust);
    return null;
  }

  public RequestCallback removeCallback(HttpRequest request) {
    if (super.hasKey(request))
      return (RequestCallback)super.removeOne(request);
    return null;
  }
}