//var request_code_url = "";
//var request_jcommonfacebook_url = "";
//var facebook_app_url = "";

window.onload = function(){
	if(!jcommonfacebook.onAboutMe)return;
	var code         = jcommonfacebook_get_parameter('code');
	if(!request_code_url){
		jcommonfacebook.event    = jcommonfacebook_event.error;
		jcommonfacebook.about_me = "data not ready";
		return;
	}
	if(!code){
		if(typeof facebook_app_url != 'undefined')
			var redirect_uri = facebook_app_url;
		else{
			var redirect_uri = window.location.href;
			//return;
		}
		request_code_url = request_code_url + "&redirect_uri=" + redirect_uri;
		if(parent)
			parent.location.href = request_code_url;
		else
			window.location.href = request_code_url;
	}
	else{
		jcommonfacebook.getAboutMe(code);
	}
}

var jcommonfacebook_event = {
	ok:"ok",
	error:"error"
}

var jcommonfacebook = {
	event   :null,
	about_me:null,
	/**
	 * successful:
	 * {
		  "id": "100003210247839", 
		  "name": "Isa Yi", 
		  "link": "https://www.facebook.com/isa.yi.5", 
		  "username": "isa.yi.5", 
		  "location": {
		    "id": "113317605345751", 
		    "name": "Hong Kong"
		  }, 
		  "work": [
		    {
		      "employer": {
		        "id": "221746464569787", 
		        "name": "SocialMM"
		      }, 
		      "position": {
		        "id": "123549261023685", 
		        "name": "Head Huncho"
		      }, 
		      "start_date": "0000-00"
		    }
		  ], 
		  "gender": "male", 
		  "timezone": 8, 
		  "locale": "en_US", 
		  "picture": {
		    "data": {
		      "url": "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc7/372346_100003210247839_382193522_q.jpg", 
		      "is_silhouette": false
		    }
		  }
		}
		
	    error: 
	    {
		  "error": {
		    "message": "(#100) Missing message or attachment", 
		    "type": "OAuthException", 
		    "code": 100
		  }
	    }
	 */
	onAboutMe:null,
	
	getting:false,
	getAboutMe:function(code){
		if(jcommonfacebook.about_me){
			 jcommonfacebook.onAboutMe(jcommonfacebook.event, jcommonfacebook.about_me);
			 return;
		}
		if(jcommonfacebook.getting)
			return;
		if(typeof facebook_app_url != 'undefined')
			var redirect_uri = facebook_app_url;
		else
			var redirect_uri = window.location.href;
		if(redirect_uri.indexOf('?')!=-1)
			redirect_uri     = redirect_uri.substring(0,redirect_uri.indexOf('?')); 
		jcommonfacebook.getting = true;
		$.ajax({
			  type: "GET",
			  url: request_jcommonfacebook_url + "?cmd=cmd_about_me&code="+code+"&redirect_uri="+redirect_uri,
			  dataType: 'json',
			  error: function error(jqXHR, textStatus, errorThrown){
				  jcommonfacebook.getting  = false;
				  jcommonfacebook.event    = jcommonfacebook_event.error;
				  jcommonfacebook.about_me = jqXHR;
				  jcommonfacebook.onAboutMe(jcommonfacebook.event, jqXHR);
			  },
			  success: function success(data, textStatus, jqXHR){
				  jcommonfacebook.getting  = false;
				  if(data && data.id)
					  jcommonfacebook.event    = jcommonfacebook_event.ok;
				  else if(data && data.type && data.message && data.code){
					  data.message = decodeURIComponent(data.message);
					  if(data.code=="100" && data.type=="OAuthException"
						  //&& data.message == "This authorization code has expired."
							  ){
						    if(typeof facebook_app_url != 'undefined')
								var redirect_uri = facebook_app_url;
							else
								var redirect_uri = window.location.href;
						    var request_ = jcommonfacebook_get_request();
						    request_ = jcommonfacebook_delete_parameter(request_,'code');
						    var parameter_ = jcommonfacebook_request_to_parameter(request_);
						    redirect_uri = redirect_uri + '?'+parameter_;
							if(parent)
								parent.location.href = redirect_uri;
							else
								window.location.href = redirect_uri;
					  }
				  }else
					  jcommonfacebook.event    = jcommonfacebook_event.error;
				  jcommonfacebook.about_me = data;
				  jcommonfacebook.onAboutMe(jcommonfacebook.event, jcommonfacebook.about_me);
			  },
			  timeout: function timeout(){
				  jcommonfacebook.getting  = false;
				  jcommonfacebook.event    = jcommonfacebook_event.error;
				  jcommonfacebook.about_me = "timeout";
				  jcommonfacebook.onAboutMe(jcommonfacebook.event, 'timeout');
			  },
			  crossDomain:true
			});
	}
}

function jcommonfacebook_get_request(){
	var url = location.search;
	var theRequest = new Object();
	if(url.indexOf("?") != -1)
	{
	  var str = url.substr(1);
	    strs = str.split("&");
	  for(var i = 0; i < strs.length; i ++)
	  {
	     theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	  }
	}
	return theRequest;
}

function jcommonfacebook_get_parameter(name){
	var theRequest = jcommonfacebook_get_request();
	if(name)return theRequest[name];
	return theRequest;
}

function jcommonfacebook_delete_parameter(request_,name){
	if(request_ && name){
		if(request_[name])request_[name]=null;
	}
	return request_;
}

function jcommonfacebook_request_to_parameter(request_){
	var url = "";
	for(var key in request_){
		if(request_[key])url=url + key + '=' + request_[key]+'&';
	}
	if(url.lastWith('&'))
		url = url.substring(0,this.length-1);
	return url;
}

String.prototype.lastWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(this.length - str.length,this.length)==str)
	  return true;
	else
	  return false;
};
