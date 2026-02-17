package sdm.freedom;


public class Match {
    private final State CurrentState;
    private int CurrentPlayer;

    public Match(int n){
        Board b = new Board(n);
        CurrentState = new State(b);
        CurrentPlayer = 1;
    }

    public State getCurrentState(){
        return CurrentState;
    }

    public int getCurrentPlayer(){
        return CurrentPlayer;
    }

    public int getCurrentPlayerIdx(){
        return getCurrentPlayer()-1;
    }

    public void checkAndApplyMove(Move NewMove){

        CurrentState.applyMove(NewMove, CurrentPlayer);
        CurrentPlayer = 3-CurrentPlayer; //swap between 1 and 2
    }

    public void applyMove(Move NewMove){
        CurrentState.applyMove(NewMove, CurrentPlayer);
        CurrentPlayer = 3-CurrentPlayer; //swap between 1 and 2
    }


    public int getPosition(Move m){
        return CurrentState.getBoardPosition(m);
    }

    public int[] evaluateBoard(){
        return CurrentState.getBoard().evaluateBoard();
    }
}
