package com.lei.dao;

import java.util.List;

import com.lei.model.ColumnInfo;

/** 
 * @author  作者 : Administrator
 * @date 创建时间：2016年1月20日 下午5:58:09 
 * @version 1.0 
 * @param <E>
 * @parameter  
 * @since  
 * @return  
 */
public interface Dao {
	
	List<String> getTableName();
	
	List<ColumnInfo> getFieldTypeObject(String tablename);
	
	String getIp();
}
