package lht.wangtong.core.utils.lang;

import org.apache.log4j.Logger;

public class ByteTool {
	public static Logger logger = Logger.getLogger(ByteTool.class.getClass());
	/**
	 * 将 byte[] 的值，在ASCII 可见字符值的范围下，转为字符串
	 * 应用：POS机传回服务器的协议中，密码段固定为8个字节，如果用户录入的密码不够8位，使用0字节代替
	 * @param bBuf
	 * @return
	 */
	public static String byteArr2VisibleStr(byte[] bBuf){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<bBuf.length;i++){
			if(bBuf[i]<=0x7E && bBuf[i]>=0x20){
			   sb.append( (char)bBuf[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将 byte[] 的值，转为 十六进制的 字符串 显示。
	 * 一般用于日志输出
	 * @param bBuf
	 * @return
	 */
	public static String byteArr2HexStr(byte[] bBuf){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<bBuf.length;i++){
			sb.append( byte2HexStr(bBuf[i]) );
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 将一个 十六进制的字符串值的数组 String[] 转换为 byte[] 
	 * @param str
	 * @return
	 */
	public static byte[] hexStr2Bytes(String[] hexs){
		if(hexs==null){
			return null;
		}
		byte[] bBuf = new byte[hexs.length];
		for(int i=0;i<hexs.length;i++){
			bBuf[i] = hexStr2Byte(hexs[i]);			
		}
		return bBuf;
	}
	
	/**
	 * 将 一个byte 的值，转为 十六进制的 字符串 显示。
	 * @param j
	 * @return
	 */
	public static String byte2HexStr(byte j){
		String str = Integer.toHexString(j&0xff).toUpperCase();
		if(str.length()<=1){
			return "0"+str;
		}
		else if(str.length()>2){
			return str.substring(2);
		}
		else{
			return str;
		}
	}
	
	/**
	 * 将一个 十六进制的 字符串 转为 为 byte 值
	 * @param hex
	 * @return
	 */
	public static byte hexStr2Byte(String hex){
		return ( byte )(Integer.parseInt(hex,16) & 0xff );
	}
	
	
	/**
	 *  将两个 byte 转为 short
	 * @param byte0 前一位，高位
	 * @param byte1 后一位，低位
	 * @return
	 */
	public static short byteToShort(byte byte0, byte byte1){
		//注意：byte 是有符号的，第一位是代表正副
		return Short.parseShort(byte2HexStr(byte0) + byte2HexStr(byte1),16);
		//return (byte0 << 8) + byte1;
	}

	
	/**
	 * 将一个 short 值，转为 两个byte
	 * @param s
	 * @return
	 */
	public static byte[] shortToByte(short s){
		byte[] bytes = new byte[2];
		bytes[0] = (byte)( s>>8 );
		bytes[1] = (byte)(s & 0x00FF);
		return bytes;
	}

	/**
	 *  将两个 byte 转为 int
	 * @param byte0 前一位，高位
	 * @param byte1 后一位，低位
	 * @return
	 */
	public static int byteToInt(byte byte0, byte byte1){
		//注意：byte 是有符号的，第一位是代表正副
		return Integer.parseInt(byte2HexStr(byte0) + byte2HexStr(byte1),16);
		//return (byte0 << 8) + byte1;
	}

	/**
	 *  将两个 byte 转为 int
	 * @param byte0 前一位，高位
	 * @param byte1 后一位，低位
	 * @return
	 */
	public static int byteToInt(byte byte0, byte byte1,byte byte2){
		//注意：byte 是有符号的，第一位是代表正副
		return Integer.parseInt(byte2HexStr(byte0) + byte2HexStr(byte1)+ byte2HexStr(byte2),16);
		//return (byte0 << 8) + byte1;
	}
	
	/**
	 * 将一个 int 值，转为 三个byte
	 * 消费学费 传到 POS 机
	 * @param i
	 * @return
	 */
	public static byte[] intToByte(int i){
		byte[] bytes = new byte[3];
		bytes[0] = (byte)( i>>16 & 0x00FF );
		bytes[1] = (byte)( i>> 8 & 0x00FF);
		bytes[2] = (byte)(     i & 0x00FF);
		return bytes;
	}

	
	/**
	 *  将8个 byte 转为 long
	 * @param byte0 前一位，高位
	 * @param byte1 后一位，低位
	 * @return
	 */
	public static long byteToLong(byte[] bytes){
		//注意：byte 是有符号的，第一位是代表正副
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<bytes.length;i++){
			sb.append(byte2HexStr(bytes[i]));
		}
		return Long.parseLong(sb.toString(),16);
	}
	
	/**
	 * 将一个 long 值，转为 八个byte
	 * 场景：long型主键传到 POS机
	 * @param i
	 * @return
	 */
	public static byte[] longToByte(long i){
		byte[] bytes = new byte[8];
		bytes[0] = (byte)( i>>56 & 0x00FF);
		bytes[1] = (byte)( i>>48 & 0x00FF);
		bytes[2] = (byte)( i>>40 & 0x00FF);
		bytes[3] = (byte)( i>>32 & 0x00FF);
		bytes[4] = (byte)( i>>24 & 0x00FF);
		bytes[5] = (byte)( i>>16 & 0x00FF);
		bytes[6] = (byte)( i>>8  & 0x00FF);
		bytes[7] = (byte)(     i & 0x00FF);
		return bytes;
	}
	
	public static void main(String[] args) {
		byte b1 =(byte)0xB5;		
		byte b2 =(byte)0xB5;
		logger.debug(  byteToInt(b1,b2) );		
		
		String[] hexs = "A1 CA 1D 82 8D 20 3C 5B 20 64 A8 21 F8 09 4B AC 51 38 0C 38 C3 51 EC 1D 6C 48 12 32 0F 9E B7 5B FF 0D 43 A7 0F A8 0A 4A BC 4F CF 1B 67 85 3F FA 06 00 8C 68 2E 09 8E 15 61 7C 08 22 33 70 3C 09 34 42 40 3F 1E 05 B6 3F 7F 16 12 23 5E C0 0B 2B 20 1C 00 04 CE 96 1D CE 08 DA 99 64 34 05 97 B6 28 D5 0A 39 87 50 B8 08 0C 30 35 3C 0C 36 38 4C FF 34 78 B9 32 5A 0D 26 23 4E 70 13 8E B4 48 DC 23 BA 2F 1F EF 03 BE 92 2F 3B 08 E8 92 2C 40 09 65 48 26 D7 0B 99 35 34 CA 10 AE D4 CD 11 10 14 C4 6C A2 CD CD EC 15 C0 C3 69 A3 BD CE DE BD 18 C0 C2 65 A4 CC CD EE CC CA 1A C0 C1 5D A1 DD DD 76 06 A2 EC BB BB C0 7E 5A A2 AC DD EF 07 A2 ED BB BB 20 C0 7E 58 A1 BB CE 6F 77 08 A2 FD CC AA 21 C0 7E 58 A1 9B BE 6D 77 0A 12 A2 EC BA A9 C0 7E 56 A1 99 BD 68 75 0A 15 A2 FB B9 A9 28 7E 54 A1 97 AB 60 71 0C 19 21 A2 B9 8A 9A 7E 4F A1 97 9A 57 65 0D 1E 27 A1 A9 79 81 7E 4B A2 87 97 97 31 A2 0C 98 86 2C C0 4A A6 67 87 76 34 47 67 76 2D C0 46 A6 77 97 53 2A B9 46 66 2F C0 41 A3 97 86 43 2C 42 A2 84 35 75 C0 3D 81 A1 75 33 26 19 54 4A A1 12 56 36 C0 36 A2 98 76 32 1D 04 62 52 47 A1 24 76 C0 30 81 A1 76 42 17 03 69 5A 4D A1 23 66 C0 7E 28 A1 79 65 1C 10 02 6F 60 54 4F 4B 46 C0 7E 21 A1 68 76 17 0D 03 72 67 5C 57 52 C0 C1 19 A3 78 44 03 30 64 60 E0".split(" ");
		byte[] bBuf = ByteTool.hexStr2Bytes(hexs);
		String str = Base64Tool.encode(bBuf);
		logger.debug(str);

		byte[] test = new byte[8];
		test[0] = 0x31;
		test[1] = 0x31;
		test[2] = 0x31;
		test[3] = 0x37;
		test[4] = 0x31;
		test[5] = 0x31;
		test[6] = 0x00;
		test[7] = 0x00;
		logger.debug(ByteTool.byteArr2VisibleStr(test));
	}	
}
