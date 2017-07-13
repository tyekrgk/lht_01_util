package lht.wangtong.core.utils.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lht.wangtong.core.utils.lang.StringTool;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ToExcelUtil {
	public static Logger logger = Logger
			.getLogger(ToExcelUtil.class.getClass());
	private String fileName;
	private static ToExcelUtil instance = null;

	private ToExcelUtil() {
	}

	public static ToExcelUtil getInstance() {
		if (instance == null) {
			instance = new ToExcelUtil();
		}
		return instance;
	}

	private String FILE_NAME;
	private String titleSheet = "sheet1";

	public void aa() {
		logger.debug(FILE_NAME);
	}

	@SuppressWarnings("deprecation")
	public String toExcel(String[] title, List<Object[]> contentlist,
			HttpServletRequest request, String path, String prefix)
			throws Exception {
		if (title == null || title.length == 0) {
			throw new Exception("title is null");
		}
		if (contentlist == null || contentlist.size() == 0) {
			throw new Exception("contentlist is null");
		}
		//系统生成名称
		FILE_NAME = prefix + new SimpleDateFormat("yyyyMMddHHmmssss").format(new Date()) + ".xls";
		fileName = FILE_NAME;//设置文件名
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		OutputStream out = new FileOutputStream(path + fileName);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(titleSheet);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		sheet.setDefaultColumnWidth((short) 15);// 默认每个单元格宽
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 生成并设置另一个样式

		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		HSSFRow row = sheet.createRow(0);

		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(title[i]);
		}
		for (int i = 0; i < contentlist.size(); i++) {
			Object[] recordColumn = contentlist.get(i);
			row = sheet.createRow(i + 1);
			for (int j = 0; j < recordColumn.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(style2);
				if(recordColumn[j]==null||StringTool.isEmpty(recordColumn[j].toString())){
					cell.setCellValue("");
				}else{
					cell.setCellValue(recordColumn[j].toString());
				}
			}

		}
		workbook.write(out);
		out.close();
		return path + fileName;
	}

	public void download(HttpServletRequest request,
			HttpServletResponse response, String contentType, String fileName)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		if (fileName == null) {
			fileName = request.getParameter("fileName");
		}
		String downLoadPath = fileName;
		long fileLength = new File(downLoadPath).length();
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public String getTitleSheet() {
		return titleSheet;
	}

	public void setTitleSheet(String titleSheet) {
		this.titleSheet = titleSheet;
	}

	public static void main(String agr[]) throws Exception {
		/*ToExcelUtil a = ToExcelUtil.getInstance();
		String[] title = new String[] { "顿方法饭", "电视剧", "ctile" };
		Object[] a1 = new Object[] { 1, 2, 3 };
		Object[] a2 = new Object[] { "A", "B", "C" };
		Object[] a3 = new Object[] { 0.01, "B", "C" };
		List<Object[]> contentlist = new ArrayList<Object[]>();
		contentlist.add(a1);
		contentlist.add(a2);
		contentlist.add(a3);*/

		// String[] title,List<Object[]> contentlist
		// a.aa();
		// a.ToExcel(title, contentlist);
	}
}
