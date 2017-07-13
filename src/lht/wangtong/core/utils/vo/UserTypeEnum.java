package lht.wangtong.core.utils.vo;

public enum UserTypeEnum {

	 Admin("1","超级管理员"),
	 CompAdmin("2","公司管理员"),
	 Employee("3","公司员工"),
	 Member("4","终端用户"),
	 //用于系统对接  EDI、ERP、B2C、手机终端、测试帐户等,不能关联具体员工
	 virtualUser("100","虚拟用户");
	
	private String code ="";
	private String name = "";

	private UserTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
