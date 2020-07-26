package AOQueryRunner.System;
/**
 * AOQueryRunner.System.Condition Class -  AOQueryRunner.System.Condition Object class, used to create valid conditions.
 *
 * @author Nicky Edge
 * @version 07/04/2020
 */
public class Condition {

    // instance variables
    private String variableName;
    private String operator;
    private String value;

    // Class constructor with four parameters
    public Condition(String variableName, String operator, String value) {
        this.variableName = variableName;
        this.operator = operator;
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    /**
     * AOQueryRunner.System.Condition class toString override
     *
     * @return - AOQueryRunner.System.Condition object to be printed.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", this.variableName, this.operator, this.value);
    }
}
