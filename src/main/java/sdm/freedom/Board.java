package sdm.freedom;



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

    public int[][] giveBoard(){
        return board;
    }

    public int givePosition(int x,int y){
        return board[x][y];
    }
    public int givePosition(Move m){
        int[] xy = m.returnMove();
        return board[xy[0]][xy[1]];
    }

    public void applyMove(Move LastMove,int player){
        int[] m =LastMove.returnMove();
        board[m[0]][m[1]]=player;
    }

    public void printBoard(){
        for (int[] ints : board) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < board.length; j++) {
                row.append(ints[j]).append(" ");
            }
            System.out.println(row);
        }

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

    public boolean checkLivePosition(int x,int y){

        return false;
    }
}
