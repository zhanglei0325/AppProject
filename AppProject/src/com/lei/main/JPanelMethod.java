package com.lei.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.lei.db.Config;
import com.lei.service.TableConfigureService.TableTreeInfo;
import com.lei.util.StringUtil;

/** 
 * @author  作者 : zhanglei
 * @date 创建时间：2016年1月22日 下午2:30:19 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class JPanelMethod extends Main{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 330;
	private Font font = new Font("宋体",Font.PLAIN,12);
	/*数据库表数组*/
	private static String[] items = {};
	
	/*所有输入框*/
	/*表选择下拉框*/
	private JComboBox tablenameField;
	/*选择表范围  0所有表  1 其他（单表）*/
	private String ntype_value = "1";
	/*IP地址*/
	private JTextField ipField;
	/*数据库登陆用户名*/
	private JTextField usernameField;
	/*数据库密码*/
	private JTextField passwordField;
	/*数据库名称*/
	private JTextField databasenameField;
	/*数据库类型*/
	private JTextField dtype;
	/*数据库信息   默认读取文件或是手动填写*/
	private String cType_value = "0";
	
	private JRadioButton mr;
	
	private JRadioButton sd;
	
	private JTextField className;
	/*数据库连接端口*/
	private JTextField port;
	
	private JTextField start;
	
	private JTextField end;
	/*文件存储目录*/
	private String readPath = "";
	
	private Config config = null;
	public JPanelMethod(){
		getMyWindows();
	}
	public void getMyWindows(){
		this.setSize(WIDTH, HEIGHT);//大小
		this.setTitle("将数据库表生成java文件");//标题
		this.setLocationRelativeTo(null);//居中
		this.setLayout(null);//设置窗体布局为空布局
		this.setResizable(false);//禁止最大化
		init();
		this.setJMenuBar(new MenuUp());//添加菜单栏
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setVisible(true);
	}
	
	private void init(){
		database();
		cType();
		dataInfo();
		ntype();
		databaseConect();
		storageDirectory();
		
		port = new JTextField("3306");
		port.setBounds(170, 15, 35, 25);
		this.add(port);
		
		JButton yes = new JButton("确认");
		JButton no = new JButton("取消");
		yes.setBounds(100, 230, 80, 25);
		no.setBounds(220, 230, 80, 25);
		this.add(yes);
		this.add(no);
		
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if("1".equals(ntype_value)){//单表
					if(!"".equals(readPath)){
						if(tablenameField!=null&&tablenameField.getSelectedItem()!=null){
							getColumnInfo(readPath,tablenameField.getSelectedItem().toString());
						}else{
							JOptionPane.showMessageDialog(new JFrame(),"请连接数据库，并检查是否存在表！","提示",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}else{
						JOptionPane.showMessageDialog(new JFrame(),"请选择文件存放路径！","提示",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}else{
					if(!"".equals(readPath)){
						if(StringUtil.isInteger(start.getText())&&StringUtil.isInteger(end.getText())){
							getDatas(readPath,Integer.parseInt(start.getText()),Integer.parseInt(end.getText()));
						}else{
							JOptionPane.showMessageDialog(new JFrame(),"请输入数字！（"+start.getText()+","+end.getText()+"）","提示",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}else{
						JOptionPane.showMessageDialog(new JFrame(),"请选择文件存放路径！","提示",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			}
		});
		no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1); 
			}
		});
	}
	/**
	 * 生成文件存储目录
	 */
	private void storageDirectory(){
		JLabel direct = new JLabel("存储目录：",SwingUtilities.RIGHT);
		direct.setFont(font);
		direct.setBounds(10, 190, 60, 25);
		this.add(direct);
		
		final JFileChooser file = new JFileChooser();
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		final JTextField directField = new JTextField();
		directField.setBounds(65, 190, 200, 25);
		JButton button = new JButton("\u6253\u5F00");
		button.setFont(font);
		button.setBounds(270, 190, 80, 25);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = file.showOpenDialog(null);
                file.setDialogType(JFileChooser.OPEN_DIALOG);
                file.setMultiSelectionEnabled(false);
                file.setAcceptAllFileFilterUsed(false);
                if (index == JFileChooser.APPROVE_OPTION) {
                    // 把获取到的文件的<a href="https://www.baidu.com/s?wd=%E7%BB%9D%E5%AF%B9%E8%B7%AF%E5%BE%84&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1Y1nAD4rycLPWfsmHnknyR40ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EnHf4PHT3nHf3P16YPjD4rjc4rf" target="_blank" class="baidu-highlight">绝对路径</a>显示在文本编辑框中
                	directField.setText(file.getSelectedFile()
                            .getAbsolutePath());
                    readPath = directField.getText();
                }
            }
        });
		add(directField);
		add(button);
	}
	/**
	 * 表名及连接按钮
	 * @param mainLeft 左边的画板
	 * @param mainRight 右边的画板
	 */
	private void databaseConect(){
		JLabel tablename = new JLabel("表名：",SwingUtilities.RIGHT);
		tablename.setFont(font);
		tablename.setBounds(10, 155, 60, 25);

		this.add(tablename);
		
		final JButton con = new JButton("连接");
		con.setBounds(250, 155, 80, 25);
		this.add(con);
		
		con.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dtypename = StringUtil.dataBaseType(dtype.getText());
				if("".equals(dtype.getText())){
					JOptionPane.showMessageDialog(new JFrame(),"请填写数据库类型（mysql、oracle、sql server）","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if("ext".equals(dtypename)){
					JOptionPane.showMessageDialog(new JFrame(),"没找到改数据库类型！请填写（mysql、oracle、sql server）","提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//将原窗口销毁
//				dispose();
				//初始化配置文件
				if("1".equals(cType_value)){//手动输入
					config = new Config(dtypename,port.getText(),ipField.getText(),databasenameField.getText(),usernameField.getText(),passwordField.getText());
				}else{//默认
					config = new Config(dtypename,port.getText());
				}
				
				
				items = initApp();
				if(tablenameField!=null){
					remove(tablenameField);
				}
				tablenameField = new JComboBox(items);
				tablenameField.setBounds(65, 155, 150, 25);
				add(tablenameField);
				repaint();
				validate();
				
				//保存数据库连接信息，存历史
				service.addTableTreeInfo(config,cType_value);
			}
			
		});
	}
	/**
	 * 数据库所有表或者是单表选择
	 * @param mainLeft 左边的画板
	 * @param mainRight 右边的画板
	 */
	@SuppressWarnings("deprecation")
	private void ntype(){
		ButtonGroup bg = new ButtonGroup();
		final JRadioButton jrb1 = new JRadioButton("单表");
		bg.add(jrb1);
		jrb1.setLocation(80, 120);
		jrb1.setSize(60, 25);
		jrb1.setSelected(true);//默认选中
		
		className = new JTextField();
		if(tablenameField!=null){
			className.setText(tablenameField.getSelectedItem().toString());
		}
		className.setLocation(220, 120);
		className.setSize(30, 20);
		
		final JRadioButton jrb2 = new JRadioButton("选择");
		bg.add(jrb2);
		jrb2.setLocation(160, 120);
		jrb2.setSize(60, 25);
		this.add(jrb1);
		this.add(jrb2);
		
		start = new JTextField("0");
		start.setLocation(220, 120);
		start.setSize(30, 20);
		start.disable();
		this.add(start);
		
		end = new JTextField("999");
		end.setLocation(255, 120);
		end.setSize(30, 20);
		end.disable();
		this.add(end);
		
		jrb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ntype_value = "1";
				start.disable();
				end.disable();
				repaint();
				validate();
			}
			
		});
		jrb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ntype_value = "0";
				start.setEnabled(true);
				end.setEnabled(true);
				repaint();
				validate();
			}
			
		});
	}
	/**手动填写数据库连接信息
	 * 数据库连接信息
	 * @param mainLeft 左边的画板
	 * @param mainRight  右边的画板
	 */
	@SuppressWarnings("deprecation")
	private void dataInfo(){
		Config.init(StringUtil.dataBaseType(dtype.getText()));
		JLabel ip = new JLabel("IP地址：",SwingUtilities.RIGHT);
		ip.setFont(font);
		ip.setBounds(10, 50, 80, 25);
		ipField = new JTextField(Config.server_ip);
		ipField.setBounds(95, 50, 90, 25);
		
		
		JLabel username = new JLabel("用户名：",SwingUtilities.RIGHT);
		username.setFont(font);
		username.setBounds(10, 85, 80, 25);
		usernameField = new JTextField(Config.username);
		usernameField.setBounds(95, 85, 90, 25);
		
		this.add(ip);
		this.add(ipField);
		this.add(username);
		this.add(usernameField);
		
		JLabel databasename = new JLabel("数据库名：",SwingUtilities.RIGHT);
		databasename.setFont(font);
		databasename.setBounds(185, 50, 90, 25);
		databasenameField = new JTextField(Config.database_sid);
		databasenameField.setBounds(270, 50, 90, 25);
		
		JLabel password = new JLabel("密码：",SwingUtilities.RIGHT);
		password.setFont(font);
		password.setBounds(185, 85, 90, 25);
		passwordField = new JTextField(Config.password);
		passwordField.setBounds(270, 85, 90, 25);
		
		this.add(databasename);
		this.add(databasenameField);
		this.add(password);
		this.add(passwordField);
		
		ipField.disable();;
		usernameField.disable();
		passwordField.disable();
		databasenameField.disable();

	}
	
	/**
	 * 数据库类型
	 * @param main
	 */
	private void database(){
		JLabel dtype_l = new JLabel("数据库类型：",SwingUtilities.RIGHT);
		dtype_l.setFont(font);
		dtype_l.setBounds(10, 15, 80, 25);
		
		dtype = new JTextField("mysql");
		dtype.setBounds(95, 15, 70, 25);
		this.add(dtype_l);
		this.add(dtype);
	}
	/**
	 * 数据库连接方式选择
	 */
	private void cType(){
		ButtonGroup bg = new ButtonGroup();
		mr = new JRadioButton("默认");
		bg.add(mr);
		mr.setLocation(210, 15);
		mr.setSize(60, 25);
		mr.setSelected(true);
		sd = new JRadioButton("手动填写");
		bg.add(sd);
		sd.setLocation(270, 15);
		sd.setSize(100, 25);
		this.add(mr);
		this.add(sd);
		
		mr.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				cType_value = "0";
				ipField.disable();
				usernameField.disable();
				passwordField.disable();
				databasenameField.disable();
				repaint();
				validate();
			}
			
		});
		sd.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				cType_value = "1";
				ipField.enable();
				usernameField.enable();
				passwordField.enable();
				databasenameField.enable();
				repaint();
				validate();
			}
			
		});
	}
	/**
	 * 选中历史记录，替换显示信息
	 * @param model
	 */
	 @SuppressWarnings("deprecation")
	public void setView(TableTreeInfo model){
		 	dtype.setText(StringUtil.dataBaseTypeRoll(model.dataBaseType));
			port.setText(model.server_port);
			ipField.setText(model.server_ip);
			databasenameField.setText(model.database_sid);
			usernameField.setText(model.username);
			passwordField.setText(model.password);
			cType_value = model.cType_value;
			if("1".equals(model.cType_value)){
				mr.setSelected(false);
				sd.setSelected(true);
				
				ipField.enable();
				usernameField.enable();
				passwordField.enable();
				databasenameField.enable();
			}else{
				mr.setSelected(true);
				sd.setSelected(false);
				
				ipField.disable();
				usernameField.disable();
				passwordField.disable();
				databasenameField.disable();
			}
			repaint();
			validate();
	 }
	
	/*********************************************菜单*************************************************/
	
	class MenuUp extends JMenuBar{
		/**
		 * 菜单布局
		 */
		private static final long serialVersionUID = 1L;
		private Color bgColor=Color.gray;//导航栏背景色
		
		private Object[][] data;//列表数据
		
		private String[] columnNames={"key（url）","username"};//{"编号","姓名","性别","年龄","电话"};表头
		
		private JTable table;//列表
		
		private JScrollPane pane;//包围列表的框
		
		public MenuUp(){
			
			JMenu m1 = new JMenu( "菜单" );
	
			JMenuItem mi1 = new JMenuItem( "清空" );
			mi1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dtype.setText("");
					port.setText("");
					ipField.setText("");
					databasenameField.setText("");
					usernameField.setText("");
					passwordField.setText("");
					repaint();
					validate();
				}
			});
	
			JMenuItem mi2 = new JMenuItem( "保存" );
			mi2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						config = new Config(StringUtil.dataBaseType(dtype.getText()),port.getText(),ipField.getText(),databasenameField.getText(),usernameField.getText(),passwordField.getText());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(new JFrame(),"请填写正确的数据库连接信息！","提示",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					service.addTableTreeInfo(config,cType_value);	
					
					JOptionPane.showMessageDialog(new JFrame(),"保存成功！","提示",JOptionPane.INFORMATION_MESSAGE);
				}
			});
			
			JMenuItem mi2_1 = new JMenuItem( "历史" );
			
			mi2_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/*获取历史信息*/
					final HashMap<String,TableTreeInfo> map = service.getTableTreeInfoList();
					
					final JFrame jframe=new JFrame("历史记录");
					//设置data值，布局列表
					setListData(map);
			        /*列表信息的双击事件*/
					table.addMouseListener(new MouseAdapter(){
			    	    public void mouseClicked(MouseEvent e) {
			    	       if(e.getClickCount()==2){//点击几次，这里是双击事件
			    	    	   TableTreeInfo model= map.get(data[table.getSelectedRow()][0]);
								setView(model);
								jframe.dispose();        
			    	       }
			    	    }
			    	   });
					
			        Container contentPane=jframe.getContentPane();
			        contentPane.setLayout(new FlowLayout(1,20,5));
			        
			        JButton search=new JButton("选择");
			        search.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							TableTreeInfo model= map.get(data[table.getSelectedRow()][0]);
							setView(model);
							jframe.dispose();
						}
					});
			        JButton delete=new JButton("删除");
			        delete.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int[] number = table.getSelectedRows();
							for(int a : number){
								map.remove(data[a][0]);
							}
							service.updateTableTreeInfo(map);
							jframe.remove(pane);
							setListData(map);
							jframe.add(pane,BorderLayout.NORTH);
							jframe.repaint();
							jframe.validate();
						}
					});
			        contentPane.add(search);
			        contentPane.add(delete);
			        
			        jframe.add(pane,BorderLayout.NORTH);
//			        jframe.add(contentPane,BorderLayout.SOUTH);
			        jframe.setSize(350, 240);//窗口大小
			        jframe.setLocationRelativeTo(null);//居中
			        jframe.setVisible(true);
				}
			});
	
			JMenuItem mi3 = new JMenuItem( "退出" ); //创建3个菜单项
			mi3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(0==JOptionPane.showConfirmDialog(new JFrame(), "确定要退出吗？")){
						System.exit(1);
					}
				}
			});
			
			JMenu m2 = new JMenu( "帮助" ); //创建两个菜单目录项
			
			JMenuItem m2_1 = new JMenuItem( "版本" );
			m2_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(new JFrame(),"2016-1-29 v1.0","版本信息",JOptionPane.INFORMATION_MESSAGE);
				}
			});
	
			m1.add( mi1 );
	
			m1.add( mi2 );
			
			m1.add( mi2_1 );
	
			m1.add( mi3 ); //将3个菜单项添加到名为File的菜单下，并加分隔符
			
			m2.add( m2_1 );
	
			add( m1 );
	
			add( m2 );
		}
		
		public void setColor(Color color)
	    {
	        bgColor=color;
	    }
	      
	    @Override
	    protected void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setColor(bgColor);
	        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
	          
	    }
	    /**
	     * 设置data数据,布局列表显示
	     * @param map
	     */
	    public void setListData(HashMap<String,TableTreeInfo> map){
	    	data=new Object[map.size()][2];/*{
            {1,"张三","男","18","010.82607080"},
            {2,"李四","女","24","021.68727080"},
            {3,"王五","男","18","0459.82607080"},
            {4,"赵六","男","18","010.82607080"},
            {5,"任琦","男","18","010.82607080"},
            {6,"吴八","男","18","010.82607080"}
		    };*/
		    int i=0;
		    for(Entry<String, TableTreeInfo> entry : map.entrySet()){
		    	data[i][0] = entry.getKey();
		    	data[i][1] = entry.getValue().username;
		    	i++;
		    }
		    /*禁止双击成编辑模式*/
		    DefaultTableModel t = new DefaultTableModel(data,columnNames) {
			    	private static final long serialVersionUID = 1L;
			    	public boolean isCellEditable(int row, int column) {
			    		return false;
			    	}
		    	};
		    table=new JTable();
		    table.setModel(t);
		    
//		    table=new JTable(data,columnNames);
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	        pane=new JScrollPane(table);
	        pane.setPreferredSize(new Dimension(300,150));
	    }
	}
}
