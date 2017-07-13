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
package lht.wangtong.core.utils.db.service;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import lht.wangtong.core.utils.db.jdbcmodel.AssociationVO;
import lht.wangtong.core.utils.db.jdbcmodel.ColumnVO;
import lht.wangtong.core.utils.db.jdbcmodel.DbTypeEnum;
import lht.wangtong.core.utils.db.jdbcmodel.TableVO;
import lht.wangtong.core.utils.db.objmodel.Column;
import lht.wangtong.core.utils.db.objmodel.SchemaModel;
import lht.wangtong.core.utils.db.objmodel.Table;

/**
 * Finds model-elements (tables and associations).
 * 接口
 * @author aibozeng
 */
public interface ModelElementFinder {
	
	/**
	 * 找出数据库类型
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public DbTypeEnum findDbType(Connection connection)  throws Exception;
	
	/**
	 * 获取所有 schema of DB
     * @param userName schema with this name may be empty , 例如：null
	 * @return
	 * @throws Exception
	 */
	public List<String> findAllSchema(Connection connection, String userName)  throws Exception;
	
	/**
	 * 获取当前连接缺省的 schema 
	 * 对于oracle,是当前连接的用户
	 * 对于其他数据库,是可以指定的
	 * @param connection
	 * @param userName   schema with this name may be empty
	 * @return
	 * @throws Exception
	 */
	public String findDefaultSchema(Connection connection, String userName) throws Exception; 
	
	/**
	 * 找某个表有哪些字段关联了另一个表的主键。
	 * 场景：下拉或开窗选择
	 * @param session
	 * @param catalog
	 * @param schema
	 * @param table   T_SALES_ORGA(fkTableName)  找出  T_SALES_ORGA.company_id 这个字段引入了 T_COMPANY(pkTableName).id 主键 . 
	 * @throws Exception
	 */
	public List<AssociationVO> findImportedKeys(Connection connection,String catalog, String schema,String table)  throws Exception;

	/**
	 * 找出某个表的主键，被哪些表和字段做为外键引用了
	 * 这里只是一级,没有继续深挖
	 * @param session
	 * @param catalog
	 * @param schema
	 * @param table  T_SALES_ORGA(pkTableName)  找出  T_SALES_ORGA这个主键 id 被哪些表引用了。如： T_COMPANY(fkTableName).sales_orga_id 
	 * @throws Exception
	 */
	public List<AssociationVO> findExportedKeys(Connection connection,String catalog, String schema,String table)  throws Exception;
	
	
	/**
	 * 找出表的简单信息（表名和注释）
	 * @param connection
	 * @param schemaPattern
	 * @param tableNamePattern
	 * @return
	 * @throws Exception
	 */
	public List<TableVO> findTableNames(Connection connection,String schemaPattern,String tableNamePattern) throws Exception ;

	/**
	 * 找出字段定义
	 * @param connection
	 * @param schemaName  用户名或模式名, oracle是用户名, postgre 是 public
	 * @param tableName   表名      例如： T% , T_USER 
	 * @param columnNamePattern  字段名    例如  % , ID , name%
	 * @return
	 * @throws Exception
	 */
	public List<ColumnVO> findColumnNames(Connection connection,String schemaName , String tableName,String columnNamePattern) throws Exception;

	/**
	 * 找出关键字的字段
	 * @param connection
	 * @param schemaName
	 * @param tableNamePattern
	 * @return
	 * @throws Exception
	 */
	public List<ColumnVO> findPrimaryKeyNames(Connection connection,String schemaPattern , String tableNamePattern) throws Exception;

	////////////////////// 以上是返回简单JDBC模型(VO)的接口
 
	
	//////////////////// 以下是返回对象模型的接口, 需要构造复杂的对象关系的
	/**
     * 构造表对象  a set of {@link Table}s. 不包含表的字段、关系、唯一性对象等。
     * 
     * @param session the statement executor for executing SQL-statements  oracle的用户名 , "%" 代表所有
     * @return a set of {@link Table}
     * 
     * @throws Exception on each error
     */
	public List<Table> buildTables(Connection connection,String schemaPattern,String tableNamePattern) throws Exception;
 
    /**
     * 构造字段对象    the {@link Column}s of a given {@link Table}.
     * 
     * @param table the table
     * @param session the statement executor for executing SQL-statements 
     * 
     * @throws Exception on each error
     */
    public List<Column> buildColumns(Connection connection,Table table) throws Exception;


    /**
     * 构造完整的表的对象关系。包含：列、关键字、外键等。
     * @param connection
     * @param schemaPattern
     * 不能有   tableNamePattern 参数,因为在查找关系时,需要目标对象,表名一旦加上约束，可能无法找出目标表。
     * @return
     * @throws Exception
     * @Deprecated @see  buildCompleteTablesFaster
     */
	public List<Table> buildCompleteTables(Connection connection,String schemaPattern) throws Exception;
	
	/**
     * 构造完整的表的对象关系。包含：列、关键字、外键等。对buildCompleteTables的优化
     * @param connection
     * @param schemaPattern
     * 不能有   tableNamePattern 参数,因为在查找关系时,需要目标对象,表名一旦加上约束，可能无法找出目标表。
     * @return
     * @throws Exception
     */
	public List<Table> buildCompleteTablesFaster(Connection connection,String schemaPattern) throws Exception;
	/**
	 * 构建某个 schema 下的所有对象 ： 表、视图、存储过程等。
	 * @param connection
	 * @param schemaName  完整的 Schema名称，不模糊查询
	 * @return
	 * @throws Exception
	 */
	public SchemaModel buildSchemaModel(Connection connection,String schemaName) throws Exception;

	/**
	 *  构建  当前连接的  缺省 schema 下的所有对象 ： 表、视图、存储过程等。
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public SchemaModel buildSchemaModel(Connection connection) throws Exception;
	
	/**
	 * 给定一个表和要删除的记录的主键值,生成删除被关联的表相应记录的SQL语句
	 * 注：有依赖关系，但是子表没有记录,不生成删除语句,如果可以为空,只生成 update语句(避免了死循环)
	 * @param table  要删除记录的表对象, 注意：是已经构造好的完整数据模型后表对象.
	 * @param pkValue 主键的值, 在本框架，主键列名为ID number(12,0) , 例子：100021
	 * @return  List<String>  可以执行的SQL，注意必须从后面(最大位置)开始执行. 因为有依赖关系. 
	 * @throws  Exception  connection可能不可用, 数据产生了死循环(外键为空,有了数据后,改为非空)
	 */
	public List<String> createDeleteSql(Connection connection, Table table , Long pkValue )  throws Exception;
	
	/**
	 * 给定一个表，生成删除被关联的表所有记录的SQL语句
	 * 注：
	 * (1)有依赖关系，都要清除
	 * (2)注意死循环,如: 部门、员工、权限、部门,形成了循环,这中间肯定有一个依赖关系是可以为空(否则插入不了数据)的。可空时,生成update语句
	 * 场景：清空产品表,需要清空相关的产品线、型号、业务单据表
	 * @param connection
	 * @param table
	 * @param ignoreTables  放置被忽略的表名,当批量调用时,此参数很重要,避免重复查找
	 * @return  delete语句和一些Update语句,执行时,update语句最先执行，对于delete语句从后面(最大位置)开始执行. 因为有依赖关系. 
	 * @throws Exception
	 */
	public List<String> createTruncateTableSql( Table table ,Set<String> hasFoundTables )  throws Exception;
    
}
