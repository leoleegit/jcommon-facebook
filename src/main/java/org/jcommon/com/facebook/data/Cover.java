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

public class Cover extends JsonObject
{
  private String cover_id;
  private String source;
  private String offset_y;

  public Cover(String data)
  {
    super(data);
  }

  public String getCover_id()
  {
    return this.cover_id;
  }

  public void setCover_id(String cover_id) {
    this.cover_id = cover_id;
  }

  public String getSource() {
    return this.source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getOffset_y() {
    return this.offset_y;
  }

  public void setOffset_y(String offset_y) {
    this.offset_y = offset_y;
  }
}