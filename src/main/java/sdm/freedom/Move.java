package sdm.freedom;

public class Move {
    private final int x;
    private final int y;
    public Move(int new_x,int new_y){
        x=new_x;
        y=new_y;
    }
    public int[] returnMove(){
        return new int[] {x,y};
    }

    public void printAsLastMove(){
        System.out.println("Last Move: (" + x + "," + y + ")");
    }

    public boolean compareMoves(Move m){
        int mx = m.returnMove()[0];
        int my = m.returnMove()[1];
        return mx == x && my == y;
    }
}
