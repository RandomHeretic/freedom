package sdm.freedom;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to Freedom, state the size of the board please");

        int n = s.nextInt();

        Match CurrentMatch = new Match(n);

        for(int i=0;i<5;i++){
            System.out.println("Current Board State:");
            CurrentMatch.printBoardState();

            System.out.println("State your next move (first x, then y)");
            int x = s.nextInt();
            int y = s.nextInt();
            CurrentMatch.applyAMove(new Move(x,y));
        }

    }
}
