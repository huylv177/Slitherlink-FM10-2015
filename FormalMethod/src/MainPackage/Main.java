package MainPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import statements
//Check if window closes automatically. Otherwise add suitable code
public class Main {
	JFrame myFrame;
	static JLabel myLabel;
	MyPanel myCanvas;
	ArrayList<JButton> buttons;
	final static int NUM_OF_BUTTON = 3;
	
	//kich thuoc theo pixel
	int CANVAS_HEIGHT;
	int CANVAS_WIDTH;
	
	//kich thuoc man choi
	int HEIGHT = 5;
	int WIDTH = 5;
	
	//kich thuoc moi o
	final static int SIZE_BOX = 50;
	
	//num[i][j] la so cua o (i,j)
	int[][] num;
	//m[i][j][k] la gia tri cua net ve tai o (j,k), i=0 la canh tren, i=1 la canh trai 
	int[][][] m;

	public static void main(String args[]) {
		new Main();
	}

	Main() {

		// doc file 
		Path filePath = Paths.get("thao.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(filePath);
			int i = 0, j = 0;
			WIDTH = scanner.nextInt();
			HEIGHT = scanner.nextInt();
			num = new int[WIDTH][HEIGHT];
			while (scanner.hasNext()) {
				if (scanner.hasNextInt()) {
					int q = scanner.nextInt();
					if (j >= WIDTH) {
						i++;
						j = 0;
					}
					num[i][j] = q;
					j++;
				} else {
					scanner.next();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		CANVAS_HEIGHT = (HEIGHT + 2) * SIZE_BOX;
		CANVAS_WIDTH = (WIDTH + 2) * SIZE_BOX;

		myCanvas = new MyPanel(WIDTH, HEIGHT, num);
		myCanvas.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

		myLabel = new JLabel("2");
		myLabel.setBounds(CANVAS_WIDTH + 50, 10, 200, 50);

		buttons = new ArrayList<JButton>();
		for (int i = 0; i < NUM_OF_BUTTON; i++) {
			buttons.add(new JButton());
		}
		buttons.get(0).setText("Check");
		buttons.get(1).setText("Clear");
		buttons.get(2).setText("New Puzzle");
		buttons.get(0).setBounds(CANVAS_WIDTH + 50, 50, 100, 40);
		buttons.get(1).setBounds(CANVAS_WIDTH + 50, 100, 100, 40);
		buttons.get(2).setBounds(CANVAS_WIDTH + 50, 150, 100, 40);

		myFrame = new JFrame();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLayout(null);
		myFrame.setSize(CANVAS_WIDTH + 300, CANVAS_HEIGHT + 50);
		myFrame.add(myCanvas);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		for (int i = 0; i < NUM_OF_BUTTON; i++) {
			myFrame.add(buttons.get(i));
		}
		myFrame.add(myLabel);

		buttons.get(0).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		buttons.get(1).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<WIDTH+1;i++){
					for(int j=0;j<HEIGHT;j++){
						myCanvas.getMainArray()[0][i][j]=false;
						myCanvas.getMainArray()[1][i][j]=false;
					}
					
				}
				
				myCanvas.repaint();
			}
		});
		buttons.get(2).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

}
