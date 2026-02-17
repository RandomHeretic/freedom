package sdm.freedom;

import javax.swing.SwingUtilities;

import sdm.freedom.gui.GameGUI;
import sdm.freedom.gui.MenuGUI;

public class UIController {

    private static UIController instance;

    private GameGUI gameWindow;
    private MoveInputListener moveListener;

    private UIController() {
    }

    public static UIController getInstance() {
        if (instance == null) {
            instance = new UIController();
        }
        return instance;
    }

    public void start(int n) {
        SwingUtilities.invokeLater(() -> {
            gameWindow = new GameGUI(n);
            gameWindow.setVisible(true);
        });
    }

    public void userClickedForMove(Move inputMove) {
        if (moveListener != null) {
            moveListener.onMoveSelected(inputMove);
        }
    }

    public void setMoveInputListener(MoveInputListener listener) {
        this.moveListener = listener;
    }

    public void refresh(int currentPlayer, int whiteScore, int blackScore) {
        gameWindow.refresh(currentPlayer, whiteScore, blackScore);
    }

    public void showGameOver(String result, int white, int black) {
        gameWindow.showGameOver(result, white, black);
    }

    public void repaintBoard() {
        gameWindow.repaint();
    }

    public void backToMenu() {
        // resetta il GameController
        GameController.getInstance().reset();

        // chiudi la finestra di gioco
        if (gameWindow != null) {
            gameWindow.dispose();
            gameWindow = null;
        }
        moveListener = null;

        // riapri il menu
        SwingUtilities.invokeLater(() -> {
            MenuGUI menu = new MenuGUI();
            menu.setVisible(true);
        });
    }
}
