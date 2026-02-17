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
        if(!checkValidMove(NewMove)) return;
        CurrentState.applyMove(NewMove, CurrentPlayer);
        CurrentPlayer = 3-CurrentPlayer; //swap between 1 and 2
    }

    public void applyMove(Move NewMove){
        CurrentState.applyMove(NewMove, CurrentPlayer);
        CurrentPlayer = 3-CurrentPlayer; //swap between 1 and 2
    }

    public boolean checkValidMove(Move NewMove){
        if (NewMove.skipMove()) {
            return true;
        }
        if (CurrentState.getBoard().isOutOfBounds(NewMove) || CurrentState.giveBoardPosition(NewMove) !=0){
            return false;
        }
        if (CurrentState.getLastMove() == null){
            return true;
        }
        int[][] neighbours = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        boolean flagFreedom=false; //checks for empty spaces next to the last move
        boolean flagNext=false;

        for(int[] nei: neighbours){

            int newx = CurrentState.getLastMove().returnMove()[0]+nei[0];
            int newy = CurrentState.getLastMove().returnMove()[1]+nei[1];
            Move LastMoveNeighbour = new Move(newx,newy);
            if(CurrentState.getBoard().isOutOfBounds(new Move(newx,newy))){
                continue;
            }
            if(CurrentState.giveBoardPosition(LastMoveNeighbour)==0) {
                flagFreedom = true;
            }
            if(NewMove.equals(LastMoveNeighbour)){
                flagNext = true;
            }
        }
        return !flagFreedom || flagNext;
    }

    public int givePosition(Move m){
        return CurrentState.giveBoardPosition(m);
    }

    public int[] evaluateBoard(){
        return CurrentState.getBoard().evaluateBoard();
    }
}
