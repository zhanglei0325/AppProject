package com.lei.test;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.lei.dao.Dao;
import com.lei.dao.impl.MysqlDaoImpl;
import com.lei.db.Config;

/** 
 * @author  作者 : Administrator
 * @date 创建时间：2016年1月20日 上午11:32:43 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class Test {
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Config config = new Config("M","3306","jsprun","root","123456");
//		DBConn conn = new DBConn();
//		System.out.print(conn.getConnection());
		Dao dao = new MysqlDaoImpl();
		System.out.println(dao.getIp());
		
		new Test1();  
//		List<String> list = dao.getTableName();
//		
//		for(String str : list){
//			System.out.println("=============="+str);
//			List<ColumnInfo> listInfo = dao.getFieldTypeObject(str);
//			for(ColumnInfo model : listInfo){
//				System.out.println("/*"+model.desc+"*/");
//				System.out.println("private "+model.dataType+" "+ model.showName+";");
//			}
//			
//		}
//		System.out.println(list.size());
	}
	
}
class Test1 extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame("漆艾林-Example");// 框架布局  
    JTabbedPane tabPane = new JTabbedPane();// 选项卡布局  
    Container con = new Container();//  
    JLabel label1 = new JLabel("文件目录");  
    JLabel label2 = new JLabel("选择文件");  
    JTextField text1 = new JTextField();// TextField 目录的路径  
    JTextField text2 = new JTextField();// 文件的路径  
    JButton button1 = new JButton("...");// 选择  
    JButton button2 = new JButton("...");// 选择  
    JFileChooser jfc = new JFileChooser();// 文件选择器  
    JButton button3 = new JButton("确定");//  
      
    Test1() {  
        jfc.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘  
          
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
          
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
          
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置  
        frame.setSize(280, 200);// 设定窗口大小  
        frame.setContentPane(tabPane);// 设置布局  
        label1.setBounds(10, 10, 70, 20);  
        text1.setBounds(75, 10, 120, 20);  
        button1.setBounds(210, 10, 50, 20);  
        label2.setBounds(10, 35, 70, 20);  
        text2.setBounds(75, 35, 120, 20);  
        button2.setBounds(210, 35, 50, 20);  
        button3.setBounds(30, 60, 60, 20);  
        button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actionPerformed(e);
			}
		}); // 添加事件处理  
//        button2.addActionListener(this); // 添加事件处理  
//        button3.addActionListener(this); // 添加事件处理  
        con.add(label1);  
        con.add(text1);  
        con.add(button1);  
        con.add(label2);  
        con.add(text2);  
        con.add(button2);  
        con.add(button3);  
        frame.setVisible(true);// 窗口可见  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序  
        tabPane.add("1面板", con);// 添加布局1  
    }  
    /** 
     * 时间监听的方法 
     */  
    public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub  
        if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个  
            jfc.setFileSelectionMode(1);// 设定只能选择到文件夹  
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
            if (state == 1) {  
                return;  
            } else {  
                File f = jfc.getSelectedFile();// f为选择到的目录  
                text1.setText(f.getAbsolutePath());  
            }  
        }  
        // 绑定到选择文件，先择文件事件  
        if (e.getSource().equals(button2)) {  
            jfc.setFileSelectionMode(0);// 设定只能选择到文件  
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句  
            if (state == 1) {  
                return;// 撤销则返回  
            } else {  
                File f = jfc.getSelectedFile();// f为选择到的文件  
                text2.setText(f.getAbsolutePath());  
            }  
        }  
        if (e.getSource().equals(button3)) {  
            // 弹出对话框可以改变里面的参数具体得靠大家自己去看，时间很短  
            JOptionPane.showMessageDialog(null, "弹出对话框的实例，欢迎您-漆艾琳！", "提示", 2);  
        }  
    }  
    
    

	/*
	 * 
	
	public void databaseConect(JPanel mainLeft,JPanel mainRight){
		JPanel conect = new JPanel(new GridLayout(1,2));
		JLabel tablename = new JLabel("表名：",SwingUtilities.RIGHT);
		tablename.setFont(font);
		JComboBox tablenameField = new JComboBox(items);
		conect.add(tablename);
		conect.add(tablenameField);
		mainLeft.add(conect);
		
		JButton con = new JButton("连接");
		mainRight.add(con);
	}

	public void ntype(JPanel mainLeft,JPanel mainRight){
		ButtonGroup bg = new ButtonGroup();
		final JRadioButton jrb1 = new JRadioButton("全部");
		bg.add(jrb1);
		jrb1.setLocation(40, 20);
		jrb1.setSize(60, 20);
		final JRadioButton jrb2 = new JRadioButton("单表");
		bg.add(jrb2);
		jrb2.setLocation(100, 20);
		jrb2.setSize(100, 20);
		mainLeft.add(jrb1);
		mainRight.add(jrb2);
	}

	public void dataInfo(JPanel mainLeft,JPanel mainRight){
		JPanel info = new JPanel(new GridLayout(2,2));
		JLabel ip = new JLabel("IP地址：",SwingUtilities.RIGHT);
		ip.setFont(font);
		JTextField ipField = new JTextField();
		
		JLabel username = new JLabel("用户名：",SwingUtilities.RIGHT);
		ip.setFont(font);
		JTextField usernameField = new JTextField();
		
		info.add(ip);
		info.add(ipField);
		info.add(username);
		info.add(usernameField);
		mainLeft.add(info);
		
		info = new JPanel(new GridLayout(2,2));
		JLabel databasename = new JLabel("数据库名：",SwingUtilities.RIGHT);
		ip.setFont(font);
		JTextField databasenameField = new JTextField();
		
		JLabel password = new JLabel("密码：",SwingUtilities.RIGHT);
		ip.setFont(font);
		JTextField passwordField = new JTextField();
		
		info.add(databasename);
		info.add(databasenameField);
		info.add(password);
		info.add(passwordField);
		mainRight.add(info);
	}

	public void database(JPanel mainLeft){
		JPanel dt = new JPanel();
		JLabel dtype_l = new JLabel("数据库类型：",SwingUtilities.RIGHT);
//		dtype_l.setFont(font);
		dtype_l.setLocation(0, 0);
		dtype_l.setSize(10, 10);
		
		JTextField dtype = new JTextField();
		dtype.setLocation(50, 0);
		dtype.setSize(10, 20);
		dt.add(dtype_l);
		dt.add(dtype);
		mainLeft.add(dt);
	}
	
	public void cType(JPanel mainRight){
		JPanel ct = new JPanel(new GridLayout(1,2));
		ButtonGroup bg = new ButtonGroup();
		final JRadioButton jrb1 = new JRadioButton("默认");
		bg.add(jrb1);
		jrb1.setLocation(40, 20);
		jrb1.setSize(60, 20);
		final JRadioButton jrb2 = new JRadioButton("手动填写");
		bg.add(jrb2);
		jrb2.setLocation(100, 20);
		jrb2.setSize(100, 20);
		ct.add(jrb1);
		ct.add(jrb2);
		mainRight.add(ct);
	}
	
	public void myJFrame(){
		JLabel jl = new JLabel("表model生成",SwingUtilities.CENTER);
		Font font = new Font("宋体",Font.BOLD,24);
		jl.setFont(font);
		jl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));		
		this.add(jl,BorderLayout.NORTH);
		
		
		font = new Font("宋体",Font.PLAIN,12);
		JLabel jl_name = new JLabel("用户名：",SwingUtilities.RIGHT);
		jl_name.setFont(font);
		
		JLabel jl_id = new JLabel("身份证号：",SwingUtilities.RIGHT);
		jl_id.setFont(font);
		
		JLabel jl_pass1 = new JLabel("密码：",SwingUtilities.RIGHT);
		jl_pass1.setFont(font);
		
		JLabel jl_pass2 = new JLabel("确认密码：",SwingUtilities.RIGHT);
		jl_pass2.setFont(font);
		
		JLabel jl_count = new JLabel("存储金额：",SwingUtilities.RIGHT);
		jl_count.setFont(font);
		
		JPanel jp_center_left = new JPanel();		
		jp_center_left.setLayout(new GridLayout(5,1));
		jp_center_left.add(jl_name);
		jp_center_left.add(jl_id);
		jp_center_left.add(jl_pass1);
		jp_center_left.add(jl_pass2);
		jp_center_left.add(jl_count);
		
		JTextField jt_name = new JTextField();
		JTextField jt_id = new JTextField("                     ");
		JPasswordField jt_pass1 = new JPasswordField();
		JPasswordField jt_pass2 = new JPasswordField();
		jt_pass1.setEchoChar('*');
		jt_pass2.setEchoChar('*');
		JTextField jt_count = new JTextField();
		
		JPanel jp_center_right = new JPanel();
		jp_center_right.setLayout(new GridLayout(5,1));
		jp_center_right.add(jt_name);
		jp_center_right.add(jt_id);
		jp_center_right.add(jt_pass1);
		jp_center_right.add(jt_pass2);
		jp_center_right.add(jt_count);
		
		JPanel jp_center = new JPanel();
		jp_center.setLayout(new GridLayout(1,2));
		jp_center.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
		jp_center.add(jp_center_left);
		jp_center.add(jp_center_right);
		
		JButton jb1 = new JButton("确认");
		JButton jb2 = new JButton("返回");
		
		JPanel jp_south = new JPanel();
		jp_south.add(jb1);
		jp_south.add(jb2);
		jp_south.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.add(jp_center);
		this.add(jp_south,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(370, 280);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	 * */
}
