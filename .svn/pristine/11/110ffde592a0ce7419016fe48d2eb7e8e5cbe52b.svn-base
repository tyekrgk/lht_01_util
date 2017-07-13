/*
 * Copyright 2007 - 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lht.wangtong.core.utils.db.jdbcmodel;

import java.io.Serializable;

/**
 * Describes a database-table.
 * 针对JDBC DatabaseMetaData 获取 getTables() 的 RESULT 返回的结果值的封装  VO
 * @author aibozeng
 */
@SuppressWarnings("serial")
public class TableVO implements Serializable {
	
	/**
	 * 分组名称，可能是数据库名称,可能为空
	 */
	private String catalogName = "";
	
	/**
	 * catalog下的分组名称，坑内是用户名称，可能为空
	 * 不同数据库,定义不同
	 */
	private String schemaName = "";
	
	/**
     * The table-name.  CODE
     */
    private String name;
    

    /**
	 * 简称  针对name的简短注释
	 */
	private String abbr = "";
	
	/**
	 * 描述,长文字
	 */
	private String remark = "";


    public TableVO() {
		super();
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取全表名,前面有 catalog 和  schema
	 * @return
	 */
	public String getFullName(){
		StringBuffer sb = new StringBuffer();
		if(getCatalogName()!=null){
			sb.append(getCatalogName()+".");
		}
		if(getSchemaName()!=null){
			sb.append(getSchemaName()+".");
		}
		sb.append(getName());
		return sb.toString();
	}

	@Override
	public String toString() {
		return "TableVO [catalogName=" + catalogName + ", schemaName="
				+ schemaName + ", name=" + name + ", abbr=" + abbr
				+ ", remark=" + remark + "]";
	}
    
	
}

