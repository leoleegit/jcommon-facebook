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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jcommon.com.facebook.seesion.Session;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.collections.MapStore;

public class SessionCache extends MapStore
{
  private static SessionCache instance = new SessionCache();

  public static SessionCache instance() { return instance; }

  public void addSession(String sessionID, Session session) {
    if (super.hasKey(sessionID))
      super.updateOne(sessionID, session);
    else
      super.addOne(sessionID, session);
  }

  public Session getSession(String sessionID) {
    if (super.hasKey(sessionID))
      return (Session)super.getOne(sessionID);
    return null;
  }

  public Session removeSession(String sessionID) {
    if (super.hasKey(sessionID))
      return (Session)super.removeOne(sessionID);
    return null;
  }

  public List<Session> getPageSession() {
    return getSessionByType(FacebookType.page);
  }

  public List<Session> getUserSession() {
    return getSessionByType(FacebookType.user);
  }
  
  public List<Session> getFacebookSession() {
	    return getSessionByType(null);
  }

  private List<Session> getSessionByType(FacebookType type) {
    List<Session> sessions = new ArrayList<Session>();
    Map<Object, Object> all = super.getAll();
    for (Iterator<?> i$ = all.values().iterator(); i$.hasNext(); ) { 
      Object o = i$.next();
      Session session = (Session)o;
      if (type==null || session.getType() == type)
        sessions.add(session);
    }
    return sessions;
  }
}