import sdm.freedom.*;

import org.junit.Test;

public class FreedomBasicTests {

    @Test
    public void verifyExistenceOfClassMatch() throws ClassNotFoundException{
        Class.forName("sdm.freedom.Match");
    }

    @Test
    public void verifyExistenceOfClassState() throws ClassNotFoundException{
        Class.forName("sdm.freedom.State");
    }

    @Test
    public void verifyExistenceOfClassBoard() throws ClassNotFoundException{
        Class.forName("sdm.freedom.Board");
    }

    @Test
    public void verifyExistenceOfClassMove() throws ClassNotFoundException{
        Class.forName("sdm.freedom.Move");
    }

    @Test
    public void verifyBoardHasASquareMatrix(){
        int n=9;
        Board b = new Board(n);
        int[][] rows = b.giveBoard();
        int c=rows.length,d=rows[0].length;
        assert c==d;
        assert c==n;
    }

    @Test
    public void verifyMoveHasATuple(){
        int x = 4;
        int y = 7;
        Move m = new Move(x,y);
        assert m.returnMove().length==2;
    }

    @Test
    public void verifyBoardMoves(){
        Move m = new Move(3,4);
        int p1=3, p2 =5;
        Board b = new Board(7);
        b.applyMove(m,p1);
        assert p1==b.givePosition(m);
        b.applyMove(m,p2);
        assert p2==b.givePosition(m);
    }

    @Test
    public void verifyNewBoardIsEmpty(){
        int n=7;
        Board b = new Board(n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                assert b.givePosition(i,j)==0;
            }
        }
    }

    @Test
    public void verifyStateHasBoardAndMove(){
        Board b = new Board(8);
        Move m = new Move(1,3);
        State s = new State(b,m);
        assert b == s.giveBoard();
        assert m == s.giveLastMove();
    }

    @Test
    public void verifyMatchHasState(){
        Match M = new Match(8);
        assert M.giveCurrentState().getClass()== State.class;
    }

    @Test
    public void verifyMatchAlternatesPlayers(){
        Move m1 = new Move(1,2);
        Move m2 = new Move(1,3);
        Match Mat = new Match(5);
        Mat.applyAMove(m1);
        Mat.applyAMove(m2);
        assert Mat.giveCurrentState().giveBoardPosition(m1)==1;
        assert Mat.giveCurrentState().giveBoardPosition(m2)==2;
    }

}
