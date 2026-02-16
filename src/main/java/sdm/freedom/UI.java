package sdm.freedom;

import javax.swing.SwingUtilities;

import sdm.freedom.gui.GameGUI;

public class UI {

    private static UI instance;

    private Match currentMatch; // logica del gioco
    private GameGUI gameWindow; // finestra di gioco
    private boolean gameOver = false; // flag per bloccare i click a fine partita

    private UI() {
    }

    public static UI getInstance() {
        if (instance == null) {
            instance = new UI();
        }
        return instance;
    }

    public void startGUI(int n) {
        // inizializza la partita
        currentMatch = new Match(n);
        gameOver = false;
        // assicura che la grafica venga creata nel Thread dedicato alla grafica
        SwingUtilities.invokeLater(() -> {
            gameWindow = new GameGUI(n);
            gameWindow.setVisible(true);
        });
    }

    public int getPieceAt(int row, int col) {
        if (currentMatch == null) {
            return 0;
        }
        return currentMatch.giveCurrentState().giveBoardPosition(row, col);
    }

    // restituisce le mosse legali per evidenziarle nella GUI
    public Move[] getLegalMoves() {
        if (currentMatch == null) {
            return new Move[0];
        }
        return currentMatch.giveCurrentState().getLegalSuccessors();
    }

    // restituisce il giocatore corrente (1=bianco, 2=nero)
    public int getCurrentPlayer() {
        if (currentMatch == null) {
            return 1;
        }
        return currentMatch.getCurrentPlayer();
    }

    // la partita è finita?
    public boolean isGameOver() {
        return gameOver;
    }

    // ultima mossa fatta
    public Move getLastMove() {
        if (currentMatch == null) {
            return null;
        }
        return currentMatch.giveCurrentState().giveLastMove();
    }

    // controlla se tra le mosse legali c'è una skip move
    public boolean canSkip() {
        if (currentMatch == null || gameOver) {
            return false;
        }
        for (Move m : getLegalMoves()) {
            if (m.skipMove()) {
                return true;
            }
        }
        return false;
    }

    // quando l'utente clicca una cella
    public void tryMove(int row, int col) {
        if (currentMatch == null || gameOver) {
            return;
        }

        Move move = new Move(row, col);

        if (currentMatch.checkValidMove(move)) {
            currentMatch.applyAMove(move);
            refreshAndCheckEnd();
        } else {
            System.out.println("Mossa non valida!");
        }
    }

    // quando l'utente preme salta mossa
    public void skipMove() {
        if (!canSkip()) {
            return;
        }

        // applica la skip -> Match cambia solo il turno
        currentMatch.applyAMove(new Move(true));

        // dopo skip -> partita finisce
        gameOver = true;
        int[] scores = currentMatch.evaluateBoard();
        gameWindow.refresh(currentMatch.getCurrentPlayer(), scores[0], scores[1]);

        String risultato;
        if (scores[0] > scores[1]) {
            risultato = "Vince il BIANCO!";
        } else if (scores[1] > scores[0]) {
            risultato = "Vince il NERO!";
        } else {
            risultato = "PAREGGIO!";
        }
        gameWindow.showGameOver(risultato, scores[0], scores[1]);
    }

    // aggiorna la GUI e controlla fine partita
    private void refreshAndCheckEnd() {
        int[] scores = currentMatch.evaluateBoard();
        int whiteScore = scores[0];
        int blackScore = scores[1];
        int currentPlayer = currentMatch.getCurrentPlayer();

        gameWindow.refresh(currentPlayer, whiteScore, blackScore);

        if (currentMatch.giveCurrentState().isTerminal()) {
            gameOver = true;
            String risultato;
            if (whiteScore > blackScore) {
                risultato = "Vince il BIANCO!";
            } else if (blackScore > whiteScore) {
                risultato = "Vince il NERO!";
            } else {
                risultato = "PAREGGIO!";
            }
            gameWindow.showGameOver(risultato, whiteScore, blackScore);
        }
    }
}
