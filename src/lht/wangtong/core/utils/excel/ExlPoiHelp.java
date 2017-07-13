package lht.wangtong.core.utils.excel;

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
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import lht.wangtong.core.utils.date.CalenderUtil;
import lht.wangtong.core.utils.file.Common;
import lht.wangtong.core.utils.lang.StringTool;

/**
 * 
 * 下载excel是分两步的: 第一步：生成excel文件(保存在服务器端某路径下面) 第二步：下载程序(不在这里实现)
 * 此应用程序主要负责生成excel文件。下载程序根据具体Web框架而定,需要在Http环境下面执行下载。
 * 和华盛的系统不同的导入，没有去掉数字的精度
 * @author li youlong
 */
public class ExlPoiHelp {

	 public static Logger logger = Logger.getLogger(ExlPoiHelp.class.getClass());
	
	private final static String CONTENTTYPE = "application/vnd.ms-excel";

	private final static String ENCODING = "utf-8";

	/**
	 * TEST
	 * 
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {

		/*// excel列表标题
		String[] title = new String[] { "客户编码", "客户姓名", "省办" };
		// excel名称前缀
		String fileNm = "客户信息";
		// excel保存excel文件路径，如果没有程序会自动创建，但有一条，系统必须有权限，而且路径必须是合理的。
		// 比如window下面根本没有L盘,写个L:/someDirect/那就不好了。
		// String path ="E:/";
		String path = "E:/nothedirectIwantcreate";
		// excel的数据体，一般来源查询结果，如果是查询出来的List<Entity>,需要程序转换一下。
		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < 10; i++) {
			// 10行数据
			Object[] data = new Object[3];
			for (int j = 0; j < 3; j++) {
				data[j] = title[j] + ":" + i + ":" + j + ",Test";
			}
			list.add(data);
		}
		List<Object> rtn = null;
		try {
			rtn = ExlPoiHelp.exlOutput(path, fileNm, title, list);
			logger.debug(rtn);
		} catch (Exception e) {
			logger.error(e);
		}
		List<List<Object>> list1 = readUploadExl(
				"E:\\nothedirectIwantcreate\\客户信息-13069375841715203595981659477357.xls",
				0, 0, 0);
		logger.debug(list1.size() + "----------S");*/
		List<List<Object>> list1 = ExlPoiHelp.readUploadExl("D:/SRBarCode1_DCMS201111020024.xls", 1, 0, 0);
		logger.debug(list1.size());
		
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
		
		HSSFWorkbook wwb = null;
		FileOutputStream fileOut = null;
		Long ct = System.currentTimeMillis();
		java.util.Random ran = new Random();
		String excelName = "";
		long rdlong = ran.nextLong();
		try {
			if (!Common.markDir(path)) {
				throw new Exception("create file path falure!");
			}
			excelName = preExlName + "-" + ct.toString() + rdlong + ".xls";
			wwb = new HSSFWorkbook();
			fileOut = new FileOutputStream(path + excelName);
			HSSFSheet sheet = wwb.createSheet();

			// 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
			sheet.setColumnWidth((short) 0, 2500);
			sheet.setColumnWidth((short) 1, 5000);

			// 创建一个单元格，从0开始
			HSSFRow rowTitle = sheet.createRow((short) 0);
			// 设置单元格样式
			HSSFCellStyle hs = wwb.createCellStyle();
			// hs.setFillBackgroundColor(HSSFColor.RED.index);//设置背景
			hs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			hs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			hs.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
			// 设置字体样式
			HSSFFont hf = wwb.createFont();
			hf.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			hf.setFontHeightInPoints((short) 13);
			hs.setFont(hf);

			// 构造一个数组设置第一行之后的单元格
			HSSFCell cellTitle[] = new HSSFCell[title.length];
			for (int i = 0; i < title.length; i++) {
				cellTitle[i] = rowTitle.createCell(i);
				cellTitle[i].setCellStyle(hs);
				cellTitle[i].setCellValue(title[i]);
			}
			for (int i = 0; i < contentList.size(); i++) {
				HSSFRow row = sheet.createRow((short) (i + 1));
				HSSFCell cell[] = new HSSFCell[title.length];
				for (int j = 0; j < title.length; j++) {
					String contentStr = "";
					if (contentList.get(i)[j] != null) {
						contentStr = contentList.get(i)[j].toString();
					}
					cell[j] = row.createCell(j);
					cell[j].setCellValue(contentStr);
				}
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
		HSSFWorkbook wwb = null;
		OutputStream os = null;
		try {
			response.reset();
			response.setContentType(CONTENTTYPE);
			String filename = URLEncoder.encode(excelFilename, ENCODING);
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ filename + ".xls\"");

			os = response.getOutputStream();
			wwb = new HSSFWorkbook();

			HSSFSheet sheet = wwb.createSheet();

			// 设置单元格的宽度(0:表示第一行的第一个单元格，1：第一行的第二个单元格)
			sheet.setColumnWidth((short) 0, 2500);
			sheet.setColumnWidth((short) 1, 5000);

			// 创建一个单元格，从0开始
			HSSFRow rowTitle = sheet.createRow((short) 0);
			// 设置单元格样式
			HSSFCellStyle hs = wwb.createCellStyle();
			// hs.setFillBackgroundColor(HSSFColor.RED.index);//设置背景
			hs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			hs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			hs.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
			// 设置字体样式
			HSSFFont hf = wwb.createFont();
			hf.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			hf.setFontHeightInPoints((short) 13);
			hs.setFont(hf);

			// 构造一个数组设置第一行之后的单元格
			HSSFCell cellTitle[] = new HSSFCell[title.length];
			for (int i = 0; i < title.length; i++) {
				cellTitle[i] = rowTitle.createCell(i);
				cellTitle[i].setCellStyle(hs);
				cellTitle[i].setCellValue(title[i]);
			}
			for (int i = 0; i < contentList.size(); i++) {
				HSSFRow row = sheet.createRow((short) (i + 1));
				HSSFCell cell[] = new HSSFCell[title.length];
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
			error.append("RowsExceededException>>" + e);
		} 
		rtn.add(flag);
		rtn.add(error.toString());
		return rtn;
	}


	public static boolean str2int(String str){
		try {
			Integer.parseInt(str);
			return true;
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
							cellValue = CalenderUtil.getDateYmd(date);
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

}
