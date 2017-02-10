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
	private int healthIncreaseValue = 1;
	private int healthDecreaseValue = 25;
	private int bottomBound = 400;
	private int upperBound = 10;
	private int collisionFrame;
	private int scoreIncreaseInterval = 3;

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
		// g.drawImage(this.model.bird.birdImage,
		// model.bird.xPosition,model.bird.yPosition, null);
		// g.drawImage(model.hand.getImage(),
		// model.hand.getXPosition(),model.hand.yPosition,null);
		// model.hand.getYPosition(), null);
		// Iterate through and draw
		tempIterator = model.getIterator();
		while (tempIterator.hasNext()) {
			Sprite temp = tempIterator.next();
			temp.drawSprite(g);
		}
		checkScore();

		// System.out.println("Birdx: " + model.bird.xPosition + " and y is " + model.bird.yPosition);
		// Decrease the score, and push them off of the boundary
		checkLowerBound();
		checkUpperBound();

		if (this.model.gameIsRunning()) {
			// Increase the score "over time".
			if (0 == frames % scoreIncreaseInterval) {
				increaseProgressBar();
			}
		}
		// wrap frame count
		if (frames >= 1000000) {
			frames = 0;
		}
	}

	private void checkLowerBound() {
		if (model.bird.yPosition > bottomBound && model.gameOver == false) {
			if (collisionFrame == 0) {
				decreaseProgressBar();
				// System.out.println("Caught");
				collisionFrame++;
			} else {
				collisionFrame++;
				if (collisionFrame >= 20) {
					decreaseProgressBar();
					// System.out.println("Decreased on interval of 10");
					collisionFrame = 0;
				}
			}
		}
	}

	private void checkUpperBound() {
		if (model.bird.yPosition <= upperBound && model.gameOver == false) {
			if (collisionFrame == 0) {
				decreaseProgressBar();
				// System.out.println("Caught");
				collisionFrame++;
			} else {
				collisionFrame++;
				if (collisionFrame >= 20) {
					decreaseProgressBar();
					// System.out.println("Decreased on interval of 10");
					collisionFrame = 0;
				}
			}
		}
	}

	private void checkScore() {
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

	/*
	 * private boolean checkCollision() { tempIterator = model.getIterator();
	 * while (tempIterator.hasNext()) { Obstacle temp = tempIterator.next(); if
	 * (temp.allowCollision() &&
	 * this.model.bird.getBounds().intersects(temp.getBounds())) { // gets a
	 * collision. temp.bypassCollision(); temp.bypassScore();
	 * decreaseProgressBar(); return true; } else if (temp.allowScore() &&
	 * this.model.bird.getBounds().intersects(temp.getPassSpace())) { // Update
	 * the score this.model.incrementScore(); temp.bypassScore(); //
	 * increaseProgressBar(); return false; } } return false; }
	 */

	public void resetDifficulty() {
		Obstacle.resetDifficulty();
	}

	private void decreaseProgressBar() {
		lifeIndicator.setValue(lifeIndicator.getValue() - healthDecreaseValue);
	}

	private void increaseProgressBar() {
		if (lifeIndicator.getValue() < 100) {
			lifeIndicator.setValue(lifeIndicator.getValue() + healthIncreaseValue);
		}
	}
}