package org.jcommon.com.facebook.permission;


public enum ExtendedPermissionV_2_5 implements Permission {
	ads_management,
	
	ads_read,
	
	email,
	
	manage_pages,
	
	pages_manage_cta,
	
	pages_manage_leads,
	
	pages_show_list,
	
	publish_actions,
	
	publish_pages,
	
	read_audience_network_insights,
	
	read_custom_friendlists,
	
	read_insights,
	
	read_page_mailboxes,
	
	rsvp_event;

	
	public static Permission[] list() {
	    return ExtendedPermissionV_2_5.class.getEnumConstants();
	}
}
