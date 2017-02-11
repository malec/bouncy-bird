import java.awt.event.*;

class Controller implements KeyListener, ActionListener, MouseListener {
	View view;
	Model model;
	Game game;

	Controller(Model m, Game g) {
		model = m;
		game = g;
	}

	public void actionPerformed(ActionEvent e) {
		if (model.gameIsRunning() && !model.gameOver) {
			model.gamePause();
		} else if (!model.gameIsRunning() && model.gameOver == false) {
			model.gameResume();
		}
	}

	public void mousePressed(MouseEvent e) {
		if (model.gameIsRunning() && e.getButton() == MouseEvent.BUTTON1) {
			model.onClick();
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (model.gameIsRunning() && e.getButton() == MouseEvent.BUTTON1) {
			model.onRelease();
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			//Call for chuck norris.
		}
	}

	void setView(View v) {
		view = v;
		view.addMouseListener(this);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			model.onClick();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			model.onRelease();
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			model.gamePause();
		}
		if (e.getKeyCode() == KeyEvent.VK_H) {
			view.gameOver();
			System.out.println("Game Over...");
		}
	}
}
