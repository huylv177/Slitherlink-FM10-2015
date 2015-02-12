package MainPackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class MKeyListener extends KeyAdapter {
	@Override
	public void keyPressed(KeyEvent event) {
		char ch = event.getKeyChar();
		if (ch == 'a' || ch == 'b' || ch == 'c') {
			System.out.println(event.getKeyChar());
		}
		if (event.getKeyCode() == KeyEvent.VK_HOME) {
			System.out.println("Key codes: " + event.getKeyCode());
		}
	}
}
