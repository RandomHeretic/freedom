package sdm.freedom.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sdm.freedom.UI;

public class InfoPanel extends JPanel {

    private final JLabel turnLabel;
    private final JLabel whiteScoreLabel;
    private final JLabel blackScoreLabel;
    private final JLabel resultLabel;
    private final JButton skipButton; // bottone "Salta Mossa"

    public InfoPanel() {
        // metto gli elementi uno sotto l'altro
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // estetics del pannello laterale
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 0));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // etichette label
        Font fontTitolo = new Font("Arial", Font.BOLD, 18);
        Font fontTesto = new Font("Arial", Font.PLAIN, 16);
        Font fontRisultato = new Font("Arial", Font.BOLD, 20);

        turnLabel = new JLabel("Turno: BIANCO");
        turnLabel.setFont(fontTitolo);
        turnLabel.setForeground(Color.BLUE); 

        whiteScoreLabel = new JLabel("Bianchi: 0");
        whiteScoreLabel.setFont(fontTesto);

        blackScoreLabel = new JLabel("Neri: 0");
        blackScoreLabel.setFont(fontTesto);

        // label risultato, nascosta finché la partita non finisce
        resultLabel = new JLabel("");
        resultLabel.setFont(fontRisultato);
        resultLabel.setForeground(new Color(180, 0, 0));
        resultLabel.setVisible(false);

        // bottone Skip -> appare solo quando è l'ultima cella
        skipButton = new JButton("Skip Move");
        skipButton.setFont(new Font("Arial", Font.BOLD, 16));
        skipButton.setBackground(new Color(200, 80, 80));
        skipButton.setForeground(Color.WHITE);
        skipButton.setOpaque(true);   // forza il rendering del colore di sfondo
        skipButton.setBorderPainted(false); // rimuove il bordo nativo (macOS lo ignora altrimenti) CONTINUA A DARE PROBLEMI SU MAC :( 
        skipButton.setFocusPainted(false);
        skipButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        skipButton.setPreferredSize(new Dimension(160, 45));  // dimensione fissa
        skipButton.setMaximumSize(new Dimension(160, 45));
        skipButton.setVisible(false); // nascosto finché non serve

        // quando premuto, dice al Singleton di eseguire lo skip
        skipButton.addActionListener(e -> {
            UI.getInstance().skipMove();
        });

        // layout
        add(turnLabel);
        add(new JLabel(" "));
        add(new JLabel(" ")); 
        add(whiteScoreLabel);
        add(new JLabel(" ")); 
        add(blackScoreLabel);
        add(new JLabel(" "));
        add(resultLabel);
        add(Box.createVerticalGlue()); // spinge il bottone in fondo al pannello
        add(skipButton);
        add(Box.createRigidArea(new Dimension(0, 20))); // margine sotto il bottone
    }

    // per aggiornare i testi
    public void updateInfo(int currentPlayer, int whiteScore, int blackScore) {
        if (currentPlayer == 1) {
            turnLabel.setText("Turno: BIANCO");
            turnLabel.setForeground(Color.BLACK);
        } else {
            turnLabel.setText("Turno: NERO");
            turnLabel.setForeground(Color.RED);
        }

        whiteScoreLabel.setText("Bianchi: " + whiteScore);
        blackScoreLabel.setText("Neri: " + blackScore);
    }

    // mostra o nasconde il bottone skip
    public void setSkipVisible(boolean visible) {
        skipButton.setVisible(visible);
    }

    public void showGameOver(String risultato, int whiteScore, int blackScore) {
        turnLabel.setText("FINE PARTITA");
        turnLabel.setForeground(new Color(180, 0, 0));
        whiteScoreLabel.setText("Bianchi: " + whiteScore);
        blackScoreLabel.setText("Neri: " + blackScore);
        skipButton.setVisible(false); // nascondi il bottone a fine partita
        resultLabel.setText(risultato);
        resultLabel.setVisible(true);
    }
}