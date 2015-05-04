package MainPackage;

import javax.swing.JDialog;


public class LoadingThread extends Thread{
	JDialog loadingDialog;
	
	public LoadingThread(JDialog dlg){
		loadingDialog=dlg;
	}
	
	@Override
	public void run() {
		super.run();
		loadingDialog.setVisible(true);
	}
}
