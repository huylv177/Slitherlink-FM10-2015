package MainPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MyPanel extends JPanel implements MouseListener {
	int numx;
	int numy;
	boolean[][][] m;
	final static int SIZE_BOX = 40;
	int[][] num;
	String c1, c2, c3;
	int[][] cp;
	ArrayList<String> retur;
	
	public MyPanel(int numx, int numy, int[][] num) {
		this.num = num;
		// m la mang 3 chieu, gia tri dau tien: 0->cac net ngang;1->cac net doc
		m = new boolean[2][numx + 1][numy + 1];

		this.numx = numx;
		this.numy = numy;

		setPreferredSize(null);
		setBackground(new Color(222, 222, 222));
		addMouseListener(this);

		m = new boolean[2][numx + 1][numy + 1];

		genClickPoint();
	}

	boolean[][][] getMainArray(){
		return this.m;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < numx + 1; i++) {
			for (int j = 0; j < numy + 1; j++) {
				g.fillOval(((i + 1) * SIZE_BOX) - 4, ((j + 1) * SIZE_BOX) - 4,
						8, 8);
			}
		}

		Font f = new Font("Arial", Font.PLAIN, 30);
		g.setFont(f);
		for (int i = 0; i < numx; i++) {
			for (int j = 0; j < numy; j++) {
				String thisstr;
				if (num[j][i] != 5) {
					thisstr = num[j][i] + "";
				} else {
					thisstr = "";
				}
				g.drawString(thisstr, i * SIZE_BOX + 52, j * SIZE_BOX + 71);
			}
		}

		for(int i=0;i<2;i++){
			for(int j=0;j<numx+1;j++){
				for(int k=0;k<numy+1;k++){
					if(m[i][j][k]){
						int x1=k*SIZE_BOX+SIZE_BOX;
						int y1=j*SIZE_BOX+SIZE_BOX;
						int x2,y2;
						if(i==0){
							x2=x1+SIZE_BOX;
							y2=y1;
						}
						else{
							x2=x1;
							y2=y1+SIZE_BOX;
						}
						((Graphics2D) g).setStroke(new BasicStroke(3));
						g.drawLine(x1, y1, x2, y2);
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		for (int i = 0; i < 2 * numx * numy + numx + numy; i++) {
			if ((Math.abs(x - cp[i][0]) < SIZE_BOX/4) && (Math.abs(y - cp[i][1]) < SIZE_BOX/4)) {
				m[cp[i][2]][cp[i][3]][cp[i][4]]=!m[cp[i][2]][cp[i][3]][cp[i][4]];
			}
		}
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


	void genClickPoint() {
		cp = new int[2 * numx * numy + numx + numy][5];

		int i = 0;
		int j = 0;
		int k = 0;
		while (i < numx * (numy + 1)) {
			j = 0;
			while (j < numx) {
				cp[i][0] = SIZE_BOX+SIZE_BOX/2 + j * SIZE_BOX;
				cp[i][1] = SIZE_BOX + k * SIZE_BOX;
				cp[i][2] = 0;
				cp[i][3] = k;
				cp[i][4] = j;
				j++;
				i++;
			}
			k++;
		}

		j = 0;
		k = 0;
		while (i < 2 * numx * numy + numx + numy) {
			j = 0;
			while (j < numy + 1) {
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
	
	void getstr(int x,int y,int[][] cp){
		for (int i = 0; i < 2 * numx * numy + numx + numy; i++) {
			if ((Math.abs(x - cp[i][0]) < 10) && (Math.abs(y - cp[i][1]) < 10)) {
				Main.myLabel.setText(cp[i][2] + " " + cp[i][3] + " "+ cp[i][4]);
				return;
			}
		}
		Main.myLabel.setText("* * *");
	}
	
}
