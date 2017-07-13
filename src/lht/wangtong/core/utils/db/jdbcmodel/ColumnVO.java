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
 * Column of a table.
 * 针对JDBC DatabaseMetaData getColumns() 的 RESULT 返回的结果值的封装  VO
 * 
 * 在 PDM 定义时，只有一个 Comment 描述的地方，使用 #进行分割. 前部分赋值给 abbr, 后部分赋值给  remark
 * 
 * @author aibozeng
 */
@SuppressWarnings("serial")
public class ColumnVO implements Serializable{
	
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
	 * 字段归属的表名
	 */
	private String tableName;
	

	/**
     * The name. 
     * 例如：ID
     */
	private String name;

	/**
	 * 简称  针对name的简短注释
	 */
	private String abbr = "";
	
	private String remark = "";
    
    /**
     * The type.
     * @see SqlUtil.SQL_TYPE
     * @see java.sql.Types
     */
	private String type;

	private String typeName;
    
    /**
     * The length (for VARCHAR, DECIMAL, ...) or <code>0</code> if type-length is not variable.
     */
	private int length;
    
    /**
     * The precision (for DECIMAL, NUMBER ...) or <code>-1</code> if precision is not variable.
     */
	private int precision;
    
    /**
     * 唯一性
     * <code>true</code> if column is identity column.
     */
	private boolean isIdentityColumn = false;
	
	/**
	 * 是否非空 NULLABLE
	 */
	private boolean nullable = true;
	
	/**
	 * 缺省值  COLUMN_DEF
	 */
	private String defValue = "";
	
	/**
	 * 枚举下拉的 key
	 */
	private String commonCdKey="";
    
    public ColumnVO(){    	
    }
    
    public ColumnVO(String name, String type, int length, int precision) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.precision = precision;
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


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public boolean isIdentityColumn() {
		return isIdentityColumn;
	}

	public void setIdentityColumn(boolean isIdentityColumn) {
		this.isIdentityColumn = isIdentityColumn;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getLength() {
		return length;
	}

	public int getPrecision() {
		return precision;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	
	
	public String getCommonCdKey() {
		return commonCdKey;
	}

	public void setCommonCdKey(String commonCdKey) {
		this.commonCdKey = commonCdKey;
	}

	@Override
	public String toString() {
		return "ColumnVO [catalogName=" + catalogName + ", schemaName="
				+ schemaName + ", tableName=" + tableName + ", name=" + name
				+ ", abbr=" + abbr + ", remark=" + remark + ", type=" + type
				+ ", typeName=" + typeName + ", length=" + length
				+ ", precision=" + precision + ", isIdentityColumn="
				+ isIdentityColumn + ", nullable=" + nullable + ", defValue="
				+ defValue + "]";
	}

	
}