import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Sprite {
	public int xPosition;// Obstacle was 1100
	public int yPosition;

	abstract Image getImage();

	abstract Boolean isObstacle();

	abstract void drawSprite(Graphics g);

	abstract boolean update();

	public boolean doesCollide(Sprite that) {
		if (isObstacle()) {
			if (this.xPosition < that.xPosition + that.getImage().getWidth(null)
					&& this.xPosition + this.getImage().getWidth(null) > that.xPosition
					&& this.yPosition < that.yPosition + that.getImage().getHeight(null)
					&& this.yPosition + this.getImage().getHeight(null) > that.yPosition) {
				return true;
			} else
				return false;
		}
		throw new RuntimeException("Object is not an obstacle");
	}
}