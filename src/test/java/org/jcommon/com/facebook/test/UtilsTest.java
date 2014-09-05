package org.jcommon.com.facebook.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jcommon.com.facebook.RequestFactory;

public class UtilsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Number n = Float.parseFloat("1.405058792624E9");
		System.out.println(n.longValue());
		Date d = new Date(n.longValue()*1000l);
		DateFormat ft= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt = ft.format(d);
		System.out.println(dt);
		//System.out.println(getMessage_id("https://graph.facebook.com/v2.0/t_id.312177318835636/messages?date_format=U"));
	}

	  private static String getMessage_id(String url)
	  {
	    url = url.replace(RequestFactory.graph_url, "");
	    System.out.println(url);
	    url = url.substring(RequestFactory.version.length()+1, url.indexOf("/",5));
	    return url;
	  }
}
