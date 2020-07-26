package AOQueryRunner.GUI;

import javax.swing.*;
import java.awt.*;
/**
 * AOQueryRunner.GUI.StdQuerySearchListener Class -    listener interface for the standard search feature
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class TablePanel extends JPanel {

    // instance vars
    private JTabbedPane tabbedPane;
    private QueryInfoPane queryInfo;

    // constructor
    public TablePanel() {

        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Stars", createPlaceholderPanel());
        tabbedPane.addTab("Messiers", createPlaceholderPanel());
        tabbedPane.addTab("Planets", createPlaceholderPanel());
        tabbedPane.addTab("Query Results", createPlaceholderPanel());

        queryInfo = new QueryInfoPane();

        add(tabbedPane, BorderLayout.CENTER);
        add(queryInfo, BorderLayout.SOUTH);
    }

    // accessor methods
    protected JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    protected QueryInfoPane getQueryInfo() {
        return queryInfo;
    }

    /**
     * createPlaceholderPanel method,   creates a placeholder panel for the table, indicating to the user that the files
     *                                  must be added for these fields to be populated with data tables.
     *
     * @return  JPanel - a placeholder panel, replaced when the tables are populated
     */
    private JPanel createPlaceholderPanel() {

        JPanel placeHolderPanel = new JPanel();
        JLabel placeHolderLabel = new JLabel("Add files to the program in order to populate these fields and commence querying");
        placeHolderPanel.add(placeHolderLabel);

        return placeHolderPanel;
    }
}
