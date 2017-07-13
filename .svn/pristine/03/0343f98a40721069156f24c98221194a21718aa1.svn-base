package lht.wangtong.core.utils.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Base Exception class that can be thrown by the business layer.
 * 
 * 业务异常基类。必须要捕获。
 * 
 * 能传递到 GWT 客户端  
 * 
 * @author  aibo zeng
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GenericBusinessException extends BaseException   {
	
	public GenericBusinessException(){	
		super();
	}
	
	/**
	 * 不要轻易使用
	 * @param cause
	 */
	public GenericBusinessException(Throwable cause){	
		super(cause);
	}
	
	public GenericBusinessException(String message){	
		super(message);
	}
	
	public GenericBusinessException(String message,Throwable cause){	
		super(message,cause);
	}
	
	public GenericBusinessException(Throwable cause,String errorCode){	
		super(cause);
		this.setErrorCode(errorCode);
	}
	
	public void printStackTrace () {
		System.err.println(this.getErrorCode());
		System.err.println(this.getMessage());
		super.printStackTrace();
	}
	
	public void printStackTrace (PrintStream s) {
		s.println(this.getErrorCode());
		s.println(this.getMessage());
		super.printStackTrace(s);
	}
	
	public void printStackTrace (PrintWriter s) {
		s.println(this.getErrorCode());
		s.println(this.getMessage());
		super.printStackTrace(s);
	}
    
	/**
	 *  业务异常基类
	 * @param errorCode 异常码
	 * @param message 错误基本描述，面向操作人员可理解
	 */
	public GenericBusinessException(String errorCode,String message){	
		super(message);
		setErrorCode(errorCode);
	}

}
