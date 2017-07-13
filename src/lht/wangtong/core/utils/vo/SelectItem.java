package lht.wangtong.core.utils.vo;

import java.io.Serializable;

/**
 * 用于键值对的VO，譬如下拉列表框 <br>
 * 
 * @author <a href="mailto:senton1101@gmail.com">TianXiangdong</a> at 2010-11-20 下午04:25:20
 * @version 1.0
 */
public class SelectItem implements Serializable {

	private static final long serialVersionUID = 4798687513847550747L;

	private String value; // 值

	private String label;  //显示名

	private String code ; // input name="xxxxx"
	
	private String userDefined;//add by weizy 用于对select的拓展
	
	public SelectItem() {}
	
	public SelectItem(String value) {
		super();
		this.value = value;
	}

	public SelectItem(String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public SelectItem(String value, String label, String code,
			String userDefined) {
		super();
		this.value = value;
		this.label = label;
		this.code = code;
		this.userDefined = userDefined;
	}

	public SelectItem(String value, String label, String code) {
		super();
		this.value = value;
		this.label = label;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(String userDefined) {
		this.userDefined = userDefined;
	}

}
