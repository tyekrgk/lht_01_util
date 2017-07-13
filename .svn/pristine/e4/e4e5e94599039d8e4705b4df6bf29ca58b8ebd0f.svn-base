package lht.wangtong.core.utils.mobile;



import java.io.IOException;
import java.io.StringWriter;


import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;



public class JsonUtil {

	 public static Logger logger = Logger.getLogger(JsonUtil.class.getClass());
	
	public static <T> T formatJson(String jsonStr,Class<T> entityClass){
		if(jsonStr==null)return null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		try {
			return mapper.readValue(jsonStr, entityClass);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return null;
	}
	
	
	public static String beanToJson(Object obj) throws IOException {   
	    // 这里异常都未进行处理，而且流的关闭也不规范。开发中请勿这样写，如果发生异常流关闭不了   
			ObjectMapper mapper = new ObjectMapper();
			  //当找不到对应的序列化器时 忽略此字段  
			mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);  
		    //使Jackson JSON支持Unicode编码非ASCII字符  
		    CustomSerializerFactory serializerFactory= new CustomSerializerFactory();  
		    serializerFactory.addSpecificMapping(String.class, new StringUnicodeSerializer());  
		    mapper.setSerializerFactory(serializerFactory);  
			
			
	        StringWriter writer = new StringWriter();   
	        JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);   
	        mapper.writeValue(gen, obj);   
	        gen.close();   
	        String json = writer.toString();   
	        writer.close();   
	        return json;   
	    } 
	
	
	public static String convert(String str) 
	{ 
	str = (str == null ? "" : str); 
	String tmp; 
	StringBuffer sb = new StringBuffer(1000); 
	char c; 
	int i, j; 
	sb.setLength(0); 
	for (i = 0; i < str.length(); i++) 
	{ 
	c = str.charAt(i); 
	sb.append("\\u"); 
	j = (c >>>8); //取出高8位 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 
	j = (c & 0xFF); //取出低8位 
	tmp = Integer.toHexString(j); 
	if (tmp.length() == 1) 
	sb.append("0"); 
	sb.append(tmp); 

	} 
	return (new String(sb)); 
	} 
}
