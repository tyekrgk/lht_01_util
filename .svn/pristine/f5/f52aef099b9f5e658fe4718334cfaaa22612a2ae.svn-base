package lht.wangtong.core.utils.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseController {
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 重定向到一个新的url
	 * redirect则是服务器收到请求后发送一个状态头给客 户，客户将再请求一次，这里多了两次网络通信的来往。
	 * 特点：浏览器重新发起请求.转发前的请求对象里的参数无法传递。
	 * response.sendRedirect("/somePage.jsp");
	 * @param url
	 * @return String
	 */
	protected String redirect (String url){
		return "redirect:" + url.trim();
	}
	
	/**
	 * 转发到一个新的url
	 * forward是服务器内部重定向，程序收到请求后重新定向到另一个程序，客户机并不知道.
	 * request.getRequestDispatcher("/somePage.jsp").forward(request, response);
	 * @param url
	 * @return String
	 */
	protected String forward (String url) {
		return "forward:" + url.trim();
	}
	
	/**
	 * 错误处理
	 * @param ex
	 * @param map
	 */
	protected void dealLog (Exception ex,ModelMap map) {
		
	}

	public Logger getLogger(){
		return logger;
	}
	
	public Object getService(ServletContext servletContext,Class theClass){
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if(ctx==null){
			return null;
		}
		return ctx.getBean(theClass);
	}
	
	public Object getService(ServletContext servletContext,String theName){
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if(ctx==null){
			return null;
		}
		return ctx.getBean(theName);
	}	
	
	public Object getController(ServletContext servletContext,String theName){
		ServletRegistration sr = servletContext.getServletRegistration("spring");
		return sr;
	}
}
