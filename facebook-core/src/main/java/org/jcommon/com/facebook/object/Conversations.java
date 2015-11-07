package org.jcommon.com.facebook.object;

import java.util.List;

public class Conversations extends JsonObject {
	private Long updated_time;
	private String id;
	private String link;
	private boolean can_reply;
	private int message_count;
	private List<User> senders;
	private List<User> participants;
	
	public Conversations(String json, boolean decode) {
		super(json, decode);
		// TODO Auto-generated constructor stub
	}

}
