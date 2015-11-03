package org.jcommon.com.facebook.config;

public class FacebookConfig {
	public static final boolean facebook_cache_default = false;
	private boolean facebook_cache = facebook_cache_default;

	public void setFacebook_cache(boolean facebook_cache) {
		this.facebook_cache = facebook_cache;
	}

	public boolean isFacebook_cache() {
		return facebook_cache;
	}
}
