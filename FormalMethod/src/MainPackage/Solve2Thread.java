package MainPackage;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import Swing.MainFrame;
import Swing.MyPanel;

public class Solve2Thread extends SwingWorker<String[], Void>{
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
	long encodeMoment;
	float encodeTime;
	long solveMoment;
	float solveTime;
	JLabel lblEncodeTime;
	JLabel lblSolveTime;
	
	public Solve2Thread(JDialog loadingDialog, int[][] val, MyPanel myCanvas,long start,JLabel lblEncodeTime,JLabel lblSolveTime,JLabel lblTotalTime,JLabel status) {
		this.loadingDialog = loadingDialog;
		this.val = val;
		this.myCanvas = myCanvas;
		this.start=start;
		this.status = status;
		this.lblTotalTime=lblTotalTime;
		this.lblEncodeTime=lblEncodeTime;
		this.lblSolveTime=lblSolveTime;
	}

	@Override
	protected String[] doInBackground() throws Exception {
		writeInput = new WriteInput(val,2);
		encodeMoment = System.currentTimeMillis();
		writeInput.writeToFile(cnfInput);
		satSolver = new SatSolver();
		if(satSolver.getRESULT_CODE()==1){
			solveMoment = System.currentTimeMillis();
			String[] stringDecoded = satSolver.getString().split(" ");
			return stringDecoded;
		}
		else return null;
		
	}
	@Override
	protected void done() {
		super.done();
		stop = System.currentTimeMillis();
		encodeTime = (float)(encodeMoment-start)/1000;
		lblEncodeTime.setText((int)(encodeTime/60)+":"+encodeTime%60);
		solveTime = (float)(solveMoment-start)/1000;
		lblSolveTime.setText((int)(solveTime/60)+":"+solveTime%60);
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
}
