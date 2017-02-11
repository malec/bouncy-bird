import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class ChuckNorris extends Sprite {
	private double yVelocity=0;
	Image chuckNorrisImage = null;
	private Random randomAddition;
	private int xRandom;

	public ChuckNorris() {
		xPosition = -10;
		yPosition = 500;
		randomAddition = new Random();
		if (chuckNorrisImage == null) {// Lazy load
			try {
				chuckNorrisImage = ImageIO.read(new File("chuck_norris.png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		xRandom=randomAddition.nextInt(15);
	}

	public Image getImage() {
		return chuckNorrisImage;
	}

	public Boolean isObstacle() {
		return false;
	}

	public void drawSprite(Graphics g) {
		g.drawImage(chuckNorrisImage, xPosition, yPosition, null);
	}

	boolean update() {
		if (xPosition > 500 || yPosition > 500) {
			return true;
		} else {
			xPosition += 5+xRandom;
			yPosition -= 30;
			yPosition+=yVelocity;
			yVelocity+=1;
			return false;
		}
	}

}
