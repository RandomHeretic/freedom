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
