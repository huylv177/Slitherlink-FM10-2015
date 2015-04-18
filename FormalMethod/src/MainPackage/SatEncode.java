package MainPackage;

import java.util.ArrayList;

public class SatEncode {

	private ArrayList<String> text;
	private int[][] val;
	private int w;
	private int h;
	int d, k;

	public SatEncode(int[][] val) {
		w = Main.WIDTH;
		h = Main.HEIGHT;
		this.val = val;
		d = (w + 1) * h + w * (h + 1);
		k = w * (h + 1);
	}

	public void encode() {
		text = new ArrayList<String>();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				switch (val[i][j]) {
				case 0:
					// System.out.println(ul(i,j)+" "+ur(i,j)+" "+dl(i,j)+" "+dr(i,j)+" "+lu(i,j)+" "+ld(i,j)+" "+ru(i,j)+" "+rd(i,j));
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
//					text.add("kkkkkkk");
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
					text.add(String.valueOf(rd(i, j)));
					text.add(String.valueOf(ru(i, j)));
					text.add(String.valueOf(ur(i, j)));
					text.add(String.valueOf(ul(i, j)));
					text.add(String.valueOf(ld(i, j)));
					text.add(String.valueOf(lu(i, j)));
					text.add(String.valueOf(dr(i, j)));
					text.add(String.valueOf(dl(i, j)));
					break;
				}
			}
		}
		for(int i=0;i<w+1;i++){
			for(int j=0;j<h+1;j++){
//				if(i==5 && j==5){
//				text.add("-----------------------------");
//				System.out.println(e1(i,j)+" "+e2(i,j)+" "+e3(i,j)+" "+e4(i,j)+ " "+e5(i,j)+" "+e6(i,j)+" "+e7(i,j)+" "+e8(i,j));
				//moi dinh co nhieu nhat 1 duong di vao
				if(e1(i,j) != -1 && e4(i,j)!=-1)	text.add("-"+e1(i,j)+" -"+e4(i,j));
				if(e1(i,j) != -1 && e6(i,j)!=-1)	text.add("-"+e1(i,j)+" -"+e6(i,j));
				if(e1(i,j) != -1 && e7(i,j)!=-1)	text.add("-"+e1(i,j)+" -"+e7(i,j));
				if(e4(i,j) != -1 && e6(i,j)!=-1)	text.add("-"+e4(i,j)+" -"+e6(i,j));
				if(e4(i,j) != -1 && e7(i,j)!=-1)	text.add("-"+e4(i,j)+" -"+e7(i,j));
				if(e6(i,j) != -1 && e7(i,j)!=-1)	text.add("-"+e6(i,j)+" -"+e7(i,j));
				
				//moi dinh co nhieu nhat 1 duong di ra
				if(e2(i,j) != -1 && e3(i,j)!=-1)	text.add("-"+e2(i,j)+" -"+e3(i,j));
				if(e2(i,j) != -1 && e5(i,j)!=-1)	text.add("-"+e2(i,j)+" -"+e5(i,j));
				if(e2(i,j) != -1 && e8(i,j)!=-1)	text.add("-"+e2(i,j)+" -"+e8(i,j));
				if(e3(i,j) != -1 && e5(i,j)!=-1)	text.add("-"+e3(i,j)+" -"+e5(i,j));
				if(e3(i,j) != -1 && e8(i,j)!=-1)	text.add("-"+e3(i,j)+" -"+e8(i,j));
				if(e5(i,j) != -1 && e8(i,j)!=-1)	text.add("-"+e5(i,j)+" -"+e8(i,j));
				
				//duong vao cua 1 dinh ko la duong ra cua chinh no
				if(e1(i,j) != -1 && e5(i,j)!=-1)	text.add("-"+e1(i,j)+" -"+e5(i,j));
				if(e2(i,j) != -1 && e6(i,j)!=-1)	text.add("-"+e2(i,j)+" -"+e6(i,j));
				if(e3(i,j) != -1 && e7(i,j)!=-1)	text.add("-"+e3(i,j)+" -"+e7(i,j));
				if(e4(i,j) != -1 && e8(i,j)!=-1)	text.add("-"+e4(i,j)+" -"+e8(i,j));
				
				//duong vao la 1 canh va duong ra o 3 canh kia
				if(e1(i,j) != -1 && e2(i,j)!=-1 && e3(i,j)!=-1 && e8(i,j)!=-1)	text.add("-"+e1(i,j)+" "+e2(i,j)+" "+e3(i,j)+" "+e8(i,j));
				if(e6(i,j) != -1 && e3(i,j)!=-1 && e5(i,j)!=-1 && e8(i,j)!=-1)	text.add("-"+e6(i,j)+" "+e3(i,j)+" "+e5(i,j)+" "+e8(i,j));
				if(e7(i,j) != -1 && e2(i,j)!=-1 && e5(i,j)!=-1 && e8(i,j)!=-1)	text.add("-"+e7(i,j)+" "+e2(i,j)+" "+e5(i,j)+" "+e8(i,j));
				if(e4(i,j) != -1 && e2(i,j)!=-1 && e3(i,j)!=-1 && e5(i,j)!=-1)	text.add("-"+e4(i,j)+" "+e2(i,j)+" "+e3(i,j)+" "+e5(i,j));
				
//				}
			}
		}

	}

	public ArrayList<String> getTextEncoded() {
		return text;
	}

	int ur(int r, int c) {
		return w * r + c + 1;
	}

	int ul(int r, int c) {
		return w * r + c + 1 + d;
	}

	int dr(int r, int c) {
		return w * (r + 1) + c + 1;
	}

	int dl(int r, int c) {
		return w * (r + 1) + c + 1 + d;
	}

	int rd(int r, int c) {
		return k + (w + 1) * r + c + 2;
	}

	int ru(int r, int c) {
		return k + (w + 1) * r + c + 2 + d;
	}

	int ld(int r, int c) {
		return k + (w + 1) * r + c + 1;
	}

	int lu(int r, int c) {
		return k + (w + 1) * r + c + 1 + d;
	}
	//Tren xuong
	int e1(int r,int c){
		if(r==0) return -1;
		if(c!=h){
			return ld(r-1,c);
		}else{
			return rd(r-1,c-1); 
		}
	}
	//Phai vao
	int e2(int r,int c){
		if(c==h) return -1;
		return e6(r,c) + d;
	}
	//Duoi len
	int e3(int r,int c){
		if(r==h) return -1;
		return e7(r,c)+d;
	}
	//Trai vao
	int e4(int r,int c){
		if(c==0) return -1;
		if(r!=0){
			return ur(r,c-1);
		}else{
			return dr(r-1,c-1);
		}
	}
	//Tren len
	int e5(int r,int c){
		if(r==0) return -1;
		return e1(r,c)+d;
	}
	//Phai ra
	int e6(int r,int c){
		if(c==h) return -1;
		if(r!=w){
			return ur(r,c);
		}else{
			return dr(r-1,c);
		}
		
	}
	//Duoi xuong
	int e7(int r,int c){
		if(r==w) return -1;
		if(c!=h){
			return ld(r,c);
		}else{
			return rd(r,c-1);
		}
	}
	//Trai ra
	int e8(int r,int c){
		if(c==0) return -1;
		return e4(r,c)+d;
	}
}
