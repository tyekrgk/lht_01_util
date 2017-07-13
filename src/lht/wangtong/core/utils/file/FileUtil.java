package lht.wangtong.core.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

public class FileUtil {

	  public static Logger logger = Logger.getLogger(FileUtil.class.getClass());
	
	// 判断目录
	public static boolean markDir(String path) {
		File dirFile = null;
		try {
			dirFile = new File(path);
			if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
				boolean creadok = dirFile.mkdirs();
				if (creadok) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	/**
	 * 文本
	 * 
	 * @param fileName
	 *            文件名含路径
	 * @param context
	 *            文本正文
	 */
	public static void saveStringToFile(String filepath, String filename,
			String context) {
		if (!markDir(filepath))
			return; // 检测文件夹
		try {
			FileWriter writer = new FileWriter(filepath + filename);
			writer.write(context);
			writer.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public static void saveStringToFile(String filepath, String filename,
			byte[] context) {
		if (!markDir(filepath))
			return; // 检测文件夹
		try {
			FileOutputStream os = new FileOutputStream(filepath + filename);
			os.write(context);
			os.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public static void copyFile(String srcFilePath, String destFilePath)
			throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				new File(srcFilePath)));
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(new File(destFilePath)));
		Streams.copy(bis, bos, true);
	}

	public static void copyFile(byte[] content, String destFilePath)
			throws Exception {
		FileCopyUtils.copy(content, new File(destFilePath));
	}

	public static void deleteFolder(String folderPath) throws Exception {
		// 删除完里面所有内容
		delAllFile(folderPath);
		File myFilePath = new File(folderPath);
		// 删除空文件夹
		myFilePath.delete();
	}

	private static void delAllFile(String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				deleteFolder(path + File.separator + tempList[i]);// 再删除空文件夹
			}
		}
	}

}
