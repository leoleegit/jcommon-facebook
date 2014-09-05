package org.jcommon.com.facebook.data;

import org.jcommon.com.util.JsonObject;

public class Proxy extends JsonObject{
	public  final static int FEED    = 1;
	public  final static int COMMENT = 2;
	public  final static int MESSAGE = 3;
	
	private int  type;
	private String id;
	private Feed feed_;
	private Comment comment_;
	private Message message_;
	
	public Proxy(String data){
		super(data);
	}

	public Proxy(String id, Feed feed, Comment comment, Message message){
		this.id   = id;
		this.feed_ = feed;
		this.comment_  = comment;
		this.message_  = message;
	
		if(message!=null)
			setType(MESSAGE);
		else if(comment!=null)
			setType(COMMENT);
		else if(feed!=null)
			setType(FEED);
	}

	
	public Feed getFeed_() {
		return feed_;
	}

	public void setFeed_(Feed feed_) {
		this.feed_ = feed_;
	}

	public Comment getComment_() {
		return comment_;
	}

	public void setComment_(Comment comment_) {
		this.comment_ = comment_;
	}

	public Message getMessage_() {
		return message_;
	}

	public void setMessage_(Message message_) {
		this.message_ = message_;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
