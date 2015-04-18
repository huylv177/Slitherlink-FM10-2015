package MainPackage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.sat4j.minisat.core.Solver;

public class Main {
	JFrame myFrame;
	static JLabel myLabel;
	MyPanel myCanvas;
	ArrayList<JButton> buttons;
	final static int NUM_OF_BUTTON = 6;
	final static int BUTTON_HEIGHT = 30;
	// kich thuoc theo pixel
	int CANVAS_HEIGHT;
	int CANVAS_WIDTH;

	// kich thuoc man choi
	static int HEIGHT;
	static int WIDTH;

	// kich thuoc moi o
	final static int SIZE_BOX = 50;

	// val[i][j] la so cua o (i,j)
	int[][] val;
	// m[i][j][k] la gia tri cua net ve tai o (j,k), i=0 la canh tren, i=1 la
	// canh trai
	// int[][][] m;

	boolean[][] rowLeft, rowRight, colUp, colDown;

	JPanel tab1, tab2, panelCanvas;
	JTabbedPane tabbedPane;
	JScrollPane scroolPane;

	// /////////////////
	WriteInput writeInput;
	SatSolver satSolver;
	String filePathInput = "input/1010/1.txt";
	String cnfInput = "input/cnf/input.cnf";
	String cnfOutputs = "input/cnf/outputs.cnf";

	// //////////////////

	public static void main(String args[]) {
		new Main();
	}

	Main() {

		// doc file
		File fileInput = new File(cnfInput);
		File fileOutputs = new File(cnfOutputs);
		Path filePath = Paths.get(filePathInput);
		Scanner scanner = null;
		try {
			scanner = new Scanner(filePath);
			int i = 0, j = 0;
			HEIGHT = scanner.nextInt(); // 3
			WIDTH = scanner.nextInt(); // 5
			val = new int[HEIGHT][WIDTH];
			while (scanner.hasNext()) {
				if (scanner.hasNextInt()) {
					int q = scanner.nextInt();
					if (j >= WIDTH) {
						i++;
						j = 0;
						// System.out.println("\n");
					}
					val[i][j] = q;
					// System.out.println(q+" ");
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

		scroolPane = new JScrollPane();
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		tab1 = new JPanel();
		tab2 = new JPanel();
		panelCanvas = new JPanel();
		myCanvas = new MyPanel(WIDTH, HEIGHT, val);
		// myCanvas.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		tabbedPane.add("Game", tab1);
		tabbedPane.add("MiniSat", tab2);

		myLabel = new JLabel("2");
		myLabel.setBounds(CANVAS_WIDTH + 50, 10, 200, 50);

		buttons = new ArrayList<JButton>();
		for (int i = 0; i < NUM_OF_BUTTON; i++) {
			buttons.add(new JButton());
		}
		buttons.get(0).setText("Check");
		buttons.get(1).setText("Clear");
		buttons.get(2).setText("New Puzzle");
		buttons.get(3).setText("Solve");
		buttons.get(4).setText("Find another output");
		buttons.get(5).setText("Find all output");

		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setBounds(CANVAS_WIDTH + 50,
					50 + (BUTTON_HEIGHT + 10) * i, 150, BUTTON_HEIGHT);
		}

		myFrame = new JFrame();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLayout(null);
		myFrame.setSize(CANVAS_WIDTH + 300, CANVAS_HEIGHT + 50);
		// myFrame.add(myCanvas);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
		for (int i = 0; i < NUM_OF_BUTTON; i++) {
			tab1.add(buttons.get(i));
		}
		tab1.add(myLabel);
		myFrame.add(tabbedPane);
		tabbedPane.setBounds(0, 0, myFrame.getWidth() - 15,
				myFrame.getHeight() - 38);

		myCanvas.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		tab1.setLayout(null);
		tab1.add(panelCanvas);
		panelCanvas.setLayout(new BorderLayout());
		// panelCanvas.add(myCanvas);
		panelCanvas.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT - 15);
		// tab1.add(myCanvas,BorderLayout.CENTER);
		// panelCanvas.add(hbar, BorderLayout.SOUTH);
		// panelCanvas.add(vbar, BorderLayout.EAST);
		scroolPane.getViewport().add(myCanvas);
		panelCanvas.add(scroolPane, BorderLayout.CENTER);
		
		myFrame.setFocusable(true);
		myFrame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {			}
			@Override
			public void keyReleased(KeyEvent e) {			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==32){
					solveNext();
				}
			}
		});

		myFrame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				Component c = (Component) arg0.getSource();
				tabbedPane.setBounds(0, 0, c.getWidth() - 15,
						c.getHeight() - 38);
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});

		buttons.get(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttons.get(1).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < HEIGHT + 1; i++) {
					for (int j = 0; j < WIDTH; j++) {
						myCanvas.getRowLeftArr()[i][j] = false;
						myCanvas.getRowRightArr()[i][j] = false;
					}
				}
				for (int i = 0; i < HEIGHT; i++) {
					for (int j = 0; j < WIDTH + 1; j++) {
						myCanvas.getColUpArr()[i][j] = false;
						myCanvas.getColDownArr()[i][j] = false;
					}
				}
				myCanvas.repaint();
			}
		});
		buttons.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		buttons.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeInput = new WriteInput(val);
				writeInput.writeToFile(cnfInput);
				satSolver = new SatSolver();
				String[] stringDecoded = satSolver.getString().split(" ");
				repaintCanvas(stringDecoded);
			}

		});
		buttons.get(4).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				solveNext();

			}
		});
		buttons.get(5).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				writeInput = new WriteInput(val);
				solve();
				writeStringToFile(fileOutputs, satSolver.getString());
				while (satSolver.getRESULT_CODE() == 1) {
					solveNext();
					appendStringToFile(fileOutputs, satSolver.getString());
				}
			}
		});
	}

	int[] e(int edgeCode) {
		int[] e = new int[3];
		int d = WIDTH * (HEIGHT + 1) + (WIDTH + 1) * HEIGHT;
		int k = WIDTH * (HEIGHT + 1);

		if (edgeCode > d) {
			edgeCode -= d;
		}
		if (edgeCode <= k) {
			if (edgeCode % HEIGHT == 0) {
				e[0] = edgeCode / HEIGHT - 1;
				e[1] = HEIGHT - 1;
				e[2] = 0;
			} else {
				e[0] = edgeCode / HEIGHT;
				e[1] = edgeCode % HEIGHT - 1;
				e[2] = 0;
			}
		} else {
			edgeCode = edgeCode - k;
			if ((edgeCode % (HEIGHT + 1)) == 0) {
				e[0] = edgeCode / (HEIGHT + 1) - 1;
				e[1] = HEIGHT;
				e[2] = 1;
			} else {
				e[0] = edgeCode / (HEIGHT + 1);
				e[1] = edgeCode % (HEIGHT + 1) - 1;
				e[2] = 1;
			}
		}

		return e;
	}

	public void solveNext() {
		String[] a = satSolver.getString().split(" ");
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < a.length - 1; i++) {
			Integer temp = Integer.parseInt(a[i]);
			b.add(-temp);
		}
		writeInput.addFoundOutput(b);
		writeInput.writeToFile(cnfInput);
		satSolver = new SatSolver();
		String[] stringDecoded = satSolver.getString().split(" ");
		repaintCanvas(stringDecoded);
	}

	public void solve() {
		
		writeInput.writeToFile(cnfInput);
		satSolver = new SatSolver();
		String[] stringDecoded = satSolver.getString().split(" ");
		repaintCanvas(stringDecoded);
	}

	private void repaintCanvas(String[] stringDecoded) {

		clearArray(myCanvas.getColDownArr(), WIDTH, HEIGHT+1);
		clearArray(myCanvas.getRowRightArr(), WIDTH+1, HEIGHT);
		
		//doc output, chuyen String thanh array int,
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < stringDecoded.length - 1; i++) {
			b.add(Integer.parseInt(stringDecoded[i]));
//			System.out.println(Integer.parseInt(stringDecoded[i])+" ");
		}
		for (int i = 0; i < b.size(); i++) {
			int edgeCode = Math.abs(b.get(i));
			if (b.get(i) == edgeCode) {

				// if(edgeCode==36) System.out.println(e(edgeCode)[0] +" "+
				// e(edgeCode)[1]);

				if (e(edgeCode)[2] == 0) {
					// System.out.println(edgeCode+" "+e(edgeCode)[0]+" "+e(edgeCode)[1]);
					
					myCanvas.getRowRightArr()[e(edgeCode)[0]][e(edgeCode)[1]] = true;
				} else if (e(edgeCode)[2] == 1) {
					// System.out.println(edgeCode+" "+e(edgeCode)[0]+" "+e(edgeCode)[1]);
					
					myCanvas.getColDownArr()[e(edgeCode)[0]][e(edgeCode)[1]] = true;
				}
			}
		}
		myCanvas.repaint();
	}

	private void appendStringToFile(File file, String text) {
		try {
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.newLine();
				bw.append(text);
				bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeStringToFile(File file, String text) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void clearArray(boolean[][] a,int r,int c){
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				a[i][j]=false;
			}
		}
	}
}
