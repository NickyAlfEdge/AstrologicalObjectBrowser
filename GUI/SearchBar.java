package AOQueryRunner.GUI;

import AOQueryRunner.System.Constants;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
/**
 * AOQueryRunner.GUI.SearchBar Class -    SearchBar panel constructor
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class SearchBar extends JPanel implements ActionListener {

    // instance vars
    private JComboBox<String> objectList;
    private JComboBox<String> objectProperties;
    private JComboBox<String> queryOperators;
    private JTextField queryValue;
    private JButton searchBtn;

    private StdQuerySearchListener stdQuerySearchListener;
    private ObjTypeChangeListener objTypeChangeListener;

    // constructor
    public SearchBar() {

        setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel primaryQueryWord = new JLabel("Select Query Object: ");
        objectList = new JComboBox<>(Constants.objTypes);
        JLabel secondaryQueryWord = new JLabel("where");
        objectProperties = new JComboBox<>(populateObjPropertiesList());
        queryOperators = new JComboBox<>(Constants.operatorGuiVar);
        queryValue = new JTextField();
        queryValue.setToolTipText("Enter a Value to Query");
        searchBtn = new JButton("Search Query");

        searchBtn.addActionListener(this);
        objectList.addActionListener(this);

        /*
         * queryValue field listener, enabling/disabling the searchBtn for valid or invalid values enter for an incorrect
         * object property.
         */
        queryValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
            public void changed() {

                if ((Objects.equals(objectProperties.getSelectedItem(), "description")) || (Objects.equals(objectProperties.getSelectedItem(), ""))) {
                    if (queryValue.getText().contains(" ")) {
                        searchBtn.setEnabled(true);
                    }
                } else if (!(Objects.equals(objectProperties.getSelectedItem(), "description"))) {
                    if (queryValue.getText().contains(" ")) {
                        searchBtn.setEnabled(false);
                    } else {
                        searchBtn.setEnabled(true);
                    }
                }
            }
        });

        add(primaryQueryWord);
        objectList.setEditable(false);
        add(objectList);
        add(secondaryQueryWord);
        objectProperties.setPreferredSize(new Dimension(90, 25));
        objectProperties.setEditable(false);
        add(objectProperties);
        add(queryOperators);
        queryValue.setPreferredSize(new Dimension(100, 26));
        add(queryValue);
        add(searchBtn);
    }

    // accesor methods for the various SearchBar properties
    protected JComboBox<String> getObjectList() {
        return objectList;
    }

    protected JComboBox<String> getObjectProperties() {
        return objectProperties;
    }

    protected JComboBox<String> getQueryOperators() {
        return queryOperators;
    }

    protected JTextField getQueryValue() {
        return queryValue;
    }

    protected JButton getSearchBtn() {
        return searchBtn;
    }

    /**
     * setStdQuerySearchListener method,       set method for the searchBtn listener
     *
     * @param listener                         - the listener to set for the associated object
     */
    protected void setStdQuerySearchListener(StdQuerySearchListener listener) {
        this.stdQuerySearchListener = listener;
    }

    /**
     * setObjTypeChangeListener method,      set method for the objectList listener
     *
     * @param listener                       - the listener to set for the associated object
     */
    protected void setObjTypeChangeListener(ObjTypeChangeListener listener) {
        this.objTypeChangeListener = listener;
    }

    /**
     * fieldEmptyCheck method,      checks whether the query fields are empty, used to send the appropriate query for
     *                              subsequent initialisation.
     *
     * @return Boolean
     */
    private Boolean fieldEmptyCheck() {

        String query = "";
        String objProperty = Objects.requireNonNull(objectProperties.getSelectedItem()).toString();
        String objOperator = Objects.requireNonNull(queryOperators.getSelectedItem()).toString();
        String value = queryValue.getText();

        query = objProperty + objOperator + value;

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
        String objType = Objects.requireNonNull(objectList.getSelectedItem()).toString() + " ";
        String where = "Where ";
        String objProperty = Objects.requireNonNull(objectProperties.getSelectedItem()).toString() + " ";
        String objOperator = Objects.requireNonNull(queryOperators.getSelectedItem()).toString() + " ";
        String value = queryValue.getText() + " ";

        if (queryType == 1) {
            query = select + objType;
        } else if (queryType == 2) {
            query = select + objType + where + objProperty + objOperator + value;
        }
        return query;
    }

    /**
     * populateObjPropertiesList method,           returns the appropriate list for the corresponding object type
     *
     * @return String[] Constants list value
     */
    private String[] populateObjPropertiesList() {

        if (Objects.equals(objectList.getSelectedItem(), "Stars")) {
            return Constants.starGuiVar;
        } else if (Objects.equals(objectList.getSelectedItem(), "Messiers")) {
            return Constants.messierGuiVar;
        } else {
            return Constants.planeGuiVar;
        }
    }

    // actionListeners
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(searchBtn)) {
            String objType = (String) objectList.getSelectedItem();
            if (fieldEmptyCheck()) {
                if (stdQuerySearchListener != null) {
                    String query = getQuery(1);
                    stdQuerySearchListener.standardQuerySearch(query, objType);
                }
            } else {
                if (stdQuerySearchListener != null) {
                    String query = getQuery(2);
                    stdQuerySearchListener.standardQuerySearch(query, objType);
                }
            }
        } else if (e.getSource().equals(objectList)) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(populateObjPropertiesList());
            objectProperties.setModel(model);
            if (objTypeChangeListener != null) {
                objTypeChangeListener.objChanged(populateObjPropertiesList());
            }
        }
    }
}