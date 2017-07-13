package lht.wangtong.core.utils.web;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * 本类仅限于EJB查找
 * 
 * 支持 在JBoss和WebLogic下的EJB查找和管理
 * jndiName ejb.ebridge.platform.ApplicationBO(jboss) 和  ejb.ebridge.platform.ApplicationBO#dcms.ebridge.platform.bo.ApplicationBO(WebLogic)
 * 使用了  expectedType 这个属性来生成  jndiName的 #符号后面的字符串
 * 根据  Properties environmentRef 里提供的  java.naming.factory.initial 的值 来判断 ejb服务端是 jboss 或 weblogic
 * @author aibozeng
 *
 */
public class MyJndiEjbFactoryService extends MyJndiObjectFactoryService {

	
	private  boolean weblogic = false;
	
	private Properties environmentRef = null;
	

	@Override
	protected <T> T lookup(String jndiName, Class<T> requiredType) throws NamingException {
		if(isWeblogic()){
			//weblogic的恶心地方
			jndiName = jndiName + "#"+getExpectedType().getName();
		}
        return	super.lookup(jndiName, requiredType);
	}
	
	@Override
	public void afterPropertiesSet() throws IllegalArgumentException, NamingException {
		if(getEnvironmentRef()!=null){
			super.getJndiTemplate().setEnvironment(getEnvironmentRef());
			String factory = getEnvironmentRef().getProperty(InitialContext.INITIAL_CONTEXT_FACTORY);
			if(factory!=null && factory.indexOf("weblogic")>=0){
	             setWeblogic(true);			
			}
		}		
		//必须最后调用
		super.afterPropertiesSet();

	}

	public boolean isWeblogic() {
		return weblogic;
	}

	public void setWeblogic(boolean weblogic) {
		this.weblogic = weblogic;
	}

	public Properties getEnvironmentRef() {
		return environmentRef;
	}

	public void setEnvironmentRef(Properties environmentRef) {
		this.environmentRef = environmentRef;
	}
	
}
