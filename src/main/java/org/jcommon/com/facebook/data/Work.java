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

public class Work extends JsonObject
{
  private BaseObject employer;
  private BaseObject position;
  private String start_date;

  public Work(String data)
  {
    super(data);
  }

  public BaseObject getEmployer() {
    return this.employer;
  }
  public void setEmployer(BaseObject employer) {
    this.employer = employer;
  }
  public BaseObject getPosition() {
    return this.position;
  }
  public void setPosition(BaseObject position) {
    this.position = position;
  }
  public String getStart_date() {
    return this.start_date;
  }
  public void setStart_date(String start_date) {
    this.start_date = start_date;
  }
}