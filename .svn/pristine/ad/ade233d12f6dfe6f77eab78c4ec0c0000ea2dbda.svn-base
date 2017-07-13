package lht.wangtong.core.utils.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.util.NestedServletException;

/**
 * httpInvoke重写，过滤ip地址。防止非法的ip请求服务
 * @author lyl
 *
 */
public class MyHttpInvokerServiceExporter extends HttpInvokerServiceExporter {
	
	private List<String> ipFilters = null;
	

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//XXX 记录请求的日志，过滤非法的请求
		String ip = WebUtil.getRequestIpAddr(request);
		logger.debug(ip);
		if (ipFilters != null){
			if (!ipFilters.contains(ip)){
				PrintWriter pw = response.getWriter();
				pw.write("Illegal request, please contact the administrator!");
				return;
			}
		}
		try {
			super.handleRequest(request, response);
		} catch (Exception ex) {
			throw new NestedServletException("Class not found during deserialization", ex);
		}
	}
	
	public void setIpFilters(List<String> ipFilters) {
		this.ipFilters = ipFilters;
	}

}
