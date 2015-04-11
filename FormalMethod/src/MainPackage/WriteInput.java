package MainPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteInput {
	private ArrayList<String> stringEncoded;
	private SatEncode satEncode;
	private int w;
	private int h;
	WriteInput(int[][] val){
		w= Main.WIDTH;
		h= Main.HEIGHT;
		satEncode = new SatEncode(val);
		satEncode.encode();
		stringEncoded = satEncode.getTextEncoded();
	}
	void write(String fPath){
//		System.out.print(stringEncoded);
		try {
			File file = new File(fPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("p cnf "+(4*w*h+2*w+2*h)+" "+stringEncoded.size());
			bw.newLine();
			
			for(int i=0;i<stringEncoded.size();i++){
				bw.write(stringEncoded.get(i)+" 0");
				bw.newLine();
			}
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void addFoundOutput(){
		
	}
	
}
