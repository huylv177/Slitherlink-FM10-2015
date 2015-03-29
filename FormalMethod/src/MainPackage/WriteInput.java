package MainPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteInput {
	ArrayList<String> stringEncoded;
	WriteInput(int[][] val){
		int w= Main.WIDTH;
		int h= Main.HEIGHT;
		SatEncode satEncode = new SatEncode(val);
		satEncode.encode();
		stringEncoded = satEncode.getTextEncoded();

//		System.out.print(stringEncoded);
		try {
			File file = new File("input/cnf/satinput.cnf");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("p cnf "+(2*w*h+w+h)+" "+stringEncoded.size());
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
}
