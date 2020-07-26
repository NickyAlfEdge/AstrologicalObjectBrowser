package AOQueryRunner.GUI;

import javax.swing.*;
/**
 * AOQueryRunner.GUI.GUI Class -    main method for the GUI, invokes the GUI for display
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class Run {

    // main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
