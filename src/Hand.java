import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Hand extends Sprite {
	private Image handOpenImage = null;
	private Image handCloseImage = null;
	public static int killGameYPosition = 550;
	private boolean handOpen;
	private Bird bird;
	private int handUpSpeed = 30;
	private int handDownSpeed = 30;
	private double handVelocity=0;
	private static final double constantHandVelocity=.8;

	Hand(Bird _bird) {
		handOpen = true;
		bird = _bird;
		// finished = false;
		if (handOpenImage == null) {
			try {
				handOpenImage = ImageIO.read(new File("hand1.png"));
			} catch (Exception error) {
				error.printStackTrace();
				System.exit(1);
			}
		}
		if (handCloseImage == null) {
			try {
				handCloseImage = ImageIO.read(new File("hand2.png"));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		xPosition = bird.xPosition;
		yPosition = 500;
	}

	public void animate(int birdYPosition) {
		//Move hand up
		if (yPosition >= birdYPosition && birdYPosition < killGameYPosition) {
			yPosition -= handUpSpeed+handVelocity;
			handVelocity-=constantHandVelocity;
		} else {
			// Change the image of the hand.
			handOpen = false;
			handVelocity=0;
			// Pull it down.
			if (yPosition >= killGameYPosition) {
				// Game Over
				System.exit(0);
			} else {
				yPosition += handDownSpeed;
				bird.yPosition += handDownSpeed+handVelocity;
				handVelocity +=constantHandVelocity;
			}
		}

		// while (yPosition!=killGameYPosition) {
		// yPosition += 10;
		// }
		// // Now change the hand image.
		// // If the animation is finished, exit.
		// if (yPosition == killGameYPosition) {
		// System.out.println("done?");
		// System.exit(0);
		// }
		// }
	}

	public Image getImage() {
		if (handOpen) {
			return handOpenImage;
		}
		return handCloseImage;
	}

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public Boolean isObstacle() {
		return false;
	}

	public boolean update() {
		return false;
	}

	public void drawSprite(Graphics g) {
		g.drawImage(getImage(),xPosition,yPosition,null);
	}
}
