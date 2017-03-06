import java.awt.Graphics;
import java.awt.Image;

public abstract class Sprite {
	public int xPosition;
	public int yPosition;

	abstract Image getImage();

	abstract Boolean isObstacle();

	abstract void drawSprite(Graphics g);

	abstract boolean update();

	abstract Sprite cloneSprite();

	public boolean doesCollide(Sprite that) {
		if (that.isObstacle()) {
			if (this.xPosition < that.xPosition + that.getImage().getWidth(null)
					&& this.xPosition + this.getImage().getWidth(null) > that.xPosition
					&& this.yPosition < that.yPosition + that.getImage().getHeight(null)
					&& this.yPosition + this.getImage().getHeight(null) > that.yPosition) {
				return true;
			}
		}
		return false;
	}
}