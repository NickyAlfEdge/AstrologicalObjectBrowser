package AOQueryRunner.GUI;
/**
 * AOQueryRunner.GUI.StdQuerySearchListener Class -    listener interface for the standard search feature
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public interface StdQuerySearchListener {

    /**
     * standardQuerySearch method,  sends a captured query retrieved from the standard search panel and it's objtype
     *                              which is then sent for subsequent initialisation.
     *
     * @param query             - the entered query
     * @param objType           - the object type of the query
     */
    void standardQuerySearch(String query, String objType);
}