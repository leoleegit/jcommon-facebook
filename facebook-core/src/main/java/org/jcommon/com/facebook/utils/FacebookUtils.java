package org.jcommon.com.facebook.utils;

public class FacebookUtils {
    public static String getNormalSource(String picUrl){
	    if (picUrl != null) {
	        String suffix = picUrl.substring(picUrl.lastIndexOf("."));
	        picUrl = picUrl.substring(0, picUrl.lastIndexOf(".") - 1 > 0 ? picUrl.lastIndexOf(".") - 1 : 0);
	        picUrl = picUrl + "n" + suffix;
	        return picUrl;
	    }
	    return null;
    }
}
