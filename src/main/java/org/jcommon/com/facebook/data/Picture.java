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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jcommon.com.util.JsonObject;
import org.jcommon.com.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Picture extends JsonObject
{
  private List<Picture.Data> data;

  public Picture(String data)
  {
    super(data);
    JSONObject jsonO = JsonUtils.getJSONObject(data);
    if (jsonO != null)
      try {
        if (jsonO.has("data")) {
					List<Object> list = json2Objects(Picture.Data.class,
							jsonO.getString("data"));
          resetData(list);
        }
      }
      catch (JSONException e) {
				logger.error("", e);
      }
  }

  private void resetData(List<Object> list)
  {
    if (list == null) return;
    if (this.data == null) this.data = new ArrayList<Picture.Data>();
    for (Iterator<?> i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
      this.data.add((Picture.Data)o); }
  }

  public void setData(List<Picture.Data> data)
  {
    this.data = data;
  }

  public List<Picture.Data> getData() {
    return this.data;
  }
  
  public class Data extends JsonObject
  {
    public Data(String data)
    {
      super(data);
    }
  }
}