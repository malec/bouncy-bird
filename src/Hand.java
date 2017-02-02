import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Hand {
	private Image handImage = null;
	public int yPosition=500;
	private int xPosition = 20;
	public static int killGameYPosition=-300;
	Hand() {
		if (handImage == null) {
			try{
			handImage = ImageIO.read(new File("hand1.png"));
			}
			catch (Exception error)
			{
				error.printStackTrace();
				System.exit(1);
			}
		}
	}

	public void animate(int birdPosition) {
		while(yPosition!=birdPosition)
		yPosition-=10;
		//If the animation is finished, exit.
		if(yPosition==killGameYPosition){
			System.exit(0);
		}
	}
}
