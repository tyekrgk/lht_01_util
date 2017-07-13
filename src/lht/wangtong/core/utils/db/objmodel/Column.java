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

import lht.wangtong.core.utils.db.jdbcmodel.ColumnVO;

/**
 * Column of a table.
 * 
 * 在 PDM 定义时，只有一个 Comment 描述的地方，使用 #进行分割. 前部分赋值给 abbr, 后部分赋值给  remark
 * 
 * @author aibozeng
 */
@SuppressWarnings("serial")
public class Column extends ColumnVO{
	
	private Table table ;
	
   public Column(String name, String type, int length, int precision) {
        super(name,type,length,precision);
   }
	
   public Column(ColumnVO vo){
	   	setAbbr(vo.getAbbr());
	   	setCatalogName(vo.getCatalogName());
	   	setName(vo.getName());
	   	setRemark(vo.getRemark());
	   	setSchemaName(getSchemaName());
	   	setTableName(vo.getTableName());
	   	setDefValue(vo.getDefValue());
	   	setIdentityColumn(vo.isIdentityColumn());
	   	setLength(vo.getLength());
	   	setNullable(vo.isNullable());
	   	setPrecision(vo.getPrecision());
	   	setType(vo.getType());
	   	setTypeName(vo.getTypeName());
   }

   public Column(Table table , ColumnVO vo){
	   this(vo);
	   this.table = table;
  }
   
	public Column() {
		super();
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	
	
}