import javax.management.RuntimeErrorException;
import javax.swing.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

class Model {
	public Bird bird;
	public Random random;
	private final int randomSeed = 9238;
	private static boolean gameRunning;
	public int score = 0;
	// public LinkedList<Obstacle> obstacleCollection;
	private int frames;
	public boolean gameOver;
	public Hand hand;
	public LinkedList<Sprite> spriteList;
	public final static int healthDecrement = 20;
	private double healthTick = 0;
	private int spawnFrequency = 20;
	//private final int difficultyIncreaseFrequency = 200;
	private static final int d = 15;
	private static final int k = 6;

	Model() {
		bird = new Bird(this);
		random = new Random(randomSeed);
		random.setSeed(13);
		gameRunning = true;
		score = 0;
		gameOver = false;
		hand = new Hand(bird);
		spriteList = new LinkedList<Sprite>();
		Obstacle obs1 = new Obstacle(true, 500, 200, random);// max
		// Obstacle obs2 = new Obstacle(false, 800, -70, random); // Test
		spriteList.add(new Obstacle(obs1));
		// spriteList.add(new Obstacle(obs2));
		spriteList.add(bird);
		spriteList.add(hand);
		bird.health = 100;
	}

	Model(Model m) {
		hand = (Hand) m.hand.cloneSprite();
		random = m.random;
		score = m.score;
		frames = m.frames;
		gameOver = m.gameOver;
		spriteList = new LinkedList<Sprite>();
		Iterator<Sprite> it = m.spriteList.iterator();
		while (it.hasNext()) {
			Sprite next = it.next();
			if (next.isBird()) {
				bird = (Bird) next.cloneSprite();
				spriteList.add(bird);
			} else {
				spriteList.add(next.cloneSprite());
			}
		}
		if (bird == null) {
			// you could throw new RuntimeException("Bird is null, and probably
			// isn't in the list");
			bird = (Bird) m.bird.cloneSprite();
		}
		bird.setModel(this);
	}

	public void update() {
		if (gameIsRunning()) {
			frames++;
			increaseProgressBar();
			Iterator<Sprite> temp = spriteList.iterator();
			Sprite tempSprite;
			if (frames % spawnFrequency == 0) {
				// print out a new obstacle every 25 frames.
				Obstacle newRandom = new Obstacle(random);
				spriteList.add(new Obstacle(newRandom));
			} else {
				while (temp.hasNext()) {
					tempSprite = temp.next();
					if (tempSprite.update()) {
						temp.remove();
					}
				}
			}
			if (bird.checkCollision()) {
				scoreReset();
				decreaseHealth(healthDecrement);
			}
		}
		if (gameOver) {
			hand.animate(bird.yPosition);
		}
	}

	public void decreaseHealth(int i) {
		bird.health -= i;

	}

	public void onClick() {
		bird.flap();
	}

	public void onRelease() {
		bird.release();
	}

	public static boolean gameIsRunning() {
		return gameRunning;
	}

	public void gamePause() {
		gameRunning = false;
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
		bird.resetCollision();
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

	public int getHealth() {
		return bird.health;
	}

	private void increaseProgressBar() {
		if (bird.health < 100) {
			healthTick += .25;
			if (healthTick % 2 == 0) {
				bird.health += 3;
			}
			if (healthTick > 5) {
				healthTick = 0;
			}
		}
	}

	public double evaluateAction(Bird.actions type, int depth) {
		if (bird.health <= 0) {
			return 0;
		}
		if (depth >= d) {
			// return 500 - Math.abs(bird.yPosition - 250);
			return bird.health;
		}
		Model copy = new Model(this);
		copy.doAction(type);
		copy.update();

		// Recursively Evaluate
		if (depth % k != 0) {
			return copy.evaluateAction(Bird.actions.do_nothing, depth + 1);
		} else {
			double best = copy.evaluateAction(Bird.actions.do_nothing, depth + 1);
			best = Math.max(best, copy.evaluateAction(Bird.actions.flap, depth + 1));
			best = Math.max(best, copy.evaluateAction(Bird.actions.call_chuck, depth + 1));
			return best;
		}
	}

	public void doAction(Bird.actions type) {
		if (type == Bird.actions.call_chuck) {
			spriteList.add(new ChuckNorris(this));
			// System.out.println("Simulated Spawn chuck");
		} else if (type == Bird.actions.flap) {
			// System.out.println("Simulated Flap");
			bird.flap();
		}
	}
}