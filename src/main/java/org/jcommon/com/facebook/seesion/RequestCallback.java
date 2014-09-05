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

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.collections.MapStore;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.json.JSONObject;

abstract class RequestCallback extends MapStore
  implements HttpListener
{
  private Logger logger = Logger.getLogger(getClass());

  public void onSuccessful(HttpRequest reqeust, StringBuilder sResult)
  {
    String str = sResult.toString();
    String method = null;
    JSONObject jsonO = null;

    if (super.hasKey(reqeust)) {
      method = (String)super.getOne(reqeust);
      super.removeOne(reqeust);
    }
    try
    {
      if (!str.startsWith("{")) {
        this.logger.warn("request fail:" + str);
        return;
      }
      jsonO = JsonUtils.getJSONObject(str);
      if ((jsonO != null) && (jsonO.has("error"))) {
        this.logger.warn("request error:" + jsonO.getString("error"));
        return;
      }

      if ((method != null) && (jsonO != null)) {
        Method m = JsonUtils.getMethod(getClass(), method);
        if (m != null)
          m.invoke(this, new Object[] { jsonO });
        else
          this.logger.warn("can't found map method:" + method);
      }
    }
    catch (Throwable t) {
      this.logger.error("", t);
    }
  }

  public void onFailure(HttpRequest reqeust, StringBuilder sResult)
  {
    if (super.hasKey(reqeust))
      super.removeOne(reqeust);
  }

  public void onTimeout(HttpRequest reqeust)
  {
    if (super.hasKey(reqeust))
      super.removeOne(reqeust);
  }

  public void onException(HttpRequest reqeust, Exception e)
  {
    if (super.hasKey(reqeust))
      super.removeOne(reqeust);
  }
}