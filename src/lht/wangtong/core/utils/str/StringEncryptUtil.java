package lht.wangtong.core.utils.str;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * 字符串加密解密工具
 * @author Rong.Xing
 * @data 2013-04-08
 */
public final class StringEncryptUtil {
	/**
	 * 
	 */
	private static final String ALGORITHM = "DES";
	
	/**
	 * The Default Key.
	 */
	public static final String DEFAULT_KEY = "LHTasdfsadf@#$%^$%^%^&*&asdf2424ABD";

	public static String encrypt(final String originalString) throws Exception {
		byte[] bEn = encrypt(originalString.getBytes(), DEFAULT_KEY.getBytes());
		return parseHexStringFromBytes(bEn);
	}

	/**
	 * 加密
	 * 
	 * @param originalString
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(final String originalString, final String key) throws Exception {
		byte[] bEn = encrypt(originalString.getBytes(), key.getBytes());
		return parseHexStringFromBytes(bEn);
	}

	private static byte[] encrypt(byte[] originalByte, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey keySpec = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, sr);
		return cipher.doFinal(originalByte);

	}

	/**
	 * 解密
	 * 
	 * @param encryptedString
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(final String encryptedString) throws Exception {
		byte[] bEn = parseBytesByHexString(encryptedString);
		byte[] orginal = decrypt(bEn, DEFAULT_KEY.getBytes());
		return new String(orginal);
	}

	public static String decrypt(final String encryptedString, final String key) throws Exception {
		byte[] bEn = parseBytesByHexString(encryptedString);
		byte[] orginal = decrypt(bEn, key.getBytes());
		return new String(orginal);
	}

	private static byte[] decrypt(byte[] encryptedByte, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey keySpec = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, sr);
		return cipher.doFinal(encryptedByte);
	}
	
	public static String parseHexStringFromBytes(final byte[] text) {
		StringBuffer buff = new StringBuffer(0);
		for (int i = 0; i < text.length; i++) {
			byte _byte = text[i];
			byte _bytel = (byte) (_byte & 0x000f);
			byte _byteh = (byte) (_byte & 0x00f0);
			getHexString(buff, (byte) ((_byteh >> 4) & 0x000f));
			getHexString(buff, _bytel);
		}
		return buff.toString();
	}
	
	private static void getHexString(final StringBuffer buffer, final byte value) {
		if (value - 9 > 0) {
			int index = value - 9;
			switch (index) {
			case 1:
				buffer.append("A");
				break;
			case 2:
				buffer.append("B");
				break;
			case 3:
				buffer.append("C");
				break;
			case 4:
				buffer.append("D");
				break;
			case 5:
				buffer.append("E");
				break;
			case 6:
				buffer.append("F");
				break;
			default:
				break;
			}
		} else {
			buffer.append(String.valueOf(value));
		}
	}
	
	public static byte[] parseBytesByHexString(final String hexString) {
		if (hexString == null || hexString.length() == 0
				|| hexString.equals("")) {
			return new byte[0];
		}
		if (hexString.length() % 2 != 0) {
			throw new IllegalArgumentException(
					"hexString length must be an even number!");
		}
		StringBuffer sb = new StringBuffer(hexString);
		StringBuffer sb1 = new StringBuffer(2);
		int n = hexString.length() / 2;
		byte[] bytes = new byte[n];
		for (int i = 0; i < n; i++) {
			if (sb1.length() > 1) {
				sb1.deleteCharAt(0);
				sb1.deleteCharAt(0);
			}
			sb1.append(sb.charAt(0));
			sb1.append(sb.charAt(1));
			sb.deleteCharAt(0);
			sb.deleteCharAt(0);
			bytes[i] = (byte) Integer.parseInt(sb1.toString(), 16);
		}
		return bytes;
	}

	public static void main(String[] args) {
		String password = "aadfffffff32sf";
		try {
			String cookieValue = StringEncryptUtil.encrypt(password, "18302848887");
			System.out.println(cookieValue.length());
			System.out.println(cookieValue);
			String value = StringEncryptUtil.decrypt(cookieValue, "18302848887");
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
