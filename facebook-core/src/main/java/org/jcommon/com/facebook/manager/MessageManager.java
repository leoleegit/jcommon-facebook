package org.jcommon.com.facebook.manager;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.FacebookSession;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.Text;
import org.jcommon.com.util.http.HttpRequest;

public class MessageManager {
	private Logger logger = Logger.getLogger(getClass());
	private String facebook_id;
	private AccessToken access_token;
	private FacebookSession session;
	
	public MessageManager(FacebookSession session){
		this.setSession(session);
		this.facebook_id = session.getFacebook_id();
		this.access_token = session.getAccess_token();
	}
	
	public HttpRequest sendMessage(String conversation_id, Text text, ResponseHandler handler){
		if(text==null || text.getMessage()==null){
			logger.info("text can not be null");
			return null;
		}
		return FacebookSession.execute(RequestFactory.publishMessageRequest(handler, access_token.getAccess_token(), conversation_id, text.getMessage()));
	}
	
	public String getFacebook_id() {
		return facebook_id;
	}
	
	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}
	
	public AccessToken getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(AccessToken access_token) {
		this.access_token = access_token;
	}
	
	public FacebookSession getSession() {
		return session;
	}
	
	public void setSession(FacebookSession session) {
		this.session = session;
	}
}
