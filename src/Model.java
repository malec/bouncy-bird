import javax.swing.*;

//import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

class Model {
	public Bird bird;
	public Random random;
	private int randomSeed = 9238;
	private boolean gameRunning;
	private int score = 0;
	// public LinkedList<Obstacle> obstacleCollection;
	public int frames;
	public boolean gameOver;
	public Hand hand;
	public LinkedList<Sprite> spriteList;

	Model() {
		bird = new Bird();
		random = new Random(randomSeed);
		random.setSeed(13);
		gameRunning = true;
		score = 0;
		gameOver = false;
		hand = new Hand(bird);
		spriteList = new LinkedList<Sprite>();
		spriteList.add(new Obstacle(true, 500, 200, random));// max
		spriteList.add(new Obstacle(false, 800, -70, random));
		spriteList.add(new Hand(bird));
		spriteList.add(bird);
	}

	public void update() {
		if (gameIsRunning()) {
			Iterator<Sprite> temp = spriteList.iterator();
			frames++;
			boolean removeFirst = false;
			if (frames % 25 == 0) {
				// print out a new obstacle every 25 frames.
				Obstacle newRandom = new Obstacle(random);
				spriteList.add(newRandom);
			} else {
				while (temp.hasNext()) {
					if (temp.next().update()) {
						removeFirst = true;
					}
				}
			}
			if (removeFirst) {
				spriteList.removeFirst();
			}
		}
		if (gameOver) {
			hand.animate(bird.yPosition);
		}
	}

	public void onClick() {
		bird.flap();
	}

	public void onRelease() {
		bird.release();
	}

	public boolean gameIsRunning() {
		return gameRunning;
	}

	public void gamePause() {
		this.gameRunning = false;
		this.bird.dblVerticalVelcoity = 0;
		final JOptionPane optionPane = new JOptionPane("Paused. Would you like to save?", JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION);
		Object options[] = { "Resume", "Save", "Load Game" };
		int dialogResult = JOptionPane.showOptionDialog(null, "Paused. Select an option", "Menu",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		if (dialogResult == 0) {
			this.gameResume();
		}
		if (dialogResult == 1) { // Save the game.
			JOptionPane.showMessageDialog(null, "To come in the future");
			this.gameResume();
		} else if (dialogResult == 2) {
			JOptionPane.showMessageDialog(null, "To come in the future");
			this.gameResume();
		} else if (dialogResult == 3) {
			scoreReset();
			this.gameResume();

		}
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

	public void incrementScore() {
		score++;
		System.out.println("SCORE++. Score: " + score);
	}

	public void scoreReset() {
		this.score = 0;
	}

	public void gameOver() {
		gameRunning = false;
		gameOver = true;
	}

	public Iterator<Sprite> getIterator() {
		return spriteList.iterator();
	}
}