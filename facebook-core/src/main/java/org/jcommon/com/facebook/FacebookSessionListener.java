package org.jcommon.com.facebook;


import org.jcommon.com.facebook.object.Comment;
import org.jcommon.com.facebook.object.Conversation;
import org.jcommon.com.facebook.object.Feed;
import org.jcommon.com.facebook.object.Message;

public interface FacebookSessionListener {
	public void onFeed(Feed feed);
	public void onComment(Feed feed, Comment comment);
	public void onMessage(Conversation conversation, Message messages);
}
