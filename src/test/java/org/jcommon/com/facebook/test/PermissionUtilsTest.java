package org.jcommon.com.facebook.test;

import org.jcommon.com.facebook.permission.Permission;
import org.jcommon.com.facebook.permission.UserPermission;
import org.jcommon.com.facebook.utils.PermissionUtils;

public class PermissionUtilsTest
{
  public static void main(String[] args)
  {
    Permission[] ps = { UserPermission.email, UserPermission.publish_actions };
    System.out.println(PermissionUtils.permissions2Str(ps));
  }
}