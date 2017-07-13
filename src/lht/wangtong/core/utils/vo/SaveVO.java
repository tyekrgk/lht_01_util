package lht.wangtong.core.utils.vo;

import lht.wangtong.core.utils.vo.BaseVO;

public class SaveVO extends BaseVO {
	private String code; // 年月日时分秒
	private String name; // 今后允许用户对这个查询条件进行命名。
	private BaseVO vo; // 实际保存Vo

	public SaveVO() {

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

	public BaseVO getVo() {
		return vo;
	}

	public void setVo(BaseVO vo) {
		this.vo = vo;
	}

}
