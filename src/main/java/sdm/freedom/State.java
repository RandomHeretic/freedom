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
        int boardSize = CurrentBoard.getBoardSize();

        ArrayList<Move> successorList = new ArrayList<>();

        //first turn case
        if(LastMove == null){
            for (int x = 0; x < boardSize; x++)
                for (int y = 0; y < boardSize; y++)
                    successorList.add(new Move(x, y));
            return successorList.toArray(new Move[0]);
        }

        int lastX = LastMove.x();
        int lastY = LastMove.y();

        //check adjacent spaces
        for(int x = Math.max(0, lastX-1); x <= Math.min(boardSize-1, lastX+1); x++){
            for(int y = Math.max(0, lastY-1); y <= Math.min(boardSize-1, lastY+1); y++){
                if(CurrentBoard.givePosition(x, y) == 0)
                    successorList.add(new Move(x, y));
            }
        }

        //if no adjacent moves are valid, all others are
        if(successorList.isEmpty()) for (int x = 0; x < boardSize; x++)
            for (int y = 0; y < boardSize; y++)
                if (CurrentBoard.givePosition(x, y) == 0)
                    successorList.add(new Move(x, y));

        return successorList.toArray(new Move[0]);
    }
}
