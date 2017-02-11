import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Random;

/**
 * Created by alec on 01/24/17.
 */
public class Obstacle extends Sprite {
	public boolean pointUP;
	public static Image spriteImage;
	public static Image tubeUP = null;
	public static Image tubeDown = null;
	private int minYUpright = 150;
	private int minYNotUpright = 100;
	private int maxYNotUpright = 300;
	private boolean bypassCollision;
	private boolean bypassScore;
	private static int birdWidth = 64;
	private static int scrollSpeed = 7;
	private static int previousYPosition;
	public int yDestination;
	private double yVelocity = 0;
	// public static LinkedList<Obstacle> obstacleCollection = new
	// LinkedList<Obstacle>();
	// public static Iterator<Obstacle> obstacleIterator;

	Obstacle(boolean UP, int xpos, int ypos, Random _random) {
		this.pointUP = UP;
		this.xPosition = xpos;
		this.yPosition = ypos;
		setOrientation(pointUP);
		bypassCollision = false;
		bypassScore = false;
		previousYPosition = ypos;
		yDestination = ypos;
	}

	// Generate a random obstacle
	public Obstacle(Random random) {
		pointUP = random.nextBoolean();
		if (pointUP) {
			yPosition = random.nextInt(previousYPosition) + minYUpright;
			previousYPosition = yPosition;
		} else {
			yPosition = (random.nextInt(maxYNotUpright - minYNotUpright) + minYNotUpright) * -1;
			previousYPosition = yPosition + getImage().getHeight(null);
		}
		xPosition = 500;
		setOrientation(pointUP);
		yDestination = yPosition;
	}

	// Return true if you should remove from the collection.
	public boolean update() {
		if (Model.gameIsRunning()) {
			// If the tube is off the screen, the redraw it.
			if (xPosition < -100) {
				// Remove from the list so return true
				return true;
			} else {
				if (yDestination == 0 ) {
					//The tubes moving, so don't allow collisions.
					bypassCollision=true;
						yPosition += 10;
						yPosition += yVelocity;
						yVelocity += 1;
				}
			}
			xPosition -= scrollSpeed; // Put this somewhere
		} // else
		return false;
	}

	private void setOrientation(boolean pointUP) {
		if (pointUP) {
			if (tubeUP == null) {
				try {
					tubeUP = ImageIO.read(new File("tube_up.png"));
				} catch (Exception error) {
					error.printStackTrace();
					System.exit(1);
				}
			}
			if (tubeDown == null) {
				try {
					tubeDown = ImageIO.read(new File("tube_down.png"));
				} catch (Exception error) {
					error.printStackTrace();
					System.exit(1);
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(this.xPosition, this.yPosition, getImage().getWidth(null), getImage().getHeight(null));
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
			return new Rectangle(this.xPosition + this.getImage().getWidth(null) + birdWidth + 5,
					this.yPosition + this.getImage().getHeight(null), this.getImage().getWidth(null),
					500 - this.yPosition + this.getImage().getHeight(null));
		}
		if (pointUP) {
			return new Rectangle(this.xPosition + this.getImage().getWidth(null) + birdWidth + 5, 0,
					this.getImage().getWidth(null), this.yPosition);
		}
		throw new RuntimeException("Rectangle Orientation not initialized");
	}

	public static void resetDifficulty() {
		scrollSpeed = 7;
	}

	public Image getImage() {
		if (pointUP) {
			return tubeUP;
		}
		return tubeDown;
	}

	public Boolean isObstacle() {
		return true;
	}

	public void drawSprite(Graphics g) {
		g.drawImage(getImage(), xPosition, yPosition, null);
	}

	public static void increaseDifficulty() {
		scrollSpeed++;
		System.out.println("Increased difficulty");
	}
}
