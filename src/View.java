import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;

class View extends JPanel {
	private JButton b1;
	public Model model;
	// private Obstacle first;
	// private Obstacle second;
	private JLabel label;
	private Iterator<Sprite> tempIterator;
	private int frames = 0;
	private JProgressBar lifeIndicator;
	private int healthDecreaseValue = 25;
	private int bottomBound = 400;
	private int upperBound = 10;
	private int collisionFrame;

	View(Controller c, Model m) {
		c.setView(this);
		model = m;
		b1 = new JButton("Pause");
		b1.addActionListener(c);
		b1.setFocusable(false);
		// this.add(b1);
		label = new JLabel();
		label.setFocusable(true);
		label.addKeyListener(c);
		this.addKeyListener(c);
		this.add(b1);
		this.add(label);
		lifeIndicator = new JProgressBar();
		lifeIndicator.setValue(100);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder(0, 250, 0, 250));
		panel.setBorder(BorderFactory.createEmptyBorder(380, 250, 10, 250));
		panel.add(lifeIndicator);
		this.add(panel);

		// To read the image in.
		try {
			this.model.bird.birdImage = ImageIO.read(new File("bird.png"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public void paintComponent(Graphics g) {
		frames++;
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		tempIterator = model.getIterator();
		while (tempIterator.hasNext()) {
			Sprite temp = tempIterator.next();
			temp.drawSprite(g);
		}
		checkScore();
		checkLowerBound();
		checkUpperBound();
		// wrap frame count
		if (frames >= 1000000) {
			frames = 0;
		}
	}

	private void checkLowerBound() {
		if (model.bird.yPosition > bottomBound && model.gameOver == false) {
			if (collisionFrame == 0) {
				model.decreaseHealth();
				collisionFrame++;
			} else {
				collisionFrame++;
				if (collisionFrame >= 20) {
					model.decreaseHealth();
					collisionFrame = 0;
				}
			}
		}
	}

	private void checkUpperBound() {
		if (model.bird.yPosition <= upperBound && model.gameOver == false) {
			if (collisionFrame == 0) {
				model.decreaseHealth();
				// System.out.println("Caught");
				collisionFrame++;
			} else {
				collisionFrame++;
				if (collisionFrame >= 20) {
					model.decreaseHealth();
					// System.out.println("Decreased on interval of 10");
					collisionFrame = 0;
				}
			}
		}
	}

	private void checkScore() {
		lifeIndicator.setValue(model.getHealth());
		if (lifeIndicator.getValue() <= 0) {
			gameOver();
		}
	}

	void gameOver() {
		// freeze game
		model.gameOver();
		model.hand.animate(model.bird.yPosition);
	}

	void removeButton() {
		this.remove(b1);
		this.repaint();
	}

	public void resetDifficulty() {
		Obstacle.resetDifficulty();
	}

	private void decreaseProgressBar() {
		lifeIndicator.setValue(lifeIndicator.getValue() - healthDecreaseValue);
	}	
}