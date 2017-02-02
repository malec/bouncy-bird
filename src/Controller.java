import java.awt.event.*;

class Controller implements KeyListener, ActionListener, MouseListener {
    View view;
    Model model;
    Game game;

    Controller(Model m, Game g) {
        model = m;
        game = g;
    }

    public void actionPerformed(ActionEvent e) {
        if (model.gameIsRunning()) {
            model.gamePause();
        } else {
            model.gameResume();
        }
    }

    public void mousePressed(MouseEvent e) {
        model.setDestination(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        model.onClick();
    }

    void setView(View v) {
        view = v;
        view.addMouseListener(this);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            model.onClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            model.gamePause();
        }
        if(e.getKeyCode()==KeyEvent.VK_H)
        {
        	view.gameOver();
        	System.out.println("Game Over...");
        }
    }
}
