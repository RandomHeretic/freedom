package sdm.freedom;

import java.util.ArrayList;

public class State {
    private final Board CurrentBoard;
    private Move LastMove;

    public State(Board Old_Board, Move Last_Move){
        LastMove=Last_Move;
        CurrentBoard=Old_Board;
    }
    public State(Board NewGameboard){
        CurrentBoard=NewGameboard;
    }

    public void applyMove(Move NewMove,int player){
        CurrentBoard.applyMove(NewMove,player);
        LastMove=NewMove;
    }


    public Board giveBoard(){
        return CurrentBoard;
    }

    public int giveBoardPosition(Move m){
        return CurrentBoard.givePosition(m);
    }
    public int giveBoardPosition(int x,int y){
        return CurrentBoard.givePosition(new Move(x,y));
    }

    public Move giveLastMove(){
        return LastMove;
    }

    public void printState(){
        if(LastMove!=null) {
            LastMove.printAsLastMove();
        }
        CurrentBoard.printBoard();
    }

    public Move[] getLegalSuccessors(){
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
