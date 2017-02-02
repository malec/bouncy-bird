import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Hand {
	private Image handOpenImage = null;
	private Image handCloseImage = null;
	public int yPosition = 500;
	private int xPosition = 20;
	public static int killGameYPosition = 550;
	private boolean handOpen;
	//private boolean finished;

	Hand() {
		handOpen = true;
		//finished = false;
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
	}

	public void animate(int birdPosition) {
		yPosition=birdPosition;
//			while (yPosition != birdPosition) {
//				yPosition -= 1;
//			}
//			// Change the image.
//			handOpen = false;
//			// Pull it down.
//			while (yPosition!=killGameYPosition) {
//				yPosition += 10;
//			}
//			// Now change the hand image.
//			// If the animation is finished, exit.
//			if (yPosition == killGameYPosition) {
//				System.out.println("done?");
//				System.exit(0);
//			}
		//}
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
}
