package org.jcommon.com.facebook;


import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Feed;

public interface FacebookSessionListener {
	public void onFeed(Feed feed);
	public void onComment(Feed feed, Comment comment);
}
