package MainPackage;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import Swing.MainFrame;
import Swing.MyPanel;

public class SolveThread extends SwingWorker<String[], Void>{
	String cnfInput = MainFrame.cnfInput;
	int[][] val;
	private WriteInput writeInput;
	private SatSolver satSolver;
	int HEIGHT = MainFrame.HEIGHT;
	int WIDTH = MainFrame.WIDTH;
	MyPanel myCanvas;
	JDialog loadingDialog;
	long start;
	long stop;
	float elapsedTime;
	JLabel status ;
	JLabel lblTotalTime;
	
	public SolveThread(JDialog loadingDialog, int[][] val, MyPanel myCanvas,long start,JLabel lblTotalTime,JLabel status) {
		this.loadingDialog = loadingDialog;
		this.val = val;
		this.myCanvas = myCanvas;
		this.start=start;
		this.status = status;
		this.lblTotalTime=lblTotalTime;
	}

	@Override
	protected String[] doInBackground() throws Exception {
		writeInput = new WriteInput(val);
		writeInput.writeToFile(cnfInput);
		satSolver = new SatSolver();

		while (true) {
			if(isCancelled()){
				System.out.println("stop");
				loadingDialog.setVisible(false);
				return null;
			}
			String[] tempString = satSolver.getString().split(" ");
			ArrayList<Integer> b = new ArrayList<Integer>();
			for (int i = 0; i < tempString.length - 1; i++) {
				Integer temp = Integer.parseInt(tempString[i]);
				b.add(-temp);
			}

			writeInput.addFoundOutput(b);
			writeInput.writeToFile(cnfInput);
			satSolver = new SatSolver();
			if (satSolver.getRESULT_CODE() == 1) {
				String[] stringDecoded = satSolver.getString().split(" ");
				int[] numbers = new int[stringDecoded.length - 1];
				for (int i = 0; i < stringDecoded.length - 1; i++) {
					numbers[i] = Integer.parseInt(stringDecoded[i]);
				}
				if (checkLoop(numbers)) {
					// myCanvas.repaintCanvas(stringDecoded);
					return stringDecoded;
				}
			}
			else return null;
		}
	}
	@Override
	protected void done() {
		super.done();
		stop = System.currentTimeMillis();
		elapsedTime = (float)(stop-start)/1000;
		lblTotalTime.setText((int)(elapsedTime/60)+":"+elapsedTime%60);
		Toolkit.getDefaultToolkit().beep();
		
		try {
			if(get()!=null){
				myCanvas.repaintCanvas(get());
				status.setText("Satifiable");
			}
			else{
				status.setText("Unsatifiable");
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loadingDialog.dispose();
	}


	public boolean checkLoop(int[] edge) {
		int firstEdge = 0;
		int count = 0;// la tong so canh nam tren duong minh duyet
		for (int i = 0; i < edge.length - 1; i++) {
			if (edge[i] > 0) {
				firstEdge = i + 1;
				// System.out.print("edge" + edge[i]);
				count = 1;
				break;
			}
		}
		int currentEdge = firstEdge;
		int d = (HEIGHT + 1) * WIDTH + (WIDTH + 1) * HEIGHT;
		int k = (HEIGHT + 1) * WIDTH;
		int vertexX, vertexY;
		while (true) {
			if (currentEdge > d + k) {
				vertexX = (currentEdge - d - k) / (WIDTH + 1);
				vertexY = (currentEdge - d - k) % (WIDTH + 1) - 1;
				if (vertexY < 0) {
					vertexY += WIDTH + 1;
					vertexX--;
				}
			} else if (currentEdge > d) {
				vertexX = (currentEdge - d) / WIDTH;
				vertexY = (currentEdge - d) % WIDTH - 1;
				if (vertexY < 0) {
					vertexY += WIDTH;
					vertexX--;
				}
			} else if (currentEdge > k) {
				vertexX = (currentEdge - k) / (WIDTH + 1) + 1;
				vertexY = (currentEdge - k) % (WIDTH + 1) - 1;
				if (vertexY < 0) {
					vertexY += WIDTH + 1;
					vertexX--;
				}
			} else {
				vertexX = currentEdge / (WIDTH);
				vertexY = currentEdge % WIDTH;
				if (vertexY == 0) {
					vertexY += WIDTH;
					vertexX--;
				}
			}
			int toUp, toRight, toDown, toLeft;
			toUp = SatEncode.e5(vertexX, vertexY);
			toRight = SatEncode.e6(vertexX, vertexY);
			toDown = SatEncode.e7(vertexX, vertexY);
			toLeft = SatEncode.e8(vertexX, vertexY);
			if (toUp != -1 && toUp != currentEdge && edge[toUp - 1] > 0) {
				currentEdge = toUp;
				count++;
			} else if (toRight != -1 && toRight != currentEdge
					&& edge[toRight - 1] > 0) {
				currentEdge = toRight;
				count++;
			} else if (toDown != -1 && toDown != currentEdge
					&& edge[toDown - 1] > 0) {
				currentEdge = toDown;
				count++;
			} else if (toLeft != -1 && toLeft != currentEdge
					&& edge[toLeft - 1] > 0) {
				currentEdge = toLeft;
				count++;
			} else
				break;
			if (currentEdge == firstEdge) {
				count--;
				break;
			}
		}
		int totalEdge = 0;
		for (int i = 0; i < edge.length; i++) {
			if (edge[i] > 0)
				totalEdge++;
		}
		if (count == totalEdge && currentEdge == firstEdge)
			return true;
		else
			return false;
	}


}
