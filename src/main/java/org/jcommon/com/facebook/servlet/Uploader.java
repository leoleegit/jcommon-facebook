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
package org.jcommon.com.facebook.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.jcommon.com.facebook.cache.FileCache;
import org.jcommon.com.facebook.data.Error;

public class Uploader extends HttpServlet
{
  private static Logger logger = Logger.getLogger(Uploader.class);
  private static final long serialVersionUID = 1L;
  
  private String upload_path = FileCache.instance().getFile_root();

  public void init(ServletConfig config) throws ServletException {
	  if(config.getInitParameter("facebook.upload")!=null){
		  upload_path = config.getInitParameter("facebook.upload");
	      upload_path = System.getProperty("user.dir")+ java.io.File.separator + upload_path;
	      FileCache.instance().setFile_root(upload_path);
	  }
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
	    request.setCharacterEncoding("utf-8");
		
		String errormsg = null;
		List<FileItem> list   = new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request)){
			FileItem item = null;
			try{
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
				@SuppressWarnings("unchecked")
				List<FileItem>  items = upload.parseRequest(request);
				Iterator<FileItem> it = items.iterator();
				while (it.hasNext()){
					item = it.next();
					if (!item.isFormField()){
						list.add(item);
					}
				}
			}
			catch (Throwable e){
				logger.error("", e);
				errormsg = "transfer exception";
			}
		}else{
			errormsg = "not found multipart content";
			return;
		}
		if(errormsg!=null){
			error(response,errormsg);
			return;
		}
		for(FileItem item : list){
			try{
				String file_name  =  item.getName();
				String file_id    = org.jcommon.com.util.BufferUtils.generateRandom(20);
				String file_type  = file_name.indexOf(".")!=-1?file_name.substring(file_name.lastIndexOf(".")):"";
				
				FileOutputStream out_file = null;
				java.io.File file  = new java.io.File(upload_path);
				if(!file.exists())
					if(!file.mkdirs()){
						throw new Exception("file mkdir fail! -->"+file.getName());
					}
				String save_name = file_id+file_type;
				file = new java.io.File(upload_path,save_name);
				if(!file.exists())
					if(!file.createNewFile()){
						throw new Exception("file createNewFile fail! -->"+file.getName());
					}
				
				out_file = new FileOutputStream(file);
				InputStream is = item.getInputStream();
				logger.info("uploading...........");
				byte[] b = new byte[1024];
				int nRead;
				while((nRead = is.read(b, 0, 1024))>0){
				   out_file.write(b, 0, nRead);
				}
				try {
					out_file.close();
					out_file.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					file.delete();
					throw e1;
				}
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw e;
				}
				logger.info("done...............");
				String msg = "{\"id\":\""+file_id+"\"}";
				logger.info(msg);
				response.getWriter().println(msg);
			}catch (Throwable e){
				logger.error("", e);
				errormsg = "transfer exception";
			}
		}
		if(errormsg!=null){
			error(response,errormsg);
			return;
		}	
  }

  private void error(HttpServletResponse response, String msg) throws IOException {
    Error error = new Error();
    error.setType("jcommonfacebook.uploader");
    error.setMessage(msg);
    response.getWriter().println(error.toJsonStr());
  }
}