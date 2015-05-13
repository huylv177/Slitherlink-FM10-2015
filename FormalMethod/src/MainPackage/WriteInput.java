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
	private int _bitNumber;
	private int encodeMethod;
	public WriteInput(int[][] val,int encodeMethod){
		w= MainFrame.WIDTH;
		h= MainFrame.HEIGHT;
		this.encodeMethod=encodeMethod;
		_bitNumber =bitNumber(h, w);
		satEncode = new SatEncode(val);
		if(encodeMethod==1){
			satEncode.encode1();
		}else if(encodeMethod==2){
			satEncode.encode2();
		}
		
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
			if(encodeMethod==1){
				MainFrame.lblNumOfVariable.setText(String.valueOf(4*w*h+2*w+2*h));
			}
			else{
				MainFrame.lblNumOfVariable.setText(String.valueOf(4*w*h+2*w+2*h +(h+1)*(w+1)*2*_bitNumber) );
			}
			MainFrame.lblNumOfClause.setText(stringEncoded.size()+"");
			MainFrame.textPaneInput.setText("p cnf "+(4*w*h+2*w+2*h)+" "+stringEncoded.size()+"\n");
			
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
	public int bitNumber(int h, int w) {
		int bit = 5;
		int mn = (h + 1) * (w + 1);
		while (Math.pow(2, bit) - 1 < mn) {
			bit++;
		}
		return bit;
	}
}
