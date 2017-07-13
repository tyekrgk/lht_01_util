package lht.wangtong.core.utils.lang;

import java.io.IOException;


import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 字符串和byte数组转化
 * 
 * 对于 URLEncoder ，直接使用 java.net 的 URLEncoder  String encode(String s, String enc)   UTF-8
 * 
 * @author aibo zeng
 *
 */

public class Base64Tool {
    private static final BASE64Encoder base64Encoder = new BASE64Encoder();

    private static final BASE64Decoder base64Decoder = new BASE64Decoder();
    
    public static Logger logger = Logger.getLogger(Base64Tool.class.getClass());

    /**
     * 把 已经编码的字符串，还原为 byte[]
     * @param base64Str
     * @return
     */
	public static byte[] decode(String base64Str){
		try {
			return base64Decoder.decodeBuffer(base64Str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		} 
		return null;
	}
	
	/**
	 * 将 byte[] 编码为 base64 串
	 * @param bytes
	 * @return
	 */
	public static String encode (byte[] bytes){
		return base64Encoder.encode(bytes);
	}
	
	public static void main(String[] args){
		String str = "mspYlnddiwlbwgcRizABCGsMVAIOhpEeQQjeEzoBCgCeVwIKgiR0wgsXJWHCCBqnHwEJ4KhGwQd8rTgCBnW1PMENCLZtQgKSvzLBG+pBWsEHIkQUAQnHSUWBCybLEwIGw089wTJFUFICCSzQdEIFmdFAgRpE00QCDjVThPMNAQ9XXGFpb3N3BAoPFRgZGRkBEFdZX2ZtcnYEChAWGRsbGxwCD15iaXB0AQQJDRIWFxcXARBVV11janB3BQwSGBscHB0dAg9fY2lvdAEEBwsPExUVFQEQU1VZX2hwAQcPFBkbHR0fHwIOYGNpb3QBAwcKDhIUFAEQUFJWXGRwAwoSFxscHR0fHwIOYWRpcHQBAwYKDhITFQAQSExPVFhhcAQNFhodHR8gICACDWFlam9zdwMGCQ0QEwAQR0pNUlZebQQRGx8gICAhISICDWJlaWxwdQIFCAwPEgAQRkdLT1RZbAoYICMjIiIjIyUDDGVpbHB0AQUJDQ8AEEhJTE9SVWEZICYmJSMjIyQlAwxmam1xdQIGCw8QABBKS0xNT1JQMSgrKSYkIyMkJAQLbG9zdgIGCxAAEElMTk5PUU49MTAtKCQjIyMjBQpvc3YCBgkBD0lLTE5QTkM3My8qIyIiIhjwshi0qlAUIdEw/Pw6DhA6LRUwxDo+xuJCN/3f9W+Gb5OVbyqTMZGWymUK5kscx1BGzo60JFwJVWyQXDrpTUNHc9oSc3L5V8JgyjHKjxoclNRFnpzxnUg+KsXm7/HoeO2UR0O+RlAu5F1KAcCRd7uMgrMm6y276VF2HqGwsHc6BtuXQGmQxm2oLhXs11pT42/X8w1REDOAA0EGzUIXN84674goXmZ61Syp9h3RLHcYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgY";
		byte[] bytes = Base64Tool.decode(str);
		logger.debug("base64Str length="+str.length());
		logger.debug("byte[] length="+bytes.length);
		for(int i=0;i<bytes.length;i++){
			System.out.print(ByteTool.byte2HexStr(bytes[i]));
			System.out.print(" ");
		}
	}
}
