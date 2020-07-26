package AOQueryRunner.GUI;

import AOQueryRunner.System.MessiersReader;
/**
 * AOQueryRunner.GUI.MessiersListListener Class -    listener interface for the messierList being added
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public interface MessiersListListener {

    /**
     * messiersListAdded method,       sends the messierList for further processing if it is valid.
     *
     * @param messiers                 - MessierReader object containing a validated data ArrayList<>
     */
    void messiersListAdded(MessiersReader messiers);
}