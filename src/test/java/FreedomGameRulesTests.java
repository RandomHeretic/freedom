
import sdm.freedom.*;

import org.junit.Test;

public class FreedomGameRulesTests {

    @Test
    public void legalMovesOnly(){
        Match Mat = new Match(8);
        Move m1 = new Move(1,1);
        Move m2 = new Move(5,5);
        Move m3 = new Move(1,2);
        Mat.applyAMove(m1);
        Mat.applyAMove(m2);
        Mat.applyAMove(m3);
        assert Mat.givePosition(m1)==1;
        assert Mat.givePosition(m2)==0;
        assert Mat.givePosition(m3)==2;
    }

    @Test
    public void checkEmptyBoardValueZero(){
        Board b = new Board(7);
        assert b.evaluateBoard()[0]==b.evaluateBoard()[1];
        assert b.evaluateBoard()[0]==0;
    }


}
