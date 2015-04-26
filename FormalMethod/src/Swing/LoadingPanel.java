package Swing;
 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;
 
public class LoadingPanel extends JPanel {
	 
	  Image image;
	 
	  public LoadingPanel() {
	    image = Toolkit.getDefaultToolkit().createImage("image/loading.gif");
	  }
	 
	  @Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (image != null) {
	      g.drawImage(image, 0,0 ,70,70, this);
	    }
	  }
	 
//	  public static void main(String[] args) {
//	    SwingUtilities.invokeLater(new Runnable() {
//	 
//	      @Override
//	      public void run() {
//	        JFrame frame = new JFrame();
//	        frame.add(new ImagePanel());
//	 
//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        frame.setSize(400, 400);
//	        frame.setLocationRelativeTo(null);
//	        frame.setVisible(true);
//	      }
//	    });
//	  }
	}