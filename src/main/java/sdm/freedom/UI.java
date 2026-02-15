package sdm.freedom;


import javax.swing.SwingUtilities;

import sdm.freedom.gui.GameGUI;


public class UI {
   private static UI instance;


   private Match currentMatch; // logica del gioco
   private GameGUI gameWindow; // finestra di gioco
  
   private UI(){}


   public static UI getInstance(){
       if(instance == null){
           instance = new UI();
       }
       return instance;
   }


   // quanto grande disegnare la griglia
   public void startGUI(int n) {
       // inizializza la partita
       currentMatch = new Match(n);
       // assicura che la grafica venga creata nel Thread dedicato alla grafica
       SwingUtilities.invokeLater(() -> {
           gameWindow = new GameGUI(n);
           gameWindow.setVisible(true);
       });
   }

   public int getPieceAt(int row, int col) {
       if (currentMatch == null) return 0;
      
       return currentMatch.giveCurrentState().giveBoardPosition(row, col);
   }


   // quando l'utente clicca
   public void tryMove(int row, int col) {
        if (currentMatch == null) return;

        Move move = new Move(row, col);

        if (currentMatch.checkValidMove(move)) {

            currentMatch.applyAMove(move);

            // calcola i punteggi -> metodo Board.java
            int[] scores = currentMatch.evaluateBoard();
            int whiteScore = scores[0];
            int blackScore = scores[1];

            // di chi è il turno ora
            int currentPlayer = currentMatch.getCurrentPlayer();

            // aggiorna la GUI
            gameWindow.refresh(currentPlayer, whiteScore, blackScore);
            System.out.println("Mossa eseguita. Punteggio W:" + whiteScore + " B:" + blackScore);

        } else {
            System.out.println("Mossa non valida!");
        }
    }
}
