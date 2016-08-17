package org.jcommon.com.facebook.test;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.facebook.RequestFactory;
import org.jcommon.com.util.http.HttpRequest;

public class NewTabRequestTest {
	public static URL init_file_is = NewTabRequestTest.class.getResource("/facebook-log4j.xml");
	static{
	    if (init_file_is != null)
	      DOMConfigurator.configure(init_file_is);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String token = "CAAKpe3uFXIwBAFm6itS8qOPwB6XBxQvR3c4tjLRoEhEWBkcMYHi3mJXtVvZA2ULTwzBIARStcZChTqF1afKZBKdfpDqoZCi9gDlhxkV4TckEfXzt5IWXQXhnW7amFFSkJRPgrQ9pgICzGQuXaC14tLCYVsH7wlK8l8vmadvWW6xhguRlqfuZCauZCiJLbgoOIZD";
//		String app_id= "749297771830412";
//		String page  = "506672062698518";
//		
//		HttpRequest request = RequestFactory.newTabRequest(null, page, token, app_id);
//		request.run();
//		System.out.println(request.getResult());
		
//		String post = "https://graph.facebook.com/v2.5/271039552948235_1171065992945582/comments?access_token=" +
//				"CAALn9fTi8zUBAJqlnWYkZB1I6XDEYoA2T7q6AdDjeWUGN7xXJGUI2b6Q4zVAHe7ZBx5juezZCWjczSeNdmTyzfEjJQtxomgQf8ic7RSDfL1oy4h42tO26Mt1rOWSqRxr4jkOeQmnNwRBru23HqzdOAPVMxnCOPmPjdhWNHqJQqyN00TFQI1MdibRUg648bw4V7VxXFbVAZDZD" +
//				"&message=Hi%20Mic%E3%80%82%E6%98%8E%E7%99%BD%E6%82%A8%E6%83%B3%E6%9F%A5%E8%A9%A2%E7%B6%B2%E4%B8%8A%E8%A1%8C%E5%8F%8Anow%20TV%E6%9C%8D%E5%8B%99%E3%80%82%E7%82%BA%E9%80%B2%E4%B8%80%E6%AD%A5%E5%8D%94%E5%8A%A9%E6%82%A8%EF%BC%8C%E8%AB%8B%E6%82%A8%E9%80%8F%E9%81%8E%E6%88%91%E5%93%8B%20facebook%20%E5%B0%88%E9%A0%81%20https%3A%2F%2Fwww.facebook.com%2Fpccwcs%20%E5%8F%B3%E4%B8%8A%E6%96%B9%E6%8C%89%20%27%20%E8%A8%8A%E6%81%AF%27%20%E5%B0%87%E6%9C%89%E9%97%9C%E5%B8%B3%E6%88%B6%E8%B3%87%E6%96%99%20%28%E5%A6%82%E7%99%BB%E5%85%A5%E5%90%8D%E7%A8%B1%E3%80%81%E8%B3%AC%E6%88%B6%E8%99%9F%E7%A2%BC%E6%88%96%E7%9B%B8%E9%97%9C%E9%9B%BB%E8%A9%B1%E8%99%9F%E7%A2%BC%29%E5%90%8C%E7%99%BB%E8%A8%98%E4%BA%BA%E5%85%A8%E5%90%8Dsend%E7%95%80%E6%88%91%E5%93%8B%EF%BC%8C%E7%95%B6%E6%94%B6%E5%88%B0%E8%B3%87%E6%96%99%E5%BE%8C%E6%88%91%E5%93%8B%E6%9C%83%E7%AB%8B%E5%8D%B3%E4%BD%9C%E5%87%BA%E8%B7%9F%E9%80%B2%E3%80%82Thx.%0A%0AGrace";
//		String access_token = "CAALn9fTi8zUBAJqlnWYkZB1I6XDEYoA2T7q6AdDjeWUGN7xXJGUI2b6Q4zVAHe7ZBx5juezZCWjczSeNdmTyzfEjJQtxomgQf8ic7RSDfL1oy4h42tO26Mt1rOWSqRxr4jkOeQmnNwRBru23HqzdOAPVMxnCOPmPjdhWNHqJQqyN00TFQI1MdibRUg648bw4V7VxXFbVAZDZD";
//		String id           = "271039552948235_1171065992945582";
//		String message      = "Hi Mic。明白您想查詢網上行及now TV服務。為進一步協助您，請您透過我哋 facebook 專頁 https://www.facebook.com/pccwcs 右上方按 ' 訊息' 將有關帳戶資料 (如登入名稱、賬戶號碼或相關電話號碼)同登記人全名send畀我哋，當收到資料後我哋會立即作出跟進。Thx."+
//
//"\n\nGrace";
//		HttpRequest request = RequestFactory.publishCommnetRequest(null,access_token,id,message);
//		request.run();
//		System.out.println(request.getResult());
		
		String access_token = "EAACZAGu9RCzsBAMRvQYnmZCd8dHLxqb6XhCirAZCzr86ZAjp3F453ZBhAI4cvlh10fn99sts8MkZB2uC0a1uwjfAyZBKGAG2s23XSx43tvgX3cIZCVNE1wtzxIgiHKwwkrpUoNmnZAOAygpsfmFKp2hfrbV2PqOvD126cShFGxhiovgZDZD";
		String id           = "223201114421192_1015790891828873";
		String message      = "Hi Mic。";
		HttpRequest request = RequestFactory.publishCommnetRequest(null,access_token,id,message);
		request.run();
		System.out.println(request.getResult());
	}

}
