package lht.wangtong.core.utils.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * WebUtil 负责Cookie/Session等的管理。通常，所有对Cookie/Session的操作都通过此类来完成。 <br>
 * Session最终是采用 Servlet容器来管理，或者采用 JBoss Cache，由此控制。
 * 
 * @author <a href="mailto:senton1101@gmail.com">TianXiangdong</a> at 2010-10-4 上午09:59:01
 * @author lyl
 * @version 1.1
 */

@SuppressWarnings("unchecked")
public class WebUtil {

	/**
	 * 根据名字从HttpSession中获取一个对象
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            Session中对象的名字
	 * @return Object
	 */
	public static <T> T getObject(HttpServletRequest request, String name) {
		return (T) request.getSession().getAttribute(name);
	}

	/**
	 * 
	 * @param session
	 * 				HttpSession 对象
	 * @param name  
	 * 				session中对象的名字
	 * @return Object
	 */
	public static <T> T getObject(HttpSession session, String name) {
		return (T) session.getAttribute(name);
	}
	
	/**
	 * 根据给定的name将一个对象保存到 HttpSession中
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            Session中对象的名字
	 * @param object
	 *            需要保存到Session的对象
	 */
	public static <T> void putObject(HttpServletRequest request, String name, T object) {
		request.getSession().setAttribute(name, object);
	}
	
	/**
	 * 根据给定的name将一个对象保存到 HttpSession中
	 * 
	 * @param session
	 *            HttpSession对象
	 * @param name
	 *            Session中对象的名字
	 * @param object
	 *            需要保存到Session的对象
	 */
	public static <T> void putObject(HttpSession session, String name, T object) {
		session.setAttribute(name, object);
	}

	/**
	 * 将Session置为无效状态，通常在注销时调用
	 * 
	 * @param request
	 *            HttpServletRequest
	 */
	public static void invalidateSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}

	/**
	 * 获取URI的路径,如路径为http://www.example.com/example/user.do?method=add, 得到的值为"/example/user.do"
	 * 
	 * @param request
	 * @return String
	 */
	public static String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	/**
	 * 获取不包含应用名字的URI的路径, 并去掉最前面的"/", <br>
	 * 如路径为http://localhost:8080/appName/user/list.do, 得到的值为"user/list.do",其中appNames为应用的名字
	 * 
	 * @param request
	 * @return String
	 */
	public static String getNoAppNamedRequestURI(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		String realUri = uri.replaceFirst(contextPath, "");
		while (realUri.startsWith("/")) {
			realUri = realUri.substring(1);
		}
		realUri = realUri.replaceAll("/+", "/");
		return realUri;
	}

	/**
	 * 获取应用的根目录
	 * 
	 * @param request
	 * @return String
	 */
	public static String getContextPath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		if (contextPath.equals("/")) {
			return "";
		}
		return contextPath;
	}
	
	/**
	 * 获取不含端口号的域名。如：www.my.com:8080,返回 .my.com:8080
	 * @param request
	 * @return String
	 */
	public static String getDomain(HttpServletRequest request){
		String domain = request.getHeader("host");
		if(domain==null){
			return "localhost";
		}
		if(domain.indexOf('.')>0){
			domain = domain.substring(domain.indexOf('.'));
		}		
		return domain;
	}

	/**
	 * 获取完整请求路径(不含域名和端口，含内容路径及请求参数)
	 * 
	 * @param request
	 * @return String
	 */
	public static String getRequestURIWithParam(HttpServletRequest request) {
		return getRequestURI(request) + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
	}
	
	/**
	 * 获取完整请求路径(含域名和端口，含内容路径及请求参数)
	 * 
	 * @param request
	 * @return String
	 */
	public static String getRequestCompleteURI(HttpServletRequest request) {
		int port = request.getServerPort();
		return "http://" + request.getServerName() + (port != 80 ? (":" + port) : "") + getRequestURIWithParam(request);
	}
	
	/**
	 * 获取服务的域名，包含端口，比如http://localhost:8080/appName/user/list.do, 得到的值为http://localhost:8080
	 * @param request
	 * @return
	 */
	public static String getServerUrlPort (HttpServletRequest request){
		int port = request.getServerPort();
		return "http://" + request.getServerName() + (port != 80 ? (":" + port) : "");
	}
	
	/**
	 * 得到ip地址
	 * @param request
	 * @return
	 */
	public static String getRequestIpAddr (HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for"); 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getRemoteAddr(); 
		} 
		return ip; 
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie的名称
	 * @param value
	 *            cookie的值
	 * @param maxAge
	 *            cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 
	 *            如果值为0,cookie立刻失效)
	 *            如果值为-1,浏览器关闭后失效
	 * @param path 
	 * 			  是相对于应用服务器存放应用的文件夹的根目录而言的,设置有效的应用。如果/ 则可以在两个不同的应用中使用    
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge,String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, String domain,String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie的值
	 * 
	 * @param request
	 * @param name
	 *            cookie的名称
	 * @return
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String str = "/1/1/do";
		//logger.debug(str.replaceFirst("/1", ""));
	}
			
}
