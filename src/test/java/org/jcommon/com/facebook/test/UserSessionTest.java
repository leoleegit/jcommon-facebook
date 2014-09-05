package org.jcommon.com.facebook.test;

import org.jcommon.com.facebook.PageListener;
import org.jcommon.com.facebook.RequestCallback;
import org.jcommon.com.facebook.data.Comment;
import org.jcommon.com.facebook.data.Error;
import org.jcommon.com.facebook.data.Feed;
import org.jcommon.com.facebook.data.Message;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.jmx.Monitor;

public class UserSessionTest extends Monitor implements PageListener,
		RequestCallback {

	public UserSessionTest() {
		super("User Session Tester");
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void reqeustSuccessful(HttpRequest paramHttpRequest,
			StringBuilder paramStringBuilder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestFailure(HttpRequest paramHttpRequest, Error paramError) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPosts(Feed paramFeed) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComments(Feed paramFeed, Comment paramComment)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessages(Message paramMessage) throws Exception {
		// TODO Auto-generated method stub

	}

}
