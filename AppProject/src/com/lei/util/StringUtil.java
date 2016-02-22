package com.lei.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** 
 * @author  作者 : Administrator
 * @date 创建时间：2016年1月21日 下午5:58:51 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class StringUtil {

	
	/**
	 * 数据库类型转换成java类型
	 * @param typeName
	 * @return
	 */
	public static String getJavaType(String typeName)
	  {
	    if ((typeName.contains("var")) || (typeName.contains("text"))) {
	      if ((typeName.indexOf("(") != -1) && (Integer.parseInt(typeName.substring(typeName.indexOf("(") + 1, typeName.indexOf(")"))) > 128))
	        return "String";
	      if (typeName.contains("text")) {
	        return "String";
	      }
	      return "String";
	    }
	    if (typeName.contains("int"))
	      return "int";
	    if (typeName.contains("time")) {
	      return "java.util.Date";
	    }
	    return typeName;
	  }
	
	/**
	 * 将source字符串中的所有from替换成to
	 * @param from
	 * @param to
	 * @param source
	 * @return
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer str = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			str.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		str.append(source);
		return str.toString();
	}
	/**
	 * 查询source字符串中search的所有下标值    依次存入集合中
	 * @param search 要搜索的字符串
	 * @param source 原字符串
	 * @return
	 */
	public static List<Integer> replaceIndex(String search, String source) {
		if (source == null || search == null )
			return null;
		List<Integer> lists = new ArrayList<Integer>();
		int index = -1;
		int length = 0;
		while ((index = source.indexOf(search)) != -1) {
			source = source.substring(index + search.length());
			lists.add(index+length);
			length += index+1;
		}
		return lists;
	}
	
	public static String dataBaseType(String dataBase){
		if("mysql".equals(dataBase.toLowerCase())){
			return "M";
		}else if("oracle".equals(dataBase.toLowerCase())){
			return "O";
		}else if("sql server".equals(dataBase.toLowerCase())){
			return "S";
		}else{
			return "ext";
		}
	}
	public static String dataBaseTypeRoll(String type){
		if("M".equals(type)){
			return "mysql";
		}else if("O".equals(type)){
			return "oracle";
		}else if("S".equals(type)){
			return "sql server";
		}else{
			return "ext";
		}
	}
	
	/**
	 * 判段是否为整数
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

}
