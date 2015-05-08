package Swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import MainPackage.SatEncode;

public class MyPanel extends JPanel implements MouseListener {
	// w la so cot, h la so hang
	int w, h;

	boolean[][] rowRight, rowLeft, colUp, colDown;
	int[][] val;

	int SIZE_BOX;
	int[][] num;

	// cp la mang luu toa do cac trung diem cac canh trong game
	// (cp[i][0],cp[i][1]) la toa do cua canh (cp[i][2],cp[i][3],cp[i][4])
	int[][] cp;

	public MyPanel(int w, int h, int[][] val) {
		init(w,h,val);
		addMouseListener(this);
		genClickPoint();
	}
	public void init(int w, int h, int[][] val){
		this.val = val;
		this.w = w;
		this.h = h;

		rowLeft = new boolean[h + 1][w];
		rowRight = new boolean[h + 1][w];
		colUp = new boolean[h][w + 1];
		colDown = new boolean[h][w + 1];

		SIZE_BOX = MainFrame.SIZE_BOX;

//		setPreferredSize(new Dimension((w+2)*SIZE_BOX,(h+2)*SIZE_BOX));
//		setBackground(Color.cyan);
	}

	boolean[][] getRowLeftArr() {
		return rowLeft;
	}

	boolean[][] getRowRightArr() {
		return rowRight;
	}

	boolean[][] getColUpArr() {
		return colUp;
	}

	boolean[][] getColDownArr() {
		return colDown;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setPreferredSize(new Dimension((w+2)*SIZE_BOX,(h+2)*SIZE_BOX));
		// ve cac diem
		for (int i = 0; i < w + 1; i++) {
			for (int j = 0; j < h + 1; j++) {
				g.fillOval(((i + 1) * SIZE_BOX) - 4, ((j + 1) * SIZE_BOX) - 4,
						8, 8);
			}
		}

		// ve cac so trong cac o
		Font f = new Font("Arial", Font.PLAIN, SIZE_BOX * 3 / 5);
		g.setFont(f);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				String thisstr;
				if (val[j][i] != 5) {
					thisstr = val[j][i] + "";
				} else {
					thisstr = "";
				}
				g.drawString(thisstr, (int) ((i + 1.35) * SIZE_BOX),
						(int) ((j + 1.7) * SIZE_BOX));
			}
		}

		// ve doan thang noi cac dinh
		int x1, x2, y1, y2;
		for (int i = 0; i < h + 1; i++) {
			for (int j = 0; j < w; j++) {
				if (rowLeft[i][j] || rowRight[i][j]) {
					x1 = SIZE_BOX * (1 + j);
					y1 = SIZE_BOX * (1 + i);
					x2 = x1 + SIZE_BOX;
					y2 = y1;
					// System.out.println(x1+" "+y1+" "+x2+" "+y2);
					((Graphics2D) g).setStroke(new BasicStroke(3));
					g.drawLine(x1, y1, x2, y2);
				}

			}
		}
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w + 1; j++) {
				if (colUp[i][j] || colDown[i][j]) {
					x1 = SIZE_BOX * (1 + j);
					y1 = SIZE_BOX * (1 + i);
					x2 = x1;
					y2 = y1 + SIZE_BOX;
					((Graphics2D) g).setStroke(new BasicStroke(3));
					g.drawLine(x1, y1, x2, y2);
				}

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		int x = e.getX();
//	int y = e.getY();
//	for(int i=0;i<2*w*h+w+h;i++){
//		if ((Math.abs(x - cp[i][0]) < SIZE_BOX/4) && (Math.abs(y - cp[i][1]) < SIZE_BOX/4)) {
//			if(cp[i][2]==0){
//		//		System.out.println(cp[i][3]+" "+cp[i][4]);
//				row[cp[i][3]][cp[i][4]]=!row[cp[i][3]][cp[i][4]];
//			}
//			else if(cp[i][2]==1){
//				
//				col[cp[i][3]][cp[i][4]]=!col[cp[i][3]][cp[i][4]];
//			}
//		}
//	}
	
//	for(int i=0;i<h+1;i++){
//		for(int j=0;j<w;j++){
//			System.out.print(row[i][j]+ " ");
//		}
//		System.out.print("\n");
//	}
//	repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	// khoi tao cac gia tri cho mang cp
	void genClickPoint() {
		cp = new int[2 * w * h + w + h][5];

		int i = 0;
		int j = 0;
		int k = 0;
		while (i < w * (h + 1)) {
			j = 0;
			while (j < w) {
				cp[i][0] = SIZE_BOX + SIZE_BOX / 2 + j * SIZE_BOX;
				cp[i][1] = SIZE_BOX + k * SIZE_BOX;
				cp[i][2] = 0;
				cp[i][3] = k;
				cp[i][4] = j;
				// System.out.println(i+" "+cp[i][0]+ " "+
				// cp[i][1]+" "+cp[i][2]+" "+cp[i][3]+" "+cp[i][4]);
				j++;
				i++;
			}
			k++;
		}

		j = 0;
		k = 0;
		while (i < 2 * w * h + w + h) {
			j = 0;
			while (j < w + 1) {
				cp[i][0] = SIZE_BOX + j * SIZE_BOX;
				cp[i][1] = SIZE_BOX + SIZE_BOX / 2 + k * SIZE_BOX;
				cp[i][2] = 1;
				cp[i][3] = k;
				cp[i][4] = j;

				j++;
				i++;
			}
			k++;
		}
	}
	public void repaintCanvas(String[] stringDecoded) {

		clearArray(colDown, w, h + 1);
		clearArray(rowRight, w + 1, w);

		// doc output, chuyen String thanh array int,
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < stringDecoded.length - 1; i++) {
			b.add(Integer.parseInt(stringDecoded[i]));
			// System.out.println(Integer.parseInt(stringDecoded[i])+" ");
		}
		for (int i = 0; i < b.size(); i++) {
			int edgeCode = Math.abs(b.get(i));
			
			if (b.get(i) == edgeCode) {
				int[] temp = decodeEdge(edgeCode);
//				 if(edgeCode==3) System.out.println(temp[0] +" "+temp[1]+" "+temp[2]);

				if (temp[2] == 0) {
					rowRight[temp[0]][temp[1]] = true;
				} else if (temp[2] == 1) {
					colDown[temp[0]][temp[1]] = true;
				}
			}
		}
//		setPreferredSize(new Dimension((w+2)*SIZE_BOX,(h+2)*SIZE_BOX));
		repaint();
	}
	
	public void clearArray(boolean[][] a, int r, int c) {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				a[i][j] = false;
			}
		}
	}
	
	int[] decodeEdge(int edgeCode) {
		int[] e = new int[3];
		int d = w * (h + 1) + (w + 1) * h;
		int k = w * (h + 1);

		if (edgeCode > d) {
			edgeCode -= d;
		}
		if (edgeCode <= k) {
			if (edgeCode % h == 0) {
				e[0] = edgeCode / h - 1;
				e[1] = h - 1;
				e[2] = 0;
			} else {
				e[0] = edgeCode / h;
				e[1] = edgeCode % h - 1;
				e[2] = 0;
			}
		} else {
			edgeCode = edgeCode - k;
			if ((edgeCode % (h + 1)) == 0) {
				e[0] = edgeCode / (h + 1) - 1;
				e[1] = h;
				e[2] = 1;
			} else {
				e[0] = edgeCode / (h + 1);
				e[1] = edgeCode % (h + 1) - 1;
				e[2] = 1;
			}
		}

		return e;
	}

}