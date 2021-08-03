import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class Remember_Figure implements Serializable{
	
	Oval oval = new Oval();
	Rectangle rect = new Rectangle();
	Line line = new Line();
	
	Color color = Color.black;
	
	boolean im_oval = false;
	boolean im_rect = false;
	boolean im_line = false;
	
	boolean is_it_fill = false; // 채워진 도형인지 아닌지 판단
	
	boolean check = false;
	
	Color return_color() {
		return color;
	}
}
