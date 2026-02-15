package sdm.freedom.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import sdm.freedom.UI;


// JPanel è una superficie vuota su cui disegnare
public class BoardPanel extends JPanel {
  
   private final int boardSize; // dimensione della griglia
   private final int CELL_SIZE = 80; // quanto è grande ogni quadrato in pixel
   private final int MARGIN = 100; // spazio ai bordi

   private final int PIECE_PADDING = 4; // dimensione pedina


   public BoardPanel(int n) {
       this.boardSize = n;
      
       // calcoliamo quanto deve essere grande il pannello in pixel
       int pixelWidth = (n * CELL_SIZE) + (MARGIN * 2);
       int pixelHeight = (n * CELL_SIZE) + (MARGIN * 2);
      
       // impostiamo la dimensione preferita (x pack())
       setPreferredSize(new Dimension(pixelWidth, pixelHeight));
      
       // impostiamo un colore di sfondo (marroncino tipo legno -> 220, 180, 130)
       setBackground(new Color(220, 180, 130));
  
       // mouse listener -> per prendere gli input
       addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               // coordinate del click in pixel
               int x = e.getX();
               int y = e.getY();


               // togliamo il margine per lavorare solo sulla griglia
               int gridX = x - MARGIN;
               int gridY = y - MARGIN;


               // controlliamo se siamo fuori dalla griglia
               if (gridX < 0 || gridY < 0) return;


               // convertiamo i pixel in indici di matrice
               int col = gridX / CELL_SIZE;
               int row = gridY / CELL_SIZE;


               // controlliamo se siamo usciti dalla griglia
               if (col >= boardSize || row >= boardSize) return;


               // AZIIONE
               System.out.println("Hai cliccato la cella: Riga " + (row+1) + ", Colonna " + (col+1));

               // proviamo a muovere
               UI.getInstance().tryMove(row, col);
              
               // repaint(); // Ridisegna la scacchiera con la nuova pedina
           }
       });
   }


   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g); // pulisce il disegno precedente


       // impostiamo il colore per le linee
       g.setColor(Color.BLACK);


       // GRIGLIA (includo la n+1 esima riga/colonna)
       for (int i = 0; i <= boardSize; i++) {
           // disegna linee orizzontali
           g.drawLine(
               MARGIN,                     // x inizio
               MARGIN + (i * CELL_SIZE),   // y inizio
               MARGIN + ((boardSize) * CELL_SIZE), // x fine
               MARGIN + (i * CELL_SIZE)    // y fine
           );


           // disegna linee verticali
           g.drawLine(
               MARGIN + (i * CELL_SIZE),   // x inizio
               MARGIN,                     // y inizio
               MARGIN + (i * CELL_SIZE),   // x fine
               MARGIN + ((boardSize) * CELL_SIZE) // y fine
           );
       }

       // PEDINE 
       // scansione x dove sono i pezzi
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                
                // x cosa c'è in questa cella
                int value = UI.getInstance().getPieceAt(r, c);
                
                if (value != 0) {
                    // calcoliamo dove disegnare il cerchio in pixel
                    int pixelX = MARGIN + (c * CELL_SIZE) + PIECE_PADDING;
                    int pixelY = MARGIN + (r * CELL_SIZE) + PIECE_PADDING;
                    int diameter = CELL_SIZE - (PIECE_PADDING * 2);

                    // impostiamo il colore
                    if (value == 1) {
                        g.setColor(Color.WHITE);
                    } else if (value == 2) {
                        g.setColor(Color.BLACK);
                    }

                    // disegniamo il cerchio pieno
                    g.fillOval(pixelX, pixelY, diameter, diameter);
                    
                    // pedina è bianca => contorno nero
                    if (value == 1) {
                        g.setColor(Color.BLACK);
                        g.drawOval(pixelX, pixelY, diameter, diameter);
                    }
                }
            }
        }
   }
}
