package lht.wangtong.core.utils.secret;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * DES加密方式   CBC 模式
 * 
 * @author aibozeng
 */
public class DesByCBC {
	
	 public static Logger logger = Logger.getLogger(DesByCBC.class.getClass());
	
	private static final byte[] SK = new byte[]{
		(byte)0x89,(byte)0x97,(byte)0xFB,0x5B,(byte)0x40,(byte)0x31,(byte)0x9E,(byte)0x9E,(byte)0xFB,(byte)0xD6,(byte)0xF1,(byte)0x19,
		(byte)0xC1,(byte)0x52,(byte)0xE5,0x2C,(byte)0xAB,(byte)0xAB,(byte)0x37,(byte)0x92,(byte)0x64,(byte)0x19,(byte)0xA4,(byte)0xAB};
	private static final byte[] IV = new byte[]{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08};
	private final static String DES_TYPE = "DESede/CBC/PKCS7Padding";
	
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	public static String encrypt(byte[] src , byte[] keyBytes , byte[] ivBytes ) {		
		  
		    SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		    
		    Cipher cipher = null;
			try {
				cipher = Cipher.getInstance(DES_TYPE,"BC");
			 
			
			    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			    byte[] enSrc = cipher.doFinal(src);
			 
                return new BASE64Encoder().encode(enSrc); 
			    
			} catch (NoSuchAlgorithmException e) {
				logger.error(e);
			} catch (NoSuchPaddingException e) {
				logger.error(e);
			} catch (InvalidKeyException e) {
				logger.error(e);
			} catch (InvalidAlgorithmParameterException e) {
				logger.error(e);
			} catch (IllegalBlockSizeException e) {
				logger.error(e);
			} catch (BadPaddingException e) {
				logger.error(e);
			} catch (NoSuchProviderException e) {
				logger.error(e);
			}
            return null;
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
	
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	public static byte[] decrypt(byte[] src , byte[] keyBytes , byte[] ivBytes ) {		
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
	    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	    Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_TYPE,"BC");
		    cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		    byte[] enSrc = cipher.doFinal(src);
            return enSrc; 
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		} catch (NoSuchPaddingException e) {
			logger.error(e);
		} catch (InvalidKeyException e) {
			logger.error(e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error(e);
		} catch (IllegalBlockSizeException e) {
			logger.error(e);
		} catch (BadPaddingException e) {
			logger.error(e);
		} catch (NoSuchProviderException e) {
			logger.error(e);
		}
        return null;
  }
	
	/**
	 * 
	 * 密码解密
	 * 
	 * @param data
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	public final static String decrypt(String data) {
		try {
		String miwen = 	byte2hex(new BASE64Decoder().decodeBuffer(data));
			return new String(DesByCBC.decrypt(hex2byte(miwen.getBytes()),SK,IV));
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 
	 * 密码加密
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public final static String encrypt(String password) {
		try {
			return DesByCBC.encrypt(password.getBytes(),SK,IV);
		} catch (Exception e) {
		}
		return null;

	}
	public static void main(String[] args) throws Exception {
		String ming="201004030002LH;SX00010001";
		logger.debug("明文："+ming);
		String mi = encrypt(ming);
		logger.debug("加密："+mi);
		String mii = byte2hex(new BASE64Decoder().decodeBuffer(mi));
		logger.debug("BASE64Decoder之后："+mii);
		String jiemi = decrypt(mi);
		logger.debug("解密: "+jiemi);
	}
}
