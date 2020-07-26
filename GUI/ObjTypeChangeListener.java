package AOQueryRunner.GUI;
/**
 * AOQueryRunner.GUI.ObjTypeChangeListener Class -    listener interface for the SearchBar objType field
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public interface ObjTypeChangeListener {

    /**
     * objChanged method,        listens for the objType being changed within the SearchBar. Sending the appropriate
     *                           object parameter list for field population.
     *
     * @param list               - the list being sent for field population
     */
    void objChanged(String[] list);
}
