package AOQueryRunner.GUI;

import javax.swing.*;
import java.awt.*;
/**
 * AOQueryRunner.GUI.QueryInfoPane Class -    QueryInfoPane panel constructor
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class QueryInfoPane extends JPanel {

    // instance vars
    private JTextArea programInfo;
    private JProgressBar queryBuffer;

    // constructor
    public QueryInfoPane() {
        JLabel programInfoHeader = new JLabel("Query Console", SwingConstants.CENTER);
        setLayout(new BorderLayout());
        programInfo = new JTextArea("This program is used in order to run queries on the collated Astrological Objects, " +
                "do this via entering a query through the available options" + "\nbelow." + "\n\n-  Additional query conditions can be entered" +
                " within the 'Advanced Query Search' option pane.\n" + "\n-  String fields can be queried as to whether or not they are populated" +
                " via querying the phrase 'null'.\n" + "\n-  Ensure that either the Stars, Messier or Planets tables" +
                " are populated prior to running a query.\n" + "\n-  Only the Messiers 'description' and an empty property field allow for the " +
                "entry of spaces. If a space is entered within the value field while any" + "\nother property is selected then searching is disabled\n",
                10, 10);
        queryBuffer = new JProgressBar();

        programInfoHeader.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        programInfo.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        programInfo.setEditable(false);
        programInfo.setLineWrap(true);
        queryBuffer.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        add(programInfoHeader, BorderLayout.NORTH);
        add(new JScrollPane(programInfo), BorderLayout.CENTER);
        add(queryBuffer, BorderLayout.SOUTH);
    }

    /**
     * appendText method,       used to append text to the programInfo JTextArea, calling scrollDown() to scroll the
     *                          scroll bar down
     *
     * @param text              - the listener to set for the associated object
     */
    protected void appendText(String text){
        programInfo.append("\n" + text + "\n");
        scrollDown();
    }

    /**
     * scrollDown method,       sets the focus point of the programInfo JTextArea to the last value entered, scrolling
     *                          the pane when data is pushed to it
     */
    private void scrollDown(){
        programInfo.setCaretPosition(programInfo.getText().length());
    }

    /**
     * getQueryBuffer method,       get method for the queryBuffer JProgressBar, used to increment progress of operations
     *                              to the bar.
     *
     * @return  JProgressBar
     */
    protected JProgressBar getQueryBuffer() {
        return queryBuffer;
    }
}