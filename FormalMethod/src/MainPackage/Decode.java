package MainPackage;

public class Decode {
	int WIDTH = 4;
	int HEIGHT = 4;
	
	
	int[] e(int edgeCode) {
		int[] e = new int[3];
		int d = WIDTH * (HEIGHT + 1) + (WIDTH + 1) * HEIGHT;
		int k = WIDTH * (HEIGHT + 1);

		if (edgeCode > d) {
			edgeCode -= d;
		}
		if (edgeCode <= k) {
			if (edgeCode % HEIGHT == 0) {
				e[0] = edgeCode / HEIGHT - 1;
				e[1] = HEIGHT - 1;
				e[2] = 0;
			} else {
				e[0] = edgeCode / HEIGHT;
				e[1] = edgeCode % HEIGHT - 1;
				e[2] = 0;
			}
		} else {
			edgeCode = edgeCode - k;
			if ((edgeCode % (HEIGHT + 1)) == 0) {
				e[0] = edgeCode / (HEIGHT + 1) - 1;
				e[1] = HEIGHT;
				e[2] = 1;
			} else {
				e[0] = edgeCode / (HEIGHT + 1);
				e[1] = edgeCode % (HEIGHT + 1) - 1;
				e[2] = 1;
			}
		}

		return e;
	}
}
