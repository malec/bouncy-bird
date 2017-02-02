import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Random;

/**
 * Created by alec on 01/24/17.
 */
public class Obstacle {
	public boolean pointUP;
	public int xPosition = 1100;
	public int yPosition;
	public Image tube;
	private double difficultyIncrease = 0;
	private int farRightXPosition = 600;
	private int maxYUpright = 450;
	private int minYUpright = 150;
	private int minYNotUpright = 100;
	private int maxYNotUpright = 300;
	private boolean bypassCollision;
	private boolean bypassScore;
	private static int birdWidth = 64;

	Obstacle(boolean UP, int xpos, int ypos) {
		this.pointUP = UP;
		difficultyIncrease = 0;
		this.xPosition = xpos;
		this.yPosition = ypos;
		setOrientation(pointUP);
		bypassCollision = false;
		bypassScore = false;
	}

	// Generate a random obstacle
	public Obstacle(Random random) {
		pointUP = random.nextBoolean();
		if (pointUP) {
			yPosition = random.nextInt(maxYUpright - minYUpright) + minYUpright;
		} else {
			yPosition = (random.nextInt(maxYNotUpright - minYNotUpright) + minYNotUpright) * -1;
		}
		setOrientation(pointUP);
	}

	// Return if you should remove from the collection or not.
	public boolean update(Random random) {
		// If the tube is off the screen, the redraw it.
		if (xPosition < -100) {
			return true;
		} else {
			// Set the orientation.

			// difficultyIncrease -= .002; Put this somewhere else
		}
		xPosition -= 4 - difficultyIncrease; // Put this somewhere else
		return false;
	}

	private void setOrientation(boolean pointUP) {
		if (pointUP) {
			try {
				tube = ImageIO.read(new File("tube_up.png"));
			} catch (Exception error) {
				error.printStackTrace();
				System.exit(1);
			}
		} else {
			try {
				tube = ImageIO.read(new File("tube_down.png"));
			} catch (Exception error) {
				error.printStackTrace();
				System.exit(1);
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(this.xPosition, this.yPosition, tube.getWidth(null), tube.getHeight(null));
	}

	public boolean allowCollision() {
		return !bypassCollision;
	}

	public void bypassCollision() {
		bypassCollision = true;
	}

	public boolean allowScore() {
		return !bypassScore;
	}

	public void bypassScore() {
		bypassScore = true;
	}

	public Rectangle getPassSpace() {
		// Rectangle X Position =
		// this.xPosition+this.tube.getWidth(null)+birdWidth+5 add score once
		// he's clear of the tube. 5 px is arbitrary
		if (!pointUP) {
			return new Rectangle(this.xPosition + this.tube.getWidth(null) + birdWidth + 5,
					this.yPosition + this.tube.getHeight(null), this.tube.getWidth(null),
					500 - this.yPosition + this.tube.getHeight(null));
		}
		if (pointUP) {
			return new Rectangle(this.xPosition + this.tube.getWidth(null) + birdWidth + 5, 0, this.tube.getWidth(null),
					this.yPosition);
		}
		return null; // Not initialized?
	}

	public void resetDifficulty() {
		difficultyIncrease = 0;
	}

	public void tubeUpdate() {
		// TODO Auto-generated method stub

	}

}
