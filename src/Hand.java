import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Hand {
	private Image handImage = null;
	public int yPosition=500;
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

	public void animate() {
		yPosition-=10;
	}
}
