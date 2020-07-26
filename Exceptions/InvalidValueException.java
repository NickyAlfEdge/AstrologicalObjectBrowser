package AOQueryRunner.Exceptions;
/**
 * AOQueryRunner.Exceptions.InvalidValueException Class -      invalid value exception, used to identify errors within
 *                                                             queries.
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class InvalidValueException extends Exception {

    public InvalidValueException() {
        super("Invalid Query Condition value. Please use a valid value for the respective variable, i.e. \"ra\" - " +
                "would require an integer value for a valid query.");
    }
}
