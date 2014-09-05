package org.jcommon.com.facebook.test;

import java.util.HashMap;
import java.util.Map;
import org.jcommon.com.facebook.utils.TempFileCache;

public class TempFileCacheTest
{
  public static void main(String[] args)
  {
    Map data = new HashMap();
    data.put("key1", "value");
    TempFileCache.saveCache(data, "test", ".test", 100);
   // TempFileCache.loadFacebookCache(data, "test", ".test");
    System.out.println(System.getProperty("java.io.tmpdir"));
    System.out.println(data.size());
  }
}