package lht.wangtong.core.utils.secret;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

public class Arithmetic {
	public Logger logger = Logger.getLogger(this.getClass());
	
	Key key;

	public void getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(strKey.getBytes()));
			key = this.generateKey(strKey);
			_generator = null;
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/** 
	 * 获得密钥 
	 *  
	 * @param secretKey 
	 * @return 
	 * @throws NoSuchAlgorithmException  
	 * @throws InvalidKeyException  
	 * @throws InvalidKeySpecException  
	 */  
	private SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException,InvalidKeyException,InvalidKeySpecException{  
	      
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	    DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());  
	    keyFactory.generateSecret(keySpec);  
	    return keyFactory.generateSecret(keySpec);  
	}  

	public String getEncString(String strMing) {
		String strMi = "";
		try {
			return byte2hex(getEncCode(strMing.getBytes()));
		} catch (Exception e) {
			logger.error(e);
		}
		return strMi;
	}

	public String getDesString(String strMi) {
		String strMing = "";
		try {
			return new String(getDesCode(hex2byte(strMi.getBytes())));

		} catch (Exception e) {
			logger.error(e);
		}
		return strMing;
	}

	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public String byte2hex(byte[] b) { // 一个字节的数，
		// 转成16进制字符串
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase(); // 转成大写
	}

	public byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}

	public static void main(String[] args) {
		Arithmetic des = new Arithmetic();// 实例化一个对像
		des.getKey("01ae-89ty-y7ip-easy");// 生成密匙
		String strEnc = des.getEncString("http://www.easybridge-v6.com:8880/platform/main.do");// 加密字符串,返回String的密文
//		logger.debug(strEnc);
//		String strDes = des.getDesString("34984B0A99A93026145A6077EBDBD51803BC1E62F86BF3641FE9DB20CF0B7D0A51E517D6A0EFA196644C95321F8EB31014EBF36724DCB5F3");// 把String 类型的密文解密
		String strDes = des.getDesString(strEnc);
//		logger.debug(strDes);
	}

}
