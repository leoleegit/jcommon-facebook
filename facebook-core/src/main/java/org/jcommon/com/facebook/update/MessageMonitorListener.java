package org.jcommon.com.facebook.update;

import java.util.List;

import org.jcommon.com.facebook.object.Conversation;
import org.jcommon.com.facebook.object.Message;

public interface MessageMonitorListener {
	public void onMessage(Conversation conversation, List<Message> messages);
}
