package org.jcommon.com.facebook.manager;

import java.util.List;

import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.facebook.ResponseHandler;
import org.jcommon.com.facebook.object.AccessToken;
import org.jcommon.com.facebook.object.Album;
import org.jcommon.com.facebook.object.Error;
import org.jcommon.com.facebook.object.JsonObject;
import org.jcommon.com.facebook.utils.AlbumType;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;

public class AlbumManager extends ResponseHandler{
	private List<Album> albums;
	private String facebook_id;
	private AccessToken access_token;
	
	public AlbumManager(String facebook_id, AccessToken access_token){
		this.facebook_id = facebook_id;
		this.access_token = access_token;
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
	
	public void getAlbums(){
		logger.info(facebook_id);
		HttpRequest re = RequestFactory.getAlbumRequest(this, facebook_id, access_token.getAccess_token());
 	    addHandlerObject(re, Album.class);
 	    ThreadManager.instance().execute(re);
	}
	
	public Album getAlbum(AlbumType type){
		if(albums==null || albums.size()==0){
			logger.info("album is Empty, try again later.");
			getAlbums();
			return null;
		}
		for(Album alb : albums){
			if(type.toString().equalsIgnoreCase(alb.getType()))
				return alb;
		}
		logger.info("can not find album : "+type);
		return null;
	}

	@Override
	public void onError(HttpRequest paramHttpRequest, Error paramError) {
		// TODO Auto-generated method stub
		logger.error(paramError.toJson());
	}

	@Override
	public void onOk(HttpRequest paramHttpRequest, JsonObject paramObject) {
		// TODO Auto-generated method stub
		if(paramObject instanceof Album){
			Album album = (Album) paramObject;
			setAlbums(album.getData());
		}else{
			logger.warn("error class type:"+paramObject);
		}
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
}
