package lht.wangtong.core.utils.engine;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class TestVO implements TestInterface{
    private List<String> sqls = new ArrayList<String>();



	public List<String> getSqls() {
		return sqls;
	}



	public void setSqls(List<String> sqls) {
		this.sqls = sqls;
	}



	@Override
	public void addSql(String sql) {
		sqls.add(sql);
	}
	
	
}
