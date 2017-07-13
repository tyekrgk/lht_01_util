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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Relational data model by Schema
 * 
 * 一个 shcema 环境下数据模型定义
 * 包含所有的表、关系、视图、存储过程等。
 * 模型建立的人、建立日期、存储到磁盘文件的文件名等。
 * 
 * 所有对象在这里建立缓存. 最终放入 缓存管理器中。 避免每次钻取这些信息花费大量时间。
 * 
 * @author aibozeng
 */
public class SchemaModel {
	
	/**
	 * Name of the schema model.
	 */
	private String name;
	
	/**
     * Maps table-names to tables.
     * 是为了快速查找到某个 table
     */
    private Map<String, Table> tables = new HashMap<String, Table>();
    
    /**
     * Internal version number. Incremented on each modification.
     */
    public long version = 0;


    public SchemaModel(){
    }
	
    public SchemaModel(String schemaName){
    	this.name = schemaName;
    }
	
    /**
     * Gets a table by name.
     * 
     * @param name the name of the table
     * @return the table or <code>null</code> iff no table with the name exists
     */
    public Table getTable(String name) {
        return tables.get(name);
    }

	/**
	 * Gets name of the model.
	 * 
	 * @return name of the model
	 */
	public String getName() {
		return name;
	}

    /**
     * Gets all tables.
     * 
     * @return a collection of all tables
     */
    public Collection<Table> getTables() {
        return tables.values();
    }
    
    public void addTable(Table table){
    	tables.put(table.getName(),table);
    }

    /**
     * Normalizes a set of tables.
     * 
     * @param tables set of tables
     * @return set of all tables from this model for which a table with same name exists in <code>tables</code> 
     */
    public Set<Table> normalize(Set<Table> tables) {
        Set<Table> result = new HashSet<Table>();
        for (Table table: tables) {
            result.add(getTable(table.getName()));
        }
        return result;
    }

    /**
     * Gets internal version number. Incremented on each modification.
     * 
     * @return internal version number. Incremented on each modification.
     */
    public long getVersion() {
    	return version;
    }
    

}
