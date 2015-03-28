package MainPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel implements MouseListener {
	//w la so cot, h la so hang
	int w, h;
	
	//tuong tu class Main
	//boolean[][][] m;
	boolean[][] row,col;
	int[][] val;
	
	int SIZE_BOX;
	int[][] num;

	//cp la mang luu toa do cac trung diem cac canh trong game
	//(cp[i][0],cp[i][1]) la toa do cua canh (cp[i][2],cp[i][3],cp[i][4])
	int[][] cp;
	
	public MyPanel(int w, int h, int[][] val) {
		this.val = val;
//s		m = new boolean[2][w + 1][h + 1];
		row = new boolean[h+1][w];
		col = new boolean[h][w+1];
		
		this.w = w;
		this.h = h;
		SIZE_BOX=Main.SIZE_BOX;
		
		setPreferredSize(null);
		setBackground(new Color(222, 222, 222));
		addMouseListener(this);

//		m = new boolean[2][w + 1][h + 1];

		genClickPoint();
	}

//	boolean[][][] getMainArray(){
//		return this.m;
//	}
	boolean[][] getRowArr(){
		return row;
	}
	boolean[][] getColArr(){
		return col;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//ve cac diem 
		for (int i = 0; i < w + 1; i++) {
			for (int j = 0; j < h + 1; j++) {
				g.fillOval(((i + 1) * SIZE_BOX) - 4, ((j + 1) * SIZE_BOX) - 4,
						8, 8);
			}
		}

		//ve cac so trong cac o
		Font f = new Font("Arial", Font.PLAIN, SIZE_BOX*3/5);
		g.setFont(f);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				String thisstr;
				if (val[j][i] != 5) {
					thisstr = val[j][i] + "";
				} else {
					thisstr = "";
				}
				g.drawString(thisstr, (int)((i+1.35) * SIZE_BOX ), (int)((j+1.7) * SIZE_BOX));
			}
		}

		int x1,x2,y1,y2;
		for(int i=0;i<h+1;i++){
			for(int j=0;j<w;j++){
				if(row[i][j]){
					x1=SIZE_BOX*(1+j);
					y1=SIZE_BOX*(1+i);
					x2=x1+SIZE_BOX;
					y2=y1;
			//			System.out.println(x1+" "+y1+" "+x2+" "+y2);
					((Graphics2D) g).setStroke(new BasicStroke(3));
					g.drawLine(x1, y1, x2, y2);
				}
				
			}
		}
		for(int i=0;i<h;i++){
			for(int j=0;j<w+1;j++){
				if(col[i][j]){
					x1=SIZE_BOX*(1+j);
					y1=SIZE_BOX*(1+i);
					x2=x1;
					y2=y1+SIZE_BOX;
					((Graphics2D) g).setStroke(new BasicStroke(3));
					g.drawLine(x1, y1, x2, y2);
				}
				
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		for(int i=0;i<2*w*h+w+h;i++){
			if ((Math.abs(x - cp[i][0]) < SIZE_BOX/4) && (Math.abs(y - cp[i][1]) < SIZE_BOX/4)) {
				if(cp[i][2]==0){
			//		System.out.println(cp[i][3]+" "+cp[i][4]);
					row[cp[i][3]][cp[i][4]]=!row[cp[i][3]][cp[i][4]];
				}
				else if(cp[i][2]==1){
					
					col[cp[i][3]][cp[i][4]]=!col[cp[i][3]][cp[i][4]];
				}
			}
		}
		
//		for(int i=0;i<h+1;i++){
//			for(int j=0;j<w;j++){
//				System.out.print(row[i][j]+ " ");
//			}
//			System.out.print("\n");
//		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//khoi tao cac gia tri cho mang cp
	void genClickPoint() {
		cp = new int[2 * w * h + w + h][5];

		int i = 0;
		int j = 0;
		int k = 0;
		while (i < w * (h + 1)) {
			j = 0;
			while (j < w) {
				cp[i][0] = SIZE_BOX+SIZE_BOX/2 + j * SIZE_BOX;
				cp[i][1] = SIZE_BOX + k * SIZE_BOX;
				cp[i][2] = 0;
				cp[i][3] = k;
				cp[i][4] = j;
			//	System.out.println(i+" "+cp[i][0]+ " "+ cp[i][1]+" "+cp[i][2]+" "+cp[i][3]+" "+cp[i][4]);
				j++;
				i++;
			}
			k++;
		}

		j = 0;
		k = 0;
		while (i < 2 * w * h + w + h) {
			j = 0;
			while (j < w+1) {
				cp[i][0] = SIZE_BOX + j * SIZE_BOX;
				cp[i][1] = SIZE_BOX+SIZE_BOX/2 + k * SIZE_BOX;
				cp[i][2] = 1;
				cp[i][3] = k;
				cp[i][4] = j;
				
				j++;
				i++;
			}
			k++;
		}
	}
	
	//ko can quan tam ham nay dau :3
	void getstr(int x,int y,int[][] cp){
		for (int i = 0; i < 2 * w * h + w + h; i++) {
			if ((Math.abs(x - cp[i][0]) < 10) && (Math.abs(y - cp[i][1]) < 10)) {
				Main.myLabel.setText(cp[i][2] + " " + cp[i][3] + " "+ cp[i][4]);
				return;
			}
		}
		Main.myLabel.setText("* * *");
	}
	
}
