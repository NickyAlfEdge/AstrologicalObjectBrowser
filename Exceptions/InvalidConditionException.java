package AOQueryRunner.Exceptions;
/**
 * AOQueryRunner.Exceptions.InvalidConditionException Class -  invalid condition exception, used to identify errors within
 *                                                             queries.
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class InvalidConditionException extends Exception {

    public InvalidConditionException() {
        super("Invalid Query Conditions, please enter conditions in the format, \"var: 'ra', operator: '>', " +
                "value: '100'\". Conditions should be given in multiples of 3.");
    }
}