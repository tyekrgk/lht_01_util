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
package lht.wangtong.core.utils.db.objmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lht.wangtong.core.utils.db.jdbcmodel.TableVO;

/**
 * Describes a database-table.
 * 
 * 有对象之间的关系（表、主键、字段列、外键等）
 * 
 * @author aibozeng
 */
@SuppressWarnings("serial")
public class Table extends TableVO implements Comparable<Table> {
    
    /**
     * The primary-key of the table.
     */
    private PrimaryKey primaryKey = new PrimaryKey();
    
    /**
     * List of table columns.
     */
    private List<Column> columns = new ArrayList<Column>();
    
    /**
     * Associations to other tables.
     * 本表作为 FK, 有多个字段关联了其他表的主键
     */
    private List<Association> importKeys = new ArrayList<Association>();
    
    /**
     * Associations to other tables.
     * 本表作为 PK, 被多个外面的表的字段关联了
     */
    private List<Association> exportKeys = new ArrayList<Association>();
    
    
    //TODO 唯一性约束列表
    
    
    public Table(String tableName){
    	super.setName(tableName);
    }
 
    public Table(TableVO vo){
    	setAbbr(vo.getAbbr());
    	setCatalogName(vo.getCatalogName());
    	setName(vo.getName());
    	setRemark(vo.getRemark());
    	setSchemaName(vo.getSchemaName());    	
    }
    
    public Table(String tableName,PrimaryKey primaryKey) {
		super();
		setName(tableName);
		this.primaryKey = primaryKey;
	}



	/**
     * Sets columns.
     * 
     * @param columns list of table columns
     */
    public void setColumns(List<Column> columns) {
    	this.columns = columns;
    }

    /**
     * Gets columns.
     * 
     * @return list of table columns
     */
    public List<Column>getColumns() {
    	if (columns == null) {
    		return Collections.emptyList();
    	}
    	return columns;
    }
    
    /**
     * 表对象，放入一个新的字段对象
     * @param col
     */
    public void addColumn(Column col){
    	getColumns().add(col);
    }
    
    /**
     * 根据字段名，找出字段对象
     * @param columnName
     * @return
     */
    public Column findColumn(String columnName){
    	for(Column col : getColumns()){
    		if(col.getName().equals(columnName)){
    			return col;
    		}
    	}
    	return null;
    }

    /**
     * Compares tables.
     */
    public boolean equals(Object other) {
        if (other instanceof Table) {
            return getFullName().equals(((Table) other).getFullName());
        }
        return false;
    }

    /**
     * The hash-code.
     */
    public int hashCode() {
        return getFullName().hashCode();
    }
    
  

    public int compareTo(Table o) {
        return getFullName().compareTo(o.getFullName());
    }
    
   
	/**
	 * Gets mapped schema name of table.
	 * 
	 * @param defaultSchema the default schema to return if table name is unqualified
	 * @return schema name
	 */
	public String getSchema(String defaultSchema) {
		if (getSchemaName()!=null) {
			return getSchemaName();
		}
		return defaultSchema;
	}



	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}



	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}



	public List<Association> getImportKeys() {
		return importKeys;
	}



	public void setImportKeys(List<Association> importKeys) {
		this.importKeys = importKeys;
	}



	public List<Association> getExportKeys() {
		return exportKeys;
	}



	public void setExportKeys(List<Association> exportKeys) {
		this.exportKeys = exportKeys;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Table ["+super.toString()+"]\n");
		sb.append("--PrimaryKey="+primaryKey.toString()+"\n");
		sb.append("--Columns:\n");
		for(Column col: getColumns()){
			sb.append("--"+col.toString()+"\n");			
		}
		sb.append("--ImportKeys:\n");
		for(Association assImp: getImportKeys()){
			sb.append("--"+assImp.toString()+"\n");			
		}
		sb.append("--ExportKeys:\n");
		for(Association assExp: getExportKeys()){
			sb.append("--"+assExp.toString()+"\n");			
		}
		return  sb.toString();
	}
	
	
}

