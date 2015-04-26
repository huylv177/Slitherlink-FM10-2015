package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import MainPackage.SatEncode;
import MainPackage.SatSolver;
import MainPackage.WriteInput;

public class MainFrame {

	private JFrame frame;
	private Dimension area;
	private MyPanel myCanvas;
	private WriteInput writeInput;
	private SatSolver satSolver;
	public static JTextArea textPaneInput;
	public static JTextArea textPaneOutput;
	public static JLabel lblNumOfVariable;
	public static JLabel lblNumOfClause;

	private String filePathInput = "input/44/1.txt";
	private String cnfInput = "cnf/input.cnf";
	private String cnfOutputs = "cnf/outputs.cnf";

	// kich thuoc theo pixel
	int CANVAS_HEIGHT;
	int CANVAS_WIDTH;

	// kich thuoc man choi
	public static int HEIGHT;
	public static int WIDTH;

	// kich thuoc moi o
	final static int SIZE_BOX = 50;
	// val[i][j] la so cua o (i,j)
	int[][] val;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		readInputFile();

		frame = new JFrame();
		frame.setBounds(100, 100, 600, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(tabbedPane,
				GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(tabbedPane,
				GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE));

		JPanel panel1 = new JPanel();
		tabbedPane.addTab("     Game     ", null, panel1, null);
		panel1.setLayout(new BorderLayout());

		JPanel panel2 = new JPanel();
		tabbedPane.addTab("     Sat4j     ", null, panel2, null);
		panel2.setLayout(new GridLayout(1, 3));

		JPanel panel = new JPanel();
		panel2.add(panel);

		JLabel lblCnfInput = new JLabel("CNF Input");

		textPaneInput = new JTextArea();
		textPaneInput.setEditable(false);
		JScrollPane textAreaInputscrollPane = new JScrollPane(textPaneInput);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(textAreaInputscrollPane,
												GroupLayout.DEFAULT_SIZE, 173,
												Short.MAX_VALUE)
										.addComponent(lblCnfInput))
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCnfInput)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textAreaInputscrollPane,
								GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
						.addContainerGap()));
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		panel2.add(panel_1);

		JLabel lblCnfOutput = new JLabel("CNF Output");

		textPaneOutput = new JTextArea();
		textPaneOutput.setEditable(false);
		JScrollPane textAreaOutputscrollPane = new JScrollPane(textPaneOutput);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(textAreaOutputscrollPane,
												GroupLayout.DEFAULT_SIZE, 173,
												Short.MAX_VALUE)
										.addComponent(lblCnfOutput))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblCnfOutput)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textAreaOutputscrollPane,
								GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
						.addContainerGap()));
		panel_1.setLayout(gl_panel_1);

		JPanel panel_2 = new JPanel();
		panel2.add(panel_2);

		JLabel lblEncodeTime1 = new JLabel("Encode Time:");

		JLabel lblEncodeTime = new JLabel("--:--");

		JLabel lblSolveTime1 = new JLabel("Solve Time:");

		JLabel lblSolveTime = new JLabel("--:--");

		JLabel lblTotalTime1 = new JLabel("Total Time:");

		JLabel lblTotalTime = new JLabel("--:--");

		JLabel lblNumOfVariable1 = new JLabel("Num of variable:");

		lblNumOfVariable = new JLabel("0");

		JLabel lblNumOfClause1 = new JLabel("Num of clause:");

		lblNumOfClause = new JLabel("0");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addComponent(
																				lblEncodeTime1)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblEncodeTime))
														.addGroup(
																gl_panel_2
																		.createParallelGroup(
																				Alignment.LEADING)
																		.addGroup(
																				gl_panel_2
																						.createSequentialGroup()
																						.addComponent(
																								lblSolveTime1)
																						.addPreferredGap(
																								ComponentPlacement.RELATED)
																						.addComponent(
																								lblSolveTime))
																		.addGroup(
																				gl_panel_2
																						.createSequentialGroup()
																						.addComponent(
																								lblTotalTime1)
																						.addPreferredGap(
																								ComponentPlacement.RELATED)
																						.addComponent(
																								lblTotalTime)))
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addComponent(
																				lblNumOfVariable1)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblNumOfVariable))
														.addGroup(
																gl_panel_2
																		.createSequentialGroup()
																		.addComponent(
																				lblNumOfClause1)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblNumOfClause)))
										.addContainerGap(92, Short.MAX_VALUE)));
		gl_panel_2
				.setVerticalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblEncodeTime)
														.addComponent(
																lblEncodeTime1))
										.addGap(18)
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblSolveTime1)
														.addComponent(
																lblSolveTime))
										.addGap(18)
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblTotalTime1)
														.addComponent(
																lblTotalTime))
										.addGap(18)
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNumOfVariable1)
														.addComponent(
																lblNumOfVariable))
										.addGap(18)
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNumOfClause1)
														.addComponent(
																lblNumOfClause))
										.addContainerGap(243, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);
		frame.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNewGame = new JMenuItem("New game");
		mnFile.add(mntmNewGame);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		JMenuItem mntmSolve = new JMenuItem("Solve");
		mnGame.add(mntmSolve);

		JMenu mnAboutUs = new JMenu("Help");
		menuBar.add(mnAboutUs);

		JMenuItem mntmAbout = new JMenuItem("About us");
		mnAboutUs.add(mntmAbout);

		myCanvas = new MyPanel(WIDTH, HEIGHT, val);
		myCanvas.setLayout(new BorderLayout());
		myCanvas.setBackground(Color.cyan);

		// Put the drawing area in a scroll pane.
		JScrollPane scroller = new JScrollPane(myCanvas);
		scroller.setPreferredSize(new Dimension(200, 200));
		panel1.add(scroller, BorderLayout.CENTER);

		JPanel p3 = new JPanel();
		panel1.add(p3, BorderLayout.EAST);

		JButton btnNewGame = new JButton("New Puzzle");

		JLabel lblNewLabel = new JLabel("Size:");

		JLabel lblSize = new JLabel(WIDTH + "x" + HEIGHT);

		JLabel lblInputCode1 = new JLabel("Input code:");

		JLabel lblInputCode = new JLabel("1");

		JLabel lblStatus = new JLabel("Status:");

		JLabel lblNotCorrect = new JLabel("Not correct");

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		JButton btnCheck = new JButton("Check");

		JButton btnSolve = new JButton("Solve");

		JButton btnFind = new JButton("Solve Now");

		LoadingPanel pnLoadingImage = new LoadingPanel();
		pnLoadingImage.setVisible(false);
		GroupLayout gl_p3 = new GroupLayout(p3);
		gl_p3.setHorizontalGroup(gl_p3
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_p3.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_p3.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														pnLoadingImage,
														GroupLayout.PREFERRED_SIZE,
														80,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														btnNewGame,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE)
												.addGroup(
														gl_p3.createSequentialGroup()
																.addComponent(
																		lblNewLabel)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		lblSize))
												.addGroup(
														gl_p3.createSequentialGroup()
																.addComponent(
																		lblInputCode1)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		lblInputCode))
												.addGroup(
														gl_p3.createSequentialGroup()
																.addComponent(
																		lblStatus)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		lblNotCorrect))
												.addComponent(btnFind,
														Alignment.TRAILING)
												.addComponent(
														btnClear,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE)
												.addComponent(
														btnCheck,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE)
												.addComponent(
														btnSolve,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														131, Short.MAX_VALUE))
								.addContainerGap()));
		gl_p3.setVerticalGroup(gl_p3.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_p3.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_p3.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblNewLabel)
												.addComponent(lblSize))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_p3.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblInputCode1)
												.addComponent(lblInputCode))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_p3.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblStatus)
												.addComponent(lblNotCorrect))
								.addGap(15)
								.addComponent(btnNewGame)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnClear)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnCheck)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSolve)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnFind)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(pnLoadingImage,
										GroupLayout.PREFERRED_SIZE, 75,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(96, Short.MAX_VALUE)));
		p3.setLayout(gl_p3);

		// area = new Dimension(CANVAS_WIDTH,CANVAS_HEIGHT);
		// myCanvas.setPreferredSize(area);

		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < HEIGHT + 1; i++) {
					for (int j = 0; j < WIDTH; j++) {
						// myCanvas.getRowLeftArr()[i][j] = false;
						// myCanvas.getRowRightArr()[i][j] = false;
					}
				}
				for (int i = 0; i < HEIGHT; i++) {
					for (int j = 0; j < WIDTH + 1; j++) {
						// myCanvas.getColUpArr()[i][j] = false;
						// myCanvas.getColDownArr()[i][j] = false;
					}
				}
				myCanvas.repaint();
			}
		});
		btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnLoadingImage.setVisible(true);
				writeInput = new WriteInput(val);
				Long beforeEncode = System.currentTimeMillis();
				writeInput.writeToFile(cnfInput);
				Long afterEncode = System.currentTimeMillis();
				float encodeTime = ((float) (afterEncode - beforeEncode)) / 1000;
				lblEncodeTime.setText("" + encodeTime);
				// System.out.println("encode Time:"+encodeTime);

				Long beforeSolve = System.currentTimeMillis();
				satSolver = new SatSolver();
				Long afterSolve = System.currentTimeMillis();
				float solveTime = ((float) (afterSolve - beforeSolve)) / 1000;
				lblSolveTime.setText("" + solveTime);
				// System.out.println("solve Time:" +solveTime);
				pnLoadingImage.setVisible(false);
				lblTotalTime.setText("" + (solveTime + encodeTime));

				String[] stringDecoded = satSolver.getString().split(" ");
				repaintCanvas(stringDecoded);
			}
		});
		btnFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solveNext();

				// ////////////////////////////////
				// writeInput = new WriteInput(val);
				// solve();
				// writeStringToFile(fileOutputs, satSolver.getString());
				// while (satSolver.getRESULT_CODE() == 1) {
				// solveNext();
				// appendStringToFile(fileOutputs, satSolver.getString());
				// }
			}
		});

	}

	// Ham dung de kiem tra da thoa man luat 3 chua
	public boolean checkLoop(int[] edge) {
		int firstEdge = 0;
		int count = 0;// la tong so canh nam tren duong minh duyet
		for (int i = 0; i < edge.length - 1; i++) {
			if (edge[i] > 0) {
				firstEdge = i + 1;
//				System.out.print("edge" + edge[i]);
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

	public void readInputFile() {
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
					}
					val[i][j] = q;
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
		writeInput = new WriteInput(val);
		writeInput.writeToFile(cnfInput);
		satSolver = new SatSolver();
		
		while (true) {
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
			int[] numbers = new int[stringDecoded.length-1];
			for (int i=0;i<stringDecoded.length-1;i++){
				numbers[i] = Integer.parseInt(stringDecoded[i]);
			}
//			System.out.print("\n"+satSolver.getString()+"\n");
//			System.out.print("Thoa man:"+checkLoop(numbers)+"\n\n\n");
			if (checkLoop(numbers))
			{
				repaintCanvas(stringDecoded);
				break;
			}
		}
	}

	public void solve() {

		writeInput.writeToFile(cnfInput);
		satSolver = new SatSolver();
		String[] stringDecoded = satSolver.getString().split(" ");
		repaintCanvas(stringDecoded);
	}

	private void repaintCanvas(String[] stringDecoded) {

		clearArray(myCanvas.getColDownArr(), WIDTH, HEIGHT + 1);
		clearArray(myCanvas.getRowRightArr(), WIDTH + 1, HEIGHT);

		// doc output, chuyen String thanh array int,
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < stringDecoded.length - 1; i++) {
			b.add(Integer.parseInt(stringDecoded[i]));
			// System.out.println(Integer.parseInt(stringDecoded[i])+" ");
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
		myCanvas.setPreferredSize(area);
		myCanvas.validate();
		myCanvas.repaint();
		myCanvas.setPreferredSize(area);
	}

	private void appendStringToFile(File file, String text) {
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
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

	void clearArray(boolean[][] a, int r, int c) {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				a[i][j] = false;
			}
		}
	}
}
