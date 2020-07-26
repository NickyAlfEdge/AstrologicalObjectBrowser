package AOQueryRunner.GUI;

import AOQueryRunner.System.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Objects;
/**
 * AOQueryRunner.GUI.Conditions Class -         Conditions panel constructor
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class Conditions extends JPanel {

    // instance vars
    private JComboBox<String> objectProperties;
    private JComboBox<String> queryOperators;
    private JTextField queryValue;

    private ConValueListener conValueListener;

    // constructor
    public Conditions(String conName) {

        setLayout(new BorderLayout());
        JPanel conditionHead = new JPanel(new BorderLayout());
        JLabel conLabel = new JLabel(conName);
        JPanel conditions = new JPanel(new FlowLayout(FlowLayout.CENTER));

        objectProperties = new JComboBox<>(Constants.starGuiVar);
        queryOperators = new JComboBox<>(Constants.operatorGuiVar);
        queryValue = new JTextField();
        queryValue.setToolTipText("Enter a Value to Query");

        conditionHead.add(conLabel, BorderLayout.CENTER);
        add(conditionHead, BorderLayout.NORTH);

        /*
         * queryValue field update listener, sends a boolean value to conValue listener set to each instance of Condition
         * which is then used to set the state of the corresponding search button
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

                if (!(Objects.equals(objectProperties.getSelectedItem(), "description")) && (queryValue.getText().contains(" "))) {
                    if (conValueListener != null) {
                        conValueListener.incorrectValue(1);
                    }
                } else if ((objectProperties.getSelectedItem().toString().isEmpty()) && !(queryValue.getText().isEmpty())) {
                    if (conValueListener != null) {
                        conValueListener.incorrectValue(2);
                    }
                } else {
                    if (conValueListener != null) {
                        conValueListener.incorrectValue(0);
                    }
                }
            }
        });

        objectProperties.setPreferredSize(new Dimension(80, 25));
        objectProperties.setEditable(false);
        conditions.add(objectProperties);
        conditions.add(queryOperators);
        queryValue.setPreferredSize(new Dimension(60, 26));
        conditions.add(queryValue);
        add(conditions);
    }

    /**
     * setConValueListener method,      set method for the queryValue field listener
     *
     * @param listener             - the listener to set for the associated object
     */
    protected void setConValueListener(ConValueListener listener) {
        this.conValueListener = listener;
    }

    /**
     * setConValueListener method,      set method for the queryValue field listener
     *
     * @return  JComboBox<String>
     */
    protected JComboBox<String> getObjectProperties() {
        return objectProperties;
    }

    /**
     * getSelectedValues method,        get method for the entered fields within the condition
     *
     * @return  String condition
     */
    protected String getSelectedValues() {

        String condition;
        String objectProp = Objects.requireNonNull(objectProperties.getSelectedItem()).toString();
        String operator = Objects.requireNonNull(queryOperators.getSelectedItem()).toString();
        String value = queryValue.getText();

        if ((objectProp.isEmpty()) && (operator.isEmpty()) && (value.isEmpty())) {
            condition = "";
        } else {
            condition = " " + objectProp + " " + operator + " " + value;
        }
        return condition;
    }
}
