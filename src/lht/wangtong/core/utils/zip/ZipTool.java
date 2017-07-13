package lht.wangtong.core.utils.zip;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 压缩帮助类
 * 
 * @author lyl
 * 
 */
public class ZipTool {
	
	public static Logger logger = Logger.getLogger(ZipTool.class.getClass());
	
	private final static String CONTENTTYPEXML = "text/xml";
	private final static String c = "-";	

	/**
	 * 生成压缩文件
	 * @param destFileName 源文件（包含路径）
	 * @param path 路径
	 * @param sourFileName 目标文件名
	 */
	public static void make2zip(String destFileName, String path,String sourFileName) {
		try {
			FileOutputStream f = new FileOutputStream(path + destFileName);
			CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
			ZipOutputStream zos = new ZipOutputStream(csum);

			zos.putNextEntry(new ZipEntry(sourFileName));
			FileInputStream in = new FileInputStream(path + sourFileName);
			int c = 0;
			byte[] readBuf = new byte[2048];
			while ((c = in.read(readBuf)) != -1){
				zos.write(readBuf, 0, c);
			}
			in.close();
			zos.flush();
			zos.close();
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} 
	}
	
	public static void main(String[] args) {
		
	}

	public static long downLoadZip(HttpServletResponse response, String path, String destFileName) {
		long totalLen = 0;// 字节
		File file = new File(path + destFileName);
		DataOutputStream outputStream = null;
		response.reset();
		response.setContentType(CONTENTTYPEXML);
		String outFilename = getFileNameByASCII(destFileName, c);
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			logger.error(e1);
			return totalLen;
		}
		try {
			response.setContentType("application/x-zip-compressed ");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + outFilename + "\"");
			outputStream = new DataOutputStream(new BufferedOutputStream(response.getOutputStream()));

			byte[] read = new byte[2048];
			int len = 0;
			int readNum = 0;
			while ((len = is.read(read)) != -1) {
				outputStream.write(read, 0, len);
				readNum++;
				totalLen = totalLen + len;
				if (readNum % 1000 == 0) {
					outputStream.flush();
				}
			}
			outputStream.flush();
			outputStream.close();
			is.close();

		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return totalLen;
	}
	
	//ASCII转换为汉字
	public static String getFileNameByASCII(String fileName,String c){
		if(c == null){
			return fileName;
		}
		String[] chars = fileName.split(c);
		StringBuffer retSting  = new StringBuffer();
		for (int i = 0; i < (chars.length-1); i++) {
			retSting.append((char) Integer.parseInt(chars[i])+"");
		}
		retSting.append(chars[chars.length-1]+"");
		return retSting.toString();
	}

}
