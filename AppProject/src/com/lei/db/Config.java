package com.lei.db;

import java.io.IOException;
import java.util.Properties;

/** 
 * @author  作者 : zhanglei
 * @date 创建时间：2016年1月20日 上午9:54:32 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class Config {
	private static Properties prop = new Properties();
	
	private static String CLASS_NAME;
	private static String DATABASE_URL;
	private static String SERVER_IP;
//	private static String SERVER_PORT;
	static{        
        try {
            //加载dbconfig.properties配置文件
            prop.load(Config.class.getResourceAsStream("/config/dbconfig.properties"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/*com.mysql.jdbc.Driver */
	public static String class_name;
	/*jdbc:mysql*/
	public static String database_url;
	/*数据库服务器IP地址localhost*/
	public static String server_ip;
	/*端口 3306*/
	public static String server_port;
	/*数据库名*/
	public static String database_sid = prop.getProperty("DATABASE_SID");
	/*用户名*/
	public static String username = prop.getProperty("USERNAME");
	/*密码*/
	public static String password = prop.getProperty("PASSWORD");
	/*组合后的url*/
	public static String url;
	/*数据库类型*/
	public static String dataBaseType;
	public Config(){}
	public Config(String type,String port){
		super();
		init(type,"");
		Config.server_port = port;
		initBaseType();
	}
	public Config(String type,String port,String database_sid, String username, String password) {
		super();
		init(type);
		Config.server_port = port;
		Config.database_sid = database_sid;
		Config.username = username;
		Config.password = password;
		initBaseType();
	}
	public Config(String type,String port,String server_ip, String database_sid, String username,
			String password) {
		super();
		init(type);
		Config.server_port = port;
		Config.server_ip = server_ip;
		Config.database_sid = database_sid;
		Config.username = username;
		Config.password = password;
		initBaseType();
	}
	/**
	 * 
	 * @param type 数据库类型 M mysql  O oracle S sql server
	 * @param class_name 数据库驱动driver
	 * @param database_url 数据库连接 url
	 * @param server_ip 数据库服务器ip
	 * @param server_port 数据库连接端口
	 * @param database_sid 数据库名称
	 * @param username 登陆用户名
	 * @param password 登录密码
	 */
	public Config(String type,String class_name, String database_url, String server_ip,
			String server_port, String database_sid, String username,
			String password) {
		super();
		init(type);
		Config.class_name = class_name;
		Config.database_url = database_url;
		Config.server_ip = server_ip;
		Config.server_port = server_port;
		Config.database_sid = database_sid;
		Config.username = username;
		Config.password = password;
		
		initBaseType();
	}
	public void initBaseType(){
		if("M".equals(dataBaseType)){
			Config.url = Config.database_url+"://"+Config.server_ip+":"+Config.server_port+"/"+Config.database_sid;
		}else if("O".equals(dataBaseType)){
			Config.url = Config.database_url+":@"+Config.server_ip+":"+Config.server_port+":"+Config.database_sid;
		}else{
			Config.url = Config.database_url+"://"+Config.server_ip+":"+Config.server_port+";DatabaseName="+Config.database_sid;
		}
	}
	/**
	 * @param dataBaseType 数据库类型 M mysql  O Oracle S sqlite
	 */
	public static void init(String dataBaseType){
		Config.dataBaseType = dataBaseType;
		if("M".equals(dataBaseType)){
			CLASS_NAME = "MYSQL_CLASS_NAME";
			DATABASE_URL = "MYSQL_DATABASE_URL";
			SERVER_IP = "MYSQL_SERVER_IP";
//			SERVER_PORT = "MYSQL_SERVER_PORT";
		} if("O".equals(dataBaseType)){
			CLASS_NAME = "O_CLASS_NAME";
			DATABASE_URL = "O_DATABASE_URL";
			SERVER_IP = "O_SERVER_IP";
//			SERVER_PORT = "O_SERVER_PORT";
		}else if("S".equals(dataBaseType)){
			CLASS_NAME = "S_CLASS_NAME";
			DATABASE_URL = "S_DATABASE_URL";
			SERVER_IP = "S_SERVER_IP";
//			SERVER_PORT = "S_SERVER_PORT";
		}else{
			CLASS_NAME = "MYSQL_CLASS_NAME";
			DATABASE_URL = "MYSQL_DATABASE_URL";
			SERVER_IP = "MYSQL_SERVER_IP";
//			SERVER_PORT = "MYSQL_SERVER_PORT";
			Config.dataBaseType = "M";
		} 

		class_name = prop.getProperty(CLASS_NAME);
		database_url = prop.getProperty(DATABASE_URL);
		server_ip = prop.getProperty(SERVER_IP);
//		server_port = prop.getProperty(SERVER_PORT);
	} 
	public static void init(String dataBaseType,String defaults){
		init(dataBaseType);
		database_sid = prop.getProperty("DATABASE_SID");
		username = prop.getProperty("USERNAME");
		password = prop.getProperty("PASSWORD");
	}
}
