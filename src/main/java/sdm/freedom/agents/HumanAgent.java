package sdm.freedom.agents;

import sdm.freedom.Board;
import sdm.freedom.Move;

import java.util.Scanner;

public class HumanAgent extends AbstractAgent {

    protected HumanAgent() {
        super("Human Player");
    }

    @Override
    public Move selectNextMove(Board b, Move[] successorMoves) {

        for(int i = 0; i < successorMoves.length; i++){
            System.out.println("Option " + i + ": " + successorMoves[i].toString());
        }

        Scanner s = new Scanner(System.in);

        int idx;
        do{
            System.out.println("Select a move by inserting the index of one of the offered options...");
            idx = s.nextInt();
        }while (idx < 0 || idx >= successorMoves.length);

        return  successorMoves[idx];
    }
}
