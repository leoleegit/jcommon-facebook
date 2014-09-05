// ========================================================================
// Copyright 2012 leolee<workspaceleo@gmail.com>
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
package org.jcommon.com.facebook.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookUtils
{
  public static String getNormalSource(String picUrl)
  {
    if (picUrl != null) {
      String suffix = picUrl.substring(picUrl.lastIndexOf("."));
      picUrl = picUrl.substring(0, picUrl.lastIndexOf(".") - 1 > 0 ? picUrl.lastIndexOf(".") - 1 : 0);
      picUrl = picUrl + "n" + suffix;
      return picUrl;
    }
    return null;
  }

  public static boolean isPicture(String extend) {
    if ((extend.equalsIgnoreCase(".jpg")) || (extend.equalsIgnoreCase(".gif")) || (extend.equalsIgnoreCase(".jpeg")) || (extend.equalsIgnoreCase(".bmp")) || (extend.equalsIgnoreCase(".png")))
    {
      return true;
    }
    return false;
  }

  public static boolean isVedio(String extend)
  {
    if ((extend.equalsIgnoreCase(".3g2")) || (extend.equalsIgnoreCase(".qt")) || (extend.equalsIgnoreCase(".tod")) || (extend.equalsIgnoreCase(".mpeg4")) || (extend.equalsIgnoreCase(".ts")) || (extend.equalsIgnoreCase(".mpe")) || (extend.equalsIgnoreCase(".mpeg")) || (extend.equalsIgnoreCase(".mpeg4")) || (extend.equalsIgnoreCase(".mpg")) || (extend.equalsIgnoreCase(".mts")) || (extend.equalsIgnoreCase(".nsv")) || (extend.equalsIgnoreCase(".ogm")) || (extend.equalsIgnoreCase(".ogv")) || (extend.equalsIgnoreCase(".3gp")) || (extend.equalsIgnoreCase(".3gpp")) || (extend.equalsIgnoreCase(".asf")) || (extend.equalsIgnoreCase(".avi")) || (extend.equalsIgnoreCase(".dat")) || (extend.equalsIgnoreCase(".divx")) || (extend.equalsIgnoreCase(".dv")) || (extend.equalsIgnoreCase(".f4v")) || (extend.equalsIgnoreCase(".flv")) || (extend.equalsIgnoreCase(".m2ts")) || (extend.equalsIgnoreCase(".m4v")) || (extend.equalsIgnoreCase(".wmv")) || (extend.equalsIgnoreCase(".vob")))
    {
      return true;
    }
    return false;
  }

  public static List<JSONObject> sortComments(List<JSONObject> comments) throws JSONException {
    JSONObject[] coms = (JSONObject[])comments.toArray(new JSONObject[comments.size()]);
    for (int i = 0; i < comments.size(); i++) {
      for (int j = i + 1; j < comments.size(); j++) {
        JSONObject json_i = coms[i];
        JSONObject json_j = coms[j];
        long create_time_i = json_i.has("created_time") ? json_i.getLong("created_time") : 0L;
        long create_time_j = json_j.has("created_time") ? json_j.getLong("created_time") : 0L;
        if (create_time_i > create_time_j) {
          JSONObject temp = coms[i];
          coms[i] = coms[j];
          coms[j] = temp;
        }
      }
    }
    comments.clear();
    comments = Arrays.asList(coms);
    return comments;
  }
  
  	//1317212277 To 2011-09-29T02:36:40+0000
	public static String  formatTime(String time){
		time   = time.replaceAll("\"", "");
		Long l = Long.valueOf(time);
		time = DateFormatUtils.formatUTC(new Date(l*1000), 
				 DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern());
		return time;
	}
	
	public static Date formatTime_(String time) throws ParseException{
		time   = time.replaceAll("T", " ");
		DateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+0000"); 
		return format.parse(time);
	}
	
	/**
	 * {
		  "data": [
		    {
		      "name": "aa", 
		      "fql_result_set": [
		        {
		          "name": "Newton", 
		          "id": 271039552948235
		        }, 
		        {
		          "name": "Row Ti", 
		          "id": 100003226166163
		        }
		      ]
		    }, 
		    {
		      "name": "bb", 
		      "fql_result_set": [
		        {
		          "id": "581811248537729_95954866", 
		          "text": "661", 
		          "time": 1373947325, 
		          "fromid": 271039552948235
		        }, 
		        {
		          "id": "581811248537729_95955421", 
		          "text": "fsadf", 
		          "time": 1373964268, 
		          "fromid": 100003226166163
		        }
		      ]
		    }
		  ]
		}
	 * @throws JSONException 
	 */
	public static JSONArray mergeFqlResult(String jsonData,String from_id) throws JSONException{
		JSONArray  result   = null;
		JSONObject jsonRoot = org.jcommon.com.util.JsonUtils.getJSONObject(jsonData);
		if(jsonRoot==null)return result;
		if(jsonRoot.has("data")){
			JSONArray  data   = jsonRoot.getJSONArray("data");
			JSONObject  jsonO1 = null;
			JSONObject  jsonO2 = null;
			for(int i=0;i<data.length();i++){
				jsonO1 = data.getJSONObject(i);
				if(result==null)
					result = jsonO1.has("fql_result_set")?jsonO1.getJSONArray("fql_result_set"):null;
				if(i+1<data.length()){
					jsonO2 = data.getJSONObject(i+1);
					if(result!=null && jsonO2.has("fql_result_set")){
						result = mergeJSONArray(result,jsonO2.getJSONArray("fql_result_set"),from_id);
					}
				}
			}
		}
		return result;
	}
	
	public static JSONArray mergeJSONArray(JSONArray json_from,JSONArray json_to,String from_id) throws JSONException{
		JSONArray  result   = new JSONArray();
		for(int i=0;i<json_from.length();i++){
			JSONObject  jsonO1 = json_from.getJSONObject(i);
			JSONObject  jsonO2 = json_to.  getJSONObject(i);
			if(jsonO1.has(from_id))jsonO1.remove(from_id);
			result.put(mergeJSONObject(jsonO1,jsonO2));
		}
		return result;
	}
	
	public static JSONObject mergeJSONObject(JSONObject  jsonO1,JSONObject  jsonO2) throws JSONException{
		Iterator<?> it = jsonO1.keys();
		for(;it.hasNext();){
			String key = (String) it.next();
			jsonO2.accumulate(key, jsonO1.get(key));
		}
		return jsonO2;
	}
}