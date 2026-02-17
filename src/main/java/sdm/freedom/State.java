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
        LastMove=NewMove;
        if(!(NewMove.skipMove())){
            CurrentBoard.applyMove(NewMove,player);
        }
    }


    public Board getBoard(){
        return CurrentBoard;
    }

    public int getBoardPosition(Move m){
        return CurrentBoard.getPosition(m);
    }
    public int getBoardPosition(int x, int y){
        return CurrentBoard.getPosition(new Move(x,y));
    }

    public Move getLastMove(){
        return LastMove;
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
                if(CurrentBoard.getPosition(x, y) == 0)
                    successorList.add(new Move(x, y));
            }
        }

        //if no adjacent moves are valid, all others are
        if(successorList.isEmpty()) for (int x = 0; x < boardSize; x++)
            for (int y = 0; y < boardSize; y++)
                if (CurrentBoard.getPosition(x, y) == 0)
                    successorList.add(new Move(x, y));

        //if it's the last move and placing would lower points allows not making the move
        if(CurrentBoard.isLastMove()){
            int player =(CurrentBoard.getBoardSize()+1)%2; //player intended as index for score
            int score = CurrentBoard.evaluateBoard()[player];
            Board testBoard = CurrentBoard.clone();
            testBoard.applyMove(successorList.toArray(new Move[0])[0],player+1);
            if (score>testBoard.evaluateBoard()[player]){
                successorList.add(new Move(true));
            }
        }
        return successorList.toArray(new Move[0]);
    }

    public boolean isTerminal(){
        return CurrentBoard.isFull() || ( LastMove!=null && LastMove.skipMove());
    }

    @Override
    public State clone(){
        return new State(CurrentBoard.clone(), LastMove);
    }
}
