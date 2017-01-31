import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

/**
 * Created by alec on 01/23/17.
 */
public class Bird {
    private static int wingflyDuration = 3;
    private static double verticalVelocityIncrement=-15;
    public int bird_y;
    public int bird_x=20;
    public double dblVerticalVelcoity;
    private int flaps;
    private int frameCouter;
    public Image birdImage;
    static public Image defaultbirdImage;


    Bird() {
        frameCouter=0;
        try {
            defaultbirdImage = ImageIO.read(new File("bird.png"));
            birdImage=defaultbirdImage;
        }
        catch (Exception error)
        {
            error.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public void update() {
        // Move the bird
        if(bird_y<400) {
            dblVerticalVelcoity += wingflyDuration;
            this.bird_y += dblVerticalVelcoity;
            frameCouter++;
            if (frameCouter >= wingflyDuration) {
                frameCouter = 0;
                this.birdImage = defaultbirdImage;
            }
        }
        else
        {
            //Game Fail.
            dblVerticalVelcoity=0;
        }
    }

    public void setDestination(int x, int y) {
        //this.dest_x = x;
        //this.dest_y = y;
    }
    public  void flap()
    {
        dblVerticalVelcoity=0;

        dblVerticalVelcoity+=verticalVelocityIncrement;
        this.bird_y+=dblVerticalVelcoity;
        try {
            birdImage = ImageIO.read(new File("bird2.png"));
        }
        catch (Exception error)
        {
            error.printStackTrace();
            System.exit(1);
        }
    }
    public Rectangle getBounds() {
        return new Rectangle(bird_x,bird_y,birdImage.getWidth(null),birdImage.getHeight(null));
    }
}
