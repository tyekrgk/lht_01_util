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

import java.sql.Types;

/**
 * Enumeration of DBMS'es.
 * 
 * @author aibozeng
 */
public enum SqlDataTypeEnum {
	
	BIGINT(Types.BIGINT,"BIGINT",""),
	BINARY(Types.BINARY,"BINARY",""),
	BIT(Types.BIT,"BIT",""),
	CHAR(Types.CHAR,"CHAR",""),
	DATE(Types.DATE,"DATE",""),
	DECIMAL(Types.DECIMAL,"DECIMAL",""),
	DOUBLE(Types.DOUBLE,"DOUBLE",""),
	FLOAT(Types.FLOAT,"FLOAT",""),
	INTEGER(Types.INTEGER,"INTEGER",""),
	NUMERIC(Types.NUMERIC,"NUMERIC",""),
	TIME(Types.TIME,"TIME",""),
	TIMESTAMP(Types.TIMESTAMP,"TIMESTAMP",""),
	VARCHAR(Types.VARCHAR,"VARCHAR",""),
	SMALLINT(Types.SMALLINT,"SMALLINT",""),
	CLOB(Types.CLOB,"CLOB",""),
	BLOB(Types.BLOB,"BLOB",""),
	NULL(0,"","");
	
	
	private int    id = 0;
	private String code ="";
	private String name = "";

	private SqlDataTypeEnum(int id , String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getCodeById(int id){
		for(SqlDataTypeEnum one : SqlDataTypeEnum.values()){
			if(one.getId() == id ){
				return one.getCode();
			}
		}
		return null;
	}
}
