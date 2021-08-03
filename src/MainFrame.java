import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class MainFrame extends JFrame implements ActionListener, Serializable{
	// �޴� ��
	JMenuItem Save, Load, New, Info;
	JMenuBar bar;
	JMenu File, Help;
	Figure_Buttons draw_Fig;
	Drawing paintPanel;
	
	// �������� �����ϴ� ArrayList ����
	static ArrayList <Remember_Figure> arr = new ArrayList();
	
	public MainFrame(String str) {
		
		super("�׸���");
		
		Color_button Color_list = new Color_button();
		
		paintPanel = new Drawing();
		
		// ���� ���̵�� - ���� �׸���
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
		
	// �޴����� ��ư Ŭ���� �߻��Ǵ� �̺�Ʈ ó��

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == New){
			arr.clear();					// �׷��ȹ迭�� ����� �ʱ�ȭ
			draw_Fig.change_to_white();
			Drawing.what = 0;
			paintPanel.repaint();

		// FILE - OPEN
		}
		else if(e.getSource() == Save){
			
			// ���� ���̾�α׸� ����(SAVE)���� �޸𸮿� ����. 
			FileDialog fdl = new FileDialog(this, "����", FileDialog.SAVE);
			

			fdl.setVisible(true);	// ���̾�α׸� ȭ�鿡 �����ֱ�

			// ���̾�α׿��� ���õ� ��ο� ���ϸ��� ������ ��´�.

			String dir = fdl.getDirectory();

			String file = fdl.getFile();
			
			if(dir == null || file == null){	// ���� ���� ���� ���̾�α׸� �����ҽ� �۾� ���� ���� �ٷ� ����

				return ;

			}
			// ObjectOutputStream oos�� ���ϰ�ο� �̸��� �����Ѵ�.

			try{

				ObjectOutputStream oos = 

						new ObjectOutputStream(

								new BufferedOutputStream(

										new FileOutputStream(

												new File(dir,file))));

				// �׷��ȹ迭�� ������ ObjectOutputStream�� ������ ��ο� �̸����� �����Ѵ�.
				
				oos.writeObject(arr);

				oos.close();
				
				
			}catch(Exception ee){

				ee.printStackTrace();

			}
			
			
		}
		else if(e.getSource() == Load){	

			

			// ���� ���̾�α׸� ����(LOAD)���� �޸𸮿� ����.

			FileDialog fdlg = new FileDialog(this, "����", FileDialog.LOAD);

			

			fdlg.setVisible(true);	// ���̾�α׸� ȭ�鿡 �����ֱ�



			// ���̾�α׿��� ���õ� ��ο� ���ϸ��� ������ ��´�.

			String dir = fdlg.getDirectory();

			String file = fdlg.getFile();

			

			if(dir == null || file == null){	// ���� ���� ���� ���̾�α׸� �����ҽ� �۾� ���� ���� �ٷ� ����

				return;

			}

			// ObjectInputStream oos �� ���ϰ�ο� �̸��� �����Ѵ�.

			try{

				ObjectInputStream oos = 

						new ObjectInputStream(

								new BufferedInputStream(

										new FileInputStream(new File(dir,file))));

				

				// ObjectInputStream�� ������ �о  �׷��ȹ迭�� ��´�.

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