package com.lei.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** 
 * @author  作者 : zhanglei
 * @date 创建时间：2016年1月20日 上午9:47:09 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class DBConn {

	/*三属性   四方法*/
	
	/*三大核心属性*/
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/*四方法*/
	//创建数据库连接
	public Connection getConnection(){
		try {
			//1: 加载连接驱动，Java反射原理
			Class.forName(Config.class_name);
			//2:创建Connection接口对象，用于获取MySQL数据库的连接对象。三个参数：url连接字符串    账号  密码
			String url = Config.url;
			conn = DriverManager.getConnection(url,Config.username,Config.password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(),"数据库连接失败！\n "+Config.url,"提示",JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(),"数据库连接失败！\n "+Config.url,"提示",JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }    
		return conn;
	}
	//关闭数据库的方法
	public void closeConn(){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//method3: 专门用于发送增删改语句的方法
    public int execOther(PreparedStatement pstmt){
        try {
            //1、使用Statement对象发送SQL语句
            int affectedRows = pstmt.executeUpdate();
            //2、返回结果
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    //method4: 专门用于发送查询语句
    public ResultSet execQuery(PreparedStatement pstmt){
        try {
            //1、使用Statement对象发送SQL语句
            rs = pstmt.executeQuery();
            //2、返回结果
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Map<String,String>> query(String sql){
    	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    	try {
			PreparedStatement pstmt = conn.prepareStatement(sql) ;
			ResultSet rs = execQuery(pstmt);
			if(rs!=null){
				Map<String,String> map = null;
				while(rs.next()){
					map = new HashMap<String, String>();
					map.put("Field", rs.getString("Field"));
					map.put("Type", rs.getString("Type"));
					map.put("Comment", rs.getString("Comment"));
					list.add(map);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return list;
    }
}
