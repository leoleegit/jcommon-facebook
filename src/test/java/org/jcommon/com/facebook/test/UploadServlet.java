package org.jcommon.com.facebook.test;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jcommon.com.util.BufferUtils;

public class UploadServlet extends HttpServlet
{
  protected static Logger log = Logger.getLogger(UploadServlet.class);
  private static final long serialVersionUID = 1L;
  private String path;

  public void init(ServletConfig config)
    throws ServletException
  {
    this.path = "./resources";
    log.info("UploadServlet up .....");
    log.info("image store :" + this.path);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    byte[] data = null;
    MultipartParser mp = new MultipartParser(request, 10485760);

    log.info("upload start ...");
    String filename = null;
    Part part;
    while ((part = mp.readNextPart()) != null)
    {
      String name = part.getName();
      if (part.isParam())
      {
        ParamPart paramPart = (ParamPart)part;
        String value = paramPart.getStringValue();
        log.info("param Part name:" + name + "\tvalue:" + value);
      }
      else if (part.isFile())
      {
        FilePart filePart = (FilePart)part;
        String fileName = filePart.getFileName();

        filename = BufferUtils.generateRandom(20);
        log.info("size:" + filePart.getInputStream().available());
        if (fileName != null)
        {
          filename = filename + fileName.substring(fileName.indexOf("."));
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          filePart.writeTo(bos);
          data = bos.toByteArray();

          OutputStream targetFile = new FileOutputStream(this.path + File.separator + filename);

          targetFile.write(data);
          targetFile.close();
        }
      }
    }
    response.setContentType("text/html;charset=utf-8");
    if (filename != null) {
      String url = "https://127.0.0.1:8080" + File.separator + "image" + File.separator + filename;
      log.info("file url:" + url);
      response.getWriter().write("true," + url);
    } else {
      response.getWriter().write("false,upload fail");
    }
    log.info("upload end ...");
  }
}