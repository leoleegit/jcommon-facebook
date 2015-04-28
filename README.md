### 1. class FacebookSession 
*  postPhoto2Wall(RequestCallback callback, File file, String message, boolean start_upload) ;
*  postVideo2Wall(RequestCallback callback, File file, String title, String description, boolean start_upload) ;
*  postFeed2Wall(RequestCallback callback, String message, String link, String picture, String name, String caption, String description);
*  postComment2Wall(RequestCallback callback, String post_id, String message);
*  postPhoto2Wall(RequestCallback callback, File file, String message, String access_token, boolean start_upload);
*  postVideo2Wall(RequestCallback callback, File file, String title, String description, String access_token, boolean start_upload) ;
*  postFeed2Wall(RequestCallback callback, String message, String link, String picture, String name, String caption, String description, String access_token);
*  postComment2Wall(RequestCallback callback, String post_id, String message, String access_token);
*  deletePost4Wall(RequestCallback callback, String post_id);
*  deleteComment4Wall(RequestCallback callback, String comment_id);
*  replayMessage(RequestCallback callback, String message_id, String message) ;

### 2. interface PageListener
*  onPosts(Feed paramFeed)
*  onComments(Feed paramFeed, Comment paramComment)
*  onMessages(Message paramMessage)

### 3. FacebookManager 

### 4. AccessToken
    ` implements Permission `
    `enum UserPermission`
    `enum FriendsPermission`   
    `enum ExtendedPermission`


web.xml
***

        <servlet>
	    <description>GetAccessToken</description>
	    <display-name>GetAccessToken</display-name>
	    <servlet-name>GetAccessToken</servlet-name>
	    <servlet-class>org.jcommon.com.facebook.servlet.GetAccessToken</servlet-class>
	   <load-on-startup>0</load-on-startup>
	   <init-param>
           <param-name>facebook_app</param-name>
           <param-value>spotlight_facebook_app</param-value>
        </init-param>
	</servlet>
	<servlet-mapping>
	    <servlet-name>GetAccessToken</servlet-name>
	    <url-pattern>/get.accesstoken/*</url-pattern>
        </servlet-mapping> 


[http://ip/protjectname/access_token.html](http://ip/protjectname/access_token.html)
