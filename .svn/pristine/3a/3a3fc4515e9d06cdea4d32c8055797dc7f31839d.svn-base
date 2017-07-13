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
 * An association between database-tables.
 * 
 * 描述两个表之间的关系
 * 方向：A.bId 引用 B.id 则：A为 fk(foreign key), B为 pk(primary key)
 * 
 * jdbc  DatabaseMetaData getExportedKeys(), getImportedKeys() 的 ResultSet 的 简单封装 VO
 * 
 * @author aibozeng
 */
@SuppressWarnings("serial")
public class AssociationVO implements Serializable {

 	/**
     * The source(foreign) Catalog
     */
	private String fkCatalogName;


 	/**
     * The source(foreign) Schema
     */
    private String fkSchemaName;

    
 	/**
     * The source(foreign) table.
     */
    private String fkTableName;

 	/**
     * The source(foreign) Column.
     */
    private String fkColumnName;

    /**
     * The destination(primary) Catalog.
     */
    private String  pkCatalogName;

    /**
     * The destination(primary) Schema.
     */
    private String  pkSchemaName;
    
    /**
     * The destination(primary) table.
     */
    private String  pkTableName;

    /**
     * The destination(primary) Column.
     */
    private String  pkColumnName;
    
    /**
     * 第几个,一般不用。
     * 经测试，如果一个表有两个字段都外键关联了另一个表的主键时，这个 keySeq总是1
     */
    private short keySeq = 1;

    /**
     * FK_NAME
     * 自定义名称
     */
    private String fkName;
    
    /**
     * PK_NAME
     * 自定义名称
     */
    private String pkName;
    
    
	public AssociationVO() {
		super();
	}


	public String getFkCatalogName() {
		return fkCatalogName;
	}


	public void setFkCatalogName(String fkCatalogName) {
		this.fkCatalogName = fkCatalogName;
	}


	public String getFkSchemaName() {
		return fkSchemaName;
	}


	public void setFkSchemaName(String fkSchemaName) {
		this.fkSchemaName = fkSchemaName;
	}


	public String getFkTableName() {
		return fkTableName;
	}


	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}


	public String getFkColumnName() {
		return fkColumnName;
	}


	public void setFkColumnName(String fkColumnName) {
		this.fkColumnName = fkColumnName;
	}


	public String getPkCatalogName() {
		return pkCatalogName;
	}


	public void setPkCatalogName(String pkCatalogName) {
		this.pkCatalogName = pkCatalogName;
	}


	public String getPkSchemaName() {
		return pkSchemaName;
	}


	public void setPkSchemaName(String pkSchemaName) {
		this.pkSchemaName = pkSchemaName;
	}


	public String getPkTableName() {
		return pkTableName;
	}


	public void setPkTableName(String pkTableName) {
		this.pkTableName = pkTableName;
	}


	public String getPkColumnName() {
		return pkColumnName;
	}


	public void setPkColumnName(String pkColumnName) {
		this.pkColumnName = pkColumnName;
	}


	public short getKeySeq() {
		return keySeq;
	}


	public void setKeySeq(short keySeq) {
		this.keySeq = keySeq;
	}
    
	@Override
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("pkCatalog="+getPkCatalogName());
    	sb.append(",pkSachema="+getPkSchemaName());
    	sb.append(",pkTableName="+getPkTableName());
    	sb.append(",pkColumnName="+getPkColumnName());
    	sb.append(",fkCatalog="+getFkCatalogName());
    	sb.append(",fkSachema="+getFkSchemaName());
    	sb.append(",fkTableName="+getFkTableName());
    	sb.append(",fkColumnName="+getFkColumnName());
    	sb.append(",keySeq="+getKeySeq());
    	sb.append(",fkName="+getFkName());
    	sb.append(",pkName="+getPkName());
    	return sb.toString();
    }


	public String getFkName() {
		return fkName;
	}


	public void setFkName(String fkName) {
		this.fkName = fkName;
	}


	public String getPkName() {
		return pkName;
	}


	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
    
    
 }
