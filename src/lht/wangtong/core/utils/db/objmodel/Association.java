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

import lht.wangtong.core.utils.db.jdbcmodel.AssociationVO;


/**
 * An association between database-tables.
 * 
 * 描述两个表之间的关系
 * 方向：A.bId 引用 B.id 则：A为source , B为destination
 * 
 * @author aibozeng
 */
@SuppressWarnings("serial")
public class Association extends AssociationVO{

    /**
     * The source table.  FK
     */
    private Table fkTable;
    
    private Column fkColumn;

    /**
     * The destination table.  PK
     */
    private Table pkTable;

    private Column pkColumn;
    
    /**
     * The cardinality. n:n 关系 , 对于我们的框架来说，一般都是 N:1, 仅当 fk 字段也是 唯一性时，才表现为  1:1
     * FK 与  PK 的 关系
     */
    private CardinalityEnum cardinality = CardinalityEnum.MANY_TO_ONE;
    
 

    /**
     * The name of the association.
     * 此次关系的命名  fk_empl_user_id_refe_user_id
     */
    private String name;

    
    

    public Association() {
		super();
	}
    
    public Association(Table fkTable,Column fkColumn,Table pkTable,Column pkColumn) {
		super();
		setFkTable(fkTable);
		setFkColumn(fkColumn);
		setPkTable(pkTable);
		setPkColumn(pkColumn);		
	}    

	/**
     * Gets the cardinality.
     * 
     * @return the cardinality. <code>null</code> if cardinality is not known.
     */
    public CardinalityEnum getCardinality() {
        return cardinality;
    }
    
    /**
     * Sets the name of the association.
     * 
     * @param name the name of the association
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the association.
     * 
     * @return the name of the association
     */
    public String getName() {
        return name;
    }

	public Table getFkTable() {
		return fkTable;
	}

	public void setFkTable(Table fkTable) {
		this.fkTable = fkTable;
		setFkTableName(fkTable.getName());
	}

	public Column getFkColumn() {
		return fkColumn;
	}

	public void setFkColumn(Column fkColumn) {
		this.fkColumn = fkColumn;
		setFkColumnName(fkColumn.getName());
	}

	public Table getPkTable() {
		return pkTable;
	}

	public void setPkTable(Table pkTable) {
		this.pkTable = pkTable;
		setPkTableName(pkTable.getName());
	}

	public Column getPkColumn() {
		return pkColumn;
	}

	public void setPkColumn(Column pkColumn) {
		this.pkColumn = pkColumn;
		setPkColumnName(pkColumn.getName());
	}
	
	public String toString(){
		return "FK_TABLE="+getFkTable().getName()+",FK_COLUMN="+getFkColumn().getName()
			+ ",PK_TABLE="+getPkTable().getName()+",PK_COLUMN="+getPkColumn().getName();
	}
	
}
