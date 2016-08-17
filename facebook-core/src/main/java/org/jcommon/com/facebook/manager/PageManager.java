package org.jcommon.com.facebook.manager;

import org.apache.log4j.Logger;
import org.jcommon.com.facebook.FacebookSession;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.Album;
import org.jcommon.com.facebook.object.Photo;
import org.jcommon.com.facebook.object.Text;
import org.jcommon.com.facebook.object.Vedio;
import org.jcommon.com.facebook.utils.AlbumType;
import org.jcommon.com.util.http.HttpRequest;

public class PageManager{
	private Logger logger = Logger.getLogger(getClass());
	private String page_id;
	private AccessToken access_token;
	private FacebookSession session;
	
	public PageManager(FacebookSession session){
		this.session = session;
		this.page_id = session.getFacebook_id();
		this.access_token = session.getAccess_token();
	}
	
	public HttpRequest publishText(Text text, ResponseHandler handler){
		if(text==null || text.getMessage()==null){
			logger.info("text can not be null");
			return null;
		}
		return session.execute(RequestFactory.publishFeedRequest(handler, access_token.getAccess_token(), page_id, text.getMessage()));
	}
	
	public HttpRequest publishComment(String post_id, Text text, ResponseHandler handler){
		if(post_id==null || text==null || text.getMessage()==null){
			logger.info("text or post id is null");
			return null;
		}
		return session.execute(RequestFactory.publishCommnetRequest(handler, access_token.getAccess_token(), post_id, text.getMessage()));
	}
	
	public HttpRequest publishPhoto(Photo photo, ResponseHandler handler){
		if(photo==null || photo.getMedia()==null){
			logger.info("photo can not be null");
			return null;
		}
		Album wall_album = session.getAlbumManager().getAlbum(AlbumType.wall);
		if(wall_album==null){
			logger.info("can not find wall_album");
			return null;
		}
		return session.execute(RequestFactory.publishPhotoRequest(handler, access_token.getAccess_token(), wall_album.getId(), photo.getMedia(), photo.getMessage()));
	}
	
	public HttpRequest publishVedio(Vedio vedio, ResponseHandler handler){
		if(vedio==null || vedio.getMedia()==null){
			logger.info("vedio can not be null");
			return null;
		}
		return session.execute(RequestFactory.publishVideoRequest(handler, access_token.getAccess_token(), page_id, vedio.getMedia(), vedio.getTitle(), vedio.getDescription()));
	}

	public String getPage_id() {
		return page_id;
	}

	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}

	public AccessToken getAccess_token() {
		return access_token;
	}

	public void setAccess_token(AccessToken access_token) {
		this.access_token = access_token;
	}

	public void setSession(FacebookSession session) {
		this.session = session;
	}

	public FacebookSession getSession() {
		return session;
	}
}
