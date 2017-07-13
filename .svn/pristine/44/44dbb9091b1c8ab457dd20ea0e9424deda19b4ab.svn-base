package lht.wangtong.core.utils.sms;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;




public class Test {
	@SuppressWarnings({ "static-access", "static-access" })
	public static void main(String[] args) {
		try {
			new Test().sendSMS("13568902392","Java Http方式短信调试已经成功!!!!![国卫云医]","");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	/*
	 * 发送方法  其他方法同理      返回0 或者 1 都是  提交成功
	 */
	public static int sendSMS(String Mobile,String Content,String send_time) throws MalformedURLException, UnsupportedEncodingException {
		URL url = null;
		String CorpID="LKSDK0004503";//账户名
		String Pwd="jkwqm814@";//密码
		String send_content=URLEncoder.encode(Content.replaceAll("<br/>", " "), "GB2312");//发送内容
		url = new URL("  http://mb345.com/WS/BatchSend.aspx?CorpID="+CorpID+"&Pwd="+Pwd+"&Mobile="+Mobile+"&Content="+send_content+"&Cell=&SendTime="+send_time);
		BufferedReader in = null;
		int inputLine = 0;
		try {
			System.out.println("开始发送短信手机号码为 ："+Mobile);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			inputLine = new Integer(in.readLine()).intValue();
		} catch (Exception e) {
			System.out.println("网络异常,发送短信失败！");
			inputLine=-2;
		}
		System.out.println("结束发送短信返回值：  "+inputLine);
		return inputLine;
	}


}
