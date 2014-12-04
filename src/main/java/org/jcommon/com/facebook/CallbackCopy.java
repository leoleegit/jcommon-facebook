// ========================================================================
// Copyright 2014 leolee<workspaceleo@gmail.com>
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//     http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ========================================================================
package org.jcommon.com.facebook;

import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.thread.ThreadManager;
import org.json.JSONException;
import org.json.JSONObject;

public class CallbackCopy extends FacebookSession{
	private String callback;
	private String myfacebook_id;
	
	public CallbackCopy(String facebook_id, String callback) {
		super(facebook_id, null, null);
		// TODO Auto-generated constructor stub
		this.callback = callback;
		myfacebook_id = facebook_id!=null && facebook_id.indexOf("-")!=-1?facebook_id.substring(0, facebook_id.lastIndexOf("-")):facebook_id;
		logger.info(callback);
	}

	public void check(JSONObject jsonO) throws JSONException{
		logger.info(jsonO);
		String id = jsonO.has("id")?jsonO.getString("id"):null;
		if(id==null || !id.equals(myfacebook_id))return;
		callback(jsonO.toString());
	}
	
	private void callback(String xml){
		logger.info(xml);
		if(callback!=null){
			HttpRequest request = new HttpRequest(callback,xml,"POST",this);
			ThreadManager.instance().execute(request);
		}
	}
	
	public void onSuccessful(HttpRequest reqeust, StringBuilder sResult){
		logger.info(sResult);
	}
	
	public void onFailure(HttpRequest reqeust, StringBuilder sResult){
		logger.info(sResult);
	}
	
    public void onTimeout(HttpRequest request){
    	logger.error(callback);
    }

    public void onException(HttpRequest request, Exception e){
    	logger.error(callback, e);
    }
}
