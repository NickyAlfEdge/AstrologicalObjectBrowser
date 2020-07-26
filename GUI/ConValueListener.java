package AOQueryRunner.GUI;
/**
 * AOQueryRunner.GUI.ConValueListener Class -    listener interface for the condition queryValue field
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public interface ConValueListener {

    /**
     * incorrectValue method,       sends a value as to whether or not the entered value within the particular
     *                              condition is valid.
     *
     * @param errorType             - integer value of whether the value is valid
     */
    void incorrectValue(int errorType);
}
