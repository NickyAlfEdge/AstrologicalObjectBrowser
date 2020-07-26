package AOQueryRunner.Exceptions;
/**
 * AOQueryRunner.Exceptions.InvalidOperatorException Class -   invalid operator exception, used to identify errors within
 *                                                             queries.
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public class InvalidOperatorException extends Exception {

    public InvalidOperatorException() {
        super("Invalid Operator. Please use a valid operator, i.e. " +
                "\">\", \"<\", \">=\", \"<=\", \"=\" or \"!=\"");
    }
}
