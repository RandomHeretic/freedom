package sdm.freedom;

import javax.swing.SwingUtilities;
import sdm.freedom.gui.GameGUI;

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
}
