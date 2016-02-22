package com.lei.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lei.dao.Dao;
import com.lei.dao.impl.MysqlDaoImpl;
import com.lei.db.Config;
import com.lei.model.ColumnInfo;
import com.lei.service.TableConfigureService;
import com.lei.util.StringUtil;

/** 
 * @author  作者 : zhanglei
 * @date 创建时间：2016年1月22日 上午10:04:39 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class Main extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected TableConfigureService service = new TableConfigureService();
	
	private Dao dao = null;
	
	private FileOutputStream out = null;
	
	private BufferedWriter buff = null;
	
	
	public static void main(String[] args) {
//		Config config = new Config("M","jsprun","root","123456");
//		Main m = new Main(); 
//		m.getDatas("E:/mywork/java");
		@SuppressWarnings("unused")
		JPanelMethod jf = new JPanelMethod();
	}
	protected String[] initApp(){
		String url = dao==null?"":dao.getIp();
		if(dao==null||!(url.contains(Config.database_sid)&&url.contains(Config.database_sid))){
			dao = new MysqlDaoImpl();//初始化数据库连接
		}
		
		List<String> tableNames = dao.getTableName();
		String[] str = new String[tableNames.size()];
		for(int i=0;i<tableNames.size();i++){
			str[i] = tableNames.get(i);
		}
		return str;
	}
	
	/**
	 * 所有表都生成文件
	 * @param fileName
	 */
	protected void getDatas(String fileName,int start,int end){
		List<String> tableNames = dao.getTableName();
		if(tableNames!=null&&tableNames.size()>0){
			int i = 0;
			for(String tablename: tableNames){
				i++;
				if(i<start){
					continue;
				}else if(i>end){
					break;
				}else{
					setFile(fileName,tablename);
					writeText(tablename);
				}
			}
			JOptionPane.showMessageDialog(this,"文件生成成功！","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	/**
	 * 单表生成单个文件
	 * @param fileName 文件存放路径
	 * @param tablename  表名
	 */
	protected void getColumnInfo(String fileName,String tablename){
		setFile(fileName,tablename);
		writeText(tablename);
		JOptionPane.showMessageDialog(this,"文件生成成功！","提示",JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * 将表名中的"_"都去掉，并且后一个字母大写  如t_common_document改成TCommonDocument
	 * @param tableName
	 * @return
	 */
	private String updateTableName(String tableName){
		List<Integer> list = StringUtil.replaceIndex("_",tableName);
		String[] strs = new String[list.size()];
		for(int i=0;i<list.size();i++){
			strs[i] = tableName.substring(list.get(i), list.get(i)+2);
		}
		for(String str : strs){
			tableName = StringUtil.replace(str,str.substring(1).toUpperCase(),tableName);
		}
		tableName = (tableName.charAt(0)+"").toUpperCase()+tableName.substring(1);
		return tableName;
	}
	/**
	 * 设置文件存放路径
	 * @param fileName
	 * @param tablename
	 */
	private void setFile(String fileName,String tablename){
		File file = new File(fileName+"/"+updateTableName(tablename)+".java");
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			out = new FileOutputStream(file,false);//true 则向文件中追加内容  false 则覆盖掉文件内容
			buff = new BufferedWriter(new OutputStreamWriter(out));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,"文件创建失败","提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	/**
	 * 根据表名将表字段写入文件
	 * @param tablename
	 */
	private void writeText(String tablename){
		List<ColumnInfo> fieldtypes = dao.getFieldTypeObject(tablename);
		try {
			buff.newLine();
			buff.write("public class "+updateTableName(tablename)+"{");
			for(ColumnInfo model : fieldtypes){
				buff.newLine();
				if(model.desc==null){
					buff.newLine();
				}else{
					buff.write("\t /*"+model.desc+"*/");
				}
				buff.newLine();
				buff.write("\t "+"private "+model.dataType+" "+ model.showName+";");
			}
			buff.newLine();
			buff.write("}");
			buff.flush();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,"文件数据写入失败","提示",JOptionPane.INFORMATION_MESSAGE);
			return ;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(buff!=null){
					try {
						buff.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
