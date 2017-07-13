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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Primary-key of a {@link Table}.
 * 
 * @author aibozeng
 */
@SuppressWarnings("serial")
public class PrimaryKey implements Serializable{
    	
    /**
     * The primary-key columns.  可能是符合组键
     * 在我们的框架里，暂没有组合主键。都是  ID 作为主键
     */
    private List<Column> columns = new ArrayList<Column>();
    
    
    public PrimaryKey(){    	
    }
    
    /**
     * Constructor.
     * 
     * @param primaryKeyColumns the primary-key columns
     */
    PrimaryKey(List<Column> columns) {
        this.columns = columns;
    }
    
    /**
     * Gets the primary-key columns.
     * 
     * @return the primary-key columns
     */
    public List<Column> getColumns() {
        return columns;
    }

    public void addColumn(Column col){
    	getColumns().add(col);
    }
    
    public Column getOneKey(){
    	if(columns!=null && columns.size()>0){
    		return columns.get(0);
    	}
    	return null;
    }
    
    /**
     * Creates a comma-separated list of column names.
     * 
     * @param columnPrefix an optional prefix for each PK-column
     */
    public String columnList(String prefix) {
        String list = "";
        for (Column column: getColumns()) {
            if (list.length() > 0) {
                list += ", ";
            }
            if (prefix != null) {
                list += prefix;
            }
            list += column.getName();
        }
        return list;
    }
    
    @Override
    public String toString(){
    	 String ret = "";
    	 for (Column column: getColumns()) {
    		 ret = ret + column.getName()+"  ";
         }
    	 return ret;
    }
    
}
