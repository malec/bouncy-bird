import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;

class View extends JPanel {
	JButton b1;
	Model model;
	Obstacle first;
	Obstacle second;
	JLabel label;
	Iterator<Obstacle> tempIterator;

	View(Controller c, Model m) {
		c.setView(this);
		model = m;
		b1 = new JButton("Pause");
		b1.addActionListener(c);
		b1.setFocusable(false);
		this.add(b1);
		label = new JLabel();
		label.setFocusable(true);
		label.addKeyListener(c);
		this.add(label);
		this.addKeyListener(c);

		// To read the image in.
		try {
			this.model.bird.birdImage = ImageIO.read(new File("bird.png"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		// Set Obstacles
		// Remove these later
		first = new Obstacle(true, 500, 200);// remove these later
		second = new Obstacle(false, 999, -70);

		// Add the first two obstacles to the list.
		this.model.obstacleCollection.add(new Obstacle(true, 500, 200));// max
																		// 100
		this.model.obstacleCollection.add(new Obstacle(false, 999, -70));
	}

	public void paintComponent(Graphics g) {

		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(this.model.bird.birdImage, model.bird.bird_x, model.bird.bird_y, null);

		// Iterate through and draw the tubes.
		tempIterator=model.getIterator();
		while (tempIterator.hasNext()) {
			Obstacle temp = tempIterator.next();
			g.drawImage(temp.tube, temp.xPosition, temp.yPosition, null);
		}
		// g.drawImage(this.first.tube, this.first.xPosition,
		// this.first.yPosition, null);
		// g.drawImage(this.second.tube, this.second.xPosition,
		// this.second.yPosition, null);
		// this.labelPause.setVisible(!this.model.gameIsRunning());
		if (this.model.gameIsRunning()) {
			// Update each obstacle.
			tempIterator = model.getIterator();
			while (tempIterator.hasNext()) {
				tempIterator.next().update(model.random);
			}
			// System.out.println(this.first.tube.getHeight(null)+" Is the
			// height");
			if (checkCollision()) {
				System.out.println("Collision!");
				this.model.scoreReset();
			}
		}
	}

	void removeButton() {
		this.remove(b1);
		this.repaint();
	}

	private boolean checkCollision() {
		if (this.first.allowCollision() && this.model.bird.getBounds().intersects(this.first.getBounds())) {
			this.first.bypassCollision();
			this.first.bypassScore();
			return true;
		} else if (this.first.allowScore() && this.model.bird.getBounds().intersects(first.getPassSpace())) {// Update
																												// the
																												// score
			this.model.incrementScore();
			this.first.bypassScore();
			return false;
		}
		if (this.second.allowCollision() && this.model.bird.getBounds().intersects(this.second.getBounds())) {
			this.second.bypassCollision();
			this.second.bypassScore();
			return true;
		} else if (this.second.allowScore() && this.model.bird.getBounds().intersects(second.getPassSpace())) {// Update
																												// the
																												// score
			this.model.incrementScore();
			this.second.bypassScore();
			return false;
		}
		return false;
	}

	private int positive(int input) {
		if (input < 0) {
			return input *= -1;
		}
		return input;
	}

	public void resetDifficulty() {
		first.resetDifficulty();
		second.resetDifficulty();
	}
}
