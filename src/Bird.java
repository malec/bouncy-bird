import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Iterator;

/**
 * Created by alec on 01/23/17.
 */
public class Bird extends Sprite {
	public static enum actions{do_nothing,flap,call_chuck};
	private static int wingflyDuration = 3;
	private static double verticalVelocityIncrement = -16;
	public double dblVerticalVelcoity;
	//private int frameCouter;
	public static Image birdImage = null;
	public static Image defaultbirdImage;
	public static Image birdFlapImage = null;
	private Model model;
	private Boolean allowCollision;
	private static int collisionFrame = 0;
	private static int bottomBound = 400;
	private static int upperBound = 10;
	public int health;

	Bird(Model m) {
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
			xPosition = 20;
			yPosition = 20;
		}
		if (birdFlapImage == null) {
			try {
				birdFlapImage = ImageIO.read(new File("bird2.png"));
			} catch (Exception error) {
				error.printStackTrace();
				System.exit(1);
			}
		}
		//frameCouter = 0;
		allowCollision = true;
		model = m;
	}

	public Bird(Bird bird) {
		super(bird);
		dblVerticalVelcoity=bird.dblVerticalVelcoity;
		allowCollision=bird.allowCollision;
		health=bird.health;
	}

	public boolean update() {
		if (model.gameIsRunning()) {
			checkBounds();
			// Move the bird
			if (yPosition < 400) {
				dblVerticalVelcoity += wingflyDuration;
				yPosition += dblVerticalVelcoity;
				//frameCouter++;
			} else {
				// Game Fail.
				dblVerticalVelcoity = 0;
			}
		}
		/*
		 * Iterator<Sprite> spriteIterator = model.getIterator(); while
		 * (spriteIterator.hasNext()) { Sprite temp = spriteIterator.next(); if
		 * (temp.isObstacle()) { Obstacle tempObs = (Obstacle) temp; if
		 * (getBounds().intersects(tempObs.getPassSpace())) { resetCollision();
		 * System.out.println("Collision reset"); } } }
		 */
		return false;
	}

	private void checkBounds() {
		checkUpperBound();
		checkLowerBound();
	}

	public void flap() {
		dblVerticalVelcoity = 0;

		dblVerticalVelcoity += verticalVelocityIncrement;
		yPosition += dblVerticalVelcoity;
		try {
			birdImage = birdFlapImage;
		} catch (Exception error) {
			error.printStackTrace();
			System.exit(1);
		}
	}

	public void release() {
		birdImage = defaultbirdImage;
	}

	public Rectangle getBounds() {
		return new Rectangle(xPosition, yPosition, birdImage.getWidth(null), birdImage.getHeight(null));
	}

	public void jumpOffBound(int bottomBound, int topBound) {
		if (yPosition < 0 || yPosition > 500) {
			throw new RuntimeException("Shouldn't be jumping off bound");
		}
		if (yPosition <= topBound) {// get off top bound
			yPosition += 5;
		} else if (yPosition >= bottomBound) {// get off bottom bound
			yPosition -= 5;
		}
	}

	public Image getImage() {
		return birdImage;
	}

	public Boolean isObstacle() {
		return false;
	}

	public void drawSprite(Graphics g) {
		// System.out.println("drawing bird");
		g.drawImage(getImage(), xPosition, yPosition, null);

	}

	public boolean checkCollision() {
		Iterator<Sprite> temp = model.getIterator();
		Obstacle tempObs = null;
		while (temp.hasNext()) {
			Sprite next = temp.next();
			if (next.isObstacle()) {
				tempObs = (Obstacle) next;
				if (this.doesCollide(next)) {

					if (tempObs.allowCollision()) {
						System.out.println("Collision");
						tempObs.bypassCollision();
						return true;
					}
				} else if (tempObs != null) {
					if (getBounds().intersects(tempObs.getPassSpace()) && tempObs.allowScore()) {
						model.incrementScore();
						tempObs.bypassScore();
					}
				}
			}
		}
		return false; 
	}

	public void resetCollision() {
		allowCollision = true;
	}
	private void checkLowerBound() {
		if (model.bird.yPosition > bottomBound && model.gameOver == false) {
			if (collisionFrame == 0) {
				model.decreaseHealth();
				collisionFrame++;
			} else {
				collisionFrame++;
				if (collisionFrame >= 20) {
					model.decreaseHealth();
					collisionFrame = 0;
				}
			}
		}
	}

	private void checkUpperBound() {
		if (model.bird.yPosition <= upperBound && model.gameOver == false) {
			if (collisionFrame == 0) {
				model.decreaseHealth();
				// System.out.println("Caught");
				collisionFrame++;
			} else {
				collisionFrame++;
				if (collisionFrame >= 20) {
					model.decreaseHealth();
					// System.out.println("Decreased on interval of 10");
					collisionFrame = 0;
				}
			}
		}
	}
	public Sprite cloneSprite(){
		Bird b = new Bird(this);
		b.setModel(model);
		return b;
	}
	public void setModel(Model m){
		model = m;
	}
}
