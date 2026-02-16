package sdm.freedom.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import sdm.freedom.UI;

public class GameGUI extends JFrame {
    
    private final BoardPanel boardPanel;
    private final InfoPanel infoPanel; 

    public GameGUI(int n) {
        super("Freedom Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // layout manager
        setLayout(new BorderLayout());

        // creo due pannelli
        boardPanel = new BoardPanel(n);
        infoPanel = new InfoPanel(); 

        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        pack(); 
        setLocationRelativeTo(null);
    }

    public void refresh(int currentPlayer, int whiteScore, int blackScore) {
        // ridisegna la scacchiera
        boardPanel.repaint();
        // aggiorna i testi laterali
        infoPanel.updateInfo(currentPlayer, whiteScore, blackScore);
        // mostra/nascondi il bottone skip in base alla logica
        infoPanel.setSkipVisible(UI.getInstance().canSkip());
    }

    public void showGameOver(String risultato, int whiteScore, int blackScore) {
        boardPanel.repaint();
        infoPanel.showGameOver(risultato, whiteScore, blackScore);
    }
}