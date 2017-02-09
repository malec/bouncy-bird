import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Created by alec on 01/23/17.
 */
public class Bird extends Sprite{
	private static int wingflyDuration = 3;
	private static double verticalVelocityIncrement = -16;
	public int bird_y = 80;
	public int bird_x = 20;
	public double dblVerticalVelcoity;
	private int frameCouter;
	public static Image birdImage = null;
	static public Image defaultbirdImage;
	public static Image birdFlapImage = null;

	Bird() {
		// Lazy load the image.
		if (birdImage == null) {
			// Load the image.
			try {
				defaultbirdImage = ImageIO.read(new File("bird.png"));
				birdImage = defaultbirdImage;
			} catch (Exception error) {
				error.printStackTrace(System.err);
				System.exit(1);
			}
		}
		if(birdFlapImage==null){
			try{
				birdFlapImage = ImageIO.read(new File("bird2.png"));
			}
			catch(Exception error){
				error.printStackTrace();
				System.exit(1);
			}
		}
		frameCouter = 0;

	}

	public boolean update() {
		// Move the bird
		if (bird_y < 400) {
			dblVerticalVelcoity += wingflyDuration;
			this.bird_y += dblVerticalVelcoity;
			frameCouter++;
			if (frameCouter >= wingflyDuration) {
				frameCouter = 0;
				//this.birdImage = defaultbirdImage;
			}
		} else {
			// Game Fail.
			dblVerticalVelcoity = 0;
		}
		return false;
	}

	public void flap() {
		dblVerticalVelcoity = 0;

		dblVerticalVelcoity += verticalVelocityIncrement;
		this.bird_y += dblVerticalVelcoity;
		try {
			birdImage = birdFlapImage;
		} catch (Exception error) {
			error.printStackTrace();
			System.exit(1);
		}
	}
	public void release(){
		birdImage=defaultbirdImage;
	}

	public Rectangle getBounds() {
		return new Rectangle(bird_x, bird_y, birdImage.getWidth(null), birdImage.getHeight(null));
	}

	public void jumpOffBound(int bottomBound, int topBound) {
		if (bird_y <= topBound) {
			bird_y += 5;
		} else if (bird_y >= bottomBound) {
			bird_y -= 5;
		}
	}
}
