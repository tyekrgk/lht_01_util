package lht.wangtong.core.utils.vo;

import java.io.Serializable;

/**
 * 所有查询的vo均需要继承该类
 * @author lyl
 *
 */
@SuppressWarnings("serial")
public abstract class BaseVO implements Serializable {

	private String orderBy;
	private String groupBy;
	
	private String [] itemNames;
	private String [] itemConditions;
	private String [] itemValues;
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public String[] getItemNames() {
		return itemNames;
	}
	public void setItemNames(String[] itemNames) {
		this.itemNames = itemNames;
	}
	public String[] getItemConditions() {
		return itemConditions;
	}
	public void setItemConditions(String[] itemConditions) {
		this.itemConditions = itemConditions;
	}
	public String[] getItemValues() {
		return itemValues;
	}
	public void setItemValues(String[] itemValues) {
		this.itemValues = itemValues;
	}
	
	
}
