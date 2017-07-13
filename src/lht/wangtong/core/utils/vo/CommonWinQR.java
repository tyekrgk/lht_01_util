package lht.wangtong.core.utils.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询结果的值
 * @author aibozeng
 *
 */
@SuppressWarnings("serial")
public class CommonWinQR extends BaseVO {
	//标题
	private List<String> labelList = new ArrayList<String>();
	
	//数据
	private  List<List<SelectItem>>  rowList = new ArrayList<List<SelectItem>>();

	public CommonWinQR() {
		super();
	}

	
	public int getRowSize(){
		return rowList.size();
	}
	
	public int getColSize(){
		List<SelectItem> colList =  rowList.get(0);
		if(colList==null){
			return 0;
		}
		return colList.size();
	}
	
	public void addRow(List<SelectItem> row ){
		rowList.add(row);
	}

	/**
	 * 增加列表头的 名称
	 * @param label
	 */
	public void addLabel(String label){
		labelList.add(label);
	}

	public List<String> getLabelList() {
		return labelList;
	}


	public void setLabelList(List<String> labelList) {
		this.labelList = labelList;
	}


	public List<List<SelectItem>> getRowList() {
		return rowList;
	}


	public void setRowList(List<List<SelectItem>> rowList) {
		this.rowList = rowList;
	}
	
	
}
