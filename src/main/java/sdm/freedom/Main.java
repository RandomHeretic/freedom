package sdm.freedom;

import javax.swing.SwingUtilities;

import sdm.freedom.gui.MenuGUI;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuGUI menu = new MenuGUI();
            menu.setVisible(true);
        });
    }
}

/* 
package sdm.freedom;

import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.AgentFactory;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Board size:");
        int n = s.nextInt();
        s.nextLine();

        System.out.println("Available agents: " + AgentFactory.availableAgents());

        AbstractAgent[] agents = {
                askUserForAgentCreation(1, s),
                askUserForAgentCreation(2, s)
        };

        UIController uiController = UIController.getInstance();
        uiController.start(n);

        GameController.getInstance().initialize(n, uiController, agents);
    }

    private static AbstractAgent askUserForAgentCreation(int player, Scanner s) {
        while (true) {
            System.out.print("Enter agent type for player " + player + ": ");
            try {
                return AgentFactory.create(s.nextLine().trim(), player);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid agent.");
            }
        }
    }
}
*/
