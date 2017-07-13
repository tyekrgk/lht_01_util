package lht.wangtong.core.utils.ckf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ckfinder.connector.ConnectorServlet;
import com.ckfinder.connector.configuration.ConfigurationFactory;
import com.ckfinder.connector.configuration.IConfiguration;

/**
 * Main connector servlet for handling CKFinder requests.
 */
@SuppressWarnings("serial")
public class LhtConnectorServlet extends ConnectorServlet {
	public Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void init() throws ServletException {
		String xml = getServletConfig().getInitParameter("XMLConfig");
		String fullPath = getServletContext().getRealPath(xml);
		logger.info(fullPath);
		super.init();
		IConfiguration c;
		try {
			c = ConfigurationFactory.getInstace().getConfiguration();
			logger.info(c.getBaseDir());
			logger.info(c.getBaseURL());
		} catch (Exception e) {
			logger.error(e);
		} 
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession(true).setAttribute("CKFinder_UserRole","operator"); 
		super.doGet(request, response);
	}
}
