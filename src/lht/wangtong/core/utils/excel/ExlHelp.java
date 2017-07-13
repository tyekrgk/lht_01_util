package lht.wangtong.core.utils.excel;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import lht.wangtong.core.utils.date.DateUtil;
import lht.wangtong.core.utils.file.Common;
import lht.wangtong.core.utils.lang.StringTool;
import lht.wangtong.core.utils.regex.RegexUtils;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 联通华盛  项目组使用 
 * 
 * 下载excel是分两步的: 第一步：生成excel文件(保存在服务器端某路径下面) 第二步：下载程序(不在这里实现)
 * 此应用程序主要负责生成excel文件。下载程序根据具体Web框架而定,需要在Http环境下面执行下载。
 * 
 * @author Key.Wang
 */
public class ExlHelp {
	
	 public static Logger logger = Logger.getLogger(ExlHelp.class.getClass());

	private final static String CONTENTTYPE = "application/vnd.ms-excel";

	private final static String CONTENTTYPEXML = "text/xml";
	
	private final static String ENCODING = "utf-8";
	
	private final static String ENCODING_WRITE = "GBK";
	
	private final static int EXCELSIZE = 5000;

	/**
	 * TEST
	 * 
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		logger.debug(Double.valueOf(1.0).longValue());
		// excel列表标题
		String[] title = new String[] { "客户编码", "客户姓名", "省办","ceshi1","ceshi1" };
		// excel名称前缀
		String fileNm = "客户信息.xml";
		// excel保存excel文件路径，如果没有程序会自动创建，但有一条，系统必须有权限，而且路径必须是合理的。
		// 比如window下面根本没有L盘,写个L:/someDirect/那就不好了。
		// String path ="E:/";
		String path = "E:/nothedirectIwantcreate";
		// excel的数据体，一般来源查询结果，如果是查询出来的List<Entity>,需要程序转换一下。
		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < 10; i++) {
			// 10行数据
			Object[] data = new Object[5];
			for (int j = 0; j < 5; j++) {
				data[j] = title[j] + ":" + i + ":" + j + ",Test";
			}
			list.add(data);
		}
		List<Object> rtn = null;
		try {
			rtn = ExlHelp.exlOutputBig(path, fileNm, title, list);
			logger.debug(rtn);
		} catch (Exception e) {
			logger.error(e);
		}
//		List<List<Object>> list1 = readUploadExl(
//				"E:\\nothedirectIwantcreate\\客户信息-1308667379761.xlsx",
//				0, 1, 0);
//		logger.debug(list1.size() + "----------S");
	}

	/**
	 * 根据输入的条件产生excel文件,并保存在服务器端，返回的List： 位置0标志符：Y/N,表示成功或失败
	 * 位置1excel文件名：表示当前excel名字的全称
	 * ，是:preName+sysTime+radom.long.xls，比如客户信息-1288192734312
	 * -6815730548140041616.xls 位置2异常或者说明：如果有的话，那么是异常说明
	 * 
	 * @param exlSaveFilePath
	 *            文件保存的路径，必须有，而且路径应该存在，如果不存在，jvm应该有权限创建此文件夹
	 * @param fileName
	 *            excel文件的前缀，同时也是 文件sheet名称
	 * @param title
	 *            excel中标题的名称
	 * @param contentList
	 *            excel中数据
	 * @return 返回List
	 * @throws Exception
	 */
	public static List<Object> exlOutput(String exlSaveFilePath, String preExlName,
			String[] title, List<Object[]> contentList) throws Exception {
		
		String flag = "Y";
		List<Object> rtn = new ArrayList<Object>();
		String path = "";
		StringBuffer error = new StringBuffer();
		if (exlSaveFilePath == null || "".equals(exlSaveFilePath.trim())) {
			rtn.add("N");
			rtn.add("");
			rtn.add("no path");
			return rtn;
		}
		char lastChar = exlSaveFilePath.charAt(exlSaveFilePath.length() - 1);
		if (lastChar == '\\' || lastChar == '/') {
			path = exlSaveFilePath.substring(0, exlSaveFilePath.length() - 1);
		} else {
			path = exlSaveFilePath;
		}
		path += "/";
		
		XSSFWorkbook wwb = null;
		FileOutputStream fileOut = null;
		Long ct = System.currentTimeMillis();
		java.util.Random ran = new Random();
		String excelName = "";
		long rdlong = ran.nextLong();
		try {
			if (!Common.markDir(path)) {
				throw new Exception("create file path falure!");
			}
			excelName = preExlName + "-" + ct.toString() + rdlong + ".xlsx";
			wwb = new XSSFWorkbook();
			fileOut = new FileOutputStream(path + excelName);
			XSSFSheet sheet = wwb.createSheet();

			// 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
			sheet.setColumnWidth((short) 0, 2500);
			sheet.setColumnWidth((short) 1, 5000);

			// 创建一个单元格，从0开始
			XSSFRow rowTitle = sheet.createRow((short) 0);
			// 设置单元格样式
			XSSFCellStyle hs = wwb.createCellStyle();
			// hs.setFillBackgroundColor(XSSFColor.RED.index);//设置背景
			hs.setFillForegroundColor((short)22);
			hs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			hs.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
			// 设置字体样式
			XSSFFont hf = wwb.createFont();
			hf.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			hf.setFontHeightInPoints((short) 13);
			hs.setFont(hf);

			// 构造一个数组设置第一行之后的单元格
			XSSFCell cellTitle[] = new XSSFCell[title.length];
			for (int i = 0; i < title.length; i++) {
				cellTitle[i] = rowTitle.createCell(i);
				cellTitle[i].setCellStyle(hs);
				cellTitle[i].setCellValue(title[i]);
			}
			int contentListSize = contentList.size();
			int rowCount = 1;
			while(contentListSize > 0){
				if(rowCount % EXCELSIZE == 0){
					fileOut.flush();
				}
				XSSFRow row = sheet.createRow(rowCount);
				XSSFCell cell[] = new XSSFCell[title.length];
				for (int j = 0; j < title.length; j++) {
					String contentStr = "";
					if (contentList.get(rowCount-1)[j] != null) {
						contentStr = contentList.get(rowCount-1)[j].toString();
					}
					cell[j] = row.createCell(j);
					cell[j].setCellValue(contentStr);
				}
				rowCount ++ ;
				contentListSize --;
			}
			wwb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			logger.error(e);
			flag = "N";
			error.append("IOException>>" + e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		rtn.add(flag);
		rtn.add(excelName);
		rtn.add(error.toString());
		return rtn;
	}

	/**
	 * 读取excel文件
	 * 
	 * @param exlFilePath
	 * @return
	 */
	public static List<List<Object>> readUploadExl(String exlFilePath) {
		return readUploadExl(exlFilePath, 0, 0, 0);
	}

	/**
	 * 读取excel文档
	 * 
	 * @param exlFilePath
	 *            excel存放的绝对路径 一定要指向excel文件,比如d:/myfile/abc.xls
	 *            或者/usr/tomcat/upload/elsfile/abc.xls
	 * @param readsheet
	 *            要读取的某个sheet，从0开始
	 * @param startRow
	 *            要读取的sheet，从某行开始读 默认需要从0
	 * @param startCol
	 *            要读取的sheet,从某列开始读 默认从0开始
	 * @return
	 */
	public static List<List<Object>> readUploadExl(String exlFilePath, int readsheet,
			int startRow, int startCol) {
		List<List<Object>> list = new ArrayList<List<Object>>();
		if (exlFilePath == null || "".equals(exlFilePath.trim()))
			return list;
		File file = new File(exlFilePath);
		if (file == null || !file.exists())
			return list;
		try {
			list = readUploadExl(new FileInputStream(exlFilePath), readsheet, startRow, startCol);
		} catch (Exception e) {
			logger.error(e);
		} 
		return list;
	}

	/**
	 * 根据输入的条件导出excel文件,并写到response中，返回的List： 位置0标志符：Y/N,表示成功或失败
	 * 位置1为异常或者说明：如果有的话，那么是异常说明
	 * 
	 * @param excelFilename
	 *            excel文件的前缀，同时也是 文件sheet名称
	 * @param title
	 *            excel中标题的名称
	 * @param contentList
	 *            excel中数据
	 * @return 返回List
	 * @throws Exception
	 */
	public static List<Object> exportExcel(HttpServletResponse response,
			String excelFilename, String[] title, List<Object[]> contentList)
			throws Exception {
		String flag = "Y";
		List<Object> rtn = new ArrayList<Object>();
		StringBuffer error = new StringBuffer();
		XSSFWorkbook wwb = null;
		OutputStream os = null;
		try {
			response.reset();
			response.setContentType(CONTENTTYPE);
			String filename = URLEncoder.encode(excelFilename, ENCODING);
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ filename + ".xlsx\"");

			os = response.getOutputStream();
			wwb = new XSSFWorkbook();

			XSSFSheet sheet = wwb.createSheet();

			// 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
			sheet.setColumnWidth(0, 2500);
			sheet.setColumnWidth( 1, 5000);

			// 创建一个单元格，从0开始
			XSSFRow rowTitle = sheet.createRow(0);
			// 设置单元格样式
			XSSFCellStyle hs = wwb.createCellStyle();
			// hs.setFillBackgroundColor(XSSFColor.RED.index);//设置背景
//			XSSFColor color = new XSSFColor();
//			color.setIndexed(indexed);
			hs.setFillForegroundColor((short)22);
			hs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			hs.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
			// 设置字体样式
			XSSFFont hf = wwb.createFont();
			hf.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			hf.setFontHeightInPoints((short) 13);
			hs.setFont(hf);

			// 构造一个数组设置第一行之后的单元格
			XSSFCell cellTitle[] = new XSSFCell[title.length];
			for (int i = 0; i < title.length; i++) {
				cellTitle[i] = rowTitle.createCell(i);
				cellTitle[i].setCellStyle(hs);
				cellTitle[i].setCellValue(title[i]);
			}
			for (int i = 0; i < contentList.size(); i++) {
				XSSFRow row = sheet.createRow((i + 1));
				XSSFCell cell[] = new XSSFCell[title.length];
				for (int j = 0; j < title.length; j++) {
					String contentStr = "";
					if (contentList.get(i)[j] != null) {
						contentStr = contentList.get(i)[j].toString();
					}
					cell[j] = row.createCell(j);
					cell[j].setCellValue(contentStr);
				}
			}
			wwb.write(os);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e);
			flag = "N";
			error.append("导出失败！");
		} 
		rtn.add(flag);
		rtn.add(error.toString());
		return rtn;
	}
	
	/**
	 * 根据输入的条件导出excel文件，先保存到服务器, AJAX返回地址，让浏览器再访问该文件达到下载。
	 * 返回的List： 位置0标志符：Y/N,表示成功或失败
	 * 位置1为异常或者说明：如果有的话，那么是异常说明
	 * 
	 * @param excelFilename
	 *            excel文件的前缀，同时也是 文件sheet名称
	 * @param tempSaveFPath 临时存放的绝对路径和文件名(都是英文)
	 * @param title
	 *            excel中标题的名称
	 * @param contentList
	 *            excel中数据
	 * @return 返回List
	 * @throws Exception
	 */
	public static List<String> exportExcelToFile(String excelFilename, String tempSaveFPath ,  String[] title, List<Object[]> contentList)
			throws Exception {
		String flag = "Y";
		List<String> rtn = new ArrayList<String>();
		StringBuffer error = new StringBuffer();
		XSSFWorkbook wwb = null;
		try {
	
			wwb = new XSSFWorkbook();

			XSSFSheet sheet = wwb.createSheet();

			// 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
			sheet.setColumnWidth(0, 2500);
			sheet.setColumnWidth( 1, 5000);

			// 创建一个单元格，从0开始
			XSSFRow rowTitle = sheet.createRow(0);
			// 设置单元格样式
			XSSFCellStyle hs = wwb.createCellStyle();
			// hs.setFillBackgroundColor(XSSFColor.RED.index);//设置背景
//			XSSFColor color = new XSSFColor();
//			color.setIndexed(indexed);
			hs.setFillForegroundColor((short)22);
			hs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			hs.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
			// 设置字体样式
			XSSFFont hf = wwb.createFont();
			hf.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			hf.setFontHeightInPoints((short) 13);
			hs.setFont(hf);

			// 构造一个数组设置第一行之后的单元格
			XSSFCell cellTitle[] = new XSSFCell[title.length];
			for (int i = 0; i < title.length; i++) {
				cellTitle[i] = rowTitle.createCell(i);
				cellTitle[i].setCellStyle(hs);
				cellTitle[i].setCellValue(title[i]);
			}
			for (int i = 0; i < contentList.size(); i++) {
				XSSFRow row = sheet.createRow((i + 1));
				XSSFCell cell[] = new XSSFCell[title.length];
				for (int j = 0; j < title.length; j++) {
					String contentStr = "";
					if (contentList.get(i)[j] != null) {
						contentStr = contentList.get(i)[j].toString();
					}
					cell[j] = row.createCell(j);
					cell[j].setCellValue(contentStr);
				}
			}
			File file = new File(tempSaveFPath);
			FileOutputStream os = new FileOutputStream(file);
			wwb.write(os);		
			os.close();
			wwb=null;
		} catch (Exception e) {
			logger.error(e);
			flag = "N";
			error.append("RowsExceededException>>" + e);
		} 
		rtn.add(flag);
		rtn.add(error.toString());
		return rtn;
	}
	
	
	/**
	 * 多个 worksheet
	 * @param excelFilename
	 *            excel文件的前缀，同时也是 文件sheet名称
	 * @param title
	 *            excel中标题的名称
	 * @param contentList
	 *            excel中数据
	 * @return 返回List
	 * @throws Exception
	 */
	public static List<Object> exportExcel(HttpServletResponse response,
			String excelFilename, String[] title, HashMap<String,List<Object[]>>  contentListMap)
			throws Exception {
		String flag = "Y";
		List<Object> rtn = new ArrayList<Object>();
		StringBuffer error = new StringBuffer();
		XSSFWorkbook wwb = null;
		OutputStream os = null;
		try {
			response.reset();
			response.setContentType(CONTENTTYPE);
			String filename = URLEncoder.encode(excelFilename, ENCODING);
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ filename + ".xlsx\"");

			os = response.getOutputStream();
			wwb = new XSSFWorkbook();
			
			Iterator<String> sheetKey = contentListMap.keySet().iterator();
			while(sheetKey.hasNext()){
				String sheetName = sheetKey.next();
				XSSFSheet sheet = wwb.createSheet(sheetName);
				List<Object[]> contentList = contentListMap.get(sheetName);
				// 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
				sheet.setColumnWidth((short) 0, 2500);
				sheet.setColumnWidth((short) 1, 5000);

				// 创建一个单元格，从0开始
				XSSFRow rowTitle = sheet.createRow((short) 0);
				// 设置单元格样式
				XSSFCellStyle hs = wwb.createCellStyle();
				hs.setFillForegroundColor((short)22);
				hs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
				hs.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
				// 设置字体样式
				XSSFFont hf = wwb.createFont();
				hf.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
				hf.setFontHeightInPoints((short) 13);
				hs.setFont(hf);

				// 构造一个数组设置第一行之后的单元格
				XSSFCell cellTitle[] = new XSSFCell[title.length];
				for (int i = 0; i < title.length; i++) {
					cellTitle[i] = rowTitle.createCell(i);
					cellTitle[i].setCellStyle(hs);
					cellTitle[i].setCellValue(title[i]);
				}
				for (int i = 0; i < contentList.size(); i++) {
					XSSFRow row = sheet.createRow((short) (i + 1));
					XSSFCell cell[] = new XSSFCell[title.length];
					for (int j = 0; j < title.length; j++) {
						String contentStr = "";
						if (contentList.get(i)[j] != null) {
							contentStr = contentList.get(i)[j].toString();
						}
						cell[j] = row.createCell(j);
						cell[j].setCellValue(contentStr);
					}
				}				
				
				
			}

			wwb.write(os);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e);
			flag = "N";
			error.append("RowsExceededException>>" + e);
		} 
		rtn.add(flag);
		rtn.add(error.toString());
		return rtn;
	}
		
	/**
	 * 特殊导出，除了第1和第2行，其他的行中如果为数字型，单元格需要设置成数字的格式
	 * @param response
	 * @param excelFilename
	 * @param title
	 * @param contentList
	 * @return
	 * @throws Exception
	 */
	public static List<Object> exportExcel1(HttpServletResponse response,
			String excelFilename, String[] title, List<Object[]> contentList)
			throws Exception {
		String flag = "Y";
		List<Object> rtn = new ArrayList<Object>();
		StringBuffer error = new StringBuffer();
		XSSFWorkbook wwb = null;
		OutputStream os = null;
		try {
			response.reset();
			response.setContentType(CONTENTTYPE);
			String filename = URLEncoder.encode(excelFilename, ENCODING);
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ filename + ".xlsx\"");

			os = response.getOutputStream();
			wwb = new XSSFWorkbook();

			XSSFSheet sheet = wwb.createSheet();

			// 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
			sheet.setColumnWidth((short) 0, 2500);
			sheet.setColumnWidth((short) 1, 5000);

			// 创建一个单元格，从0开始
			XSSFRow rowTitle = sheet.createRow((short) 0);
			// 设置单元格样式
			XSSFCellStyle hs = wwb.createCellStyle();
			// hs.setFillBackgroundColor(XSSFColor.RED.index);//设置背景
			hs.setFillForegroundColor((short)22);
			hs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			hs.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置居中
			// 设置字体样式
			XSSFFont hf = wwb.createFont();
			hf.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			hf.setFontHeightInPoints((short) 13);
			hs.setFont(hf);

			// 构造一个数组设置第一行之后的单元格
			XSSFCell cellTitle[] = new XSSFCell[title.length];
			for (int i = 0; i < title.length; i++) {
				cellTitle[i] = rowTitle.createCell(i);
				cellTitle[i].setCellStyle(hs);
				cellTitle[i].setCellValue(title[i]);
			}
			for (int i = 0; i < contentList.size(); i++) {
				XSSFRow row = sheet.createRow((short) (i + 1));
				XSSFCell cell[] = new XSSFCell[title.length];
				for (int j = 0; j < title.length; j++) {
					String contentStr = "";
					if (contentList.get(i)[j] != null) {
						contentStr = contentList.get(i)[j].toString();
					}
					cell[j] = row.createCell(j);
					if((j==0 || j > 2) && contentStr != null && str2int(contentStr.trim())){
						cell[j].setCellType(0);
						cell[j].setCellValue(Long.valueOf(contentStr.trim()));
					} else {
						cell[j].setCellValue(contentStr);
					}
				}
			}
			wwb.write(os);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e);
			flag = "N";
			error.append("RowsExceededException>>" + e);
		} 
		rtn.add(flag);
		rtn.add(error.toString());
		return rtn;
	}


	public static boolean str2int(String str){
		try {
			return RegexUtils.checkStrByRegex(str, "^-?[0-9]{1,}$");
		} catch (Exception e) {
			return false;
		}
	}
	
	public static List<List<Object>> readUploadExl(InputStream inputStream,
			int readsheet, int startRow, int startCol) {
		List<List<Object>> list = new ArrayList<List<Object>>();
		Workbook wb = null;
		Sheet sheet = null;
		int rows = 0;
		int cols = 0;
		try {
			// 得到exl文件
			wb = WorkbookFactory.create(inputStream);
			if (readsheet > wb.getNumberOfSheets() - 1){
				return list;
			}
			//得到当前要读的页
			sheet = wb.getSheetAt((short)readsheet);
			
			rows = sheet.getLastRowNum();
			if (sheet.getRow(0) == null){
				return list;
			}
			cols = sheet.getRow(0).getLastCellNum();
			// 从0开始
			if (startRow < 0)
				startRow = 0;
			if (startCol < 0)
				startCol = 0;
			// 读最后一行一列吧
			if (startRow > rows)
				startRow = rows;
			if (startCol > cols-1)
				startCol = cols-1;
			List<Object> rowList = null;
			for (int i = startRow; i <= rows; i++) {
				rowList = new ArrayList<Object>();
				Row row = sheet.getRow(i);
				if (row == null){
					continue;
				}
				int sum = 0;
				for (int j = startCol; j < cols; j++) {
					Cell cell = row.getCell(j);
					if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
						rowList.add("");
						sum ++;
//						logger.debug(sum);
						continue;
					}
					Object cellValue = null;
//					logger.debug("cellType=" + cell.getCellType());
					if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
						cellValue = cell.getBooleanCellValue();
					} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {  
							Date date = cell.getDateCellValue();
							cellValue = DateUtil.date2String(date, "yyyy-MM-dd");
						} else {
							BigDecimal d = new BigDecimal(cell.getNumericCellValue());
							if (d.equals(new BigDecimal(d.longValue()))){
								cellValue = String.valueOf(d.longValue());
							} else {
								cellValue = String.valueOf(d);
							}
						} 
					} else if (Cell.CELL_TYPE_ERROR == cell.getCellType()){
						cellValue = cell.getErrorCellValue();
					} else if (Cell.CELL_TYPE_FORMULA == cell.getCellType()){ 
						cellValue = cell.getCellFormula();
					} else {
						cellValue = cell.getStringCellValue();
					}
//					logger.debug("cellValue=" + cellValue + "|");
					rowList.add(cellValue);
					if (cellValue == null || !StringTool.isNotEmpty(cellValue.toString())){
						sum ++;
					}
				}
				if (sum != cols){
					list.add(rowList);
				} 
			}
		} catch (Exception e) {
			logger.error(e);
		} 
		return list;
	}
	
	/**
	 * 海量数据导出，生产的文件的后缀名为.xml 双击就直接用excel 打开了，如果行数大于65536 就需要用07以上来打开了，如果用03则只能显示出65536条记录 
	 * @param exlSaveFilePath
	 * @param preExlName
	 * @param title
	 * @param contentList
	 * @return
	 * @throws Exception
	 */
	public static List<Object> exlOutputBig(String exlSaveFilePath, String preExlName,
			String[] title, List<Object[]> contentList) throws Exception{
		String flag = "Y";
		List<Object> rtn = new ArrayList<Object>();
		String path = "";
		StringBuffer error = new StringBuffer();
		if (exlSaveFilePath == null || "".equals(exlSaveFilePath.trim())) {
			rtn.add("N");
			rtn.add("");
			rtn.add("no path");
			return rtn;
		}
		StringBuffer sb = new StringBuffer();
		DataOutputStream rafs = null;
		char lastChar = exlSaveFilePath.charAt(exlSaveFilePath.length() - 1);
		if (lastChar == '\\' || lastChar == '/') {
			path = exlSaveFilePath.substring(0, exlSaveFilePath.length() - 1);
		} else {
			path = exlSaveFilePath;
		}
		path += "/";
		Long ct = System.currentTimeMillis();
		java.util.Random ran = new Random();
		String excelName = "";
		long rdlong = ran.nextLong();
		if (!Common.markDir(path)) {
			throw new Exception("create file path falure!");
		}
		try {
			excelName = preExlName + "-" + ct.toString() + rdlong + ".xml";
			rafs = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(path + excelName))));
			int total = contentList.size()+1;;//总行数，content的条数加上表头
			int currentRecord = 0;
			int col = title.length; //总列数，
			sb.append("<?xml version=\"1.0\" encoding=\""+ENCODING_WRITE+"\"?>");
			
			sb.append("<?mso-application progid=\"Excel.Sheet\"?>");
			
			sb.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"");
			
			sb.append("  xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
			
			sb.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
			
			sb.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"");
			
			sb.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">");
			
			sb.append(" <Styles>");
			sb.append("  <Style ss:ID=\"Default\" ss:Name=\"Normal\">");
			sb.append("   <Alignment ss:Vertical=\"Center\"/>");
			sb.append("   <Borders/>");
			sb.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>");
			sb.append("   <Interior/>");
			sb.append("   <NumberFormat/>");
			sb.append("   <Protection/>");
			sb.append("  </Style>");
			sb.append("  <Style ss:ID=\"s62\">");
			sb.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>");
			sb.append("  </Style>");
			sb.append("  <Style ss:ID=\"s83\">");
			sb.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>");
			sb.append("   <Borders/>");
			sb.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"16\" ss:Bold=\"1\"/>");
			sb.append("   <Interior ss:Color=\"#A5A5A5\" ss:Pattern=\"Solid\"/>");
			sb.append("   <NumberFormat/>");
			sb.append("   <Protection/>");
			sb.append("  </Style>");
			sb.append(" </Styles>");
			sb.append("<Worksheet ss:Name=\"Sheet0\">");
			
			sb.append("<Table ss:ExpandedColumnCount=\"" + col
					+ "\" ss:ExpandedRowCount=\"" + total
					+ "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:StyleID=\"s62\">");
			//设置表头
			sb.append("<Column ss:StyleID=\"s62\" ss:Width=\"160\" ss:Span=\"" + (col-1) + "\"/>");
			sb.append("<Row ss:AutoFitHeight=\"0\">");
			for (int i = 0; i < col; i++) {
				sb.append("<Cell ss:StyleID=\"s83\"><Data ss:Type=\"String\">" + title[i] + "</Data></Cell>");
				
			}
			sb.append("</Row>");
			while(currentRecord < total - 1){
				sb.append("<Row>");
				for (int i = 0; i < col; i++) {
					sb.append("<Cell><Data ss:Type=\"String\">" + contentList.get(currentRecord)[i] + "</Data></Cell>");
					
				}
				sb.append("</Row>");
				if (currentRecord % EXCELSIZE == 0) {
					rafs.write(sb.toString().getBytes(ENCODING_WRITE));
					rafs.flush();
					sb.setLength(0);
				}
				currentRecord++;
			}
			sb.append("</Table>");
			sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			
			sb.append("<ProtectObjects>False</ProtectObjects>");
			
			sb.append("<ProtectScenarios>False</ProtectScenarios>");
			
			sb.append("</WorksheetOptions>");
			
			sb.append("</Worksheet>");
			sb.append("</Workbook>");
			
			rafs.write(sb.toString().getBytes());
			rafs.flush();
			rafs.close();
		} catch (Exception e) {
			logger.error(e);
			flag = "N";
			error.append("IOException>>" + e);
		} finally {
			if(rafs != null){
				rafs.close();
			}
		}
		rtn.add(flag);
		rtn.add(excelName);
		rtn.add(error.toString());
		return rtn;
	}
	
	/**
	 * 海量数据导出，生产的文件的后缀名为.xml 双击就直接用excel 打开了，如果行数大于65536 就需要用07以上来打开了，如果用03则只能显示出65536条记录
	 * 特殊导出，除了第1和第2列，其他的行中如果为数字型，单元格需要设置成数字的格式
	 * @param response
	 * @param excelFilename
	 * @param title
	 * @param contentList
	 * @return
	 * @throws Exception
	 */
	public static List<Object> exportExcelBig(HttpServletResponse response,
			String excelFilename, String[] title, List<Object[]> contentList)
			throws Exception {
		String flag = "Y";
		List<Object> rtn = new ArrayList<Object>();
		StringBuffer error = new StringBuffer();
		OutputStream os = null;
		response.reset();
		response.setContentType(CONTENTTYPEXML);
		String filename = URLEncoder.encode(excelFilename, ENCODING);
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ filename + ".xml\"");
		StringBuffer sb = new StringBuffer();
		DataOutputStream rafs = null;
		os = response.getOutputStream();
		
		try {
			rafs = new DataOutputStream(new BufferedOutputStream(os));
			int total = contentList.size()+1;;//总行数，content的条数加上表头
			int currentRecord = 0;
			int col = title.length; //总列数，
			sb.append("<?xml version=\"1.0\" encoding=\""+ENCODING_WRITE+"\"?>");
			
			sb.append("<?mso-application progid=\"Excel.Sheet\"?>");
			
			sb.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"");
			
			sb.append("  xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
			
			sb.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
			
			sb.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"");
			
			sb.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">");
			
			sb.append(" <Styles>");
			sb.append("  <Style ss:ID=\"Default\" ss:Name=\"Normal\">");
			sb.append("   <Alignment ss:Vertical=\"Center\"/>");
			sb.append("   <Borders/>");
			sb.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>");
			sb.append("   <Interior/>");
			sb.append("   <NumberFormat/>");
			sb.append("   <Protection/>");
			sb.append("  </Style>");
			sb.append("  <Style ss:ID=\"s62\">");
			sb.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>");
			sb.append("  </Style>");
			sb.append("  <Style ss:ID=\"s83\">");
			sb.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>");
			sb.append("   <Borders/>");
			sb.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"16\" ss:Bold=\"1\"/>");
			sb.append("   <Interior ss:Color=\"#A5A5A5\" ss:Pattern=\"Solid\"/>");
			sb.append("   <NumberFormat/>");
			sb.append("   <Protection/>");
			sb.append("  </Style>");
			sb.append(" </Styles>");
			sb.append("<Worksheet ss:Name=\"Sheet0\">");
			
			sb.append("<Table ss:ExpandedColumnCount=\"" + col
					+ "\" ss:ExpandedRowCount=\"" + total
					+ "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:StyleID=\"s62\">");
			//设置表头
			sb.append("<Column ss:StyleID=\"s62\" ss:Width=\"160\" ss:Span=\"" + (col-1) + "\"/>");
			sb.append("<Row ss:AutoFitHeight=\"0\">");
			for (int i = 0; i < col; i++) {
				sb.append("<Cell ss:StyleID=\"s83\"><Data ss:Type=\"String\">" + title[i] + "</Data></Cell>");
				
			}
			sb.append("</Row>");
			while(currentRecord < total - 1){
				sb.append("<Row>");
				for (int i = 0; i < col; i++) {
					Object obj = contentList.get(currentRecord)[i];
					String str = "";
					if (obj != null){
						str = obj.toString();
					}
					if(i==0 || i > 2 && obj != null && str2int(str.trim())){
						sb.append("<Cell><Data ss:Type=\"Number\">" + str + "</Data></Cell>");
					} else {
						sb.append("<Cell><Data ss:Type=\"String\">" + str + "</Data></Cell>");
					}
					
				}
				sb.append("</Row>");
				if (currentRecord % EXCELSIZE == 0) {
					rafs.write(sb.toString().getBytes());
					rafs.flush();
					sb.setLength(0);
				}
				currentRecord++;
			}
			sb.append("</Table>");
			sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			
			sb.append("<ProtectObjects>False</ProtectObjects>");
			
			sb.append("<ProtectScenarios>False</ProtectScenarios>");
			
			sb.append("</WorksheetOptions>");
			
			sb.append("</Worksheet>");
			sb.append("</Workbook>");
			
			rafs.write(sb.toString().getBytes(ENCODING_WRITE));
			rafs.flush();
			response.flushBuffer();
			rafs.close();
		} catch (Exception e) {
			logger.error(e);
			flag = "N";
			error.append("IOException>>" + e);
		} finally {
			if(rafs != null){
				rafs.close();
			}
		}
		rtn.add(flag);
		rtn.add(filename+".xml");
		rtn.add(error.toString());
		return rtn;
	}

}
