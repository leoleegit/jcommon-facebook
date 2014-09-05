package org.jcommon.com.facebook.test;

import org.jcommon.com.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Json2ObjectTest
{
  public static void main(String[] args)
  {
    String data = "{ \"id\": \"271039552948235_467209446664577\", \"name\": \"Like\",\"type\": \"photo\"}";

    JSONObject jsonO = JsonUtils.getJSONObject(data);
    try {
      jsonO.remove("id");
      jsonO.accumulate("id", "123");
    }
    catch (JSONException e) {
      e.printStackTrace();
    }
    System.out.println(jsonO.toString());

    String message_id = "t_id.312177318835636_m_id.260226537433621";
    if ((message_id != null) && (message_id.indexOf("_m_id") != -1)) {
      message_id = message_id.substring(0, message_id.indexOf("_m_id"));
    }
    System.out.println(message_id);
  }
}