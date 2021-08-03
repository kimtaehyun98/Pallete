import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class MainFrame extends JFrame implements ActionListener, Serializable{
	// 메뉴 바
	JMenuItem Save, Load, New, Info;
	JMenuBar bar;
	JMenu File, Help;
	Figure_Buttons draw_Fig;
	Drawing paintPanel;
	
	// 도형들을 저장하는 ArrayList 생성
	static ArrayList <Remember_Figure> arr = new ArrayList();
	
	public MainFrame(String str) {
		
		super("그림판");
		
		Color_button Color_list = new Color_button();
		
		paintPanel = new Drawing();
		
		// 왼쪽 사이드바 - 도형 그리기
		draw_Fig = new Figure_Buttons();
		
		paintPanel.setBackground(Color.WHITE);
		
		this.add(paintPanel, BorderLayout.CENTER);
		
		this.add(draw_Fig, BorderLayout.WEST);
		
		this.add(Color_list, BorderLayout.EAST);
		
		
		bar = new JMenuBar();
		
		File = new JMenu("File");
		File.setMnemonic('F');
		
		Help = new JMenu("Help");
		Help.setMnemonic('H');
		
		ImageIcon info = new ImageIcon("images/help.png");
		Info = new JMenuItem("Info", info);
		
		New = new JMenuItem("New");
		New.setMnemonic('N');
		
		Save = new JMenuItem("Save");
		Save.setMnemonic('S');
		
		Load = new JMenuItem("Load");
		Load.setMnemonic('L');
		
		File.add(Save);
		File.add(Load);
		File.add(New);
		
		Help.add(Info);
		
		Save.addActionListener(this);
		Load.addActionListener(this);
		New.addActionListener(this);
		
		
		bar.add(File);
		bar.add(Help);
		
		this.add(bar,BorderLayout.NORTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000,800);
		this.setVisible(true);
	}
		
	// 메뉴에서 버튼 클릭시 발생되는 이벤트 처리

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == New){
			arr.clear();					// 그래픽배열을 비워서 초기화
			draw_Fig.change_to_white();
			Drawing.what = 0;
			paintPanel.repaint();

		// FILE - OPEN
		}
		else if(e.getSource() == Save){
			
			// 파일 다이얼로그를 저장(SAVE)모드로 메모리에 띄운다. 
			FileDialog fdl = new FileDialog(this, "저장", FileDialog.SAVE);
			

			fdl.setVisible(true);	// 다이얼로그를 화면에 보여주기

			// 다이얼로그에서 선택된 경로와 파일명을 변수에 담는다.

			String dir = fdl.getDirectory();

			String file = fdl.getFile();
			
			if(dir == null || file == null){	// 파일 선택 없이 다이얼로그를 종료할시 작업 수행 없이 바로 리턴

				return ;

			}
			// ObjectOutputStream oos에 파일경로와 이름을 설정한다.

			try{

				ObjectOutputStream oos = 

						new ObjectOutputStream(

								new BufferedOutputStream(

										new FileOutputStream(

												new File(dir,file))));

				// 그래픽배열의 내용을 ObjectOutputStream이 지정된 경로와 이름으로 저장한다.
				
				oos.writeObject(arr);

				oos.close();
				
				
			}catch(Exception ee){

				ee.printStackTrace();

			}
			
			
		}
		else if(e.getSource() == Load){	

			

			// 파일 다이얼로그를 열기(LOAD)모드로 메모리에 띄운다.

			FileDialog fdlg = new FileDialog(this, "열기", FileDialog.LOAD);

			

			fdlg.setVisible(true);	// 다이얼로그를 화면에 보여주기



			// 다이얼로그에서 선택된 경로와 파일명을 변수에 담는다.

			String dir = fdlg.getDirectory();

			String file = fdlg.getFile();

			

			if(dir == null || file == null){	// 파일 선택 없이 다이얼로그를 종료할시 작업 수행 없이 바로 리턴

				return;

			}

			// ObjectInputStream oos 에 파일경로와 이름을 설정한다.

			try{

				ObjectInputStream oos = 

						new ObjectInputStream(

								new BufferedInputStream(

										new FileInputStream(new File(dir,file))));

				

				// ObjectInputStream이 파일을 읽어서  그래픽배열에 담는다.

				arr = (ArrayList<Remember_Figure>)oos.readObject();

				oos.close();

			}catch(Exception ex){

				ex.printStackTrace();

			}
			paintPanel.repaint();
		}
		draw_Fig.change_to_white();
		Drawing.what = 0;
	}
	
}