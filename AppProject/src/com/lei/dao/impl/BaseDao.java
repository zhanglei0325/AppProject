package com.lei.dao.impl;

import java.sql.Connection;

import com.lei.db.DBConn;

/** 
 * @author  作者 : Administrator
 * @date 创建时间：2016年1月21日 下午5:20:17 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class BaseDao {
	protected static DBConn dbconn;
	
	protected static Connection conns;
	
	public BaseDao(){
		dbconn = new DBConn();
		conns = dbconn.getConnection();
	}
}
