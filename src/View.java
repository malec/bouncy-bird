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
	int frames=0;
	JProgressBar lifeIndicator;

	View(Controller c, Model m) {
		c.setView(this);
		model = m;
		b1 = new JButton("Pause");
		b1.addActionListener(c);
		b1.setFocusable(false);
		//this.add(b1);
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
		this.setBorder(BorderFactory.createEmptyBorder(0,250,0,250));
		panel.setBorder(BorderFactory.createEmptyBorder(380,250,10,250));
		panel.add(lifeIndicator);
		this.add(panel);

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
		// Iterate through and draw the tubes.
		tempIterator = model.getIterator();
		while (tempIterator.hasNext()) {
			Obstacle temp = tempIterator.next();
			g.drawImage(temp.gettube(), temp.xPosition, temp.yPosition, null);
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
		tempIterator = model.getIterator();
		while (tempIterator.hasNext()) {
			Obstacle temp = tempIterator.next();
			if (temp.allowCollision() && this.model.bird.getBounds().intersects(temp.getBounds())) {
				temp.bypassCollision();
				temp.bypassScore();
				lifeIndicator.setValue(lifeIndicator.getValue()-30);
				return true;
			} else if (temp.allowScore() && this.model.bird.getBounds().intersects(temp.getPassSpace())) {
				//Update the score
				this.model.incrementScore();
				temp.bypassScore();
				lifeIndicator.setValue(lifeIndicator.getValue()+5);
				return false;
			}
		}
		return false;
	}

	public void resetDifficulty() {
		first.resetDifficulty();
		second.resetDifficulty();
	}
}
