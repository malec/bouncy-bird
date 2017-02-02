import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;

class View extends JPanel {
	private JButton b1;
	public Model model;
	private Hand hand;
	// private Obstacle first;
	// private Obstacle second;
	private JLabel label;
	private Iterator<Obstacle> tempIterator;
	private int frames = 0;
	private JProgressBar lifeIndicator;

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
		hand = new Hand();

		// To read the image in.
		try {
			this.model.bird.birdImage = ImageIO.read(new File("bird.png"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		// Add the first two obstacles to the list.
		this.model.obstacleCollection.add(new Obstacle(true, 500, 200));// max
		this.model.obstacleCollection.add(new Obstacle(false, 800, -70));
	}

	public void paintComponent(Graphics g) {
		frames++;
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(this.model.bird.birdImage, model.bird.bird_x, model.bird.bird_y, null);
		g.drawImage(hand.getImage(), hand.getXPosition(), hand.getYPosition(), null);
		// Iterate through and draw the tubes.
		tempIterator = model.getIterator();
		while (tempIterator.hasNext()) {
			Obstacle temp = tempIterator.next();
			g.drawImage(temp.gettube(), temp.xPosition, temp.yPosition, null);
		}
		checkScore();
		if (this.model.gameIsRunning()) {
			// Update each obstacle.
			tempIterator = model.getIterator();
			while (tempIterator.hasNext()) {
				tempIterator.next().update(model.random);
			}
			if (checkCollision()) {
				System.out.println("Collision!");
				this.model.scoreReset();
			}
		}
	}

	private void checkScore() {
		if (lifeIndicator.getValue() <= 0) {
			gameOver();
		}
	}

	void gameOver() {
		//freeze game
		model.gameOver();
		hand.animate(model.bird.bird_y);
	}

	void removeButton() {
		this.remove(b1);
		this.repaint();
	}

	private boolean checkCollision() {
		tempIterator = model.getIterator();
		while (tempIterator.hasNext()) {
			Obstacle temp = tempIterator.next();
			if (temp.allowCollision() && this.model.bird.getBounds().intersects(temp.getBounds())) {
				temp.bypassCollision();
				temp.bypassScore();
				decreaseProgressBar();
				return true;
			} else if (temp.allowScore() && this.model.bird.getBounds().intersects(temp.getPassSpace())) {
				// Update the score
				this.model.incrementScore();
				temp.bypassScore();
				increaseProgressBar();
				return false;
			}
		}
		return false;
	}

	public void resetDifficulty() {
		tempIterator = model.getIterator();
		while (tempIterator.hasNext()) {
			tempIterator.next().resetDifficulty();
		}
	}

	private void decreaseProgressBar() {
		lifeIndicator.setValue(lifeIndicator.getValue() - 30);
	}

	private void increaseProgressBar() {
		if (lifeIndicator.getValue() < 100) {
			lifeIndicator.setValue(lifeIndicator.getValue() + 5);
		}
	}
}