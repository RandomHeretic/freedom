package sdm.freedom.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants; // x dividere la finestra

public class GameGUI extends JFrame {
    
    // riferimenti ai due pannelli
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

        add(boardPanel, BorderLayout.CENTER); // scacchiera - centro
        // aggiungo i pannelli alle zone specifiche secondo il layout manager
        add(infoPanel, BorderLayout.EAST);    // info - destra

        pack(); 
        setLocationRelativeTo(null);
    }

    public void refresh(int currentPlayer, int whiteScore, int blackScore) {
        // ridisegna la scacchiera
        boardPanel.repaint();
        // aggiorna i testi laterali
        infoPanel.updateInfo(currentPlayer, whiteScore, blackScore);
    }
}