package lht.wangtong.core.utils.jasperReport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import lht.wangtong.core.utils.exception.GenericBusinessException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


/**
 * JasperReport 工具类
 * 
 * 根据业务数据，生成可分页打印的 html、PDF,供浏览器端进行打印       使用  iReport 开发 blankA4HengDa.jrxml  和  四个样式。
 * 
 * 传入的参数，仿 ExlHelp
 * 
 * @author aibozeng
 *
 */

public class JasperReportHelp {
	
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	//JasperReport的模板文件名,不含扩展名(.jrxml)
	//JasperReport编译模板后为二进制文件(.jasper)
	private static String templateFileName="blankA4";
	//模块xml文件和编译后的文件的存放缺省路径,真正的路径，应该由业务调用端传入
	private static String defaultPath="d:\\\\tmp\\";
	
	//报表模块里定义的样式名称,在动态构造一些数据时需要用来设置样式
	private static String phTitleStyle="phTitleStyle";//页头的标题项
	private static String phDataStyle="phDataStyle";  //页头的内容项
	private static String chTitleStyle="chTitleStyle";//列头
	private static String dataStyle="dataStyle";//列数据
	private static String titelParameterName = "reportTitleP";
	private static String htmlFooter = "</td><td width=\"50%\">&nbsp;</td></tr></table><script>window.print();</script></body></html>";

	
	/**
	 * 生成html,调用参见：main方法
	 * @param templatePath  模板文件的绝对路径
	 * @param templateFName 模板文件名
	 * @param reportTitle   报表大标题
	 * @param pageHeadColumnSize 页头表格的大列数(名称:xxxx,编码:xxx,这是两列)
	 * @param pageHeadDatas      页头表格要放置的内容
	 * @param tableHeads         内容头标题  
	 * @param tableDetails       内容数据列表
	 * @return  html文件名,不含路径
	 * @throws GenericBusinessException
	 */
	public String exportHtml(String templatePath,String templateFName,String reportTitle , int pageHeadColumnSize , List<String[]> pageHeadDatas
			,List<String> tableHeads, List<String[]> tableDetails ) throws GenericBusinessException{
		JasperDesign jasperDesign = null;
		if(templateFName==null || templateFName.length()<=0){
			templateFName = templateFileName;
		}
		if(templatePath==null || templatePath.length()<=0){
			templatePath = defaultPath;
		}
		if(pageHeadColumnSize<=0){
			pageHeadColumnSize =1;
		}
		if(tableHeads==null || tableHeads.size()<=0){
			throw new GenericBusinessException("报表的内容表格的标题头内容不能为空(tableHeads is null or tableHeads.size()<=0)");
		}
		//InputStream is = null;    				
	    try {    
	          //读取报表模板    
		      File file = new File(templatePath+templateFName+".jrxml");
		      if(!file.exists()){
		    	  return "notfound"+templatePath+templateFName+".jrxml";
		      }else{
		    	  logger.debug("begin to create "+file.getName());
		      }
		      jasperDesign = (JasperDesign) JRXmlLoader.load(file);
		      //从报表定义里读取样式,在构造报表内容时进行样式设置
		      Map styleMap = jasperDesign.getStylesMap();    
		      // page header 对应的样式    
		      JRDesignStyle thHeadStyle = (JRDesignStyle) styleMap.get(phTitleStyle);    
		      JRDesignStyle thDataStyle = (JRDesignStyle) styleMap.get(phDataStyle);  
		      // column head and detail 对应的样式    
		      JRDesignStyle tdHeadStyle = (JRDesignStyle) styleMap.get(chTitleStyle);  
		      JRDesignStyle tdDataStyle = (JRDesignStyle) styleMap.get(dataStyle);  
		      //设置导出的文件名 , jasperDesign.getName() 是模板里原来制定的,针对每次调用,累加上时间戳,避免并发冲突
		      jasperDesign.setName(jasperDesign.getName()+ new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
		   
		      //1.放置：报表标题,  以参数的方式
			  //如果iReport设计的查询SQL有参数，则在这里给出参数对应的值。  parameters.put("myDate",new Date());
			  Map parameters = new HashMap(); 
			  parameters.put(titelParameterName,reportTitle);
			  //报表标题：也可以采用动态的方式
//		      JRDesignBand titleBand = (JRDesignBand) jasperDesign.getTitle();   
//	          JRDesignStaticText titleText = new JRDesignStaticText();    
//	          titleText.setStyle(theaderStyle);    
//	          titleText.setWidth(jasperDesign.getPageWidth());    
//	          titleText.setY(0);    
//	          titleText.setX(_START_X_);    
//	          titleText.setHeight(titleBand.getHeight());    
//	          titleText.setText(title+",height="+titleBand.getHeight());    
//	          titleBand.addElement(titleText);    		      		      
			  //END 报表标题：也可以采用动态的方式

			  //2.放置:报表的页头,这里支持一个GRID表格: 多列多行
			  //x轴的起始位置 ,所有内容起始的位置   == 页边距
		      int _START_X_ = jasperDesign.getLeftMargin();
		      JRDesignBand pageHeadBand = (JRDesignBand) jasperDesign.getPageHeader();  
		      //根据内容，重新设置页头的高度(注：模板里只是一行数据的高度)
		      int pageHeadRowNum = pageHeadDatas.size() / pageHeadColumnSize;
		      if(pageHeadDatas.size() % pageHeadColumnSize>0){
		    	  pageHeadRowNum++;
		      }
		      //页头，每个数据项的高度
		      int pageHeadRowHeight = pageHeadBand.getHeight();
		      pageHeadBand.setHeight(pageHeadRowNum * pageHeadRowHeight);
		      //页头，每个数据项的宽度,th或td中一个的宽度
		      int pageHeadColumnWidth = (jasperDesign.getPageWidth()-jasperDesign.getLeftMargin()-jasperDesign.getRightMargin())/(pageHeadColumnSize*2);
		      for(int i=1;i<=pageHeadRowNum;i++){
			      for(int j=1;j<=pageHeadColumnSize;j++){
			    	  int index = (i-1)*pageHeadColumnSize+j-1;
			    	  if(index>pageHeadDatas.size()-1){
			    		  break;
			    	  }
			    	  String[] pageHeadData = pageHeadDatas.get(index);
			    	  //数组0是标题
			          JRDesignStaticText thText = new JRDesignStaticText();    
			          thText.setStyle(thHeadStyle);    
			          thText.setWidth(pageHeadColumnWidth);    
			          thText.setY((i-1)*pageHeadRowHeight);
			          //X轴的位置:第几列(这里列包含了 th,td)
			          thText.setX(_START_X_+(j-1)*pageHeadColumnWidth*2);    
			          thText.setHeight(pageHeadRowHeight);    
			          thText.setText(pageHeadData[0]);    
			          pageHeadBand.addElement(thText);
	                  //数组1是内容
			          JRDesignStaticText tdText = new JRDesignStaticText();    
			          tdText.setStyle(thDataStyle);    
			          tdText.setWidth(pageHeadColumnWidth);    
			          tdText.setY((i-1)*pageHeadRowHeight);    
			          tdText.setX(_START_X_+(j-1)*pageHeadColumnWidth*2+pageHeadColumnWidth);    
			          tdText.setHeight(pageHeadRowHeight);    
			          tdText.setText(pageHeadData[1]);    
			          pageHeadBand.addElement(tdText);				  			    	 
			      }		    	  
		      }
		      
		      
		      //3.放置:报表的内容表格的行头
		      int startX = _START_X_; // x轴的起始位置    		      
		      //一列的宽度    
		      int columnWidth = (jasperDesign.getPageWidth()-jasperDesign.getLeftMargin()-jasperDesign.getRightMargin()) / tableHeads.size();    
	          
		      JRDesignBand columnHeadBand = (JRDesignBand) jasperDesign.getColumnHeader();   
		      // 绘制内容表头    
		      for (String thTitle : tableHeads) {    
		          JRDesignStaticText staticText = new JRDesignStaticText();    
		          staticText.setStyle(tdHeadStyle);    
		          staticText.setWidth(columnWidth);    
		          staticText.setY(0);    
		          staticText.setX(startX);    
		          staticText.setHeight(columnHeadBand.getHeight());    
		          staticText.setText(thTitle);    
		          columnHeadBand.addElement(staticText);    
		          startX += columnWidth;    
		      }    
		      
		      //4.放置:报表的内容, 多行的,分页的关键
		      if(tableDetails==null || tableDetails.size()<=0){
		    	  //空报表
			      // 编译报表  : 该方式编译结果存下来,如果对同一个编译结果，只是数据来源不一样，则可以再利用 . 注意：文件名约定为jasper扩展名
			      JasperCompileManager.compileReportToFile(jasperDesign,templatePath+jasperDesign.getName()+".jasper");
				  //空数据源
				  JasperRunManager.runReportToHtmlFile(templatePath+jasperDesign.getName()+".jasper",parameters);		    	  
		      }else{
		    	  //取出第一个即可构造模板
		    	  String[] rowDatas = tableDetails.get(0);
		    	  if(rowDatas==null || rowDatas.length<=0){
		    		  throw new GenericBusinessException("报表的内容表格的内容不能为空");
		    	  }
			      //定义 Field变量,报表引擎处理时按此field去匹配数据源
			      startX = _START_X_;    
			      JRDesignBand columnDetailBand = (JRDesignBand) jasperDesign.getDetailSection().getBands()[0];
			      for (int idx = 0; idx < rowDatas.length; idx++) {
			    	 //定义字段(变量)
			        JRDesignField field = new JRDesignField();    
			        field.setName("field_" + idx);    
			        field.setValueClass(java.lang.String.class);    
			        jasperDesign.addField(field);  
			        //定义字段的显示
			        JRDesignTextField textField = new JRDesignTextField();    
			        textField.setStretchWithOverflow(true);    
			        textField.setX(startX);    
			        textField.setY(0);    
		            textField.setWidth(columnWidth);    
			        startX += columnWidth;    
			        textField.setHeight(columnDetailBand.getHeight());    
			        //textField.setPositionType(JRElement.POSITION_TYPE_FLOAT);    
			        textField.setStyle(tdDataStyle);    
			        textField.setBlankWhenNull(true);    
			        JRDesignExpression expression = new JRDesignExpression();    
			        expression.setValueClass(java.lang.String.class);    
			        expression.setText("$F{field_" + idx + "}");  //必须与   定义字段(变量) 的名称一致
			        textField.setExpression(expression);    
			        columnDetailBand.addElement(textField);    
			      }    		
			      //5.增加一个报表脚,输出 javascript --不管用
//			      JRDesignBand lastPageFooterBand = (JRDesignBand)jasperDesign.getSummary();
//			      JRDesignStaticText scriptText = new JRDesignStaticText();    
//			      scriptText.setWidth(200);    
//			      scriptText.setY(0);    
//			      scriptText.setX(_START_X_);    
//			      scriptText.setHeight(1);    
//			      scriptText.setText("<script>window.print();</script>");    
//			      lastPageFooterBand.addElement(scriptText);    
			      
			    		  
			      // 编译报表    
			      JasperCompileManager.compileReportToFile(jasperDesign,templatePath+jasperDesign.getName()+".jasper");
				  //数据来源于自己编写的 Bean
			      ReportVO vos = new ReportVO();
			      vos.setDatas(tableDetails);
				  //JasperRunManager.runReportToHtmlFile(templatePath+jasperDesign.getName()+".jasper",parameters, vos);
			      // 编译报表  : 该方式编译结果自动保存,返回JasperReport对象
			      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, vos);
				  JRHtmlExporter exporter = new JRHtmlExporter();					
				  exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				  exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, templatePath+jasperDesign.getName() + ".html");
				  //添加script打印
				  exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER,htmlFooter);
				  exporter.exportReport();
			  }
			  logger.debug("create html at "+templatePath+jasperDesign.getName()+".html");
		    } catch (Exception e) {    
		      logger.error(e);    
		    }    
		
		return jasperDesign.getName()+".html";
		
	}
	
	
	public class ReportVO implements JRDataSource {
		private List<String[]>  datas = new ArrayList<String[]>();
		public ReportVO() {			
		}
		
		public void setDatas(List<String[]>  datas ){
			this.datas = datas;
		}

		/**
		 * 访问游标。必须从-1开始
		 */
		private int index = -1;
		
		public void start(){
			index = -1;
		}
		
		
		@Override
		public boolean next() throws JRException {
			index++;
			return (index < datas.size());
		}

		/**
		 * 取某列的值（jasperReportEnginee再调用数据时，先调用next()进行移行的）
		 * 返回数据类型必须匹配
		 */
		@Override
		public Object getFieldValue(JRField jrField) throws JRException {
	        String[] data = datas.get(index);
			int index = jrField.getName().indexOf("field_");
			//注：变量名必须是field_开头，加数字. 是 iReport设计时，制定的  TextField 的名称
			int no = 0;
			if(jrField.getName().length()<7){
				return "define error";
			}else{
				try{
				   no = Integer.parseInt(jrField.getName().substring(6));
				}catch(NumberFormatException nfe){
					logger.error(nfe);
				}
			}
			return data[no];
		}

	}
	
	
	public class MyScriptlet extends  JRDefaultScriptlet{
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String templatePath="f:/template/";
		String templateFName="blankA4HengDa";
		String reportTitle="测试报表";
		int pageHeadColumnSize = 4;
		List<String[]> pageHeadDatas = new ArrayList<String[]>();
		pageHeadDatas.add(new String[]{"编码","SO20010909"});
		pageHeadDatas.add(new String[]{"名称","鼎捷销售单"});
		pageHeadDatas.add(new String[]{"下单日期","2012-09-09"});
		pageHeadDatas.add(new String[]{"出库日期","2012-09-09"});
		pageHeadDatas.add(new String[]{"出库日期","2012-09-09"});
		pageHeadDatas.add(new String[]{"出库日期","2012-09-09"});
		pageHeadDatas.add(new String[]{"出库日期","2012-09-09"});
		pageHeadDatas.add(new String[]{"出库日期","2012-09-09"});
		List<String> tableHeads = new ArrayList<String>();
		tableHeads.add("产品");
		tableHeads.add("产品线");
		tableHeads.add("数量");
		tableHeads.add("已发数量");
		tableHeads.add("库房");
		tableHeads.add("库房");
		tableHeads.add("库房");
		tableHeads.add("库房");
		List<String[]> tableDetails = new ArrayList<String[]>();
		for(int i=0;i<100;i++){
			//tableHeads里有几个元素,这里String[]就有几个元素
			tableDetails.add(new String[]{"产品"+i,"iphone4s天生一副富贵相","100","20","上海","上海","上海","上海"});			
		}
		try {
			new JasperReportHelp().exportHtml(templatePath,templateFName,reportTitle,pageHeadColumnSize,pageHeadDatas,tableHeads,tableDetails);
		} catch (GenericBusinessException e) {
		//	logger.error(e);
		}
	}

}
