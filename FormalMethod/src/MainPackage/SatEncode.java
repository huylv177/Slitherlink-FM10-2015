package MainPackage;

import java.util.ArrayList;

import Swing.MainFrame;

public class SatEncode {

	private ArrayList<String> text;
	private int[][] val; // mảng 2 chiều chứa giá trị trong các ô số
	private static int h; // kích thước trò chơi  - cỡ m
	private static int w; // kích thước trò chơi  - cỡ n
	
	private static int d; // tổng số biến dùng để mã hóa cạnh ngang và dọc
	private static int k; // tổng số biến dùng để mã hóa cạnh ngang
	private int _bitNumber; // số bit cần để mã hóa mỗi đỉnh
	private int[][][] v; // mảng 3 chiều chứa mã biến và phần nhớ của mỗi đỉnh

	public SatEncode(int[][] val) {
		text = new ArrayList<String>();
		this.val = val;
		h = MainFrame.HEIGHT;
		w = MainFrame.WIDTH;
		d = (h + 1) * w + h * (w + 1);
		k = (h + 1) * w;
		
		_bitNumber = bitNumber(h,w);
		int _bit = 2 * _bitNumber + 1;
		v = new int[h + 1][w + 1][_bit];
		int value = 2 * d + 1;
		for (int i = 0; i < h + 1; i++) {
			for (int j = 0; j < w + 1; j++) {
				for (int k = 1; k < _bit; k++) {
					v[i][j][k] = value;
					value++;
				}
			}
		}
	}
	
	// ---------------------------------------------//
	// Tính số bit cần để mã hóa mỗi đỉnh dựa trên kích thước của trò chơi//
	public int bitNumber(int h, int w) {
		int bit = 2;
		int mn = (h + 1) * (w + 1);
		while (Math.pow(2, bit) - 1 < mn) {
			bit++;
		}
		return bit;
	}

	// ---------------------------------------------//
	// Mảng biến nguyên mã hóa cho giá trị a, b ,c ( a = b+ 1 with c ) dưới dạng
	// nhị phân//
	private int[] setArrayBit(int m, int n, int z) {
		int[] arrBit = new int[_bitNumber];
		int i = 0;
		for (int k = z; k < z + _bitNumber; k++) {
			arrBit[i] = v[m][n][k];
			i++;
		}
		return arrBit;
	}
	
	// ---------------------------------------------//
	// Thực hiện phép toán công bit: a = b+ 1 ( và bộ nhớ c ) //
	private void bitAddition(int z, int[] a, int[] b, int[] c) {
		int i, j, t;
		for (i = 0; i < _bitNumber; i++) {
			if (i == 0) {
				t = 1;
			} else {
				t = -1;
			}
			// z là mã biến của cạnh như x1, x2, x3, x4
			
			// a[0] = -b[0] => cnf: (-a[0] v b[0]) ^ (a[0] v b[0])
//			if (!(t == 1 && i == 0)) {
				text.add(t * a[i] + " " + -a[0] + " " + -b[0] + " " + -z);
//			}
//			if (!(t == -1 && i == 0)) {
				text.add(t * a[i] + " " + a[0] + " " + b[0] + " " + -z);
//			}
			
			// c[0 = b[0]] => cnf: (b[0] v -c[0]) ^ (-b[0] v c[0])
			text.add(t * a[i] + " " + b[0] + " " + -c[0] + " " + -z);
			text.add(t * a[i] + " " + c[0] + " " + -b[0] + " " + -z);

			for (j = 1; j < _bitNumber; j++) {
				// c[i] = b[i] ^ c[i-1] 
				// => cnf: (-c[i] v b[i]) ^ (-c[i] v c[i-1]) ^
				// (c[i] v -c[i-1] v -b[i])
				text.add(t * a[i] + " " + -c[j] + " " + b[j] + " " + -z);
				text.add(t * a[i] + " " + -c[j] + " " + c[j - 1] + " " + -z);
				text.add(t * a[i] + " " + c[j] + " " + -c[j - 1] + " " + -b[j]
						+ " " + -z);

				// a[i] = b[i] + c[i-1]
				// => cnf: (-a[i] v b[i] v c[i-1]) ^ (a[i] v -b[i] v c[i-1]) ^
				// (a[i] v b[i] v -c[i-1]) ^ (-a[i] v -b[i] v -c[i-1])
				
				text.add(t * a[i] + " " + -a[j] + " " + b[j] + " " + c[j - 1]
						+ " " + -z);
				text.add(t * a[i] + " " + a[j] + " " + -b[j] + " " + c[j - 1]
						+ " " + -z);
				text.add(t * a[i] + " " + a[j] + " " + b[j] + " " + -c[j - 1]
						+ " " + -z);
				text.add(t * a[i] + " " + -a[j] + " " + -b[j] + " " + -c[j - 1]
						+ " " + -z);
			}
		}
	}
	
	// ---------------------------------------------//
	// Luật thỏa mãn số trong ô //
	private void ruleNumber() {
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				switch (val[i][j]) {
				case 0:
					text.add("-" + ul(i, j));
					text.add("-" + ur(i, j));
					text.add("-" + dl(i, j));
					text.add("-" + dr(i, j));
					text.add("-" + lu(i, j));
					text.add("-" + ld(i, j));
					text.add("-" + ru(i, j));
					text.add("-" + rd(i, j));
					break;
					
				case 1:
					text.add(ul(i, j) + " " + ur(i, j) + " " + dl(i, j) + " "
							+ dr(i, j) + " " + lu(i, j) + " " + ld(i, j) + " "
							+ ru(i, j) + " " + rd(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j));

					text.add("-" + rd(i, j) + " -" + ld(i, j));
					text.add("-" + rd(i, j) + " -" + lu(i, j));
					text.add("-" + ru(i, j) + " -" + ld(i, j));
					text.add("-" + ru(i, j) + " -" + lu(i, j));

					text.add("-" + rd(i, j) + " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + dl(i, j));
					text.add("-" + ru(i, j) + " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + dl(i, j));

					text.add("-" + ur(i, j) + " -" + ld(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j));

					text.add("-" + ur(i, j) + " -" + dr(i, j));
					text.add("-" + ur(i, j) + " -" + dl(i, j));
					text.add("-" + ul(i, j) + " -" + dr(i, j));
					text.add("-" + ul(i, j) + " -" + dl(i, j));

					text.add("-" + ld(i, j) + " -" + dr(i, j));
					text.add("-" + ld(i, j) + " -" + dl(i, j));
					text.add("-" + lu(i, j) + " -" + dr(i, j));
					text.add("-" + lu(i, j) + " -" + dl(i, j));
					break;
					
				case 2:
					text.add(rd(i, j) + " " + ru(i, j) + " " + ur(i, j) + " "
							+ ul(i, j) + " " + ld(i, j) + " " + lu(i, j));
					text.add(rd(i, j) + " " + ru(i, j) + " " + ur(i, j) + " "
							+ ul(i, j) + " " + dr(i, j) + " " + dl(i, j));
					text.add(rd(i, j) + " " + ru(i, j) + " " + ld(i, j) + " "
							+ lu(i, j) + " " + dr(i, j) + " " + dl(i, j));
					text.add(ur(i, j) + " " + ul(i, j) + " " + ld(i, j) + " "
							+ lu(i, j) + " " + dr(i, j) + " " + dl(i, j));

					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + ld(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + ld(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + lu(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + lu(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + ld(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + ld(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + lu(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + lu(i, j));

					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + dl(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + dl(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + dl(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + dl(i, j));

					text.add("-" + rd(i, j) + " -" + ld(i, j) + " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + ld(i, j) + " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + ld(i, j) + " -" + dl(i, j));
					text.add("-" + ru(i, j) + " -" + ld(i, j) + " -" + dl(i, j));
					text.add("-" + rd(i, j) + " -" + lu(i, j) + " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + lu(i, j) + " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + lu(i, j) + " -" + dl(i, j));
					text.add("-" + ru(i, j) + " -" + lu(i, j) + " -" + dl(i, j));

					text.add("-" + ur(i, j) + " -" + ld(i, j) + " -" + dr(i, j));
					text.add("-" + ul(i, j) + " -" + ld(i, j) + " -" + dr(i, j));
					text.add("-" + ur(i, j) + " -" + ld(i, j) + " -" + dl(i, j));
					text.add("-" + ul(i, j) + " -" + ld(i, j) + " -" + dl(i, j));
					text.add("-" + ur(i, j) + " -" + lu(i, j) + " -" + dr(i, j));
					text.add("-" + ul(i, j) + " -" + lu(i, j) + " -" + dr(i, j));
					text.add("-" + ur(i, j) + " -" + lu(i, j) + " -" + dl(i, j));
					text.add("-" + ul(i, j) + " -" + lu(i, j) + " -" + dl(i, j));
					break;
					
				case 3:
					text.add(rd(i, j) + " " + ru(i, j) + " " + ur(i, j) + " "
							+ ul(i, j));
					text.add(rd(i, j) + " " + ru(i, j) + " " + ld(i, j) + " "
							+ lu(i, j));
					text.add(rd(i, j) + " " + ru(i, j) + " " + dr(i, j) + " "
							+ dl(i, j));
					text.add(ur(i, j) + " " + ul(i, j) + " " + ld(i, j) + " "
							+ lu(i, j));
					text.add(ur(i, j) + " " + ul(i, j) + " " + dr(i, j) + " "
							+ dl(i, j));
					text.add(ld(i, j) + " " + lu(i, j) + " " + dr(i, j) + " "
							+ dl(i, j));

					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + ld(i, j)
							+ " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + ld(i, j)
							+ " -" + dl(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + lu(i, j)
							+ " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + ur(i, j) + " -" + lu(i, j)
							+ " -" + dl(i, j));

					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + ld(i, j)
							+ " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + ld(i, j)
							+ " -" + dl(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + lu(i, j)
							+ " -" + dr(i, j));
					text.add("-" + rd(i, j) + " -" + ul(i, j) + " -" + lu(i, j)
							+ " -" + dl(i, j));

					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + ld(i, j)
							+ " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + ld(i, j)
							+ " -" + dl(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + lu(i, j)
							+ " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + ur(i, j) + " -" + lu(i, j)
							+ " -" + dl(i, j));

					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + ld(i, j)
							+ " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + ld(i, j)
							+ " -" + dl(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + lu(i, j)
							+ " -" + dr(i, j));
					text.add("-" + ru(i, j) + " -" + ul(i, j) + " -" + lu(i, j)
							+ " -" + dl(i, j));
					break;
					
				case 4:
					text.add(rd(i, j) + " " + ru(i, j));
					text.add(ld(i, j) + " " + lu(i, j));
					text.add(ur(i, j) + " " + ul(i, j));
					text.add(dr(i, j) + " " + dl(i, j));
					break;
				}
			}
		}
	}
	
	// ---------------------------------------------//
	// Luật bổ sung thứ nhất: 2 số 3 chéo nhau //
	private void diagonalThree() {
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (i < h - 1 && j < w - 1) {
					if (val[i][j] == 3 && val[i + 1][j + 1] == 3) {
						text.add(lu(i, j) + " " + ld(i, j));
						text.add(ul(i, j) + " " + ur(i, j));
						text.add("-" + lu(i, j) + " " + ur(i, j));
						text.add("-" + ul(i, j) + " " + ld(i, j));
						
						text.add(dl(i + 1, j + 1) + " " + dr(i + 1, j + 1));
						text.add(ru(i + 1, j + 1) + " " + rd(i + 1, j + 1));
						text.add("-" + dr(i + 1, j + 1) + " "
								+ lu(i + 1, j + 1));
						text.add("-" + ld(i + 1, j + 1) + " "
								+ dl(i + 1, j + 1));
					}
				}
				if (i < h - 1 && j > 0) {
					if (val[i][j] == 3 && val[i + 1][j - 1] == 3) {
						text.add(ul(i, j) + " " + ur(i, j));
						text.add(ru(i, j) + " " + rd(i, j));
						text.add("-" + ur(i, j) + " " + rd(i, j));
						text.add("-" + ru(i, j) + " " + ul(i, j));
						
						text.add(lu(i + 1, j - 1) + " " + ld(i + 1, j - 1));
						text.add(dl(i + 1, j - 1) + " " + dr(i + 1, j - 1));
						text.add("-" + ld(i + 1, j - 1) + " "
								+ dr(i + 1, j - 1));
						text.add("-" + dl(i + 1, j - 1) + " "
								+ lu(i + 1, j - 1));
					}
				}
			}
		}
	}
	
	// ---------------------------------------------//
	// Luật bổ sung thứ hai: số 0 và số 3 cạnh nhau //
	private void sideZeroThree() {
		for (int i = 1; i < h - 1; i++) {
			for (int j = 1; j < w - 1; j++) {
				if (val[i][j] == 3) {
					if (val[i][j - 1] == 0) {
						text.add(lu(i - 1, j) + " " + ld(i - 1, j));
						text.add(ul(i, j) + " " + ur(i, j));
						text.add(ru(i, j) + " " + rd(i, j));
						text.add(dl(i, j) + " " + dr(i, j));
						text.add(lu(i + 1, j) + " " + ld(i + 1, j));
						
						text.add("-" + ld(i - 1, j) + " " + ur(i, j));
						text.add("-" + ur(i, j) + " " + rd(i, j));
						text.add("-" + rd(i, j) + " " + dl(i, j));
						text.add("-" + dl(i, j) + " " + ld(i + 1, j));
						
						text.add("-" + lu(i + 1, j) + " " + dr(i, j));
						text.add("-" + dr(i, j) + " " + ru(i, j));
						text.add("-" + ru(i, j) + " " + ul(i, j));
						text.add("-" + ul(i, j) + " " + lu(i - 1, j));
					}
					if (val[i][j + 1] == 0) {
						text.add(ru(i - 1, j) + " " + rd(i - 1, j));
						text.add(ul(i, j) + " " + ur(i, j));
						text.add(lu(i, j) + " " + ld(i, j));
						text.add(dl(i, j) + " " + dr(i, j));
						text.add(ru(i + 1, j) + " " + rd(i + 1, j));
						
						text.add("-" + rd(i - 1, j) + " " + ul(i, j));
						text.add("-" + ul(i, j) + " " + ld(i, j));
						text.add("-" + ld(i, j) + " " + dr(i, j));
						text.add("-" + dr(i, j) + " " + rd(i + 1, j));

						text.add("-" + ru(i + 1, j) + " " + dl(i, j));
						text.add("-" + dl(i, j) + " " + lu(i, j));
						text.add("-" + lu(i, j) + " " + ur(i, j));
						text.add("-" + ur(i,j)+" "+lu(i-1,j));
					}
					if (val[i - 1][j] == 0) {
						text.add(ul(i, j - 1) + " " + ur(i, j - 1));
						text.add(lu(i, j) + " " + ld(i, j));
						text.add(dl(i, j) + " " + dr(i, j));
						text.add(ru(i, j) + " " + rd(i, j));
						text.add(ul(i, j + 1) + " " + ur(i, j + 1));
						
						text.add("-"+ur(i,j-1)+" "+ld(i,j));
						text.add("-"+ld(i,j)+" "+dr(i,j));
						text.add("-"+dr(i,j)+" "+ru(i,j));
						text.add("-"+ru(i,j)+" "+ur(i,j+1));
						
						text.add("-"+ul(i,j+1)+" "+rd(i,j));
						text.add("-"+rd(i,j)+" "+dl(i,j));
						text.add("-"+dl(i,j)+" "+lu(i,j));
						text.add("-"+lu(i,j)+" "+ul(i,j-1));
					}
					if (val[i + 1][j] == 0) {
						text.add(dl(i, j - 1) + " " + dr(i, j - 1));
						text.add(lu(i, j) + " " + ld(i, j));
						text.add(ul(i, j) + " " + ur(i, j));
						text.add(ru(i, j) + " " + rd(i, j));
						text.add(dl(i, j + 1) + " " + dr(i, j + 1));
						
						text.add("-" + dr(i, j - 1) + " " + lu(i, j));
						text.add("-" + lu(i, j) + " " + ur(i, j));
						text.add("-" + ur(i, j) + " " + rd(i, j));
						text.add("-" + rd(i, j) + " " + dr(i, j + 1));

						text.add("-" + dl(i, j + 1) + " " + ru(i, j));
						text.add("-" + ru(i, j) + " " + ul(i, j));
						text.add("-" + ul(i, j) + " " + ld(i, j));
						text.add("-" + ld(i, j) + " " + dl(i, j - 1));
					}
				}
			}
		}
	}
	
	// ---------------------------------------------//
	// Luật bổ sung thứ ba: số 3 ở góc //
	private void cornerThree() {
		if (val[0][0] == 3) {
			text.add(lu(0, 0) + " " + ld(0, 0));
			text.add(ul(0, 0) + " " + ur(0, 0));
		}
		if (val[0][w - 1] == 3) {
			text.add(ul(0, w - 1) + " " + ur(0, w - 1));
			text.add(ru(0, w - 1) + " " + rd(0, w - 1));
		}
		if (val[h - 1][0] == 3) {
			text.add(lu(h - 1, 0) + " " + ld(h - 1, 0));
			text.add(dl(h - 1, 0) + " " + dr(h - 1, 0));
		}
		if (val[h - 1][w - 1] == 3) {
			text.add(dr(h - 1, w - 1) + " " + dr(h - 1, w - 1));
			text.add(ru(h - 1, w - 1) + " " + rd(h - 1, w - 1));
		}
	}
	
	// ---------------------------------------------//
	// Luật bổ sung thứ tư: 2 số 3 cạnh nhau //
	private void sideThree() {
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w - 1; j++) {
				if (val[i][j] == 3 && val[i][j + 1] == 3) {
					text.add(lu(i, j) + " " + ld(i, j));
					text.add(ru(i, j) + " " + rd(i, j));
					text.add(ru(i, j + 1) + " " + rd(i, j + 1));
				}
			}
		}
		for (int i = 0; i < h - 1; i++) {
			for (int j = 0; j < w; j++) {
				if (val[i][j] == 3 && val[i + 1][j] == 3) {
					text.add(ul(i, j) + " " + ur(i, j));
					text.add(dl(i, j) + " " + dr(i, j));
					text.add(dl(i + 1, j) + " " + dr(i + 1, j));
				}
			}
		}
	}
	
	// ---------------------------------------------//
	// Luật thỏa mãn cạnh //
	private void ruleEdge() {
		for (int i = 0; i < h + 1; i++) {
			for (int j = 0; j < w + 1; j++) {

				// Mỗi đỉnh có nhiều nhất một đường đi vào
				if (e1(i, j) != -1 && e2(i, j) != -1)
					text.add("-" + e1(i, j) + " -" + e2(i, j));
				if (e1(i, j) != -1 && e3(i, j) != -1)
					text.add("-" + e1(i, j) + " -" + e3(i, j));
				if (e1(i, j) != -1 && e4(i, j) != -1)
					text.add("-" + e1(i, j) + " -" + e4(i, j));
				if (e2(i, j) != -1 && e3(i, j) != -1)
					text.add("-" + e2(i, j) + " -" + e3(i, j));
				if (e2(i, j) != -1 && e4(i, j) != -1)
					text.add("-" + e2(i, j) + " -" + e4(i, j));
				if (e3(i, j) != -1 && e4(i, j) != -1)
					text.add("-" + e3(i, j) + " -" + e4(i, j));

				// Mỗi đỉnh có nhiều nhất một đường đi ra
				if (e5(i, j) != -1 && e6(i, j) != -1)
					text.add("-" + e5(i, j) + " -" + e6(i, j));
				if (e5(i, j) != -1 && e7(i, j) != -1)
					text.add("-" + e5(i, j) + " -" + e7(i, j));
				if (e5(i, j) != -1 && e8(i, j) != -1)
					text.add("-" + e5(i, j) + " -" + e8(i, j));
				if (e6(i, j) != -1 && e7(i, j) != -1)
					text.add("-" + e6(i, j) + " -" + e7(i, j));
				if (e6(i, j) != -1 && e8(i, j) != -1)
					text.add("-" + e6(i, j) + " -" + e8(i, j));
				if (e7(i, j) != -1 && e8(i, j) != -1)
					text.add("-" + e7(i, j) + " -" + e8(i, j));

				// Đường vào một đỉnh không là đường ra của chính nó
				if (e1(i, j) != -1 && e5(i, j) != -1)
					text.add("-" + e1(i, j) + " -" + e5(i, j));
				if (e2(i, j) != -1 && e6(i, j) != -1)
					text.add("-" + e2(i, j) + " -" + e6(i, j));
				if (e3(i, j) != -1 && e7(i, j) != -1)
					text.add("-" + e3(i, j) + " -" + e7(i, j));
				if (e4(i, j) != -1 && e8(i, j) != -1)
					text.add("-" + e4(i, j) + " -" + e8(i, j));

				// Đường vào là 1 cạnh và phải ra ở 3 cạnh kia

				// Tổng quát với đỉnh có cả 4 cạnh xung quanh
				if (e1(i, j) != -1 && e6(i, j) != -1 && e7(i, j) != -1
						&& e8(i, j) != -1)
					text.add("-" + e1(i, j) + " " + e6(i, j) + " " + e7(i, j)
							+ " " + e8(i, j));
				if (e2(i, j) != -1 && e5(i, j) != -1 && e7(i, j) != -1
						&& e8(i, j) != -1)
					text.add("-" + e2(i, j) + " " + e5(i, j) + " " + e7(i, j)
							+ " " + e8(i, j));
				if (e3(i, j) != -1 && e5(i, j) != -1 && e6(i, j) != -1
						&& e8(i, j) != -1)
					text.add("-" + e3(i, j) + " " + e5(i, j) + " " + e6(i, j)
							+ " " + e8(i, j));
				if (e4(i, j) != -1 && e5(i, j) != -1 && e6(i, j) != -1
						&& e7(i, j) != -1)
					text.add("-" + e4(i, j) + " " + e5(i, j) + " " + e6(i, j)
							+ " " + e7(i, j));

				// Đỉnh ở góc chỉ có 2 cạnh

				// Đỉnh góc trái trên
				if (e2(i, j) != -1 && e3(i, j) != -1 && e4(i, j) == -1
						&& e1(i, j) == -1) {
					// text.add("dinh goc trai tren cung");
					text.add("-" + e3(i, j) + " " + e6(i, j));
					text.add("-" + e2(i, j) + " " + e7(i, j));
				}

				// Đỉnh góc phải dưới
				if (e2(i, j) != -1 && e1(i, j) != -1 && e3(i, j) == -1
						&& e4(i, j) == -1) {
					text.add("-" + e2(i, j) + " " + e5(i, j));
					text.add("-" + e1(i, j) + " " + e6(i, j));
				}

				// Đỉnh góc phải trên
				if (e4(i, j) != -1 && e3(i, j) != -1 && e2(i, j) == -1
						&& e1(i, j) == -1) {
					// text.add("dinh goc phai tren cung");
					text.add("-" + e4(i, j) + " " + e7(i, j));
					text.add("-" + e3(i, j) + " " + e8(i, j));
				}

				// Đỉnh góc phải dưới
				if (e4(i, j) != -1 && e1(i, j) != -1 && e2(i, j) == -1
						&& e3(i, j) == -1) {
					text.add("-" + e4(i, j) + " " + e5(i, j));
					text.add("-" + e1(i, j) + " " + e8(i, j));
				}

				// Đỉnh ở biên (ngoại trừ các đỉnh góc) thì có 3 cạnh

				// Biên trên
				if (e4(i, j) != -1 && e2(i, j) != -1 && e3(i, j) != -1
						&& e1(i, j) == -1) {
					text.add("-" + e4(i, j) + " " + e6(i, j) + " " + e7(i, j));
					text.add("-" + e3(i, j) + " " + e6(i, j) + " " + e8(i, j));
					text.add("-" + e2(i, j) + " " + e8(i, j) + " " + e7(i, j));
				}

				// Biên trái
				if (e1(i, j) != -1 && e2(i, j) != -1 && e3(i, j) != -1
						&& e4(i, j) == -1) {
					text.add("-" + e2(i, j) + " " + e5(i, j) + " " + e7(i, j));
					text.add("-" + e3(i, j) + " " + e5(i, j) + " " + e6(i, j));
					text.add("-" + e1(i, j) + " " + e6(i, j) + " " + e7(i, j));
				}

				// Biên dưới
				if (e4(i, j) != -1 && e2(i, j) != -1 && e1(i, j) != -1
						&& e3(i, j) == -1) {
					text.add("-" + e2(i, j) + " " + e5(i, j) + " " + e8(i, j));
					text.add("-" + e1(i, j) + " " + e6(i, j) + " " + e8(i, j));
					text.add("-" + e4(i, j) + " " + e6(i, j) + " " + e5(i, j));
				}

				// Biên phải
				if (e4(i, j) != -1 && e1(i, j) != -1 && e3(i, j) != -1
						&& e2(i, j) == -1) {
					text.add("-" + e4(i, j) + " " + e5(i, j) + " " + e7(i, j));
					text.add("-" + e1(i, j) + " " + e8(i, j) + " " + e7(i, j));
					text.add("-" + e3(i, j) + " " + e5(i, j) + " " + e8(i, j));
				}
			}
		}
	}
	
	// ---------------------------------------------//
	// Luật đánh số đỉnh //
	private void numberVertex() {
		int i, j, k, t, u;
		// Mỗi đỉnh sẽ được đánh số 1 hoặc bằng số của đỉnh có cạnh đi vào nó
		// cộng 1.
		for (i = 0; i < h + 1; i++) {
			for (j = 0; j < w + 1; j++) {
				if (e1(i, j) != -1) {
					bitAddition(e1(i, j), setArrayBit(i, j, 1),
							setArrayBit(i - 1, j, 1),
							setArrayBit(i, j, 1 + _bitNumber));
				}
				if (e2(i, j) != -1) {
					bitAddition(e2(i, j), setArrayBit(i, j, 1),
							setArrayBit(i, j + 1, 1),
							setArrayBit(i, j, 1 + _bitNumber));
				}
				if (e3(i, j) != -1) {
					bitAddition(e3(i, j), setArrayBit(i, j, 1),
							setArrayBit(i + 1, j, 1),
							setArrayBit(i, j, 1 + _bitNumber));
				}
				if (e4(i, j) != -1) {
					bitAddition(e4(i, j), setArrayBit(i, j, 1),
							setArrayBit(i, j - 1, 1),
							setArrayBit(i, j, 1 + _bitNumber));
				}
			}
		}

		// Chỉ có duy nhất một đỉnh được đánh số 1 //
		for (i = 0; i < h + 1; i++) {
			for (j = 0; j < w + 1; j++) {
				for (k = 0; k < h + 1; k++) {
					for (t = 0; t < w + 1; t++) {
						if (i != k || j != t) {
							String valueOne = "";
							valueOne += Integer.toString(-v[i][j][1]) + " "
									+ Integer.toString(-v[k][t][1]);
							for (u = 2; u < 1 + _bitNumber; u++) {
								valueOne += " " + v[i][j][u] + " " + v[k][t][u];
							}
							text.add(valueOne);
						}
					}
				}
			}
		}
	}

	// ---------------------------------------------//
	// Encode Solve 1 gọi Sat nhiều lần, kiểm tra điều kiện tổng số cạnh //
	public void encode1(){
		ruleNumber();
		ruleEdge();
//		diagonalThree();
//		sideZeroThree();
//		cornerThree();
	}
	
	// ---------------------------------------------//
	// Encode Solve 2 đánh số đỉnh //
	public void encode2() {
		ruleNumber();
		ruleEdge();
		numberVertex();
//		diagonalThree();
//		sideZeroThree();
//		cornerThree();
	}
	
	
	public ArrayList<String> getTextEncoded() {
		return text;
	}
	// ---------------------------------------------//
	// Xác định mã biến các cạnh xung quanh ô x[r][c]
	
	// Cạnh trên hướng phải của ô [r][c]
	static int ur(int r, int c) {
		return w * r + c + 1;
	}

	// Cạnh trên hướng trái của ô [r][c]
	static int ul(int r, int c) {
		return w * r + c + 1 + d;
	}

	// Cạnh dưới hướng phải của ô [r][c]
	static int dr(int r, int c) {
		return w * (r + 1) + c + 1;
	}
	
	// Cạnh dưới hướng trái của ô [r][c]
	static int dl(int r, int c) {
		return w * (r + 1) + c + 1 + d;
	}
	
	// Cạnh phải hướng xuống của ô [r][c]
	static int rd(int r, int c) {
		return k + (w + 1) * r + c + 2;
	}
	
	// Cạnh phải hướng lên của ô [r][c]
	static int ru(int r, int c) {
		return k + (w + 1) * r + c + 2 + d;
	}
	
	// Cạnh trái hướng xuống của ô [r][c]
	static int ld(int r, int c) {
		return k + (w + 1) * r + c + 1;
	}
	
	// Cạnh trái hướng lên của ô [r][c]
	static int lu(int r, int c) {
		return k + (w + 1) * r + c + 1 + d;
	}
	
	// ---------------------------------------------//
	// Xác định mã biến các cạnh xung quanh đỉnh v[r][c]
	
	// Cạnh có chiều trên xuống của đỉnh [r][c]
	static int e1(int r, int c) {
		if (r == 0)
			return -1;
		if (c != w) {
			return ld(r - 1, c);
		} else {
			return rd(r - 1, c - 1);
		}
	}

	// Cạnh có chiều phải vào của đỉnh [r][c]
	static int e2(int r, int c) {
		if (c == w)
			return -1;
		return e6(r, c) + d;
	}

	// Cạnh có chiều dưới lên của đỉnh [r][c]
	static int e3(int r, int c) {
		if (r == h)
			return -1;
		return e7(r, c) + d;
	}

	// Cạnh có chiều trái vào của đỉnh [r][c]
	static int e4(int r, int c) {
		if (c == 0)
			return -1;
		if (r != 0) {
			return ur(r, c - 1);
		} else {
			return dr(r - 1, c - 1);
		}
	}

	// Cạnh có chiều từ trên lên của đỉnh [r][c]
	static int e5(int r, int c) {
		if (r == 0)
			return -1;
		return e1(r, c) + d;
	}

	// Cạnh có chiều từ phải ra của đỉnh [r][c]
	static int e6(int r, int c) {
		if (c == w)
			return -1;
		if (r != h) {
			return ur(r, c);
		} else {
			return dr(r - 1, c);
		}
	}

	// Cạnh có chiều từ dưới xuống của đỉnh [r][c]
	static int e7(int r, int c) {
		if (r == h)
			return -1;
		if (c != w) {
			return ld(r, c);
		} else {
			return rd(r, c - 1);
		}
	}

	// Cạnh có chiều từ trái ra của đỉnh [r][c]
	static int e8(int r, int c) {
		if (c == 0)
			return -1;
		return e4(r, c) + d;
	}
}
