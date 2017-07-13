package lht.wangtong.core.utils.entity;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

/**
 * 属性复制
 * 1.参考了  apache BeanUtilsBean 和  spring 的 BeanUtils 两个类
 * 2.只针对  BaseEntity的实现类的 jdk基本类型进行复制
 * 
 * Java的基本类型有八种：int, double, float, long, short, boolean, byte, char， void
 * @author aibozeng
 *
 */

public class PropertyCopyer {
	public static Logger logger = Logger.getLogger(PropertyCopyer.class.getClass());
	/**
	 * 声明可以拷贝的类,  基本类型的必须拷贝, 除了  name="id"的不拷贝
	 */
	private static List<Class<?>> copyClasses = new ArrayList<Class<?>>();
	
	private static PropertyCopyer instance = new PropertyCopyer();
	
	private PropertyCopyer(){
		init();
	}

	private void init(){
		copyClasses.add(Date.class);
		copyClasses.add(String.class);
		copyClasses.add(Long.class);
		copyClasses.add(BigDecimal.class);
		copyClasses.add(Integer.class);
		copyClasses.add(Double.class);
		copyClasses.add(Float.class);
		copyClasses.add(Boolean.class);
		copyClasses.add(Short.class);
		copyClasses.add(Byte.class);
		copyClasses.add(Character.class);
		copyClasses.add(int.class);
		copyClasses.add(double.class);
		copyClasses.add(float.class);
		copyClasses.add(long.class);
		copyClasses.add(boolean.class);
		copyClasses.add(short.class);
		copyClasses.add(byte.class);
		copyClasses.add(char.class);
	}
	
    public void copyPropertyValue(BaseEntity source, BaseEntity target , String[] ignoreProperties ){
    	List<String> ignoreList = new ArrayList<String>();
    	if(ignoreProperties!=null){
    		ignoreList.addAll(Arrays.asList(ignoreProperties));
    	}
    	//固定不复制ID
    	ignoreList.add("id");
    	
    	
    	PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(source.getClass());
    	
    	for (PropertyDescriptor sourcePd : sourcePds) {
			if (sourcePd.getReadMethod() == null ){
				continue;
			}
			if(ignoreList.contains(sourcePd.getName())) {
				logger.debug(sourcePd.getName()+" not copy.");
				continue;
			}
			PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(target.getClass(), sourcePd.getName());
			if (targetPd == null || targetPd.getWriteMethod() == null) {
				logger.debug(sourcePd.getName()+" in Target no WriteMethod.");
				continue;
			}
			try {
				Method readMethod = sourcePd.getReadMethod();
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				Object value = readMethod.invoke(source);
				//判断对象是不是在复制范围
				if( value!=null&&!copyClasses.contains(value.getClass())){
					logger.debug(value.getClass()+" not copy.");
					continue;
				}				
				Method writeMethod = targetPd.getWriteMethod();
				if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
					writeMethod.setAccessible(true);
				}
				writeMethod.invoke(target, value);
			}
			catch (Throwable ex) {
				throw new FatalBeanException("Could not copy properties from source to target", ex);
			}
		}
    	
    }
	
	public static PropertyCopyer getInstance() {
		return instance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyCopyer pc = PropertyCopyer.getInstance();
		logger.debug(pc.toString());
	}

}
