package Swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import MainPackage.LoadingThread;
import MainPackage.SatSolver;
import MainPackage.SolveThread;
import MainPackage.WriteInput;

import javax.swing.JInternalFrame;


public class MainFrame {

	private JFrame frame;
	private MyPanel myCanvas;
	private WriteInput writeInput;
	private SatSolver satSolver;
	public static JTextArea textPaneInput;
	public static JTextArea textPaneOutput;
	public static JLabel lblNumOfVariable;
	public static JLabel lblNumOfClause;
	public JDialog loadingDialog;
	JLabel lblStatus;
	SolveThread solveThread;
	NewGameFrame newGameFrame;
	
	public JLabel lblTotalTime;
//	JLabel timerLabel;
	
	private String filePathInput = "input/55/2.txt";
	public static String cnfInput = "cnf/input.cnf";
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

		newGameFrame = new NewGameFrame();
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

		lblTotalTime = new JLabel("--:--");

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
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				newGameFrame.setVisible(true);
			}
		});
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
		btnNewGame.setToolTipText("Play a new random puzzle");

		JLabel lblNewLabel = new JLabel("Size:");

		JLabel lblSize = new JLabel(WIDTH + "x" + HEIGHT);

		JLabel lblInputCode1 = new JLabel("Input code:");

		JLabel lblInputCode = new JLabel("1");

		JLabel lblStatus1 = new JLabel("Status:");

		lblStatus = new JLabel("Not correct");

		JButton btnClear = new JButton("Clear");
		btnClear.setToolTipText("Clear game");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		JButton btnCheck = new JButton("Check");

		JButton btnSolve = new JButton("Solve");

		JButton btnFind = new JButton("Solve Now");
		GroupLayout gl_p3 = new GroupLayout(p3);
		gl_p3.setHorizontalGroup(
			gl_p3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_p3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p3.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewGame, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
						.addGroup(gl_p3.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblSize))
						.addGroup(gl_p3.createSequentialGroup()
							.addComponent(lblInputCode1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblInputCode))
						.addGroup(gl_p3.createSequentialGroup()
							.addComponent(lblStatus1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblStatus))
						.addComponent(btnClear, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
						.addComponent(btnCheck, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
						.addComponent(btnSolve, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
						.addComponent(btnFind, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_p3.setVerticalGroup(
			gl_p3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblSize))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_p3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInputCode1)
						.addComponent(lblInputCode))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_p3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatus1)
						.addComponent(lblStatus))
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
					.addContainerGap(177, Short.MAX_VALUE))
		);

		p3.setLayout(gl_p3);

		
		
		JButton btCancel = new JButton("Cancel");
//		timerLabel = new JLabel("0");
		loadingDialog = new JDialog(frame, "Progress Dialog", true);
        JProgressBar dpb = new JProgressBar(0, 100);
//        loadingDialog.add(BorderLayout.NORTH,timerLabel);
        loadingDialog.getContentPane().add(BorderLayout.CENTER, dpb);
        loadingDialog.getContentPane().add(BorderLayout.SOUTH,btCancel);
        loadingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dpb.setIndeterminate(true);
        loadingDialog.setSize(200, 80);
        loadingDialog.setLocationRelativeTo(frame);

		btCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
				solveThread.cancel(true);
				}catch(CancellationException e){
					System.out.println(e.getMessage());
				}
			}
		});
		
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
				lblTotalTime.setText("" + (solveTime + encodeTime));

				String[] stringDecoded = satSolver.getString().split(" ");
				myCanvas.repaintCanvas(stringDecoded);
			}
		});
		btnFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();
				solveThread = new SolveThread(loadingDialog,val,myCanvas,start,lblTotalTime,lblStatus);
				solveThread.execute();
				loadingDialog.setVisible(true);
			}
		});

		newGameFrame.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = newGameFrame.comboBox.getSelectedItem().toString();	
				String[] newSize = temp.split("x");
				filePathInput = "input/"+newSize[0]+newSize[1]+"/1.txt";
				System.out.println(filePathInput);
			}
		});
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


	// public void solveNext() {
	// writeInput = new WriteInput(val);
	// writeInput.writeToFile(cnfInput);
	// satSolver = new SatSolver();
	//
	// while (true) {
	// String[] a = satSolver.getString().split(" ");
	// ArrayList<Integer> b = new ArrayList<Integer>();
	// for (int i = 0; i < a.length - 1; i++) {
	// Integer temp = Integer.parseInt(a[i]);
	// b.add(-temp);
	// }
	//
	// writeInput.addFoundOutput(b);
	// writeInput.writeToFile(cnfInput);
	// satSolver = new SatSolver();
	// String[] stringDecoded = satSolver.getString().split(" ");
	// int[] numbers = new int[stringDecoded.length-1];
	// for (int i=0;i<stringDecoded.length-1;i++){
	// numbers[i] = Integer.parseInt(stringDecoded[i]);
	// }
	// if (myCanvas.checkLoop(numbers))
	// {
	// myCanvas.repaintCanvas(stringDecoded);
	// break;
	// }
	// }
	// }

	public void solve() {
		writeInput.writeToFile(cnfInput);
		satSolver = new SatSolver();
		String[] stringDecoded = satSolver.getString().split(" ");
		myCanvas.repaintCanvas(stringDecoded);
	}
	
}