package lht.wangtong.core.utils.fullseach;

public enum ModeCode {
	
	PRODUCT("product", "商品模块编码"),
	Article("article", "文章模块编码");
	
	private String code = "";
	private String desc = "";
	
	private ModeCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
