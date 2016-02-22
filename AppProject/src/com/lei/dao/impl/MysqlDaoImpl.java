package com.lei.dao.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lei.dao.Dao;
import com.lei.model.ColumnInfo;
import com.lei.util.StringUtil;

/** 
 * @author  作者 : Administrator
 * @date 创建时间：2016年1月21日 下午5:17:12 
 * @version 1.0 
 * @param <E>
 * @parameter  
 * @since  
 * @return  
 */
public class MysqlDaoImpl extends BaseDao implements Dao {
	
	/**
	 * 获取表名集合
	 */
	@Override
	public List<String> getTableName() {
	    List<String> tables = new ArrayList<String>();
	    String[] para = new String[1];
	    para[0] = "TABLE";
	    try {
	      
	      DatabaseMetaData dm = conns.getMetaData();

	      ResultSet rs = dm.getTables(null, null, "%", para);
	      while (rs.next())
	        tables.add(rs.getString(3));
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return tables;
	}
	/**
	 * 获取字段名及类型
	 * @param tableName
	 * @return
	 */
	@Override
	public List<ColumnInfo> getFieldTypeObject(String tableName)
	  {
	    List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();
	    ColumnInfo model = null;
	    String sql = "SHOW FULL COLUMNS FROM " + tableName;
	    List<Map<String,String>> list = dbconn.query(sql);
	    for (Map<String,String> maps : list) {
	      model = new ColumnInfo();
	      model.name = maps.get("Field").toString();
	      model.dataType = StringUtil.getJavaType(maps.get("Type").toString());
	      model.showName = maps.get("Field").toString().toLowerCase();
	      model.desc = maps.get("Comment")+"";
	      columnInfos.add(model);
	    }
	    return columnInfos;
	  }
	
	public String getIp(){
		String url = "";
		try {
			if(conns!=null){
				DatabaseMetaData dm = conns.getMetaData();
				url = dm.getURL();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		if(!"".equals(url)){//截取ip
//			url = url.substring(url.indexOf("//")+2, url.indexOf(":", url.indexOf("//")+2));
//		}
		return url;
	}

}
