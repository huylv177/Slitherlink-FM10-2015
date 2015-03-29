package MainPackage;

public class Cell {
	private int r,c;
	private int value;
	private int up,down,left,right;
	int w= Main.WIDTH;
	int h= Main.HEIGHT;
	int d=w*(h+1);
	public Cell(int r,int c){
		this.r=r;
		this.c=c;
		up = r*w+c;
		down= (r+1)*w+c;
		left= d+r*(w+1)+c;
		right=left+1;
	}
	
//	public 
	
}
