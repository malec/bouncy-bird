import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;

public class ChuckNorris extends Sprite {
	private double yVelocity = 0;
	Image chuckNorrisImage = null;
	private Random randomAddition;
	private int xRandom;
	private Model model;
	private boolean positiveX;
	private double xVelocity;

	public ChuckNorris(Model m) {
		positiveX = true;
		xVelocity = 0;
		model = m;
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
		xRandom = randomAddition.nextInt(15);
	}
	
	public ChuckNorris(ChuckNorris that){
		positiveX=that.positiveX;
		xVelocity=that.xVelocity;
		model=that.model;
		xPosition=that.xPosition;
		yPosition=that.yPosition;
		randomAddition=that.randomAddition;
		xRandom=that.xRandom;
		chuckNorrisImage=that.chuckNorrisImage;
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
		if (positiveX) { // Do this only once.
			Iterator<Sprite> iterator = model.getIterator();
			while (iterator.hasNext()) {// cycle through sprites
				Sprite next = iterator.next();
				if (next.isObstacle()) {
					Obstacle temp = (Obstacle) next;
					if (this.doesCollide(next)&&!temp.beenHit) {
						//If it collides with an obstacle.
						positiveX = false;
						temp.yDestination = 0;
						yVelocity=0;
						temp.beenHit=true;
					}
				}
			}
		}
		if (xPosition > 500 || yPosition > 500) {
			return true;
		}
		//move up and right
		if (positiveX) {
			xPosition += 5 + xRandom;
			yPosition -= 30;
		} else {
			//if it hit
			xPosition -= 5 + xRandom;
			xPosition += xVelocity;
			yPosition+=10;
			yVelocity+=1;
		}
		//move down (arc affect)
		yPosition += yVelocity;
		yVelocity += 1;
		return false;
	}
	
	public Sprite cloneSprite(){
		return new ChuckNorris(this);
	}

}
