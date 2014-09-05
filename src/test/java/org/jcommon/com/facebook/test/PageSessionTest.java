package org.jcommon.com.facebook.test;

import java.util.List;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import org.apache.log4j.Logger;
import org.jcommon.com.facebook.FacebookManager;
import org.jcommon.com.facebook.PageListener;
import org.jcommon.com.facebook.RequestCallback;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.cache.SessionCache;
import org.jcommon.com.facebook.data.App;
import org.jcommon.com.facebook.data.Comment;
import org.jcommon.com.facebook.data.Error;
import org.jcommon.com.facebook.data.Feed;
import org.jcommon.com.facebook.data.Message;
import org.jcommon.com.facebook.seesion.Session;
import org.jcommon.com.facebook.seesion.FacebookSession;
import org.jcommon.com.facebook.utils.FacebookType;
import org.jcommon.com.util.Json2Object;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.jmx.Monitor;
import org.jcommon.com.util.thread.ThreadManager;

public class PageSessionTest extends Monitor
  implements PageListener, RequestCallback, HttpListener
{
  private Logger logger = Logger.getLogger(getClass());
  private boolean disable_auto = true;

  public PageSessionTest() {
    super("Page Session Tester");
  }
  
  //read_stream,manage_pages,publish_stream,offline_access,read_mailbox,read_page_mailboxes
  public void initOperation()
  {
    addOperation(new MBeanOperationInfo("newSession", "add or update pageSession", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("session_id", "java.lang.String", "page id or user ip"), 
    		new MBeanParameterInfo("is_page", "java.lang.Boolean", "page is true else is false"),
    		new MBeanParameterInfo("login", "java.lang.Boolean", "page login or not"),
    		new MBeanParameterInfo("access_token", "java.lang.String", "page or user' access_token")
    		}, 
    		"void", 1));

    addOperation(new MBeanOperationInfo("disableAutoReply", "disable auto reply message", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("disable", "java.lang.Boolean", "true of false") 
    		}, 
    		"void", 1));
    
    addOperation(new MBeanOperationInfo("removeSession", "removeSession a session", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("session_id", "java.lang.String", "page or user' session id") 
    		}, 
    		"void", 1));
    
    addOperation(new MBeanOperationInfo("deletePostOrComment", "delete a post or comment", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("id", "java.lang.String", "post id or comment id") 
    		}, 
    		"void", 1));
    addOperation(new MBeanOperationInfo("newTab", "newTab", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("api_id", "java.lang.String", "api_id") ,
    		new MBeanParameterInfo("page_id", "java.lang.String", "page_id"),
    		new MBeanParameterInfo("access_token", "java.lang.String", "access_token")
    		}, 
    		"void", 1));
    addOperation(new MBeanOperationInfo("getTab", "getTab", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("api_id", "java.lang.String", "api_id") ,
    		new MBeanParameterInfo("page_id", "java.lang.String", "page_id"),
    		new MBeanParameterInfo("access_token", "java.lang.String", "access_token")
    		}, 
    		"void", 1));
    addOperation(new MBeanOperationInfo("deleteTab", "deleteTab", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("tab_id", "java.lang.String", "tab_id") ,
    		new MBeanParameterInfo("access_token", "java.lang.String", "access_token")
    		}, 
    		"void", 1));
    super.initOperation();
  }
  
  public void deletePostOrComment(String id){
	  List<Session> sessions = SessionCache.instance().getPageSession();
	  if(sessions.size()==0){
		  logger.warn("can't find page session");
		  return;
	  }
	  FacebookSession session = (FacebookSession) sessions.get(0);
	  session.deletePost4Wall(this, id);
  }

  public void disableAutoReply(Boolean disable)
  {
    this.disable_auto = disable;
  }

  public void removeSession(String session_id){
	  Session session = SessionCache.instance().removeSession(session_id);
	    if (session != null) {
	      session.logout();
	  }
	  super.removeProperties(session_id);
  }
  
  public void newSession(String page_id, Boolean is_page, Boolean login,String access_token) {
	try{
	    if(is_page)
	    	FacebookManager.instance().addPageSession(this, page_id, access_token);
	    else
	    	FacebookManager.instance().addUserSession(this, page_id, access_token);
	    super.addProperties(page_id, access_token);
	}catch(Exception e){
		e.printStackTrace();
	};
  }
  
  public void newTab(String api_id, String page_id, String access_token){
	  App app = new App(api_id, null);
	  HttpRequest request = RequestFactory.createNewTabReqeust(this, page_id, access_token, app);
	  ThreadManager.instance().execute(request);
  }
  
  public void deleteTab(String tab_id, String access_token){
	  HttpRequest request = RequestFactory.createDeleteRequest(this, tab_id, access_token);
	  ThreadManager.instance().execute(request);
  }
  
  public void getTab(String api_id, String page_id, String access_token){
	  App app = new App(api_id, null);
	  HttpRequest request = RequestFactory.createGetTabsReqeust(this, page_id, access_token, app);
	  ThreadManager.instance().execute(request);
  }

  public void shutdown()
  {
    List<Session> list = SessionCache.instance().getPageSession();
    if (list == null) return;
    for (Session session : list)
      session.logout();
  }

  public void onPosts(Feed post)
    throws Exception
  {
    String post_id = post.getId();
    this.logger.info(post.getJsonData());
    this.logger.info(Json2Object.object2Json(post));
    if (this.disable_auto) return;
    String page_id = post_id.substring(0, post_id.indexOf("_"));
    Session session = SessionCache.instance().getSession(page_id);
    if (session != null) {
      if (!"auto new post".equals(post.getMessage()));
      ((FacebookSession)session).postFeed2Wall(this, "auto new post", null, null, null, null, null);
    } else {
      this.logger.warn("can't find page session :" + post_id);
    }
  }

  public void onComments(Feed post, Comment comments) throws Exception
  {
    String post_id = post.getId();
    this.logger.info("post:" + post.getJsonData());
    this.logger.info("comment:" + comments.getMessage());
    this.logger.info(post.getId());
    this.logger.info(comments.getId());
    if (this.disable_auto) return;
    String page_id = post_id.substring(0, post_id.indexOf("_"));
    Session session = SessionCache.instance().getSession(page_id);

    if (session != null) {
      if (!"auto replay comment".equals(comments.getMessage()));
      ((FacebookSession)session).postComment2Wall(this, post_id, "auto replay comment");
    } else {
      this.logger.warn("can't find page session :" + post_id);
    }
  }

  public void onMessages(Message messages) throws Exception
  {
    this.logger.info(messages.getJsonData());
    this.logger.info(messages.getId());
    if (this.disable_auto) return;
    if (SessionCache.instance().getPageSession().size() > 0) {
      Session session = (Session)SessionCache.instance().getPageSession().get(0);
      if (!"auto replay message".equals(messages.getMessage())) {
        HttpRequest request = ((FacebookSession)session).replayMessage(this, messages.getId(), "auto replay message");
        this.logger.info(request.getUrl());
      }
    }
  }

  public void reqeustSuccessful(HttpRequest reqeust, StringBuilder sResult)
  {
    this.logger.info(sResult);
  }

  public void requestFailure(HttpRequest request, Error error)
  {
    this.logger.info(Json2Object.object2Json(error));
  }

	@Override
	public void onException(HttpRequest arg0, Exception arg1) {
		// TODO Auto-generated method stub
		logger.error("", arg1);
	}
	
	@Override
	public void onFailure(HttpRequest arg0, StringBuilder arg1) {
		// TODO Auto-generated method stub
		logger.error(arg1.toString());
	}
	
	@Override
	public void onSuccessful(HttpRequest arg0, StringBuilder arg1) {
		// TODO Auto-generated method stub
		this.logger.info(arg1.toString());
	}
	
	@Override
	public void onTimeout(HttpRequest arg0) {
		// TODO Auto-generated method stub
		logger.error("Timeout");
	}
}