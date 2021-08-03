import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.util.*;


public class Figure_Buttons extends JPanel implements ActionListener{
	
	int what_to_do = 0;
	JButton button_oval, button_rect , button_line ,button_select, button_move, button_erase, button_change , button_fill , button_c_c, button_copy;
	ImageIcon drawOval, drawrect, drawline, select, move, erase, change, fill, color_change, copy;
	Color b = Color.GRAY;
	Color background = new Color(255, 255, 255); //255 -> white
	
	public Figure_Buttons() {
		
		// 버튼 생성
		this.setBackground(background);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		drawOval = new ImageIcon("images/oval.png");
		drawrect = new ImageIcon("images/rectangle.png");
		drawline = new ImageIcon("images/line.png");
		select = new ImageIcon("images/choose.png");
		move = new ImageIcon("images/move.png");
		erase = new ImageIcon("images/erase.png");
		change = new ImageIcon("images/change.png");
		fill = new ImageIcon("images/fill.png");
		color_change = new ImageIcon("images/color_changed.png");
		copy = new ImageIcon("images/copy.png");
		
		button_oval = new JButton(drawOval);
		button_rect = new JButton(drawrect);
		button_line = new JButton(drawline);
		button_select = new JButton(select);
		button_move = new JButton(move);
		button_erase = new JButton(erase);
		button_change = new JButton(change);
		button_fill = new JButton(fill);
		button_c_c = new JButton(color_change);
		button_copy = new JButton(copy);
		
		button_oval.setSize(30,30); button_oval.setName("oval"); button_oval.setBackground(background); button_oval.setBorderPainted(false);  button_oval.setFocusPainted(false);
		button_rect.setSize(30,30); button_rect.setName("rect"); button_rect.setBackground(background); button_rect.setBorderPainted(false);  button_rect.setFocusPainted(false);
		button_line.setSize(30,30); button_line.setName("line"); button_line.setBackground(background); button_line.setBorderPainted(false);  button_line.setFocusPainted(false);
		button_select.setSize(30,30); button_select.setName("select"); button_select.setBackground(background); button_select.setBorderPainted(false);  button_select.setFocusPainted(false);
		button_move.setSize(30,30); button_move.setName("move"); button_move.setBackground(background); button_move.setBorderPainted(false);  button_move.setFocusPainted(false);
		button_erase.setSize(30,30); button_erase.setName("erase"); button_erase.setBackground(background); button_erase.setBorderPainted(false);  button_erase.setFocusPainted(false);
		button_change.setSize(30,30); button_change.setName("change"); button_change.setBackground(background); button_change.setBorderPainted(false);  button_change.setFocusPainted(false);
		button_fill.setSize(30,30); button_fill.setName("fill"); button_fill.setBackground(background); button_fill.setBorderPainted(false);  button_fill.setFocusPainted(false);
		button_c_c.setSize(30,30); button_c_c.setName("cc"); button_c_c.setBackground(background); button_c_c.setBorderPainted(false); button_c_c.setFocusPainted(false);
		button_copy.setSize(30,30); button_copy.setName("copy"); button_copy.setBackground(background); button_copy.setBorderPainted(false); button_copy.setFocusPainted(false);
		
		button_oval.addActionListener(this);
		button_rect.addActionListener(this);
		button_line.addActionListener(this);
		button_select.addActionListener(this);
		button_move.addActionListener(this);
		button_erase.addActionListener(this);
		button_change.addActionListener(this);
		button_fill.addActionListener(this);
		button_c_c.addActionListener(this);
		button_copy.addActionListener(this);
		
		this.add(button_oval);
		this.add(button_rect);
		this.add(button_line);
		this.add(button_select);
		this.add(button_c_c);
		this.add(button_fill);
		this.add(button_move);
		this.add(button_erase);
		this.add(button_copy);
		this.add(button_change);
		
		
	}
	
	// 혹시 필요하면 쓸 예정	// String str[] = {"oval", "rect", "line", "select", "move", "erase", "change", "fill", "cc", "copy"};
	
	public void actionPerformed(ActionEvent e) {
		
		JButton currentButton = (JButton)e.getSource();
		change_to_white();
		switch(currentButton.getName())
		{
		case "oval" : 
			Drawing.what = 1;
			button_oval.setBackground(b);
			break;
		case "rect" : 
			Drawing.what = 2;
			button_rect.setBackground(b);
			break;
		case "line" :
			Drawing.what = 3;
			button_line.setBackground(b);
			break;
		case "select" :
			Drawing.what = 4;
			button_select.setBackground(b);
			break;
		case "move" :
			Drawing.what = 5;
			button_move.setBackground(b);
			break;
		case "erase" :
			Drawing.what = 6;
			button_erase.setBackground(b);
			break;
		case "change" :
			Drawing.what = 7;
			button_change.setBackground(b);
			break;
		case "fill" :
			Drawing.what = 8;
			button_fill.setBackground(b);
			break;
		case "cc" :
			Drawing.what = 9;
			button_c_c.setBackground(b);
			break;
		case "copy" :
			Drawing.what = 10;
			button_copy.setBackground(b);
			break;
		default : break;
		}
	}
	
	// 자기 자신만 gray, 나머지는 전부 white로
	void change_to_white() {
		button_oval.setBackground(background);
		button_rect.setBackground(background);
		button_line.setBackground(background);
		button_select.setBackground(background);
		button_move.setBackground(background);
		button_erase.setBackground(background);
		button_change.setBackground(background);
		button_fill.setBackground(background);
		button_c_c.setBackground(background);
		button_copy.setBackground(background);
		return;
	}
	
}
