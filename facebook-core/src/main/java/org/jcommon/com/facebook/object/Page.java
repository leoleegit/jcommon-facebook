package org.jcommon.com.facebook.object;

public class Page extends JsonObject {
	private String name;
	private String is_published;
	private String has_added_app;
	private String username;
	private String about;
	private boolean can_post;
	private int talking_about_count;
	private int unread_notif_count;
	private int unread_message_count;
	private int unseen_message_count;
	private String promotion_ineligible_reason;
	private String category;
	private String id;
	private String link;
	private int likes;
	private Cover cover;
	  
	public Page(String json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIs_published() {
		return is_published;
	}

	public void setIs_published(String is_published) {
		this.is_published = is_published;
	}

	public String getHas_added_app() {
		return has_added_app;
	}

	public void setHas_added_app(String has_added_app) {
		this.has_added_app = has_added_app;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isCan_post() {
		return can_post;
	}

	public void setCan_post(boolean can_post) {
		this.can_post = can_post;
	}

	public int getTalking_about_count() {
		return talking_about_count;
	}

	public void setTalking_about_count(int talking_about_count) {
		this.talking_about_count = talking_about_count;
	}

	public int getUnread_notif_count() {
		return unread_notif_count;
	}

	public void setUnread_notif_count(int unread_notif_count) {
		this.unread_notif_count = unread_notif_count;
	}

	public int getUnread_message_count() {
		return unread_message_count;
	}

	public void setUnread_message_count(int unread_message_count) {
		this.unread_message_count = unread_message_count;
	}

	public int getUnseen_message_count() {
		return unseen_message_count;
	}

	public void setUnseen_message_count(int unseen_message_count) {
		this.unseen_message_count = unseen_message_count;
	}

	public String getPromotion_ineligible_reason() {
		return promotion_ineligible_reason;
	}

	public void setPromotion_ineligible_reason(String promotion_ineligible_reason) {
		this.promotion_ineligible_reason = promotion_ineligible_reason;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Cover getCover() {
		return cover;
	}

	public void setCover(Cover cover) {
		this.cover = cover;
	}
}
