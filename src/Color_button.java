import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Color_button extends JPanel{
	
	Color []ColorList = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK,  Color.WHITE, Color.black};
	JPanel ColorPanel;
	
	public Color_button() {
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		ColorPanel = new JPanel();
		GridLayout colorLayout = new GridLayout(8,1);
		
		ColorPanel.setLayout(colorLayout);
		
		// 색깔 버튼을 추가시켜주기
		JButton temp;
		for (int i =0 ; i < 8; i++){
			temp = new JButton();
			temp.setBorderPainted(true);
			temp.setBackground(ColorList[i]);
			temp.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JButton current = (JButton)e.getSource();
					Color curColor=current.getBackground();
					Drawing.cur_color = curColor;
				}
			
			});
			temp.setSize(30, 10);
			temp.setBorderPainted(false);  temp.setFocusPainted(false);
			ColorPanel.add(temp);
		}
		this.add(ColorPanel);
	}
	
}
