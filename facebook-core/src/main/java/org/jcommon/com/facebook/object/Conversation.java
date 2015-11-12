package org.jcommon.com.facebook.object;


public class Conversation extends JsonObject {
	private Long updated_time;
	private String id;
	private String link;
	private boolean can_reply;
	private int message_count;
	private Message messages;
	private User senders;
	private User participants;
	
	public Conversation(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}

	public Conversation(String json) {
		super(json, true);
		// TODO Auto-generated constructor stub
	}

	public Long getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(Long updated_time) {
		this.updated_time = updated_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isCan_reply() {
		return can_reply;
	}

	public void setCan_reply(boolean can_reply) {
		this.can_reply = can_reply;
	}

	public int getMessage_count() {
		return message_count;
	}

	public void setMessage_count(int message_count) {
		this.message_count = message_count;
	}

	public Message getMessages() {
		return messages;
	}

	public void setMessages(Message messages) {
		this.messages = messages;
	}

	public void setSenders(User senders) {
		this.senders = senders;
	}

	public User getSenders() {
		return senders;
	}

	public void setParticipants(User participants) {
		this.participants = participants;
	}

	public User getParticipants() {
		return participants;
	}
}
