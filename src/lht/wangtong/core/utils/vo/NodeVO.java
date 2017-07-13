package lht.wangtong.core.utils.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用的树节点基本信息的VO
 * @author zengyubo
 *
 */

@SuppressWarnings("serial")
public class NodeVO implements Serializable{

	private long id = 0;
	private String code = "";
	private String name = "";
	private NodeVO parent = null;
	private int level = 0; //表示根,第一层
	private String icon = "";
	private List<NodeVO> children = new ArrayList<NodeVO>();
	
	public NodeVO(long id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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
	public NodeVO getParent() {
		return parent;
	}
	public void setParent(NodeVO parent) {
		this.parent = parent;
	}
	public List<NodeVO> getChildren() {
		return children;
	}
	public void setChildren(List<NodeVO> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "NodeVO [id=" + id + ", code=" + code + ", name=" + name
				+ ", level=" + level+ ", icon=" + icon;
	}
	
	
}
