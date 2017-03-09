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
	private static int callCost=5;
	private boolean decreaseHealth;
	private static final int xMoveAmount = 10;
	private boolean hasHitObstacle;

	public ChuckNorris(Model m) {
		positiveX = true;
		xVelocity = 0;
		model = m;
		xPosition = -10;
		yPosition = 500;
		randomAddition = new Random();
		decreaseHealth=true;
		hasHitObstacle=false;
		if (chuckNorrisImage == null) {// Lazy load
			try {
				chuckNorrisImage = ImageIO.read(new File("chuck_norris.png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public ChuckNorris(ChuckNorris that) {
		super(that);
		decreaseHealth=that.decreaseHealth;
		positiveX = that.positiveX;
		xVelocity = that.xVelocity;
		model = that.model;
		randomAddition = that.randomAddition;
		xRandom = that.xRandom;
		chuckNorrisImage = that.chuckNorrisImage;
		hasHitObstacle=that.hasHitObstacle;
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
		if(decreaseHealth){
			model.decreaseHealth(callCost);
			decreaseHealth=false;
		}
		if (positiveX) { // Do this only once.
			Iterator<Sprite> iterator = model.getIterator();
			while (iterator.hasNext()) {// cycle through sprites
				Sprite next = iterator.next();
				if (next.isObstacle()) {
					Obstacle temp = (Obstacle) next;
					if (this.doesCollide(next) && !hasHitObstacle&&!temp.beenHit) {
						// If it collides with an obstacle.
						positiveX = false;
						temp.yDestination = 0;
						yVelocity = 0;
						temp.beenHit = true;
						hasHitObstacle=true;
					}
				}
			}
		}
		if (xPosition > 500 || yPosition > 500) {
			return true;
		}
		// move up and right
		if (positiveX) {
			xPosition += xMoveAmount;
			yPosition -= 30;
		} else {
			// if it hit
			xPosition -= 5 + xRandom;
			xPosition += xVelocity;
			yPosition += 10;
			yVelocity += 1;
		}
		// move down (arc affect)
		yPosition += yVelocity;
		yVelocity += 1;
		return false;
	}

	public Sprite cloneSprite() {
		return new ChuckNorris(this);
	}

	public Boolean isBird() {
		return false;
	}

}
