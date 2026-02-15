package sdm.freedom.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InfoPanel extends JPanel {

    private final JLabel turnLabel;
    private final JLabel whiteScoreLabel;
    private final JLabel blackScoreLabel;

    public InfoPanel() {
        // metto gli elementi uno sotto l'altro
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // estetics del pannello laterale
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 0)); // largo 200 pixel, altezza automatica
        setBorder(new EmptyBorder(20, 20, 20, 20)); // margine interno 

        // etichette label
        Font fontTitolo = new Font("Arial", Font.BOLD, 18);
        Font fontTesto = new Font("Arial", Font.PLAIN, 16);

        turnLabel = new JLabel("Turno: BIANCO");
        turnLabel.setFont(fontTitolo);
        turnLabel.setForeground(Color.BLUE); 

        whiteScoreLabel = new JLabel("Bianchi: 0");
        whiteScoreLabel.setFont(fontTesto);

        blackScoreLabel = new JLabel("Neri: 0");
        blackScoreLabel.setFont(fontTesto);

        // Aggiungiamo tutto al pannello con un po' di spazio in mezzo
        add(turnLabel);
        add(new JLabel(" "));
        add(new JLabel(" ")); 
        add(whiteScoreLabel);
        add(new JLabel(" ")); 
        add(blackScoreLabel);
    }

    // per aggiornare i testi
    public void updateInfo(int currentPlayer, int whiteScore, int blackScore) {
        if (currentPlayer == 1) {
            turnLabel.setText("Turno: BIANCO");
            turnLabel.setForeground(Color.BLACK); // O un colore a scelta
        } else {
            turnLabel.setText("Turno: NERO");
            turnLabel.setForeground(Color.RED);
        }

        whiteScoreLabel.setText("Bianchi: " + whiteScore);
        blackScoreLabel.setText("Neri: " + blackScore);
    }
}