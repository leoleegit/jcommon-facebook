package org.jcommon.com.facebook.test;

import java.io.UnsupportedEncodingException;

public class ErrorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "get+access_token+error%3A%7B%22error%22%3A%7B%22message%22%3A%22This+authorization+code+has+expired.%22%2C%22type%22%3A%22OAuthException%22%2C%22code%22%3A100%7D%7D";
		//String a   = encode(str);
		//a          = encodeToFlex(a);
		//System.out.println(a);
		System.out.println(decode(str));
	}
	
	public static String decode(String str){
		if(str!=null)
			try {
				str = java.net.URLDecoder.decode(str, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return str;
	}

}
