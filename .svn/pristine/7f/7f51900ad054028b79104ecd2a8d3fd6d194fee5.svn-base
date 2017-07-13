package lht.wangtong.core.utils.exception;

/**
 * BaseException 异常类的基础类，所有的异常类都从此类继承<br>
 * 
 * @author <a href="mailto:senton1101@gmail.com">TianXiangdong</a> at 2010-10-4 上午09:56:31
 * @author lyl
 * @version 1.0
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 643140390071869253L;
	
	//错误Key，统一在资源文件 ErrorMessage.java 里编排
	private String errorCode ="0";                                  
	//错误基本描述，面向操作人员可理解，必须为中文 , 例子：箱号10018已锁定，无法开户
	private String errorDescription = ""; 
	//错误原因和详细信息，一般从具体的异常类中取出，如 jdbc execption , hibernate , spring 等框架的异常信息。可能为英文。
	//例子：drawer id=9 not found 
	private String errorDetail = ""; 

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseException(String errorCode,String errorDescription,String errorDetail){
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.errorDetail = errorDetail;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

}
