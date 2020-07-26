package AOQueryRunner.GUI;

import AOQueryRunner.System.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
/**
 * AOQueryRunner.GUI.MainToolBar Class -    MainToolBar panel constructor
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class MainToolBar extends JPanel implements ActionListener {

    // instance vars
    private JMenuItem starFileBtn;
    private JMenuItem messierFileBtn;
    private JMenuItem planetsFileBtn;
    private JMenuItem exitBtn;

    private StarsListListener starsListListener;
    private MessiersListListener messiersListListener;
    private PlanetsListListener planetsListListener;

    // constructor
    public MainToolBar() {

        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu sysMenu = new JMenu("System");

        starFileBtn = new JMenuItem("Add Stars File");
        messierFileBtn = new JMenuItem("Add Messiers File");
        planetsFileBtn = new JMenuItem("Add Planets File");
        exitBtn = new JMenuItem("Exit");

        fileMenu.add(starFileBtn);
        fileMenu.add(messierFileBtn);
        fileMenu.add(planetsFileBtn);
        sysMenu.add(exitBtn);

        starFileBtn.addActionListener(this);
        messierFileBtn.addActionListener(this);
        planetsFileBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        menuBar.add(fileMenu, BorderLayout.WEST);
        menuBar.add(sysMenu, BorderLayout.CENTER);

        add(menuBar);
    }

    /**
     * setStarsListener method,        set method for the 'Add Stars File' field listener
     *
     * @param listener                 - the listener to set for the associated object
     */
    protected void setStarsListener(StarsListListener listener) {
        this.starsListListener = listener;
    }

    /**
     * setMessiersListener method,     set method for the 'Add Messiers File' field listener
     *
     * @param listener                 - the listener to set for the associated object
     */
    protected void setMessiersListener(MessiersListListener listener) {
        this.messiersListListener = listener;
    }

    /**
     * setPlanetsListener method,      set method for the 'Add Planets File'field listener
     *
     * @param listener                 - the listener to set for the associated object
     */
    protected void setPlanetsListener(PlanetsListListener listener) {
        this.planetsListListener = listener;
    }

    // actionListeners
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(exitBtn)) {
            int input = JOptionPane.showConfirmDialog(null,"Are you sure you wish to Exit?",
                    "AO Query Runner", JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                System.exit(0);
            }
        } else if (e.getSource().equals(starFileBtn)) {
            String starFileName = JOptionPane.showInputDialog(null,"Enter the Directory " +
                            "of the Stars File: ","Stars File Directory", JOptionPane.PLAIN_MESSAGE);
            if (starFileName != null && starFileName.endsWith(".txt")) {
                try {
                    if (starsListListener != null) {
                        starsListListener.starsListAdded(new StarsReader(starFileName));
                    }
                // catch format errors within the entered file
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Field type mismatch found within: " +
                                    starFileName + "\nFor: " + nfe.getMessage(),"Error", JOptionPane.PLAIN_MESSAGE);
                // catch read data exception, if too few fields are passed
                } catch (ArrayIndexOutOfBoundsException aoob) {
                    JOptionPane.showMessageDialog(null, "Too few fields found within: " + starFileName,
                            "Error", JOptionPane.PLAIN_MESSAGE);
                // catch unreachable/erroneous file exception
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Couldn't find file: " + starFileName,
                            "Error", JOptionPane.PLAIN_MESSAGE);
                // catch other exceptions that may arise
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected Error: " + ex.toString(),
                            "Error", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (starFileName != null && !starFileName.endsWith(".txt")) {
                JOptionPane.showMessageDialog(null, "Please enter a file in '.txt' format",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            }
        } else if (e.getSource().equals(messierFileBtn)) {
            String messierFileName = JOptionPane.showInputDialog(null,"Enter the Directory " +
                    "of the Messiers File: ","Messiers File Directory", JOptionPane.PLAIN_MESSAGE);
            if (messierFileName != null && messierFileName.endsWith(".txt")) {
                try {
                    if (messiersListListener != null) {
                        messiersListListener.messiersListAdded(new MessiersReader(messierFileName));
                    }
                // catch format errors within the entered file
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Field type mismatch found within: " +
                            messierFileName + "\nFor: " + nfe.getMessage(),"Error", JOptionPane.PLAIN_MESSAGE);
                // catch read data exception, if too few fields are passed
                } catch (ArrayIndexOutOfBoundsException aoob) {
                    JOptionPane.showMessageDialog(null, "Too few fields found within: " + messierFileName,
                            "Error", JOptionPane.PLAIN_MESSAGE);
                // catch unreachable/erroneous file exception
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Couldn't find file: " + messierFileName,
                            "Error", JOptionPane.PLAIN_MESSAGE);
                // catch other exceptions that may arise
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected Error: " + ex.toString(),
                            "Error", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (messierFileName != null && !messierFileName.endsWith(".txt")) {
                JOptionPane.showMessageDialog(null, "Please enter a file in '.txt' format",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            }
        } else if (e.getSource().equals(planetsFileBtn)) {
            String planetFileName = JOptionPane.showInputDialog(null,"Enter the Directory " +
                    "of the Planets File: ","Planets File Directory", JOptionPane.PLAIN_MESSAGE);
            if (planetFileName != null && planetFileName.endsWith(".txt")) {
                try {
                    if (planetsListListener != null) {
                        planetsListListener.planetsListAdded(new PlanetsReader(planetFileName));
                    }
                // catch format errors within the entered file
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Field type mismatch found within: " +
                            planetFileName + "\nFor: " + nfe.getMessage(),"Error", JOptionPane.PLAIN_MESSAGE);
                // catch read data exception, if too few fields are passed
                } catch (ArrayIndexOutOfBoundsException aoob) {
                    JOptionPane.showMessageDialog(null, "Too few fields found within: " + planetFileName,
                            "Error", JOptionPane.PLAIN_MESSAGE);
                // catch unreachable/erroneous file exception
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Couldn't find file: " + planetFileName,
                            "Error", JOptionPane.PLAIN_MESSAGE);
                // catch other exceptions that may arise
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected Error: " + ex.toString(),
                            "Error", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (planetFileName != null && !planetFileName.endsWith(".txt")) {
                JOptionPane.showMessageDialog(null, "Please enter a file in '.txt' format",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
