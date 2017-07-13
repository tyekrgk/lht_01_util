package lht.wangtong.core.utils.vo;

import java.io.Serializable;

/**
 * 封装返回的结果，E表示的我们需要返回的内容，code和msg分别为需要返回的消息
 * @author lyl
 *
 * @param <E>
 */
public class ResultVO<E> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4898118641750618703L;
	private E item;
	private String code;
	private String msg;
	
	public ResultVO(E item, String code, String msg) {
		super();
		this.item = item;
		this.code = code;
		this.msg = msg;
	}
	public E getItem() {
		return item;
	}
	public void setItem(E item) {
		this.item = item;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
