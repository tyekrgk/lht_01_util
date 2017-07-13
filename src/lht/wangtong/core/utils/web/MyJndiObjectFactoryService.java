package lht.wangtong.core.utils.web;

import javax.naming.NamingException;

import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * 重写jndi访问方式，针对不同的服务器，自动适应
 * @author lyl
 *
 */
public class MyJndiObjectFactoryService extends JndiObjectFactoryBean {

	/** JNDI prefix used in a J2EE container */
	public static final String JBOSS_CONTAINER_PREFIX = "java:/";

	@Override
	protected <T> T lookup(String jndiName, Class<T> requiredType) throws NamingException {
		T jndiObject;
		try {
			jndiObject = super.lookup(jndiName, requiredType);
		}
		catch (NamingException ex) {
			logger.info("调用父类的lookup方法失败...");
			// lookup出错，使用jboss的方式找
			String jbossConvertedName = convertJndiName4Jboss(jndiName);
			logger.info("尝试使用[" + jbossConvertedName + "]lookup...");
			try {
				jndiObject = getJndiTemplate().lookup(jbossConvertedName, requiredType);
			}
			catch (NamingException ex2) {
				logger.info("使用[" + jbossConvertedName + "]lookup失败...");
				throw ex;
			}
			logger.info("使用[" + jbossConvertedName + "]lookup成功...");
		}
		return jndiObject;
	}

	protected String convertJndiName4Jboss(String jndiName) {
		// Prepend container prefix if not already specified and no other scheme given.
		if (isResourceRef() && !jndiName.startsWith(JBOSS_CONTAINER_PREFIX) && jndiName.indexOf(':') == -1) {
			jndiName = JBOSS_CONTAINER_PREFIX + jndiName;
		}
		return jndiName;
	}

}
