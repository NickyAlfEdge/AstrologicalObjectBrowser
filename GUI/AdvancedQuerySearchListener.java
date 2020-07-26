package AOQueryRunner.GUI;
/**
 * AOQueryRunner.GUI.AdvancedQuerySearchListener Class -    listener interface for the advanced search feature
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public interface AdvancedQuerySearchListener {

    /**
     * advancedQuerySearch method,  sends a captured query retrieved from the advanced search panel and it's objtype
     *                              which is then sent to the MainFrame for subsequent initialisation.
     *
     * @param query             - the entered query
     * @param objType           - the object type of the query
     */
    void advancedQuerySearch(String query, String objType);
}
