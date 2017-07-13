package lht.wangtong.core.utils.vo;

/**
 * 声明有多个子系统   枚举类
 * 子系统的编号、简名
 * @see DocV1 / 02开发计划 / 营销系统-计划及相关要求.xls
 * @author aibozeng
 *
 */

public enum ApplicationEnum {
	
	//注：ID 不能改,数据库已经在使用
	
	Marketing(  2,"B2000","营销系统",""),
	Platform(   3,"A3000","系统平台",""),
	SupplyChain(4,"C4000","供应链系统",""),
	B2C(        5,"D5000","B2C系统",""),
	B2B_SQS(    6,"E6000","B2B首秦系统",""),
	Dealer(     7,"F7000","客户自助服务系统",""),
	Logistics(  8,"G8000","物流自助服务系统",""),
	Vender(     9,"H9000","供应商自助服务系统",""),
	Mall(       10,"I10000","商城系统",""),
	Member(     11,"J11000","会员服务系统",""),
    
	NULL(0,"","","");
	
	private long   id = 0;
	private String code = "";
	private String name = "";
	private String desc = "";
	
	private ApplicationEnum(long id , String code, String name, String desc) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.desc = desc;
	}

	public static String getName(String code) {
		for(ApplicationEnum each : ApplicationEnum.values()) {
			if(each.getCode().equals(code) )
				return each.getName();
		}
		return "";
	}

	public static String getDesc(String code) {
		for(ApplicationEnum each : ApplicationEnum.values()) {
			if(each.getCode().equals(code) )
				return each.getDesc();
		}
		return "";
	}
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
