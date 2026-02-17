package sdm.freedom;

import java.util.Arrays;

public class Board {
    private final int[][] board;

    public Board(int n){
        board = new int[n][n];
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                board[i][j]=0;
            }
        }
    }
    public Board(int[][] NewBoard){
        board = NewBoard;
    }

    public int[][] getBoardMatrix(){
        return board;
    }
    public int getBoardSize(){
        return board.length;
    }

    public int getPosition(int x, int y){
        return board[x][y];
    }
    public int getPosition(Move m){
        int[] xy = m.returnMove();
        return board[xy[0]][xy[1]];
    }

    public void applyMove(Move LastMove,int player){
        int[] m =LastMove.returnMove();
        board[m[0]][m[1]]=player;
    }

    public int[] evaluateBoard(){
        int white=0;
        int black =0;
        for (int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(checkLivePosition(i,j)){
                    if(board[i][j]==1){
                        white+=1;
                    }else if(board[i][j]==2){
                        black+=1;
                    }

                }
            }
        }
        return new int[] {white,black};
    }

    private boolean checkLivePosition(int x,int y){

        int[][][] directions = {
                {
                {-4,0},{-3,0},{-2,0},{-1,0},
                {1,0},{2,0},{3,0},{4,0}
                },
                {
                {0,-4},{0,-3},{0,-2},{0,-1},
                {0,1},{0,2},{0,3},{0,4}
                },
                {
                {-4,-4},{-3,-3},{-2,-2},{-1,-1},
                {1,1},{2,2},{3,3},{4,4}
                },
                {
                {-4,4},{-3,3},{-2,2},{-1,1},
                {1,-1},{2,-2},{3,-3},{4,-4}
                }
        };
        int player = board[x][y];
        boolean flagLive = false;
        for(int[][] dir : directions){
            int counterDead = 0;
            int counterLive = 0;
            for(int[] coordinates : dir){
                int newx = x + coordinates[0];
                int newy = y + coordinates[1];
                if(isOutOfBounds(new Move(newx,newy))){
                    continue;
                }
                if(board[newx][newy]==player){
                    counterDead +=1;
                }else{
                    counterDead = 0;
                }
                if(board[newx][newy]==player && Math.abs(coordinates[0])<4 && Math.abs(coordinates[1])<4){
                    counterLive +=1;
                }else{
                    counterLive =0;
                }
                if(counterLive ==3){
                    flagLive = true;
                }

                if(counterDead>=4){
                    flagLive = false;
                }
            }
            if(flagLive){
                return flagLive;
            }
        }

        return flagLive;
    }

    public boolean isOutOfBounds(Move m){
        int x = m.returnMove()[0];
        int y = m.returnMove()[1];

        return x<0 || y<0 || y>=board.length || x>=board.length;
    }

    public boolean isFull() {
        return Arrays.stream(board)
                .flatMapToInt(Arrays::stream)
                .allMatch(x -> x != 0);
    }

    public boolean isLastMove(){
        return Arrays.stream(board).flatMapToInt(Arrays::stream).filter(n -> n == 0).count() == 1;
    }

    @Override
    public Board clone(){
        return new Board(Arrays.stream(board)
                .map(int[]::clone)
                .toArray(int[][]::new));
    }
}
