package lht.wangtong.core.utils.vo;

import java.util.ArrayList;
import java.util.List;

import lht.wangtong.core.utils.vo.SelectItem;


/**
 * 查询条件的字段名称和值
 * @author aibozeng
 *
 */


@SuppressWarnings("serial")
public class CommonWinQC extends BaseVO {
	private  List<SelectItem>  paramList = new ArrayList<SelectItem>();

	public CommonWinQC() {
		super();
	}

	public List<SelectItem> getParamList() {
		return paramList;
	}

	public void setParamList(List<SelectItem> paramList) {
		this.paramList = paramList;
	}
	
	public int getSize(){
		return paramList.size();
	}
	
	public SelectItem getItem(int index){
		if(paramList.size()>index){
			return paramList.get(index);
		}
		return null;
	}
	
	/**
	 * 增加一个条件参数
	 * @param item
	 */
	public void addParam(SelectItem item){
		paramList.add(item);
	}
}
