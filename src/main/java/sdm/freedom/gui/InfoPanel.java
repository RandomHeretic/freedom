package sdm.freedom.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sdm.freedom.Move;
import sdm.freedom.UIController;

public class InfoPanel extends JPanel {

    private final JLabel turnLabel;
    private final JLabel whiteScoreLabel;
    private final JLabel blackScoreLabel;
    private final JLabel resultLabel;
    private final JButton skipButton;
    private final JButton menuButton;

    public InfoPanel() {
        // metto gli elementi uno sotto l'altro
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // estetics del pannello laterale
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(250, 0));
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
        // fix bottone Mac-> creo bottone custom che si dipinge da solo per evitare il look nativo bianco del Mac
        skipButton = new JButton("Skip Move") {
            @Override
            protected void paintComponent(Graphics g) {
                // colore scuso se pushed
                if (getModel().isPressed()) {
                    g.setColor(getBackground().darker());
                } else {
                    g.setColor(getBackground());
                }

                g.fillRect(0, 0, getWidth(), getHeight());

                super.paintComponent(g);
            }
        };

        skipButton.setFont(new Font("Arial", Font.BOLD, 16));
        skipButton.setBackground(new Color(200, 80, 80));
        skipButton.setForeground(Color.WHITE);

        // disattiva stile nativo Mac
        skipButton.setContentAreaFilled(false);
        skipButton.setFocusPainted(false);
        skipButton.setBorderPainted(false);
        skipButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        skipButton.setPreferredSize(new Dimension(160, 45));  // dimensione fissa
        skipButton.setMaximumSize(new Dimension(160, 45));
        skipButton.setVisible(false); // nascosto finché non serve

        // push -> Singleton esegue skip
        skipButton.addActionListener(e -> UIController.getInstance().userClickedForMove(new Move(true)));

        // bottone Menu -> torna al menu principale
        menuButton = new JButton("Menu") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(getBackground().darker());
                } else {
                    g.setColor(getBackground());
                }
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        menuButton.setFont(new Font("Arial", Font.BOLD, 16));
        menuButton.setBackground(new Color(80, 80, 200));
        menuButton.setForeground(Color.WHITE);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.setPreferredSize(new Dimension(160, 45));
        menuButton.setMaximumSize(new Dimension(160, 45));

        menuButton.addActionListener(e -> UIController.getInstance().backToMenu());

        add(turnLabel);
        add(new JLabel(" "));
        add(new JLabel(" "));
        add(whiteScoreLabel);
        add(new JLabel(" "));
        add(blackScoreLabel);
        add(new JLabel(" "));
        add(new JLabel(" "));
        add(resultLabel);
        add(Box.createVerticalGlue()); // bottone in fondo pannello
        add(skipButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(menuButton);
        add(Box.createRigidArea(new Dimension(0, 20))); // margine sotto bottone
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
        skipButton.setVisible(false);
        resultLabel.setText(risultato);
        resultLabel.setVisible(true);
    }
}
