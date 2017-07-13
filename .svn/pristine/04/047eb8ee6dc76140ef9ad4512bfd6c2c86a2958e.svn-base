package lht.wangtong.core.utils.excel;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import lht.wangtong.core.utils.exception.GenericBusinessException;
import lht.wangtong.core.utils.file.Common;
import lht.wangtong.core.utils.regex.RegexUtils;

/**
 * EXCEL 导出   工具
 * 
 * 可以以追加数据行的方式导出数据，---适用于大数据量处理（上百万条）
 * 
 * 使用者：
 * (1) new ExlAppend() , 对象具有上下文。只能处理一个 Excel导出。
 * (2) 
 * @author liyoulong , aibo zeng
 */
public class ExlAppend {

	public Logger logger = Logger.getLogger(this.getClass());
	
	private final static String CONTENTTYPE = "application/vnd.ms-excel";

	private final static String CONTENTTYPEXML = "text/xml";
	
	private final static String ENCODING = "utf-8";
	
	private final static String ENCODING_WRITE = "GBK";
	
	private StringBuffer sb = new StringBuffer();
	
	private int currentRow = 0;
	
	/**
	 * 多少行写入一次
	 */
	private final static int EXCELSIZE = 5000;
	
	/**
	 * 输出流,可以是磁盘文件，也是  web response
	 * new DataOutputStream(new BufferedOutputStream(response.getOutputStream()))
	 */
	private DataOutputStream outputStream = null;
	
	/**
	 * 全路径，如： d://temp/salesreport20111111.xls
	 */
	private String excelFullName = "";
	
	/**
	 * EXCEL工作薄的标题
	 */
	private String workSheetTitle="worksheetTitle";
	
	/**
	 * 工作薄里的表格的标题
	 */
	private String[] dataTitle ;
	
	/**
	 * 数据行总数。不含标题行
	 */
	private int dataRowCount = 1;

	
	public ExlAppend(){		
	}
	

	public String getWorkSheetTitle() {
		return workSheetTitle;
	}
	public void setWorkSheetTitle(String workSheetTitle) {
		this.workSheetTitle = workSheetTitle;
	}
	public String[] getDataTitle() {
		return dataTitle;
	}
	public void setDataTitle(String[] title) {
		this.dataTitle = title;
	}
	public int getDataRowCount() {
		return dataRowCount;
	}
	public void setDataRowCount(int rowCount) {
		this.dataRowCount = rowCount;
	}
	
	/**
	 * 设置输出流--任意
	 * @param outputStream
	 */
	public void setOutputStream(DataOutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * 设置输出流为  浏览器
	 * @param response
	 */
	public void setOutputStream(HttpServletResponse response,String excelFilename)  throws GenericBusinessException{
		response.reset();
		response.setContentType(CONTENTTYPEXML);
		String filename = "";
		try {
			filename = URLEncoder.encode(excelFilename, ENCODING);
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ filename + ".xml\"");
			outputStream = new DataOutputStream(new BufferedOutputStream(response.getOutputStream()));
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			throw new GenericBusinessException(e.getMessage());
		} catch (IOException e) {
			logger.error(e);
			throw new GenericBusinessException(e.getMessage());
		}
	}
	
	public String getExcelFullName() {
		return excelFullName;
	}
	public void setExcelFullName(String excelFullName) {
		this.excelFullName = excelFullName;
	}


	/**
	 * 设置输出流 -- 本地磁盘
	 * @param exlSaveFilePath  绝对路径(应该通过CacheSystemParam 获取 img 路径,加上  temp 目录 )
	 * @param exlFileName   文件名,不包括后缀, 注意：应该附上随机值,如：salesreport2010111112
	 * @throws GenericBusinessException
	 */
	public void setOutputStream(String exlSaveFilePath, String exlFileName) throws GenericBusinessException{
		String path = "";
		if (exlSaveFilePath == null || "".equals(exlSaveFilePath.trim())) {			
			throw new GenericBusinessException("exlSaveFilePath is null");
		}
		char lastChar = exlSaveFilePath.charAt(exlSaveFilePath.length() - 1);
		if (lastChar == '\\' || lastChar == '/') {
			path = exlSaveFilePath.substring(0, exlSaveFilePath.length() - 1);
		} else {
			path = exlSaveFilePath;
		}
		path += "/";
		if (!Common.markDir(path)) {
			throw new GenericBusinessException("create file path falure!");
		}
		//excelFullName = path + exlFileName + ".xml";
		//exlFileName 调用端拼接.xml 
		excelFullName = path + exlFileName ;
		try {
			setOutputStream(new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File( excelFullName)))));
		}catch(FileNotFoundException fne){
			throw new GenericBusinessException(fne.getMessage());
		}
	}	

   public boolean str2int(String str){
		try {
			return RegexUtils.checkStrByRegex(str, "^-?[0-9]{1,}$");
		} catch (Exception e) {
			return false;
		}
	}
	
   /**
    * 输出头部信息
    */
	public void writeHead() {
		int col = dataTitle.length; //总列数，
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
					+ "\" ss:ExpandedRowCount=\"" + (getDataRowCount()+1)
					+ "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:StyleID=\"s62\">");
			//设置表头
			sb.append("<Column ss:StyleID=\"s62\" ss:Width=\"160\" ss:Span=\"" + (col-1) + "\"/>");
			sb.append("<Row ss:AutoFitHeight=\"0\">");
			for (int i = 0; i < col; i++) {
				sb.append("<Cell ss:StyleID=\"s83\"><Data ss:Type=\"String\">" + dataTitle[i] + "</Data></Cell>");
				
			}
			sb.append("</Row>");
			
		try {
			outputStream.write(sb.toString().getBytes(ENCODING_WRITE));
			outputStream.flush();
			sb.setLength(0);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	/**
	 * 追加一行数据
	 * @param content 放置一行多列的值
	 * @return 追加了多少行
	 */
	public int appendRowData(Object[] content){
		sb.append("<Row>");
		for (Object obj: content) {
			sb.append("<Cell><Data ss:Type=\"String\">" + obj + "</Data></Cell>");
			
		}
		sb.append("</Row>");
		currentRow++;
		try {
			flushDate();
		} catch (IOException e) {
			logger.error(e);
		}
		return 1;
	}
	
	private void flushDate() throws IOException{
		if (currentRow % EXCELSIZE == 0){
			try {
				outputStream.write(sb.toString().getBytes(ENCODING_WRITE));
				outputStream.flush();
				sb.setLength(0);
			} catch (IOException e) {
				logger.error(e);
				throw e;
			}
		}
	}
	
	/**
	 * 追加多行数据
	 * @param contentList
	 * @return 追加了多少行
	 */
	public int appendRowData(List<Object[]> contentList){
		for(Object[] objs : contentList){
			appendRowData(objs);
		}
		return currentRow;
	}
	
	/**
	 * 追加多行数据. 调用端自行维护  resultSet 的打开和关闭
	 * @param resultSet JDBC的数据集,传入时,确保游标不要执行 next(),是在初始化位置状态
	 * @return 追加了多少行
	 */
	public int appendRowData(ResultSet resultSet){
		try{
			while(resultSet.next()){
				currentRow ++;
				sb.append("<Row>");
				for(int i=1;i<=dataTitle.length;i++) {
					Object obj = null;
					try {
						obj = resultSet.getObject(i);
					} catch (SQLException e) {
						logger.error(e);
						obj="";
					}
					if (obj == null){
						obj = "";
					}
					sb.append("<Cell><Data ss:Type=\"String\">" + String.valueOf(obj) + "</Data></Cell>");
					
				}
				sb.append("</Row>");
				try {
					flushDate();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}catch(SQLException e){	
			logger.error(e);
		}
		return currentRow;
	}
	
	
	/**
	 * 输出结尾信息。并关闭输出流
	 */
	public void  writeEnd(){
		sb.append("</Table>");
		sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
		
		sb.append("<ProtectObjects>False</ProtectObjects>");
		
		sb.append("<ProtectScenarios>False</ProtectScenarios>");
		
		sb.append("</WorksheetOptions>");
		
		sb.append("</Worksheet>");
		sb.append("</Workbook>");
		
       	try{		
			outputStream.write(sb.toString().getBytes(ENCODING_WRITE));
			outputStream.flush();
			sb.setLength(0);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

	
	/**
	 * TEST
	 * 
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		//logger.debug(Double.valueOf(1.0).longValue());
		// excel列表标题
		String[] title = new String[] { "客户编码", "客户姓名", "省办","ceshi1","ceshi1" };
		// excel名称前缀
		String fileNm = "客户信息";
		// excel保存excel文件路径，如果没有程序会自动创建，但有一条，系统必须有权限，而且路径必须是合理的。
		// 比如window下面根本没有L盘,写个L:/someDirect/那就不好了。
		// String path ="E:/";
		String path = "E:/nothedirectIwantcreate";
		// excel的数据体，一般来源查询结果，如果是查询出来的List<Entity>,需要程序转换一下。
		List<Object[]> list = new ArrayList<Object[]>();

		ExlAppend exl = new ExlAppend();
		//1.写标题行
		exl.setDataTitle(title);
		exl.setWorkSheetTitle("test");
		//数据行, 实际多少行，就传多少行(不含标题)
		exl.setDataRowCount(700000);
		try {
			exl.setOutputStream(path, fileNm);
			//xml格式头
			exl.writeHead();
			
			//准备模拟数据
			for (int i = 0; i < 100000; i++) {
				// 10行数据
				Object[] data = new Object[5];
				for (int j = 0; j < 5; j++) {
					data[j] = title[j] + ":" + i + ":" + j + ",Test";
				}
				list.add(data);
			}
			exl.appendRowData(list);
			//准备模拟数据
			list = new ArrayList<Object[]>();
			for (int i = 100000; i < 200000; i++) {
				// 10行数据
				Object[] data = new Object[5];
				for (int j = 0; j < 5; j++) {
					data[j] = title[j] + ":" + i + ":" + j + ",Test";
				}
				list.add(data);
			}
			exl.appendRowData(list);
			list = new ArrayList<Object[]>();
			for (int i = 200000; i < 300000; i++) {
				// 10行数据
				Object[] data = new Object[5];
				for (int j = 0; j < 5; j++) {
					data[j] = title[j] + ":" + i + ":" + j + ",Test";
				}
				list.add(data);
			}
			exl.appendRowData(list);
			list = new ArrayList<Object[]>();
			for (int i = 300000; i < 400000; i++) {
				// 10行数据
				Object[] data = new Object[5];
				for (int j = 0; j < 5; j++) {
					data[j] = title[j] + ":" + i + ":" + j + ",Test";
				}
				list.add(data);
			}
			exl.appendRowData(list);
			list = new ArrayList<Object[]>();
			for (int i = 400000; i < 500000; i++) {
				// 10行数据
				Object[] data = new Object[5];
				for (int j = 0; j < 5; j++) {
					data[j] = title[j] + ":" + i + ":" + j + ",Test";
				}
				list.add(data);
			}
			exl.appendRowData(list);
			list = new ArrayList<Object[]>();
			for (int i = 500000; i < 600000; i++) {
				// 10行数据
				Object[] data = new Object[5];
				for (int j = 0; j < 5; j++) {
					data[j] = title[j] + ":" + i + ":" + j + ",Test";
				}
				list.add(data);
			}
			exl.appendRowData(list);
			list = new ArrayList<Object[]>();
			for (int i = 600000; i < 700000; i++) {
				// 10行数据
				Object[] data = new Object[5];
				for (int j = 0; j < 5; j++) {
					data[j] = title[j] + ":" + i + ":" + j + ",Test";
				}
				list.add(data);
			}
			exl.appendRowData(list);
			//3.xml格式尾
			exl.writeEnd();
		} catch (GenericBusinessException e1) {
			//logger.error(e1);
		}
	}

	
}
