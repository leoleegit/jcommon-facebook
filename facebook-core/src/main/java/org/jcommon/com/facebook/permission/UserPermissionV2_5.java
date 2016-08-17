package org.jcommon.com.facebook.permission;


public enum UserPermissionV2_5 implements Permission {
	user_about_me,
	
	user_actions_books("user_actions.books"),
	
	user_actions_fitness("user_actions.fitness"),
	
	user_actions_music("user_actions.music"),
	
	user_actions_news("user_actions.news"),
	
	user_actions_video("user_actions.video"),
	
	user_birthday,
	
	user_education_history,
	
	user_events,
	
	user_friends,
	
	user_games_activity,
	
	user_hometown,
	
	user_likes,
	
	user_location,
	
	user_managed_groups,
	
	user_photos,
	
	user_posts,
	
	user_relationship_details,
	
	user_relationships,
	
	user_religion_politics,
	
	user_status,
	
	user_tagged_places,
	
	user_videos,
	
	user_website,
	
	user_work_history;
	
	private String str;
	
	UserPermissionV2_5(String str){
		this.str = str;
	}
	
	UserPermissionV2_5(){
	}
	
	public String toString(){
		if(str!=null)
			return str;
		return super.toString();
	}
	
	public static Permission[] list() {
	    return UserPermissionV2_5.class.getEnumConstants();
	}
}
