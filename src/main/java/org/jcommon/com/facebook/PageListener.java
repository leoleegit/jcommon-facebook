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
package org.jcommon.com.facebook;

import org.jcommon.com.facebook.data.Comment;
import org.jcommon.com.facebook.data.Feed;
import org.jcommon.com.facebook.data.Message;

public abstract interface PageListener
{
  public abstract void onPosts(Feed paramFeed)
    throws Exception;

  public abstract void onComments(Feed paramFeed, Comment paramComment)
    throws Exception;

  public abstract void onMessages(Message paramMessage)
    throws Exception;
}