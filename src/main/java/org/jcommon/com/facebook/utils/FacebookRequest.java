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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jcommon.com.util.http.HttpListener;
import org.jcommon.com.util.http.HttpRequest;

public class FacebookRequest extends HttpRequest
{
  private String[] keys;
  private String[] values;
  private String stream_name;
  private String file_name;
  private InputStream in;

  public FacebookRequest(String url, HttpListener listener, InputStream in, String stream_name, String file_name, String[] keys, String[] values)
  {
    super(url, listener);

    this.keys = keys;
    this.stream_name = stream_name;
    this.file_name = file_name;
    this.values = values;
    this.in = in;
  }

  public void run()
  {
    HttpClient httpclient = new DefaultHttpClient();
    try {
      logger.info(Integer.valueOf("file size:" + this.in != null ? this.in.available() : 0));
      HttpPost httppost = new HttpPost(this.url_);
      MultipartEntity reqEntity = new MultipartEntity();

      FormBodyPart stream_part = new FormBodyPart(this.stream_name, new InputStreamBody(this.in, this.file_name));

      reqEntity.addPart(stream_part);
      for (int i = 0; i < this.keys.length; i++) {
        reqEntity.addPart(this.keys[i], new StringBody(this.values[i]));
      }

      httppost.setEntity(reqEntity);
      HttpResponse response = httpclient.execute(httppost);
      HttpEntity resEntity = response.getEntity();
      StatusLine status_line = response.getStatusLine();
      int responseCode = status_line.getStatusCode();

      BufferedReader http_reader = null;
      if (responseCode == 200) {
        http_reader = new BufferedReader(new InputStreamReader(resEntity.getContent(), "utf-8"));
        String line = null;
        while ((line = http_reader.readLine()) != null) {
          this.sResult.append(line);
        }
        if (this.listener_ != null) this.listener_.onSuccessful(this, this.sResult); 
      }
      else if (responseCode >= 400) {
        http_reader = new BufferedReader(new InputStreamReader(resEntity.getContent(), "utf-8"));
        String line = null;
        while ((line = http_reader.readLine()) != null) {
          this.sResult.append(line);
        }
        logger.info("[URL][response][failure]" + this.sResult);
        if (this.listener_ != null) this.listener_.onFailure(this, this.sResult); 
      }
      else
      {
        this.sResult.append("[URL][response][failure][code : " + responseCode + " ]");
        if (this.listener_ != null) this.listener_.onFailure(this, this.sResult);
        logger.info("[URL][response][failure][code : " + responseCode + " ]");
      }
      EntityUtils.consume(resEntity);
    }
    catch (UnsupportedEncodingException e) {
      logger.warn("[HttpReqeust] error:" + this.url_ + "\n" + e);
      if (this.listener_ != null) this.listener_.onException(this, e); 
    }
    catch (ClientProtocolException e)
    {
      logger.warn("[HttpReqeust] error:" + this.url_ + "\n" + e);
      if (this.listener_ != null) this.listener_.onException(this, e); 
    }
    catch (IOException e)
    {
      logger.warn("[HttpReqeust] error:" + this.url_ + "\n" + e);
      if (this.listener_ != null) this.listener_.onException(this, e); 
    } finally {
      try { httpclient.getConnectionManager().shutdown(); }
      catch (Exception ignore)
      {
      }
    }
  }
}