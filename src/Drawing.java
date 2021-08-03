import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.awt.geom.*;

public class Drawing extends JPanel implements Serializable{
	
	int f_x, f_y, s_x, s_y;
	int x, y;
	int tx1, tx2, ty1, ty2;
	float jumsun[] = {10,10f};
	static int what;
	static Color cur_color;
	
	// Mouse Event Handling
	public Drawing() {
		addMouseListener(
			new MouseListener() {
				public void mousePressed(MouseEvent e) {
					f_x = e.getX();
					f_y = e.getY();
				}
				public void mouseReleased(MouseEvent e) {
					s_x = e.getX();
					s_y = e.getY();
					if(what == 1) { // �� ����
						all_bye();
						Remember_Figure temp = new Remember_Figure();
						temp.im_oval = true;
						temp.oval.first_x = f_x;
						temp.oval.first_y = f_y;
						temp.oval.second_x = s_x;
						temp.oval.second_y = s_y;
						temp.oval.center_x = (f_x + s_x)/2;
						temp.oval.center_y = (f_y + s_y)/2;
						temp.oval.r = Math.abs(f_y - temp.oval.center_y);
						MainFrame.arr.add(temp);
					}
					else if(what == 2) { // �簢�� ����
						all_bye();
						Remember_Figure temp = new Remember_Figure();
						temp.im_rect = true;
						temp.rect.first_x = f_x;
						temp.rect.first_y = f_y;
						temp.rect.second_x = s_x;
						temp.rect.second_y = s_y;
						MainFrame.arr.add(temp);
					}
					else if(what == 3) { // ���� ����
						all_bye();
						Remember_Figure temp = new Remember_Figure();
						temp.im_line = true;
						temp.line.first_x = f_x;
						temp.line.first_y = f_y;
						temp.line.second_x = s_x;
						temp.line.second_y = s_y;
						MainFrame.arr.add(temp);
					}
					else if(what == 5) { // �̵�
						for(int i=0; i < MainFrame.arr.size(); i++) { // ���� ���õ� ������ ���
							change_t(i);
							int nx = Math.min(tx1, tx2);
							int ny = Math.min(ty1, ty2);
							nx += (s_x - f_x);
							ny += (s_y - f_y);
							if( MainFrame.arr.get(i).check == true) { // ���� ���õ� �����̶��
								int temp_w = Math.abs(tx1 - tx2);
								int temp_h = Math.abs(ty1 - ty2);
								set_xy(i, nx, ny, nx+temp_w, ny+temp_h);
							}
						}
					}
					else if(what == 6) { // �����
						while(true) {
							boolean flag = false;
							for(int i = 0; i<MainFrame.arr.size(); i++) {
								if(MainFrame.arr.get(i).check == true) {
									MainFrame.arr.remove(i);
									flag = true;
									break;
								}
							}
							if(flag == false) break;
						}
					}
					else if(what == 8) { // ���� ä���
						for(int i = 0; i<MainFrame.arr.size();i++) {
							if(MainFrame.arr.get(i).check == true) {
								// ���� ����� �ѹ� �� Ŭ���� �ȰŶ�� ����ش�
								if(MainFrame.arr.get(i).is_it_fill == true && MainFrame.arr.get(i).color == cur_color){
									MainFrame.arr.get(i).is_it_fill = false;
									MainFrame.arr.get(i).color = cur_color;
								}
								else {
									MainFrame.arr.get(i).is_it_fill = true;
									MainFrame.arr.get(i).color = cur_color;
								}
							}
							
						}
						
					}
					else if(what == 9) { // ���� ����
						for(int i = 0; i<MainFrame.arr.size();i++) {
							if(MainFrame.arr.get(i).check == true) {
								MainFrame.arr.get(i).color = cur_color;
							}
						}
					}
					else if(what == 10) { // copy/paste ���
						for(int i=0; i<MainFrame.arr.size(); i++) {
							change_t(i);
							int nx = Math.min(tx1, tx2);
							int ny = Math.min(ty1, ty2);
							nx += (s_x - f_x);
							ny += (s_y - f_y);
							if( MainFrame.arr.get(i).check == true) { // ���� ���õ� �����̶��
								int temp_w = Math.abs(tx1 - tx2);
								int temp_h = Math.abs(ty1 - ty2);
								copy_fig(i, nx, ny, nx+temp_w, ny+temp_h);
							}
						}
					}
					repaint();
				}
				// ���� ���
				public void mouseClicked(MouseEvent e) { 
					x = e.getX();
					y = e.getY();
					if(what == 4) { // �����ϴ� ���
						// MainFrame.arr�� ���鼭 �ش��ϴ� ������ ã�´�. -> �̶� �Ųٷ� ã�� ������ �ֱٿ� ������ �������� ����
						boolean flag = false;
						if(MainFrame.arr.size() > 0) {
							for(int i=MainFrame.arr.size()-1;i>=0;i--) {
								change_t(i);
								// �ش� ������ (Ÿ)���̶�� Ÿ���� ���������� ���ο� �ִ��� �Ǵ� 
								if(MainFrame.arr.get(i).im_oval == true) {
									// Ÿ���� ������ ���ϱ�
									int a = (Math.max(Math.abs(tx1 - tx2), Math.abs(ty1 - ty2)))/2;
									int b = (Math.min(Math.abs(tx1 - tx2), Math.abs(ty1 - ty2)))/2;
									double cmp = (double)(x - MainFrame.arr.get(i).oval.center_x) *(x - MainFrame.arr.get(i).oval.center_x)/(a*a) + (double)(y - MainFrame.arr.get(i).oval.center_y)*(y - MainFrame.arr.get(i).oval.center_y)/(b*b);
									if(cmp <= 1) { // Ÿ�� ������ ���̶��
										flag = true;
										if(MainFrame.arr.get(i).check == false) { // ���� üũ�� �ȵǾ� �ִٸ�
											MainFrame.arr.get(i).check = true;
										}
										else { // �̹� ���õ� ������ �� Ŭ���ϸ� ���� ����
											MainFrame.arr.get(i).check = false; 
										}
										break;
									}
								}
								else if(MainFrame.arr.get(i).im_rect == true) {
									// �簢�� ���� üũ
									change_t(i);
									int nx1 = Math.min(tx1, tx2);
									int ny1 = Math.min(ty1, ty2);
									int nx2 = Math.max(tx1, tx2);
									int ny2 = Math.max(ty1, ty2);
									if(nx1 <= x && x <= nx2 && ny1 <= y && y <= ny2) { // �簢�� ������ ���̶��
										flag = true;
										if(MainFrame.arr.get(i).check == false) { // ���� üũ�� �ȵǾ� �ִٸ�
											MainFrame.arr.get(i).check = true;
										}
										else { // �̹� ���õ� ������ �� Ŭ���ϸ� ���� ����
											MainFrame.arr.get(i).check = false; 
										}
										break;
									}
								}
								else if(MainFrame.arr.get(i).im_line == true) {
									// ���ʰ� ������ �� ���ϱ� (���⸦ ���Ϸ��� �̸� ���ؾ� ��!)
									int nx1, nx2, ny1, ny2;
									if(tx1>tx2) {
										nx1 = tx2;
										ny1 = ty2;
										nx2 = tx1;
										ny2 = ty1;
									}
									else {
										nx1 = tx1;
										ny1 = ty1;
										nx2 = tx2;
										ny2 = ty2;
									}
									double m = (double)(ny2-ny1)/(nx2-nx1);
									if( y >= m * (x - nx1) + ny1 - 5 && y <= m * (x - nx1) + ny1 + 5) { // ������ ������ ���� �ִٸ� (������ �����ϱ� ����Ƿ� 5 ������ ������ ��)
										if(MainFrame.arr.get(i).check == false) { // ���� üũ�� �ȵǾ� �ִٸ�
											MainFrame.arr.get(i).check = true;
										}
										else { // �̹� ���õ� ������ �� Ŭ���ϸ� ���� ����
											MainFrame.arr.get(i).check = false; 
										}
										flag = true;
										break;
									}
								}
							}
						}
						
						if(flag == false) all_bye(); // ���� ������ ���� ���� Ŭ���ϸ� ��������
					}
					
					repaint();
				}

				//���콺�� �ش� ������Ʈ ���� ������ ���ö� �߻�

				public void mouseEntered(MouseEvent e) { }

				//���콺�� �ش� ������Ʈ ���� ������ ������ �߻�

				public void mouseExited(MouseEvent e) { }
			}
		);
		addMouseMotionListener(
				new MouseMotionAdapter() {
					public void mouseDragged(MouseEvent e) { // �巡�� �� ��
						if(what == 7) { // resize
							x = e.getX();
							y = e.getY();
							int idx;
							// ���õ� ���� ã��
							for(int i=0;i<MainFrame.arr.size();i++) {
								if(MainFrame.arr.get(i).check == true) {
									change_t(i);
									set_xy(i,tx1, ty1, x, y);
									break;
								}
							}
							// ������ ����ؼ� repaint���ֱ�
							repaint();
						}
					}
				}
				);
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		// � �۾��� ���õǾ�����
		
		for(int i = 0; i < MainFrame.arr.size(); i++) {
			change_t(i);
			int nx, ny;
			// ���� �ִ� �� ���ϱ�
			nx = Math.min(tx1, tx2);
			ny = Math.min(ty1, ty2);
			
			// ���� ���� ����� ȣ��Ǿ��ٸ�
			if(MainFrame.arr.get(i).check == true) {
				g2.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1,jumsun,0));
				
				
				g.drawRect(nx - 5,ny - 5,Math.abs((tx1 - tx2)) + 10, Math.abs((ty1 - ty2)) + 10);
				g2.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,0));
			}
			// �׸� �׸��� �ش� ��ü�� ���� ���� �� ���
			g.setColor(MainFrame.arr.get(i).color);
			if(MainFrame.arr.get(i).im_oval == true) {
				if(MainFrame.arr.get(i).is_it_fill == false) { // ä���� ������ �ƴ϶��
					g.drawOval(nx,ny,Math.abs((tx1 - tx2)) , Math.abs((ty1 - ty2)));
				}
				else {
					g.fillOval(nx,ny,Math.abs((tx1 - tx2)) , Math.abs((ty1 - ty2)));
				}
			}
			else if(MainFrame.arr.get(i).im_rect == true) {
				if(MainFrame.arr.get(i).is_it_fill == false) { // ä���� ������ �ƴ϶��
					g.drawRect(nx,ny,Math.abs((tx1 - tx2)) , Math.abs((ty1 - ty2)));
				}
				else {
					g.fillRect(nx,ny,Math.abs((tx1 - tx2)) , Math.abs((ty1 - ty2)));
				}
			}
			else if(MainFrame.arr.get(i).im_line == true) {
				// g.drawLine(MainFrame.arr.get(i).line.first_x, MainFrame.arr.get(i).line.first_y, MainFrame.arr.get(i).line.second_x,MainFrame.arr.get(i).line.second_y);
				g.drawLine(tx1, ty1, tx2, ty2);
			}
			g.setColor(Color.BLACK);
			
			
		}
		
	}
	
	// ���� ���� �޾ƿ���
	void change_t(int i) {
		if(MainFrame.arr.get(i).im_oval == true) {
			
			tx1 = MainFrame.arr.get(i).oval.first_x;
			ty1 = MainFrame.arr.get(i).oval.first_y;
			tx2 = MainFrame.arr.get(i).oval.second_x;
			ty2 = MainFrame.arr.get(i).oval.second_y;
			
		}
		else if(MainFrame.arr.get(i).im_rect == true) {
			tx1 = MainFrame.arr.get(i).rect.first_x;
			ty1 = MainFrame.arr.get(i).rect.first_y;
			tx2 = MainFrame.arr.get(i).rect.second_x;
			ty2 = MainFrame.arr.get(i).rect.second_y;
		}
		else if(MainFrame.arr.get(i).im_line == true) {
			tx1 = MainFrame.arr.get(i).line.first_x;
			ty1 = MainFrame.arr.get(i).line.first_y;
			tx2 = MainFrame.arr.get(i).line.second_x;
			ty2 = MainFrame.arr.get(i).line.second_y;
		}
	}
	
	// ���� �缳��
	void set_xy(int i, int a, int b, int c, int d) {
		if(MainFrame.arr.get(i).im_oval == true) {
			MainFrame.arr.get(i).oval.first_x = a;
			MainFrame.arr.get(i).oval.first_y = b;
			MainFrame.arr.get(i).oval.second_x = c;
			MainFrame.arr.get(i).oval.second_y = d;
			MainFrame.arr.get(i).oval.center_x = (a + c)/2;
			MainFrame.arr.get(i).oval.center_y = (b + d)/2;
			MainFrame.arr.get(i).oval.r = Math.abs(b - MainFrame.arr.get(i).oval.center_y);
			
		}
		else if(MainFrame.arr.get(i).im_rect == true) {
			MainFrame.arr.get(i).rect.first_x = a;
			MainFrame.arr.get(i).rect.first_y = b;
			MainFrame.arr.get(i).rect.second_x = c;
			MainFrame.arr.get(i).rect.second_y = d;
		}
		else if(MainFrame.arr.get(i).im_line == true) {
			MainFrame.arr.get(i).line.first_x = tx1 + (s_x - f_x);
			MainFrame.arr.get(i).line.first_y = ty1 + (s_y - f_y);
			MainFrame.arr.get(i).line.second_x = tx2 + (s_x - f_x);
			MainFrame.arr.get(i).line.second_y = ty2 + (s_y - f_y);
		}
	}
	
	void all_bye() { // ��� ���� ���� ���
		for(int i = 0; i<MainFrame.arr.size(); i++) {
			MainFrame.arr.get(i).check = false;
		}
	}
	
	// ���� ����
	void copy_fig(int i, int a, int b, int c, int d){
		Remember_Figure temp = new Remember_Figure();
		if(MainFrame.arr.get(i).im_oval == true) {
			temp.im_oval = true;
			temp.oval.first_x = a;
			temp.oval.first_y = b;
			temp.oval.second_x = c;
			temp.oval.second_y = d;
			temp.oval.center_x = (a+c)/2;
			temp.oval.center_y = (b+d)/2;
			temp.color = MainFrame.arr.get(i).color;
			temp.is_it_fill = MainFrame.arr.get(i).is_it_fill;
			temp.oval.r = Math.abs(b - MainFrame.arr.get(i).oval.center_y);
			
		}
		else if(MainFrame.arr.get(i).im_rect == true) {
			temp.im_rect = true;
			temp.rect.first_x = a;
			temp.rect.first_y = b;
			temp.rect.second_x = c;
			temp.rect.second_y = d;
			temp.is_it_fill = MainFrame.arr.get(i).is_it_fill;
			temp.color = MainFrame.arr.get(i).color;
		}
		else if(MainFrame.arr.get(i).im_line == true) {
			temp.im_line = true;
			temp.line.first_x = tx1 + (s_x - f_x);
			temp.line.first_y = ty1 + (s_y - f_y);
			temp.line.second_x = tx2 + (s_x - f_x);
			temp.line.second_y = ty2 + (s_y - f_y);
			temp.color = MainFrame.arr.get(i).color;
		}
		MainFrame.arr.add(temp);
	}
}