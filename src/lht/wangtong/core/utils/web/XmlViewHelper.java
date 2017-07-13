package lht.wangtong.core.utils.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * XmlViewHelper 负责将XML文本输出到response中。<br>
 * 
 * @author <a href="mailto:senton1101@gmail.com">TianXiangdong</a> at 2010-9-1 下午05:21:44
 * @version 1.0
 */
public class XmlViewHelper {

	public static void print(HttpServletResponse response, String xmlText) throws IOException {
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");// HTTP 1.1
		response.setHeader("Cache-Control", "no-cache");// HTTP 1.0
		response.setHeader("Expires", "0");// 防止被proxy
		response.getWriter().write(xmlText);
		response.getWriter().close();
	}

}
