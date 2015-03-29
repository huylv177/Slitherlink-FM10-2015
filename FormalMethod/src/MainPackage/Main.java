package MainPackage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
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

public class Main {
	JFrame myFrame;
	static JLabel myLabel;
	MyPanel myCanvas;
	ArrayList<JButton> buttons;
	final static int NUM_OF_BUTTON = 4;

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

	boolean[][] row;
	boolean[][] col;

	JPanel tab1, tab2, panelCanvas;
	JTabbedPane tabbedPane;
	JScrollPane scroolPane;

	public static void main(String args[]) {
		new Main();
	}

	Main() {

		// doc file
		Path filePath = Paths.get("input/55/thao.txt");
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
		buttons.get(0).setBounds(CANVAS_WIDTH + 50, 50, 100, 40);
		buttons.get(1).setBounds(CANVAS_WIDTH + 50, 100, 100, 40);
		buttons.get(2).setBounds(CANVAS_WIDTH + 50, 150, 100, 40);
		buttons.get(3).setBounds(CANVAS_WIDTH + 50, 200, 100, 40);

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
						myCanvas.getRowArr()[i][j] = false;
					}
				}
				for (int i = 0; i < HEIGHT; i++) {
					for (int j = 0; j < WIDTH + 1; j++) {
						myCanvas.getColArr()[i][j] = false;
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
				WriteInput writeInput = new WriteInput(val);
				SatSolver satSolver = new SatSolver();
				String[] a = satSolver.getString().split(" ");
				ArrayList<Integer> b = new ArrayList<Integer>();
				for (int i = 0; i < a.length-1; i++) {
						b.add(Integer.parseInt(a[i]));
				}
				
				 for(int i=0;i<b.size();i++){
					 int edgeCode = Math.abs(b.get(i));
						if (b.get(i) == edgeCode) {
							if (e(edgeCode)[2] == 0) {
//								System.out.println(edgeCode+" " +e(edgeCode)[0]+" "+e(edgeCode)[1]);
								myCanvas.getRowArr()[e(edgeCode)[0]][e(edgeCode)[1]] = true;
							}else if(e(edgeCode)[2]==1){
//								System.out.println(edgeCode+" " +e(edgeCode)[0]+" "+e(edgeCode)[1]);
								myCanvas.getColArr()[e(edgeCode)[0]][e(edgeCode)[1]] = true;
							}
						}
				 }
				myCanvas.repaint();
			}
		});
	}

	int[] e(int edgeCode) {
		int[] e = new int[3];
		int d = WIDTH * (HEIGHT + 1);
		if (edgeCode < d) {
			e[1] = edgeCode % WIDTH-1;
			e[0] = (edgeCode - e[1]) / WIDTH ;
			e[2] = 0;
		} else {
			e[1] = (edgeCode - d) % WIDTH-1;
			e[0] = (edgeCode -d -e[1]) / WIDTH ;
			e[2] = 1;
		}
		return e;
	}

}
