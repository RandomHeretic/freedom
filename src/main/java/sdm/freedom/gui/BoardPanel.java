package sdm.freedom.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import sdm.freedom.GameController;
import sdm.freedom.Move;
import sdm.freedom.UIController;

public class BoardPanel extends JPanel {

    private final int boardSize;
    private final int CELL_SIZE = 80;
    private final int MARGIN = 60;
    private final int PIECE_PADDING = 6;

    private static final Color LAST_MOVE_COLOR = new Color(255, 235, 59);

    // colori cella alternati (scacchiera)
    private static final Color CELL_LIGHT = new Color(240, 217, 181);  // beige chiaro
    private static final Color CELL_DARK = new Color(181, 136, 99);   // marrone

    // colore sfondo esterno 
    private static final Color BG_COLOR = new Color(49, 46, 43);       // grigio scuro

    // colore etichette
    private static final Color LABEL_COLOR = new Color(200, 200, 200); // grigio chiaro

    // colore bordo griglia
    private static final Color GRID_BORDER_COLOR = new Color(30, 30, 30);

    public BoardPanel(int n) {
        this.boardSize = n;

        // calcoliamo quanto deve essere grande il pannello in pixel
        int pixelWidth = (n * CELL_SIZE) + (MARGIN * 2);
        int pixelHeight = (n * CELL_SIZE) + (MARGIN * 2);

        // impostiamo la dimensione preferita (x pack())
        setPreferredSize(new Dimension(pixelWidth, pixelHeight));
        setBackground(BG_COLOR);

        // FIX CLICK (non funziona benissimo -> serve x migliorare lo sniff del mouseListener)
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();

                int gridX = e.getX() - MARGIN;
                int gridY = e.getY() - MARGIN;
                if (gridX < 0 || gridY < 0) {
                    return;
                }

                // convertiamo i pixel in indici di matrice
                int col = gridX / CELL_SIZE;
                int row = gridY / CELL_SIZE;

                // controlliamo se siamo usciti dalla griglia
                if (col >= boardSize || row >= boardSize) {
                    return;
                }

                // AZIONE
                // System.out.println("Hai cliccato la cella: Riga " + (row + 1) + ", Colonna " + (col + 1));

                Move inputMove = new Move(row, col);
                UIController.getInstance().userClickedForMove(inputMove);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // x fare grafica in 2D (con ombre)
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // celle colore diverso
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if ((r + c) % 2 == 0) {
                    g2.setColor(CELL_LIGHT);
                } else {
                    g2.setColor(CELL_DARK);
                }
                g2.fillRect(
                        MARGIN + (c * CELL_SIZE),
                        MARGIN + (r * CELL_SIZE),
                        CELL_SIZE, CELL_SIZE
                );
            }
        }

        // ultima mossa
        Move lastMove = GameController.getInstance().getLastMove();
        if (lastMove != null && !lastMove.skipMove()) {
            // controlliamo coordinate valide
            if (lastMove.x() >= 0 && lastMove.y() >= 0) {
                g2.setColor(LAST_MOVE_COLOR);
                g2.fillRect(
                        MARGIN + (lastMove.y() * CELL_SIZE),
                        MARGIN + (lastMove.x() * CELL_SIZE),
                        CELL_SIZE, CELL_SIZE
                );
            }
        }

        // mosse legali
        if (!GameController.getInstance().isGameOver()) {
            Move[] legalMoves = GameController.getInstance().getLegalMoves();

            // dimensione pallino
            int hintDiameter = CELL_SIZE / 4;
            // l'offset x centrarlo
            int offset = (CELL_SIZE - hintDiameter) / 2;

            int currentPlayer = GameController.getInstance().getPlayerTurn();

            if (currentPlayer == 1) {
                // pallino BIANCO
                g2.setColor(Color.LIGHT_GRAY);
            } else {
                // pallino NERO
                g2.setColor(Color.BLACK);
            }

            for (Move m : legalMoves) {
                if (m.x() >= 0 && m.y() >= 0) {
                    int drawX = MARGIN + (m.y() * CELL_SIZE) + offset;
                    int drawY = MARGIN + (m.x() * CELL_SIZE) + offset;
                    g2.fillOval(drawX, drawY, hintDiameter, hintDiameter);
                }
            }
        }

        // bordo griglia
        g2.setColor(GRID_BORDER_COLOR);
        g2.drawRect(
                MARGIN, MARGIN,
                boardSize * CELL_SIZE, boardSize * CELL_SIZE
        );

        // etichette
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        g2.setFont(labelFont);
        g2.setColor(LABEL_COLOR);
        FontMetrics fm = g2.getFontMetrics();

        for (int i = 0; i < boardSize; i++) {
            // lettere colonne
            String colLabel = String.valueOf((char) ('A' + i));
            int labelW = fm.stringWidth(colLabel);

            // sopra
            int colX = MARGIN + (i * CELL_SIZE) + (CELL_SIZE / 2) - (labelW / 2);
            int colYTop = MARGIN - 12;
            g2.drawString(colLabel, colX, colYTop);

            // numeri righe
            String rowLabel = String.valueOf(i + 1);
            int rowLabelW = fm.stringWidth(rowLabel);
            int rowY = MARGIN + (i * CELL_SIZE) + (CELL_SIZE / 2) + (fm.getAscent() / 2) - 2;

            // sinistra
            int rowXLeft = MARGIN - rowLabelW - 12;
            g2.drawString(rowLabel, rowXLeft, rowY);
        }


        int[][] board = GameController.getInstance().getBoard();

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {

                int value = board[r][c];

                if (value != 0) {
                    // calcoliamo dove disegnare il cerchio in pixel
                    int pixelX = MARGIN + (c * CELL_SIZE) + PIECE_PADDING;
                    int pixelY = MARGIN + (r * CELL_SIZE) + PIECE_PADDING;
                    int diameter = CELL_SIZE - (PIECE_PADDING * 2);

                    // ombra sotto la pedina 
                    g2.setColor(new Color(0, 0, 0, 60));
                    g2.fillOval(pixelX + 3, pixelY + 3, diameter, diameter);

                    if (value == 1) {
                        g2.setColor(new Color(245, 245, 245)); // bianco leggero
                    } else {
                        g2.setColor(new Color(30, 30, 30)); // nero intenso
                    }
                    g2.fillOval(pixelX, pixelY, diameter, diameter);

                    // contorno per entrambe le pedine
                    g2.setColor(new Color(60, 60, 60));
                    g2.drawOval(pixelX, pixelY, diameter, diameter);
                }
            }
        }
    }
}
