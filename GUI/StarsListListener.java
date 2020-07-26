package AOQueryRunner.GUI;

import AOQueryRunner.System.StarsReader;
/**
 * AOQueryRunner.GUI.StarsListListener Class -    listener interface for the starsList being added
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public interface StarsListListener {

    /**
     * starsListAdded method,       sends the starsList for further processing if it is valid.
     *
     * @param stars                 - StarsReader object containing a validated data ArrayList<>
     */
    void starsListAdded(StarsReader stars);
}
