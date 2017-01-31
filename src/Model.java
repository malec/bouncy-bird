import javax.swing.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Random;

class Model {
    Bird bird;
    Random random;
    private boolean gameRunning;
    private int score=0;
    LinkedList<Obstacle> obstacleCollection;

    Model() {
        bird = new Bird();
        random = new Random();
        random.setSeed(13);
        gameRunning = true;
        score = 0;
    }

    public void update() {
        if (gameIsRunning()) {
            bird.update();
        }
    }

    public void setDestination(int x, int y) {
        //bird.dest_x = x;
        //bird.dest_y = y;
    }

    public void onClick() {
        if (gameIsRunning()) {
            bird.flap();
        }
    }

    public boolean gameIsRunning() {
        return gameRunning;
    }

    public void gamePause() {
        this.gameRunning = false;
        this.bird.dblVerticalVelcoity = 0;
        final JOptionPane optionPane = new JOptionPane(
                "Paused. Would you like to save?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        Object options[] = {"Resume", "Save", "Load Game"};
        int dialogResult = JOptionPane.showOptionDialog(null, "Paused. Select an option", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (dialogResult == 1) { //Save the game.
           JOptionPane.showMessageDialog(null,"To come in the future");
        } else if (dialogResult == 2) {
            JOptionPane.showMessageDialog(null,"To come in the future");
        }
        else if(dialogResult==3)
        {
            scoreReset();

        }
        this.gameResume();
    }

    public void gameResume() {
        this.gameRunning = true;

    }

    private String getMAC() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();

        }
        return null;
    }

    public void incrementScore()
    {
        score++;
        System.out.println("SCORE++. Score: "+score);
    }
    public void scoreReset()
    {
        this.score=0;
    }
}