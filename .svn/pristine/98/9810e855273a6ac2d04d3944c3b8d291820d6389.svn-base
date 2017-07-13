package lht.wangtong.core.utils.reflect;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import lht.wangtong.core.utils.date.CalenderUtil;


/**
 * 产生全局唯一的字符串
 * @author aibo zeng
 *
 */

public class UUIDHexGeneratorEx {
	public static Logger logger = Logger.getLogger(UUIDHexGeneratorEx.class.getClass());
   /**
    * 返回 16 个字符的 UUID : MM dd hh mm ss SSS + 三位随机数
    * 场合：POS使用的 session id 
    * @return
    */
   public static synchronized String gen16(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<3;i++){
		   sb.append(rand.nextInt(9));	      
	   }
	   return CalenderUtil.getDateYmd(new Date(),"MMddHHmmssSSS")+sb.toString();
   }
   
   /**
    * 返回 18 个字符的 UUID : MM dd hh mm ss SSS + 三位随机数
    * 场合：上传文件的文件名 
    * @return
    */
   public static synchronized String gen18(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<3;i++){
		   sb.append(rand.nextInt(9));	      
	   }
	   return CalenderUtil.getDateYmd(new Date(),"yyMMddHHmmssSSS")+sb.toString();
   }   
   /*
    * 产生 6个数字的随机码
    * 场合：临时验证码
    */
   public static String gen6Digital(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<6;i++){
		   sb.append(rand.nextInt(9));	      
	   }	   
	   return sb.toString();
   }
   
   /*
    * 产生 4个数字的随机码
    * 场合：临时验证码
    */
   public static String gen4Digital(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<4;i++){
		   sb.append(rand.nextInt(9));	      
	   }	   
	   return sb.toString();
   }
   
   /*
    * 产生 2个数字的随机码
    * 场合：银行帐户验证值
    */
   public static String gen2Digital(){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<2;i++){
		   sb.append(rand.nextInt(9));	      
	   }	   
	   return sb.toString();
   }

   /*
    * 产生 n个数字的随机码
    * 场合：
    */
   public static String genDigital(int n){
	   StringBuffer sb = new StringBuffer();
	   Random rand = new Random();
	   for(int i=0;i<n;i++){
		   sb.append(rand.nextInt(9));	      
	   }	   
	   return sb.toString();
   }
   
   /**
	 * 自动生成32位的令牌
	 * @return
	 */
	public static String base32(){
		char[] tempChar = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < 32; i++) {
			result.append(tempChar[new Random().nextInt(tempChar.length)]);
		}		
		return result.toString().trim();
	}
	
	/**
	 * 自动生成指定位的令牌
	 * @return
	 */
	public static String base(int sum){
		char[] tempChar = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < sum; i++) {
			result.append(tempChar[new Random().nextInt(tempChar.length)]);
		}		
		return result.toString().trim();
	}

	public static void main(String[] args){
		logger.debug(UUIDHexGeneratorEx.gen16());
		logger.debug(UUIDHexGeneratorEx.gen16());
		logger.debug(UUIDHexGeneratorEx.gen16());
		logger.debug(UUIDHexGeneratorEx.gen2Digital());
	}
   
}
