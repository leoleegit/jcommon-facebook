package org.jcommon.com.facebook.object;

import java.util.List;


public class Feed extends JsonObject {
	private Comment comments;
	private String id;
	private User from;
	private List<User> to;
	private String message;
	private String created_time;
	private String updated_time;
	private String type;
	private List<Link> actions;
	private String picture;
	private String source;
	private Privacy privacy;
	private String link;
	private String icon;
	private String status_type;
	private String object_id;
	
	public Feed(String json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public List<User> getTo() {
		return to;
	}

	public void setTo(List<User> to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Link> getActions() {
		return actions;
	}

	public void setActions(List<Link> actions) {
		this.actions = actions;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStatus_type() {
		return status_type;
	}

	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}

	public String getObject_id() {
		return object_id;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}

	public Comment getComments() {
		return comments;
	}

	public void setComments(Comment comments) {
		this.comments = comments;
	}
	
	public List<Comment> getCommentList() {
		if(comments!=null && comments.getData()!=null)
			return comments.getData();
		return null;
	}
}
