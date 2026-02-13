package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

import java.util.Scanner;

public class HumanAgent extends AbstractAgent {

    protected HumanAgent(int playerNumber) {
        super("Human Player", playerNumber);
    }

    @Override
    public Move selectNextMove(State s) {

        Move[] successors = s.getLegalSuccessors();
        for(int i = 0; i < successors.length; i++){
            System.out.println("Option " + i + ": " + successors[i].toString());
        }

        Scanner scanner = new Scanner(System.in);

        int idx;
        do{
            System.out.println("Select a move by inserting the index of one of the offered options...");
            idx = scanner.nextInt();
        }while (idx < 0 || idx >= successors.length);

        return  successors[idx];
    }
}
