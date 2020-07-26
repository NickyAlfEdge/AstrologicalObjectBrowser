package AOQueryRunner.System;

import AOQueryRunner.Exceptions.*;
/**
 * ConditionReader Class -  reads the Conditions passed from AOQueryRunner.System.QueryValidator, catching errors and outputting a valid
 *                          condition String value
 * @author Nicky Edge
 * @version 05/04/2020
 */
public class ConditionValidator {

    private final String queryLine;

    /**
     * ConditionReader Object Constructor
     *
     * @param queryLine     - the line being parsed for conditions
     */
    public ConditionValidator(String queryLine) {

        this.queryLine = queryLine;
    }

    /**
     * validateOperator method, validates the operator entered within a query string
     *
     * @param operator          - the String value being parsed
     * @return operator
     */
    private String validateOperator(String operator, String variableName) throws InvalidOperatorException {

        if ((Constants.operatorList.contains(operator)) && (Constants.numericVarList.contains(variableName))) {
            return operator;
        } else if ((operator.equals("=")) || (operator.equals("!=")) && Constants.nonNumericVarList.contains(variableName)) {
            return operator;
        } else {
            throw new InvalidOperatorException();
        }
    }

    /**
     * validateVal method, validates the value entered within a query string
     *
     * @param value             - the String value being parsed
     * @param variableName      - the variable name within a condition, used for error checking
     * @return value
     */
    public String validateVal(String value, String variableName) throws InvalidValueException {

        if ((value.matches("^-?[0-9]\\d*(\\.\\d+)?$")) && (Constants.numericVarList.contains(variableName))) {
            return value;
        } else if (Constants.nonNumericVarList.contains(variableName)) {
            return value;
        } else {
            throw new InvalidValueException();
        }
    }

    /**
     * validateConditions method, reads through the condition line and calls the appropriate validation methods
     * to ensure the conditions within a query are valid.
     *
     * @param condition     - the condition line being validated
     * @return sb.toString  - the validated condition String value
     */
    protected String validateConditions(ConditionValidator condition) throws InvalidConditionException, InvalidOperatorException, InvalidValueException  {

        StringBuilder validConditions = new StringBuilder();
        boolean descriptionFlag = true;
        String[] words = condition.queryLine.trim().split("\\s++");

        int conCounter = 0;
        if (words.length >= 3) {
            while (conCounter < words.length) {
                String variableName = words[conCounter].trim();
                conCounter++;
                String operator = validateOperator(words[conCounter].trim(), variableName);
                conCounter++;
                String value;
                if (variableName.equalsIgnoreCase("description")) {
                    StringBuilder description = new StringBuilder();
                    description.append("desc: ");
                    while ((conCounter < words.length) && (descriptionFlag)) {
                        if (!(Constants.descriptionList.contains(words[conCounter]))) {
                            description.append(words[conCounter]).append("-");
                            conCounter++;
                        } else if (Constants.descriptionList.contains(words[conCounter])) {
                            descriptionFlag = false;
                        }
                    }
                    value = description.toString().trim();
                } else {
                    value = validateVal(words[conCounter].trim(), variableName);
                    conCounter++;
                }
                if (!descriptionFlag) {
                    descriptionFlag = true;
                }
                validConditions.append(" ").append(variableName).append(" ").append(operator).append(" ").append(value);
            }
        } else {
            throw new InvalidConditionException();
        }
        return validConditions.toString();
    }
}
