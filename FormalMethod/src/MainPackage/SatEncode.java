package MainPackage;

import java.util.ArrayList;

public class SatEncode {
	
	private ArrayList<String> text;
	private int[][] val;
	private int w;
	private int h;
	private int numOfClause;
	
	public SatEncode(int[][] val) {
		w=Main.WIDTH;
		h=Main.HEIGHT;
		this.val=val;
	}
	
	public void encode(){
		text = new ArrayList<String>();
		String temp="";
		numOfClause=0;
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				switch(val[i][j]){
				case 0:
					//	System.out.println(u(i,j)+" "+d(i,j)+" "+l(i,j)+" "+r(i,j));
					temp="-"+u(i,j);
					text.add(temp);
					temp="-"+d(i,j);
					text.add(temp);
					temp="-"+l(i,j);
					text.add(temp);
					temp="-"+r(i,j);
					text.add(temp);
					numOfClause+=4;
					break;
				case 1:
					temp=u(i,j)+" "+d(i,j)+" "+l(i,j)+" "+r(i,j);
					text.add(temp);
					temp="-"+u(i,j)+" -"+r(i,j);
					text.add(temp);
					temp="-"+u(i,j)+" -"+d(i,j);
					text.add(temp);
					temp="-"+u(i,j)+" -"+l(i,j);
					text.add(temp);
					temp="-"+d(i,j)+" -"+l(i,j);
					text.add(temp);
					temp="-"+d(i,j)+" -"+r(i,j);
					text.add(temp);
					temp="-"+r(i,j)+" -"+l(i,j);
					text.add(temp);
					numOfClause+=7;
					break;
				case 2:
					temp=u(i,j)+" "+r(i,j)+" "+d(i,j);
					text.add(temp);
					temp=r(i,j)+" "+d(i,j)+" "+l(i,j);
					text.add(temp);
					temp=d(i,j)+" "+l(i,j)+" "+u(i,j);
					text.add(temp);
					temp=l(i,j)+" "+u(i,j)+" "+r(i,j);
					text.add(temp);
					
					temp="-"+u(i,j)+" -"+r(i,j)+" -"+d(i,j);
					text.add(temp);
					temp="-"+r(i,j)+" -"+d(i,j)+" -"+l(i,j);
					text.add(temp);
					temp="-"+d(i,j)+" -"+l(i,j)+" -"+u(i,j);
					text.add(temp);
					temp="-"+l(i,j)+" -"+u(i,j)+" -"+r(i,j);
					text.add(temp);
					
					numOfClause+=8;
					break;
				case 3:
					temp="-"+l(i,j)+" -"+u(i,j)+" -"+r(i,j)+" -"+d(i,j);
					text.add(temp);
					temp="-"+u(i,j)+" -"+r(i,j);
					text.add(temp);
					temp="-"+u(i,j)+" -"+d(i,j);
					text.add(temp);
					temp="-"+u(i,j)+" -"+l(i,j);
					text.add(temp);
					temp="-"+d(i,j)+" -"+l(i,j);
					text.add(temp);
					temp="-"+d(i,j)+" -"+r(i,j);
					text.add(temp);
					temp="-"+r(i,j)+" -"+l(i,j);
					text.add(temp);
					break;
				case 4:
					temp=String.valueOf(u(i,j));
					text.add(temp);
					temp=String.valueOf(d(i,j));
					text.add(temp);
					temp=String.valueOf(l(i,j));
					text.add(temp);
					temp=String.valueOf(r(i,j));
					text.add(temp);
					numOfClause+=4;
					break;
				}
			}
		}
		
	}
	
	public ArrayList<String> getTextEncoded(){
		return text;
	}
	int u(int r,int c){
		return r*w+c+1;
	}
	int d(int r, int c){
		return (r+1)*w+c+1;
	}
	int l(int r,int c){
		int d=w*(h+1);
		return d+r*(w+1)+c+1;
	}
	int r(int r,int c){
		int d=w*(h+1);
		return d+r*(w+1)+c+2;
	}
}
