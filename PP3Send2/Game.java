import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame {
    View view;
    Model model;

    public Game() {
    	model = new Model();
        Controller controller = new Controller(model, this);
        view = new View(controller, model);
        this.setTitle("Bouncy Bird V 2.0 - Alec Ahlbrandt");
        this.setSize(500, 500);
        this.getContentPane().add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);//error here
        this.setResizable(false);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void run() {
        while (true) {
            model.update();
            view.repaint(); // Indirectly calls View.paintComponent
            // Go to sleep for 50 miliseconds
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
    public void reset()
    {
        model.scoreReset();
        view.resetDifficulty();
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
        g.model.gameResume();
    }
}
