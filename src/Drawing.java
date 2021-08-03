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
					if(what == 1) { // 원 저장
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
					else if(what == 2) { // 사각형 저장
						all_bye();
						Remember_Figure temp = new Remember_Figure();
						temp.im_rect = true;
						temp.rect.first_x = f_x;
						temp.rect.first_y = f_y;
						temp.rect.second_x = s_x;
						temp.rect.second_y = s_y;
						MainFrame.arr.add(temp);
					}
					else if(what == 3) { // 직선 저장
						all_bye();
						Remember_Figure temp = new Remember_Figure();
						temp.im_line = true;
						temp.line.first_x = f_x;
						temp.line.first_y = f_y;
						temp.line.second_x = s_x;
						temp.line.second_y = s_y;
						MainFrame.arr.add(temp);
					}
					else if(what == 5) { // 이동
						for(int i=0; i < MainFrame.arr.size(); i++) { // 현재 선택된 도형들 모두
							change_t(i);
							int nx = Math.min(tx1, tx2);
							int ny = Math.min(ty1, ty2);
							nx += (s_x - f_x);
							ny += (s_y - f_y);
							if( MainFrame.arr.get(i).check == true) { // 현재 선택된 도형이라면
								int temp_w = Math.abs(tx1 - tx2);
								int temp_h = Math.abs(ty1 - ty2);
								set_xy(i, nx, ny, nx+temp_w, ny+temp_h);
							}
						}
					}
					else if(what == 6) { // 지우기
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
					else if(what == 8) { // 도형 채우기
						for(int i = 0; i<MainFrame.arr.size();i++) {
							if(MainFrame.arr.get(i).check == true) {
								// 같은 색깔로 한번 더 클릭이 된거라면 비워준다
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
					else if(what == 9) { // 색깔 변경
						for(int i = 0; i<MainFrame.arr.size();i++) {
							if(MainFrame.arr.get(i).check == true) {
								MainFrame.arr.get(i).color = cur_color;
							}
						}
					}
					else if(what == 10) { // copy/paste 기능
						for(int i=0; i<MainFrame.arr.size(); i++) {
							change_t(i);
							int nx = Math.min(tx1, tx2);
							int ny = Math.min(ty1, ty2);
							nx += (s_x - f_x);
							ny += (s_y - f_y);
							if( MainFrame.arr.get(i).check == true) { // 현재 선택된 도형이라면
								int temp_w = Math.abs(tx1 - tx2);
								int temp_h = Math.abs(ty1 - ty2);
								copy_fig(i, nx, ny, nx+temp_w, ny+temp_h);
							}
						}
					}
					repaint();
				}
				// 선택 기능
				public void mouseClicked(MouseEvent e) { 
					x = e.getX();
					y = e.getY();
					if(what == 4) { // 선택하는 기능
						// MainFrame.arr를 돌면서 해당하는 도형을 찾는다. -> 이때 거꾸로 찾기 때문에 최근에 생성된 도형부터 선택
						boolean flag = false;
						if(MainFrame.arr.size() > 0) {
							for(int i=MainFrame.arr.size()-1;i>=0;i--) {
								change_t(i);
								// 해당 도형이 (타)원이라면 타원의 방정식으로 내부에 있는지 판단 
								if(MainFrame.arr.get(i).im_oval == true) {
									// 타원의 방정식 구하기
									int a = (Math.max(Math.abs(tx1 - tx2), Math.abs(ty1 - ty2)))/2;
									int b = (Math.min(Math.abs(tx1 - tx2), Math.abs(ty1 - ty2)))/2;
									double cmp = (double)(x - MainFrame.arr.get(i).oval.center_x) *(x - MainFrame.arr.get(i).oval.center_x)/(a*a) + (double)(y - MainFrame.arr.get(i).oval.center_y)*(y - MainFrame.arr.get(i).oval.center_y)/(b*b);
									if(cmp <= 1) { // 타원 내부의 점이라면
										flag = true;
										if(MainFrame.arr.get(i).check == false) { // 아직 체크가 안되어 있다면
											MainFrame.arr.get(i).check = true;
										}
										else { // 이미 선택된 도형을 또 클릭하면 선택 해제
											MainFrame.arr.get(i).check = false; 
										}
										break;
									}
								}
								else if(MainFrame.arr.get(i).im_rect == true) {
									// 사각형 범위 체크
									change_t(i);
									int nx1 = Math.min(tx1, tx2);
									int ny1 = Math.min(ty1, ty2);
									int nx2 = Math.max(tx1, tx2);
									int ny2 = Math.max(ty1, ty2);
									if(nx1 <= x && x <= nx2 && ny1 <= y && y <= ny2) { // 사각형 내부의 점이라면
										flag = true;
										if(MainFrame.arr.get(i).check == false) { // 아직 체크가 안되어 있다면
											MainFrame.arr.get(i).check = true;
										}
										else { // 이미 선택된 도형을 또 클릭하면 선택 해제
											MainFrame.arr.get(i).check = false; 
										}
										break;
									}
								}
								else if(MainFrame.arr.get(i).im_line == true) {
									// 왼쪽과 오른쪽 점 구하기 (기울기를 구하려면 미리 구해야 함!)
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
									if( y >= m * (x - nx1) + ny1 - 5 && y <= m * (x - nx1) + ny1 + 5) { // 직선의 방정식 위에 있다면 (직선이 선택하기 힘드므로 5 정도의 오차를 줌)
										if(MainFrame.arr.get(i).check == false) { // 만약 체크가 안되어 있다면
											MainFrame.arr.get(i).check = true;
										}
										else { // 이미 선택된 도형을 또 클릭하면 선택 해제
											MainFrame.arr.get(i).check = false; 
										}
										flag = true;
										break;
									}
								}
							}
						}
						
						if(flag == false) all_bye(); // 만약 도형이 없는 곳을 클릭하면 선택해제
					}
					
					repaint();
				}

				//마우스가 해당 컴포넌트 영역 안으로 들어올때 발생

				public void mouseEntered(MouseEvent e) { }

				//마우스가 해당 컴포넌트 영역 밖으로 나갈때 발생

				public void mouseExited(MouseEvent e) { }
			}
		);
		addMouseMotionListener(
				new MouseMotionAdapter() {
					public void mouseDragged(MouseEvent e) { // 드래그 될 때
						if(what == 7) { // resize
							x = e.getX();
							y = e.getY();
							int idx;
							// 선택된 도형 찾기
							for(int i=0;i<MainFrame.arr.size();i++) {
								if(MainFrame.arr.get(i).check == true) {
									change_t(i);
									set_xy(i,tx1, ty1, x, y);
									break;
								}
							}
							// 도형을 계속해서 repaint해주기
							repaint();
						}
					}
				}
				);
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		// 어떤 작업이 선택되었는지
		
		for(int i = 0; i < MainFrame.arr.size(); i++) {
			change_t(i);
			int nx, ny;
			// 위에 있는 점 구하기
			nx = Math.min(tx1, tx2);
			ny = Math.min(ty1, ty2);
			
			// 만약 선택 기능이 호출되었다면
			if(MainFrame.arr.get(i).check == true) {
				g2.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1,jumsun,0));
				
				
				g.drawRect(nx - 5,ny - 5,Math.abs((tx1 - tx2)) + 10, Math.abs((ty1 - ty2)) + 10);
				g2.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,0));
			}
			// 그림 그릴때 해당 객체의 색깔에 따라 펜 들기
			g.setColor(MainFrame.arr.get(i).color);
			if(MainFrame.arr.get(i).im_oval == true) {
				if(MainFrame.arr.get(i).is_it_fill == false) { // 채워진 도형이 아니라면
					g.drawOval(nx,ny,Math.abs((tx1 - tx2)) , Math.abs((ty1 - ty2)));
				}
				else {
					g.fillOval(nx,ny,Math.abs((tx1 - tx2)) , Math.abs((ty1 - ty2)));
				}
			}
			else if(MainFrame.arr.get(i).im_rect == true) {
				if(MainFrame.arr.get(i).is_it_fill == false) { // 채워진 도형이 아니라면
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
	
	// 도형 정보 받아오기
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
	
	// 도형 재설정
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
	
	void all_bye() { // 모든 도형 선택 취소
		for(int i = 0; i<MainFrame.arr.size(); i++) {
			MainFrame.arr.get(i).check = false;
		}
	}
	
	// 도형 복사
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