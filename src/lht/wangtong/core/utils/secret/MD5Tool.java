package lht.wangtong.core.utils.secret;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import beartool.MD5;

/**
 * MD5加密
 * 用途：
 * (1)用户登录密码， key 使用 t_user.id id放在密码的前面，生成MD5为大写。
 * 
 * @author aibozeng
 *
 */
public class MD5Tool { 
	
	 public static Logger logger = Logger.getLogger(MD5Tool.class.getClass());
	
	private static int keyLen = 12;
	private static long MaxID = 999999999999L;
	private static MD5 md5 = new MD5();
	
	/**
	 * 返回MD5(大写)
	 * @param pwd
	 * @param id
	 * @return
	 */
	public static String encryptionPwd(String pwd , long id){
		return md5.getMD5ofStr(formatKey(id)+pwd).toUpperCase();
	}

	/**
	 * 将 source 根据 key 生成一个 MD5字符串
	 * @param source
	 * @param key
	 * @return
	 */
	public static String encryptionStr(String source , String key){
		return md5.getMD5ofStr(key+source).toUpperCase();
	}
	
	public static boolean validatePwd(String entryPwd ,String pwdDB ,long userId){
		return validatePwd(entryPwd,pwdDB,userId,null);
	}
	
	public static boolean validatePwd(String entryPwd ,String pwdDB ,long userId,String sha1Pwd){
		if(pwdDB==null){
			return false;
		}
		if (entryPwd == null){
			return false;
		}
		if (entryPwd.length() == 32){  //输入密码为32则表示是通过js加密后的密码
			if (pwdDB.length() == 32) { //如果数据库密码为32位，则表示数据库的密码也是加密后的密码
				return pwdDB.toUpperCase().equals(entryPwd.toUpperCase());
			} else if (pwdDB.length() == 28) { //表示数据库的密码是SHA1加密方式的密码
				if (sha1Pwd == null){
					return false;
				}
				byte[] passwordHash = null;
				try {
					passwordHash = new BASE64Decoder().decodeBuffer(pwdDB);//把数据库的密码先解密
				} catch (IOException e) {
					logger.error(e);
				}
				if (passwordHash == null){
					return false;
				}
				return byte2hex(passwordHash).equals(sha1Pwd.toUpperCase());
			} else {   //则表示数据库的密码保持方式为明文
				String pwdMd5=MD5Tool.encryptionPwd(pwdDB,userId);  //需要先把数据库的明文密码加密后，再比较
				return pwdMd5.toUpperCase().equals(entryPwd.toUpperCase());
			}
		} else {  //表示输入的密码为明文
			if(pwdDB.length()==32){
				//32长度默认为是加过密的,把前台传入的进行加密
				String pwdMd5=MD5Tool.encryptionPwd(entryPwd,userId);
				return pwdMd5.toUpperCase().equals(pwdDB.toUpperCase());
			} else if(pwdDB.length()==28){
				//28位默认老易桥账号加过密的
				String pwdMd5=ecoding_old(entryPwd);
				return pwdMd5.toUpperCase().equals(pwdDB.toUpperCase());
			} else {
				//没有加密的
				return pwdDB.equals(entryPwd);
			}
		}
	}
	
	/**
	 * 老易桥加密方式
	 * @param passwd
	 * @return
	 */
	public static String ecoding_old(String passwd) {
		try {
			byte[] enc = passwd.getBytes("UTF8");
			
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] passwordHash=md.digest(enc);
			String encryptedString = new BASE64Encoder().encode(passwordHash);
			return encryptedString;
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 转化为12位的，不足前面补零
	 * @param id
	 * @return
	 */
	public static String formatKey(long id){
		if(id>MaxID){
			return String.valueOf(id);
		}
		 //得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(keyLen);
        //设置最小整数位数    
        nf.setMinimumIntegerDigits(keyLen);
        return nf.format(id);
	}
	
	/**
	 * 物流商加密入库
	 * @param xml
	 * @param markCode
	 * @return
	 */
	public static String encryptionLGS(String xml , String markCode){
		return md5.getMD5ofStr(xml + markCode).toUpperCase();
	}
	
	/**
	 * 标准算法创建一个MD5串, 
	 * @param source
	 * @return  32位大写的
	 */
	public static  String createMD5(String source){
		return md5.getMD5ofStr(source).toUpperCase();		
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        //logger.debug(MD5Tool.formatKey(1234567890123L));
        //logger.debug(MD5Tool.encryptionPwd("wangxuelian",1234L));
//        logger.debug("加密前密码 ：qwewrtwwetewt,加密后密码：" + ecoding_old("qwewrtwwetewt"));
//        logger.debug("加密前密码 ：safdsafds,加密后密码：" + ecoding_old("safdsafds"));
//        logger.debug("加密前密码 ：1wdsfcs,加密后密码：" + ecoding_old("1wdsfcs"));
//        logger.debug("加密前密码 ：!@#fdsfd123,加密后密码：" + ecoding_old("!@#fdsfd123"));
//        logger.debug("加密前密码 ：tfdfbhh64,加密后密码：" + ecoding_old("tfdfbhh64"));
//        logger.debug("加密前密码 ：poo096hhh,加密后密码：" + ecoding_old("poo096hhh"));
//        logger.debug("加密前密码 ：sdf^&54^,加密后密码：" + ecoding_old("sdf^&54^"));
//        logger.debug("加密前密码 ：bhujgtddas,加密后密码：" + ecoding_old("bhujgtddas"));
//        logger.debug("加密前密码 ：cdafrtyh,加密后密码：" + ecoding_old("cdafrtyh"));
//        logger.debug("加密前密码 ：123145674,加密后密码：" + ecoding_old("123145674"));
//        logger.debug("加密前密码 ：8765343$%333,加密后密码：" + ecoding_old("8765343$%333"));
//        logger.debug("加密前密码 ：Dsfbh8844,加密后密码：" + ecoding_old("Dsfbh8844"));
//        logger.debug("加密前密码 ：09hhgj&&g$$33,加密后密码：" + ecoding_old("09hhgj&&g$$33"));
//        logger.debug("加密前密码 ：1234567!@#,加密后密码：" + ecoding_old("1234567!@#"));
		logger.debug(MD5Tool.createMD5("SDK-CKS-010-00307^f8^c^38"));
		  
	}
	
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
}
