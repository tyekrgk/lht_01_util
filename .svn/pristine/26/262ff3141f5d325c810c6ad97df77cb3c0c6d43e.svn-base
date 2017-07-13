package lht.wangtong.core.utils.exception;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;



/**
 * 错误处理工具：
 * 1. 根据 错误Key，从Properties文件里 找出错误提示信息
 * 2. 转换错误对象（取出详细的堆栈错误信息）（尤其是后台与前台使用的异常）
 * @author aibo zeng
 *
 */
public class ExceptionHelper {

    private ExceptionHelper(){    	
    }
    

    public static GenericBusinessException transfer(SQLException sqlException){
    	GenericBusinessException gbe = new GenericBusinessException();
    	gbe.setErrorDetail(getStackTraceAsString(sqlException));
    	return gbe;
    }
    
    
    /**
     * 将 logger.error(e) 到控制台的信息，保存为一个 String 
     * @param e
     * @return
     */
    public static String getStackTraceAsString(Throwable e){
           //StringWriter将包含堆栈信息
           StringWriter stringWriter = new StringWriter();
           //必须将StringWriter封装成PrintWriter对象，以满足logger.errorTrace的要求
           PrintWriter printWriter = new PrintWriter(stringWriter);
           //获取堆栈信息
           e.printStackTrace(printWriter);
           //转换成String，并返回该String
           return stringWriter.getBuffer().toString();
    }
    
    
    
}
