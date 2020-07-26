package AOQueryRunner.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
/**
 * AOQueryRunner.GUI.AdvancedSearch Class -         AdvancedSearch frame constructor
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class AdvancedSearch extends JFrame implements ActionListener {

    private JButton advancedSearchBtn;
    private SearchBar searchBar;
    private Conditions conditionsOne;
    private Conditions conditionsTwo;
    private Conditions conditionsThree;
    private int conOneFlag;
    private int conTwoFlag;
    private int conThreeFlag;

    private AdvancedQuerySearchListener advancedQuerySearchListener;

    // constructor
    public AdvancedSearch() {
        super("Advanced Query Search");
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        searchBar = new SearchBar();
        advancedSearchBtn = new JButton("Search Query");
        conditionsOne = new Conditions("condition 2");
        conditionsTwo = new Conditions("condition 3");
        conditionsThree = new Conditions("condition 4");

        advancedSearchBtn = searchBar.getSearchBtn();
        advancedSearchBtn.addActionListener(this);

        /*
         * searchBar listener for the ObjType field, updates the objProperty lists to the type of the object selected
         * within the searchBar
         */
        searchBar.setObjTypeChangeListener(new ObjTypeChangeListener() {
            @Override
            public void objChanged(String[] list) {

                DefaultComboBoxModel<String> conOneModel = new DefaultComboBoxModel<>(list);
                DefaultComboBoxModel<String> conTwoModel = new DefaultComboBoxModel<>(list);
                DefaultComboBoxModel<String> conThreeModel = new DefaultComboBoxModel<>(list);

                conditionsOne.getObjectProperties().setModel(conOneModel);
                conditionsTwo.getObjectProperties().setModel(conTwoModel);
                conditionsThree.getObjectProperties().setModel(conThreeModel);
            }
        });

        /*
         * conditionOne listener, checks the value field is valid for the appropriate object property
         */
        conditionsOne.setConValueListener(new ConValueListener() {
            @Override
            public void incorrectValue(int errorType) {
                setConOneError(errorType);
            }
        });

        /*
         * conditionTwo listener, checks the value field is valid for the appropriate object property
         */
        conditionsTwo.setConValueListener(new ConValueListener() {
            @Override
            public void incorrectValue(int errorType) {
                setConTwoError(errorType);
            }
        });

        /*
         * conditionThree listener, checks the value field is valid for the appropriate object property
         */
        conditionsThree.setConValueListener(new ConValueListener() {
            @Override
            public void incorrectValue(int errorType) {
                setConThreeError(errorType);
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(searchBar, BorderLayout.NORTH);
        mainPanel.add(conditionsOne, BorderLayout.WEST);
        mainPanel.add(conditionsTwo, BorderLayout.SOUTH);
        mainPanel.add(conditionsThree, BorderLayout.WEST);

        setSize(770, 130);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * setConOneError method,       set method for the errorcode of the condition fields
     *
     * @param errorType             - the error type to throw. 0: valid, 1: space error, 2: empty fields
     */
    private void setConOneError(int errorType) {

        if (errorType == 0) {
            conOneFlag = 0;
        } else if (errorType == 1) {
            conOneFlag = 1;
        } else if (errorType == 2) {
            conOneFlag = 2;
        }
    }

    /**
     * setConTwoError method,       set method for the errorcode of the condition fields
     *
     * @param errorType             - the error type to throw. 0: valid, 1: space error, 2: empty fields
     */
    private void setConTwoError(int errorType) {

        if (errorType == 0) {
            conTwoFlag = 0;
        } else if (errorType == 1) {
            conTwoFlag = 1;
        } else if (errorType == 2) {
            conTwoFlag = 2;
        }
    }

    /**
     * setConThreeError method,       set method for the errorcode of the condition fields
     *
     * @param errorType             - the error type to throw. 0: valid, 1: space error, 2: empty fields
     */
    private void setConThreeError(int errorType) {

        if (errorType == 0) {
            conThreeFlag = 0;
        } else if (errorType == 1) {
            conThreeFlag = 1;
        } else if (errorType == 2) {
            conThreeFlag = 2;
        }
    }

    /**
     * setAdvancedQuerySearchListener method,  set method for the advanced search listener
     *
     * @param listener             - the listener to set for the associated object
     */
    protected void setAdvancedQuerySearchListener(AdvancedQuerySearchListener listener) {
        this.advancedQuerySearchListener = listener;
    }

    /**
     * fieldEmptyCheck method,      checks whether the query fields are empty, used to send the appropriate query for
     *                              subsequent initialisation.
     *
     * @return Boolean
     */
    private Boolean fieldEmptyCheck() {

        String query = "";
        String objProperty = Objects.requireNonNull(searchBar.getObjectProperties().getSelectedItem()).toString();
        String objOperator = Objects.requireNonNull(searchBar.getQueryOperators().getSelectedItem()).toString();
        String value = searchBar.getQueryValue().getText();
        String conditionOne = conditionsOne.getSelectedValues();
        String conditionTwo = conditionsTwo.getSelectedValues();
        String conditionThree = conditionsThree.getSelectedValues();

        query = objProperty + objOperator + value + conditionOne + conditionTwo + conditionThree;

        return query.isEmpty();
    }

    /**
     * getQuery method,             returns a completed query, fieldEmptyCheck() is used to determine the query type
     *
     * @return String query
     */
    private String getQuery(int queryType) {

        String query = "";
        String select = "Select ";
        String objType = Objects.requireNonNull(searchBar.getObjectList().getSelectedItem()).toString() + " ";
        String where = "Where ";
        String objProperty = Objects.requireNonNull(searchBar.getObjectProperties().getSelectedItem()).toString() + " ";
        String objOperator = Objects.requireNonNull(searchBar.getQueryOperators().getSelectedItem()).toString() + " ";
        String value = searchBar.getQueryValue().getText() + " ";
        String conditionOne = conditionsOne.getSelectedValues() + " ";
        String conditionTwo = conditionsTwo.getSelectedValues() + " ";
        String conditionThree = conditionsThree.getSelectedValues() + " ";

        if (queryType == 1) {
            query = select + objType;
        } else if (queryType == 2) {
            query = select + objType + where + objProperty + objOperator + value + conditionOne + conditionTwo + conditionThree;
        }
        return query;
    }

    // actionListeners
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(advancedSearchBtn)) {
            if ((conOneFlag == 0) && (conTwoFlag == 0) && (conThreeFlag == 0)) {
                String objType = (String) searchBar.getObjectList().getSelectedItem();
                if (fieldEmptyCheck()) {
                    if (advancedQuerySearchListener != null) {
                        String query = getQuery(1);
                        advancedQuerySearchListener.advancedQuerySearch(query, objType);
                    }
                } else {
                    if (advancedQuerySearchListener != null) {
                        String query = getQuery(2);
                        advancedQuerySearchListener.advancedQuerySearch(query, objType);
                    }
                }
            } else if ((conOneFlag == 1) || (conTwoFlag == 1) || (conThreeFlag == 1)) {
                JOptionPane.showMessageDialog(null, "Spaces can only be entered for the Messiers Description property",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            } else if ((conOneFlag == 2) || (conTwoFlag == 2) || (conThreeFlag == 2)) {
                JOptionPane.showMessageDialog(null, "Please ensure there are no empty Condition Fields",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
