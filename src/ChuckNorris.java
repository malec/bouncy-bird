import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ChuckNorris extends Sprite {
	Image chuckNorrisImage = null;

	public Image getImage() {
		return chuckNorrisImage;
	}

	public Boolean isObstacle() {
		return false;
	}

	public void drawSprite(Graphics g) {

	}

	boolean update() {
		return false;
	}

	public ChuckNorris() {
		if (chuckNorrisImage == null) {//Lazy load
			try {
				chuckNorrisImage = ImageIO.read(new File("chuck_norris.png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
