package com.lei.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lei.db.Config;
import com.lei.util.JsonHelper;
import com.lei.util.UtilsHelper;

/**
 * json操作
 * @author zhanglei
 *history_dbconfig.json 中存取数据
 */
public class TableConfigureService
{
  private HashMap<String, TableTreeInfo> tableTreeInfo = new HashMap<String, TableTreeInfo>();
  
  private final String CONFIGTREE = "/history_dbconfig.json";

  public TableConfigureService()
  {
	  loadTreeConfig();
  }

  public HashMap<String, TableTreeInfo> getTableTreeInfoList()
  {
    if ((this.tableTreeInfo == null) || (!loadTreeConfig())) {
      return null;
    }
    return this.tableTreeInfo;
  }
  public TableTreeInfo getTableTreeInfo(String name)
  {
    if ((this.tableTreeInfo == null) || (!loadTreeConfig())) {
      return null;
    }
    return this.tableTreeInfo.get(name);
  }

  public boolean addTableTreeInfo(Config info,String cType_value) {
	  this.tableTreeInfo.put(Config.url, new TableTreeInfo(cType_value));
    return saveTreeConfig();
  }
  public boolean updateTableTreeInfo(HashMap<String, TableTreeInfo> info) {
	  this.tableTreeInfo = info; 
    return saveTreeConfig();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
public boolean loadTreeConfig()
  {
    String dataPath = UtilsHelper.getProjectPath() + CONFIGTREE;
    File file = new File(dataPath);
    if (!file.exists()) {
      return false;
    }
    
  ObjectMapper mapper = new ObjectMapper();
  mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, new Class[] { String.class, TableTreeInfo.class });
  try {
    this.tableTreeInfo = ((HashMap)mapper.readValue(file, javaType));
  } catch (JsonMappingException e) {
	  this.tableTreeInfo = new HashMap<String, TableTreeInfo>();
//    e.printStackTrace();
  } catch (IOException e) {
    e.printStackTrace();
  }
    if (this.tableTreeInfo == null) {
      this.tableTreeInfo = new HashMap<String, TableTreeInfo>();
      return false;
    }
    return true;
  }

  @SuppressWarnings("resource")
private boolean saveTreeConfig()
  {
    String dataPath = UtilsHelper.getProjectPath() + CONFIGTREE;

    String path = dataPath.substring(0, dataPath.lastIndexOf(47));
    
    try {
      File file = new File(path);
      if (!file.exists()) {
        file.mkdirs();
      }
      
      UtilsHelper.reNameFile(dataPath);
      RandomAccessFile randomFile = new RandomAccessFile(dataPath, "rw");
      long fileLength = randomFile.length();
      if (fileLength != 0L) {
        return false;
      }
      randomFile.seek(fileLength);
      String fileContent = JsonHelper.serialize(this.tableTreeInfo);
      randomFile.write(fileContent.getBytes());
      randomFile.close();
    } catch (IOException e) {
      System.out.print("error");
      e.printStackTrace();
    }
    return true;
  }
  public static class TableTreeInfo
  {
	  public String class_name;
		/*jdbc:mysql*/
		public String database_url;
		/*数据库服务器IP地址localhost*/
		public String server_ip;
		/*端口 3306*/
		public String server_port;
		/*数据库名*/
		public String database_sid;
		/*用户名*/
		public String username;
		/*密码*/
		public String password;
		/*组合后的url*/
		public String url;
		/*数据库类型*/
		public String dataBaseType;
		
		public String cType_value;
		public TableTreeInfo(){}
		public TableTreeInfo(String cType_value){
			class_name = Config.class_name;
			database_url = Config.database_url;
			server_ip = Config.server_ip;
			server_port = Config.server_port;
			database_sid = Config.database_sid;
			username = Config.username;
			password = Config.password;
			dataBaseType = Config.dataBaseType;
			url = Config.url;
			this.cType_value = cType_value;
		}
		
  }
}