
import sdm.freedom.*;

import org.junit.Test;

public class FreedomGameRulesTests {

    @Test
    public void legalMovesOnly(){
        Match Mat = new Match(8);
        Move m1 = new Move(1,1);
        Move m2 = new Move(5,5);
        Move m3 = new Move(1,2);
        Move m4 = new Move(2,3);
        Mat.checkAndApplyMove(m1);
        Mat.checkAndApplyMove(m2);
        Mat.checkAndApplyMove(m3);
        Mat.checkAndApplyMove(m4);
        assert Mat.getPosition(m1)==1;
        assert Mat.getPosition(m2)==0;
        assert Mat.getPosition(m3)==2;
        assert Mat.getPosition(m4)==1;
    }

    @Test
    public void freedomMove(){
        Match Mat = new Match(5);
        Move m1 = new Move(1,1);
        Move m2 = new Move(1,0);
        Move m3 = new Move(0,1);
        Move m4 = new Move(0,0);
        Move m5 = new Move(3,3);
        Mat.applyMove(m1);
        Mat.applyMove(m2);
        Mat.applyMove(m3);
        Mat.applyMove(m4);
        Mat.applyMove(m5);
        assert Mat.getPosition(m5)==1;
    }

    @Test
    public void checkEmptyBoardValueZero(){
        Board b = new Board(7);
        assert b.evaluateBoard()[0]==b.evaluateBoard()[1];
        assert b.evaluateBoard()[0]==0;
    }

    @Test
    public void checkEvaluationValueHorizontalBasic(){
        Board b = new Board(4);
        b.applyMove(new Move(0,0),1);
        b.applyMove(new Move(0,1),1);
        b.applyMove(new Move(0,2),1);
        b.applyMove(new Move(0,3),1);
        assert b.evaluateBoard()[0]==4;
    }

    @Test
    public void checkEvaluationValueVerticalBasic(){
        Board b = new Board(4);
        b.applyMove(new Move(0,0),1);
        b.applyMove(new Move(1,0),1);
        b.applyMove(new Move(2,0),1);
        b.applyMove(new Move(3,0),1);
        assert b.evaluateBoard()[0]==4;
    }

    @Test
    public void checkEvaluationValueMainDiagonalBasic(){
        Board b = new Board(4);
        b.applyMove(new Move(0,0),1);
        b.applyMove(new Move(1,1),1);
        b.applyMove(new Move(2,2),1);
        b.applyMove(new Move(3,3),1);
        assert b.evaluateBoard()[0]==4;
    }

    @Test
    public void checkEvaluationValueOppositeDiagonalBasic(){
        Board b = new Board(4);
        b.applyMove(new Move(0,3),1);
        b.applyMove(new Move(1,2),1);
        b.applyMove(new Move(2,1),1);
        b.applyMove(new Move(3,0),1);
        assert b.evaluateBoard()[0]==4;
    }

    @Test
    public void checkEvaluationValueHorizontalDeadStones(){
        Board b = new Board(5);
        b.applyMove(new Move(0,0),1);
        b.applyMove(new Move(0,1),1);
        b.applyMove(new Move(0,2),1);
        b.applyMove(new Move(0,3),1);
        b.applyMove(new Move(0,4),1);
        assert b.evaluateBoard()[0]==0;
    }

    @Test
    public void checkEvaluationValueVerticalDeadStones(){
        Board b = new Board(5);
        b.applyMove(new Move(0,0),1);
        b.applyMove(new Move(1,0),1);
        b.applyMove(new Move(2,0),1);
        b.applyMove(new Move(3,0),1);
        b.applyMove(new Move(4,0),1);
        assert b.evaluateBoard()[0]==0;
    }

    @Test
    public void checkEvaluationValueDiagonalDeadStones(){
        Board b = new Board(5);
        b.applyMove(new Move(0,0),1);
        b.applyMove(new Move(1,1),1);
        b.applyMove(new Move(2,2),1);
        b.applyMove(new Move(3,3),1);
        b.applyMove(new Move(4,4),1);
        assert b.evaluateBoard()[0]==0;
    }

    @Test
    public void checkEvaluationValueOppositeDiagonalDeadStones(){
        Board b = new Board(5);
        b.applyMove(new Move(0,4),1);
        b.applyMove(new Move(1,3),1);
        b.applyMove(new Move(2,2),1);
        b.applyMove(new Move(3,1),1);
        b.applyMove(new Move(4,0),1);
        assert b.evaluateBoard()[0]==0;
    }

    @Test
    public void checkSmallGame(){
        int[][] b={
                {1,1,2,2,2},
                {1,2,1,2,1},
                {1,2,2,1,1},
                {1,2,1,2,2},
                {2,2,2,2,2}
        };
        Board board = new Board(b);
        assert board.evaluateBoard()[0]==4;
        assert board.evaluateBoard()[1]==7;

    }

    @Test
    public void checkMediumGame(){
        int[][] b={
                {1,1,2,2,2,1,2,1},
                {1,2,1,2,1,2,2,1},
                {1,2,2,1,1,1,1,2},
                {1,2,1,2,2,1,1,1},
                {2,2,2,2,2,2,1,2},
                {1,2,1,2,2,2,1,1},
                {1,2,1,2,2,1,1,2},
                {1,1,1,1,1,1,1,1}
        };

        Board board = new Board(b);
        assert board.evaluateBoard()[0]==14;
        assert board.evaluateBoard()[1]==10;
    }

    @Test
    public void checkBigGame(){
        int[][] b={
                {1,2,1,2,1,2,2,1,2,1},
                {1,2,1,2,2,1,1,2,2,1},
                {2,1,2,1,1,2,2,1,2,2},
                {1,2,1,1,2,2,1,2,1,1},
                {2,1,2,2,1,1,2,1,1,2},
                {1,1,2,1,2,2,1,1,2,2},
                {2,1,2,1,2,1,2,2,1,1},
                {1,1,2,2,1,2,2,1,1,2},
                {2,2,1,2,1,1,2,1,2,1},
                {2,1,2,1,1,2,1,2,1,2}
        };

        Board board = new Board(b);
        assert board.evaluateBoard()[0]==14;
        assert board.evaluateBoard()[1]==23;
    }
}
