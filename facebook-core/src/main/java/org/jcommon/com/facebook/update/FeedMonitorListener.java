package org.jcommon.com.facebook.update;

import java.util.List;

import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Feed;

public interface FeedMonitorListener {
	public void onFeed(Feed feed);
	public void onComment(Feed feed, List<Comment> comments);
}
