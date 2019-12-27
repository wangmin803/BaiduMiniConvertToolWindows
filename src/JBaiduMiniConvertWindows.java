import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
class MyRadio {
	private JFrame jFrame = new JFrame("wjm");// 
	private Container container = jFrame.getContentPane();// 
	private JRadioButton jb1 = new JRadioButton("生成全部");// 
	private JRadioButton jb2wx = new JRadioButton("生成微信小程序");// 
	private JRadioButton jb3tt = new JRadioButton("生成头条小程序");// 
	
	
	private JTextArea jta = new JTextArea("显示区域");

	private JPanel panel = new JPanel();// 
	private JPanel panel1 = new JPanel();// 
	private JPanel panel2 = new JPanel();// 
	private JButton source = new JButton("选择源文件夹");
	private JButton target = new JButton("选择目标文件夹");
	private JButton generate = new JButton("生成小程序");
	
	
	private JTextField sourceText = new JTextField("选择源文件夹");
	private JTextField targetText = new JTextField("选择目标文件夹");

	private String sourcedirectory="";
	private String targetdirectory="";
	private static String text="";
	
 
	public MyRadio() {
		panel.setBorder(BorderFactory.createTitledBorder("选择类型"));
		panel.setLayout(new GridLayout(1, 6));
		panel.add(this.jb1);
		panel.add(this.jb2wx);
		panel.add(this.jb3tt);

		
		panel2.setBorder(BorderFactory.createTitledBorder("选择文件夹"));
		panel2.setLayout(new GridLayout(3, 2));
		panel2.add(this.source);
		panel2.add(this.sourceText);
		panel2.add(this.target);
		panel2.add(this.targetText);
		panel2.add(this.generate);
		
		
		panel1.setBorder(BorderFactory.createTitledBorder("显示区域"));
		panel1.setLayout(new GridLayout(2, 2));
		panel1.add(jta);
		

		final ButtonGroup group = new ButtonGroup();
		group.add(this.jb1);
		group.add(this.jb3tt);
		group.add(this.jb2wx);
	
		source.addMouseListener(new MouseAdapter() {
		
		public void mouseClicked(MouseEvent event) {
		
			sourcedirectory = eventOnImport(new JButton(),jta,"source",sourceText,targetText);
	
		}});
		
		target.addMouseListener(new MouseAdapter() {
			
		public void mouseClicked(MouseEvent event) {
		
			targetdirectory=eventOnImport(new JButton(),jta,"target",sourceText,targetText);
		}});
		
		generate.addMouseListener(new MouseAdapter() {
			
			
			public void mouseClicked(MouseEvent event) {
			
				if(jb1.isSelected()){
					String midtargetdirectory = targetdirectory;
					targetdirectory=midtargetdirectory+"\\work\\wx";
					text+="生成微信小程序开始"+"\r\n";
					extracted(BaiduMiniConterMini.CONVERTE_TYPE_WX);
					text+="生成微信小程序结束"+"\r\n";
					text+="生成头条小程序开始"+"\r\n";
					targetdirectory=midtargetdirectory+"\\work\\tt";
					extracted(BaiduMiniConterMini.CONVERTE_TYPE_TOUTIAO);
					text+="生成头条小程序结束"+"\r\n";
					
				}
				
				
				else if(jb2wx.isSelected()){
					text+="生成微信小程序开始 "+"\r\n";
					extracted(BaiduMiniConterMini.CONVERTE_TYPE_WX);
					text+="生成微信小程序结束"+"\r\n";
				}
				else if(jb3tt.isSelected()){
					text+="生成头条小程序开始"+"\r\n";
					extracted(BaiduMiniConterMini.CONVERTE_TYPE_TOUTIAO);
					text+="生成头条小程序结束"+"\r\n";
				}
				else {
					text+="没有选择转换类型"+"\r\n";
					
				}
				  jta.setText(text);
		       	  jta.paintImmediately(jta.getBounds());  
		}

			private void extracted(String type) {
				File filenew = new File(targetdirectory);
				if(!filenew.exists()) {
					filenew.mkdirs();
				}
				System.out.println("sssssssssssss"+sourcedirectory);
				BaiduMiniConterMini.print_file(sourcedirectory,type,sourcedirectory,targetdirectory);
			}});
		
		container.add(panel,BorderLayout.NORTH);// 
		container.add(panel2,BorderLayout.SOUTH);// 
		container.add(panel1,BorderLayout.CENTER);// 
		this.jFrame.setSize(1000, 590);
		this.jFrame.setVisible(true);
		this.jFrame.addWindowListener(new WindowAdapter() { 
					public void windowClosing(WindowEvent arg0) { 
						System.exit(1);
					}
				});
 
	}
	
	
	public static String eventOnImport(JButton developer,JTextArea jta,String type,JTextField sourceText,JTextField targetText) {
		try {
		  File desktop = new File(System.getProperty("user.home")+System.getProperty("file.separator")+"Desktop");
		  JFileChooser chooser = new JFileChooser(desktop);
		  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		  chooser.setMultiSelectionEnabled(true);
		  int returnVal = chooser.showOpenDialog(developer);
		  if (returnVal == JFileChooser.APPROVE_OPTION) {
			  File[] arrfiles = chooser.getSelectedFiles();
			   if (arrfiles == null || arrfiles.length == 0) {
			    return "";
			   }
			  if(type.equals("source")) {
				  text+="源文件:"+arrfiles[0].getAbsolutePath()+"\r\n";
				  sourceText.setText(arrfiles[0].getAbsolutePath());
				
				
			  }else {
				  text+="目标文件:"+arrfiles[0].getAbsolutePath()+"\r\n";
				  targetText.setText(arrfiles[0].getAbsolutePath());
				 
			  }
			   
			    jta.setText(text);
	       		jta.paintImmediately(jta.getBounds());  
	       	    return arrfiles[0].getAbsolutePath();
			}
		  
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		return "";
	}


	
}
 



public class JBaiduMiniConvertWindows {
	public static void main(String[] args) {
		new MyRadio();
	}
}
