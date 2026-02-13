package sdm.freedom;

import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.AgentFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to Freedom, state the size of the board please");

        int n = s.nextInt();
        s.nextLine(); //flush after nextInt

        Match CurrentMatch = new Match(n);

        System.out.println("Available agents: " + AgentFactory.availableAgents());

        AbstractAgent[] agents = new AbstractAgent[] {askUserForAgentCreation(1, s), askUserForAgentCreation(2, s)};

        for(int i=0;i<n*n;i++){
            System.out.println("Current Board State:");
            CurrentMatch.printBoardState();

            CurrentMatch.applyAMove(agents[CurrentMatch.getCurrentPlayerIdx()].selectNextMove(
                    CurrentMatch.giveCurrentState()
            ));
        }
        System.out.println("The game ended with the following scores");
        int[] scores = CurrentMatch.evaluateBoard();
        System.out.println("White: " + scores[0]);
        System.out.println("Black: " + scores[1]);

    }

    private static AbstractAgent askUserForAgentCreation(int playerNumber, Scanner s){
        AbstractAgent agent = null;
        while (agent == null) {
            System.out.print("Enter type for Agent "+playerNumber+": ");
            String type = s.nextLine().trim();
            try {
                agent = AgentFactory.create(type, playerNumber);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid agent type. Please choose from: " + AgentFactory.availableAgents());
            }
        }
        return agent;
    }
}
