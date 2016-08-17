package org.jcommon.com.facebook;

import java.io.File;

import org.jcommon.com.facebook.config.FacebookConfig;
import org.jcommon.com.util.JsonUtils;
import org.jcommon.com.util.http.FileRequest;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;

public class RequestFactory {
	 public static final String graph_url = "https://graph.facebook.com/";
	 public static final String graph_video_url = "https://graph-video.facebook.com/";
	 public  static boolean trusted = true;
	 public  static String  version = FacebookConfig.version;
	 
	 public static HttpRequest getMessageReqeust(HttpListener listener, String page_id, String access_token, String fields, int limit){
		 if (limit == 0) limit = 25;
	     String[] keys = { "access_token", "date_format", "limit", "fields" };
	     String[] values = { access_token,  "U", String.valueOf(limit), fields };

	     String url = graph_url + version + "/" + page_id + "/conversations";
	     url = JsonUtils.toRequestURL(url, keys, values);
	     return new HttpRequest(url, listener, trusted);
	 }
	 
	 public static HttpRequest messageUpdateReqeust(HttpListener listener, String page_id, String access_token, int limit){
		 String fields = "messages.limit(1).order(reverse_chronological).date_format(U).fields(created_time),updated_time";
	     return getMessageReqeust(listener,page_id,access_token,fields,limit);
	 }
	 
	 public static HttpRequest messageDetailRequest(HttpListener listener, String id, String access_token) {
		 String fields = "can_reply,link,message_count,updated_time,senders,participants,"
			 		+ "messages.order(reverse_chronological).limit(25).date_format(U){from,created_time,message,to,attachments}";
		 return getDetailRequest(listener,id,fields,access_token);
	 }
	 
	 public static HttpRequest getFeedReqeust(HttpListener listener, String page_id, String access_token, String fields, int limit){
		 if (limit == 0) limit = 25;
	     String[] keys = { "access_token", "date_format", "limit", "fields" };
	     String[] values = { access_token,  "U", String.valueOf(limit), fields };

	     String url = graph_url + version + "/" + page_id + "/feed";
	     url = JsonUtils.toRequestURL(url, keys, values);
	     return new HttpRequest(url, listener, trusted);
	 }
	 
	 public static HttpRequest feedUpdateReqeust(HttpListener listener, String feed_id, String access_token){
		 String fields = "updated_time,comments.limit(1).order(reverse_chronological).date_format(U).fields(created_time)";
	     return getDetailRequest(listener,feed_id,fields,access_token);
	 }
	 
	 //271039552948235/feed?fields=updated_time,comments.limit(1).date_format(U).fields(created_time)&date_format=U
	 public static HttpRequest feedUpdateReqeust(HttpListener listener, String page_id, String access_token, int limit) {
	     String fields = "updated_time,comments.limit(1).order(reverse_chronological).date_format(U).fields(created_time)";
	     return getFeedReqeust(listener, page_id, access_token, fields, limit);
	 }
	 
	 public static HttpRequest feedDetailRequest(HttpListener listener, String id, String access_token) {
		 String fields = "from,type,picture,message,created_time,actions,updated_time,full_picture,source,link,privacy,icon,status_type,object_id";
		 return getDetailRequest(listener,id,fields,access_token);
	 }
	 
	 public static HttpRequest getCommentsRequest(HttpListener listener, String id, String access_token) {
		 String fields = "from,type,picture,message,created_time,actions,updated_time,full_picture,source,link,privacy,icon,status_type,object_id,"
		 		+ "comments.order(reverse_chronological).limit(25).date_format(U){message,from,created_time,comments.order(reverse_chronological).limit(25).date_format(U){message,from,created_time}}";
		 return getDetailRequest(listener,id,fields,access_token);
	 }
	 
	 public static HttpRequest getDetailRequest(HttpListener listener, String id, String fields, String access_token) {
		 String[] keys = { "access_token", "date_format", "fields" };
		 String[] values = { access_token, "U", fields };

		 String url = graph_url + version + "/" + id;
		 url = JsonUtils.toRequestURL(url, keys, values);
		 return new HttpRequest(url, listener, trusted);
	 }
	 
	 public static HttpRequest getAlbumRequest(HttpListener listener, String id, String access_token) {
		 String fields = "type,count,can_upload,cover_photo,created_time,from,description,link,updated_time,privacy,name";
		 String[] keys = { "access_token", "date_format", "fields" };
		 String[] values = { access_token, "U", fields };

		 String url = graph_url + version + "/" + id + "/albums";
		 url = JsonUtils.toRequestURL(url, keys, values);
		 return new HttpRequest(url, listener, trusted);
	 }
	 
	 public static HttpRequest getAboutMeReqeust(HttpListener listener, String access_token){
	    return getDetailRequest(listener, "me", "name,link,location,work,gender,timezone,languages,locale,picture", access_token);
	 }
	 
	 public static HttpRequest getAboutMeReqeust(HttpListener listener, String id, String access_token){
	    return getDetailRequest(listener, id, "name", access_token);
	 }
	 
	 public static String getAccessCodeUrl(String redirect_uri, String permissions, String app_id) {
	     String[] keys = { "redirect_uri", "scope", "response_type" };
	     String[] values = { redirect_uri, permissions, "code" };
	     
	     String url = "http://www.facebook.com/dialog/oauth?client_id=" + app_id;
	     return JsonUtils.toRequestURL(url, keys, values);
	 }
	 
	 public static HttpRequest getAccessTokenReqeust(HttpListener listener, String api_id, String app_secret, String code, String redirect_uri) {
	     String[] keys = { "client_id", "client_secret", "code", "redirect_uri" };
	     String[] values = { api_id, app_secret, code, redirect_uri };

	     String url = graph_url + version + "/oauth/access_token";
	     url = JsonUtils.toRequestURL(url, keys, values);
	     return new HttpRequest(url, listener, trusted);
	 }
	 
	 public static HttpRequest getAllAccessTokenReqeust(HttpListener listener, String access_token) {
	     String url = graph_url + version +"/me/accounts";
	     String[] keys = { "access_token"};
	     String[] values = { access_token };

	     url = JsonUtils.toRequestURL(url, keys, values);
	     return new HttpRequest(url, listener, trusted);
	 }
	 
	 public static HttpRequest getTabsReqeust(HttpListener listener,String page_id,String access_token,String app_id) {
	     String url = graph_url + version + "/" + page_id + "/tabs";
	     String[] keys = { "app_id", "access_token", "method"};
	     String[] values = {app_id, access_token, HttpRequest.GET};
	     url = JsonUtils.toRequestURL(url, keys, values);
	     return new HttpRequest(url, listener, trusted);
	 }
	 
	 public static HttpRequest newTabRequest(HttpListener listener,String page_id,String access_token,String app_id){
		 String url = graph_url + version + "/" + page_id + "/tabs";
		 String[] keys = { "app_id", "access_token", "method"};
		 String[] values = {app_id, access_token, HttpRequest.POST};
		 url = JsonUtils.toRequestURL(url, keys, values);
	     return publishRequest(listener,url,keys,values);
	 }
	 
	 public static HttpRequest publishFeedRequest(HttpListener listener, String access_token, String id, String message){
		 String url = graph_url + version + "/" + id + "/feed";
	     String[] keys = { "access_token" ,"message"};
	     String[] values = { access_token , message};
	     return publishRequest(listener,url,keys,values);
	 }
	 
	 public static HttpRequest publishCommnetRequest(HttpListener listener, String access_token, String id, String message){
		 String url = graph_url + version + "/" + id + "/comments";
		 String[] keys = { "access_token" ,"message"};
	     String[] values = { access_token , message};
	     return publishRequest(listener,url,keys,values);
	 }
	 
	 public static HttpRequest publishMessageRequest(HttpListener listener, String access_token, String id, String message){
		 String url = graph_url + version + "/" + id + "/messages";
		 String[] keys = { "access_token" ,"message"};
	     String[] values = { access_token , message};
	     return publishRequest(listener,url,keys,values);
	 }
	 
	 public static HttpRequest publishVideoRequest(HttpListener listener, String access_token, String id, File file, String title, String description){
		 String url = graph_url + version + "/" + id + "/videos";
		 String[] keys = { "access_token" ,"title" , "description"};
	     String[] values = { access_token , title, description};
	     return publishMediaRequest(listener,url,file,keys,values);
	 }
	 
	 public static HttpRequest publishPhotoRequest(HttpListener listener, String access_token, String id, File file, String message){
		 String url = graph_url + version + "/" + id + "/photos";
		 String[] keys = { "access_token" ,"message"};
	     String[] values = { access_token , message};
	     return publishMediaRequest(listener,url,file,keys,values);
	 }
	 
	 public static HttpRequest publishRequest(HttpListener listener, String url, String[] keys, String[] values){
		 url = JsonUtils.toRequestURL(url, keys, values);
		 return new HttpRequest(url, "", HttpRequest.POST, listener, trusted);
	 }
	 
	 public static HttpRequest publishMediaRequest(HttpListener listener, String url, File file, String[] keys, String[] values){
		 url = JsonUtils.toRequestURL(url, keys, values);
		 return new FileRequest(url, file, "media", listener);
	 }
	 
	 public static HttpRequest deleteRequest(HttpListener listener, String id, String access_token) {
	     String[] keys   = { "access_token" };
	     String[] values = { access_token};
	     String url = graph_url + version + "/" + id;
	     url = JsonUtils.toRequestURL(url, keys, values);
	     return new HttpRequest(url, null, HttpRequest.DELETE, listener, trusted);
	 }
}
