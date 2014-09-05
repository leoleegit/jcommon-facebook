package org.jcommon.com.facebook.test;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import org.jcommon.com.facebook.cache.AppCache;
import org.jcommon.com.facebook.data.App;
import org.jcommon.com.util.jmx.Monitor;

public class GetAccessTokenTest extends Monitor
{
  public GetAccessTokenTest()
  {
    super("Get AccessToken Tester");
  }

  public void startup()
  {
    initOperation();
    registerMBean();
  }

  public void initOperation()
  {
    addOperation(new MBeanOperationInfo("addApp", "add or update app", new MBeanParameterInfo[] { 
    		new MBeanParameterInfo("app_name", "java.lang.String", "facebook app name"), 
    		new MBeanParameterInfo("app_id", "java.lang.String", "facebook app id"), 
    		new MBeanParameterInfo("app_secret", "java.lang.String", "facebook app secrect"), 
    		new MBeanParameterInfo("permissions", "java.lang.String", "facebook app reqeuest permissions") }, 
    		"void", 1));
    super.initOperation();
  }

  public void addApp(String app_name, String api_id, String app_secret, String permissions)
  {
    App app = new App(api_id, app_secret);
    app.setPermissions(permissions);
    AppCache.instance().addApp(app_name, app);
    super.addProperties(api_id, app_secret);
  }
}