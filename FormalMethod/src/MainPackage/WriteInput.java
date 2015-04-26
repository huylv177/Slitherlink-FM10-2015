package MainPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Swing.MainFrame;

public class WriteInput {
	private ArrayList<String> stringEncoded;
	private SatEncode satEncode;
	private int w;
	private int h;
	public WriteInput(int[][] val){
		w= MainFrame.WIDTH;
		h= MainFrame.HEIGHT;
		satEncode = new SatEncode(val);
		satEncode.encode();
		stringEncoded = satEncode.getTextEncoded();
	}
	public void writeToFile(String fPath){
//		System.out.print(stringEncoded);
		try {
			File file = new File(fPath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("p cnf "+(4*w*h+2*w+2*h)+" "+stringEncoded.size());
			MainFrame.lblNumOfVariable.setText((4*w*h+2*w+2*h)+"");
			MainFrame.lblNumOfClause.setText(stringEncoded.size()+"");
			MainFrame.textPaneInput.setText("p cnf "+(4*w*h+2*w+2*h)+" "+stringEncoded.size());
			bw.newLine();
			
			for(int i=0;i<stringEncoded.size();i++){
				bw.write(stringEncoded.get(i)+" 0");
				MainFrame.textPaneInput.append(stringEncoded.get(i)+" 0\n");
				bw.newLine();
			}
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addFoundOutput(ArrayList<Integer> invertArray){
		String tempString="";
		for(int i=0;i<invertArray.size();i++){
			tempString+=invertArray.get(i)+" ";
		}
		stringEncoded.add(tempString);
	}
	
}
