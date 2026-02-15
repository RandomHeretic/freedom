package sdm.freedom;

public record Move(int x, int y, boolean skipMove) {

    public Move(int newx, int newy){
        this(newx,newy,false);
    }
    public Move(boolean skipThisMove){
        this(-1,-1,skipThisMove);
    }

    public int[] returnMove(){
        return new int[] {x,y};
    }

    public void printAsLastMove(){
        if(skipMove){
            System.out.println("Last Move: Skip");
        }else {
            System.out.println("Last Move: (" + x + "," + y + ")");
        }
    }
}
