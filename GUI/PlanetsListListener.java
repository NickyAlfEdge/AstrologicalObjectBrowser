package AOQueryRunner.GUI;

import AOQueryRunner.System.PlanetsReader;
/**
 * AOQueryRunner.GUI.PlanetsListListener Class -    listener interface for the planetList being added
 *
 * @author Nicky Edge
 * @version 05/05/2020
 */
public interface PlanetsListListener {

    /**
     * planetsListAdded method,       sends the planetsList for further processing if it is valid.
     *
     * @param planets                 - PlanetsReader object containing a validated data ArrayList<>
     */
    void planetsListAdded(PlanetsReader planets);
}
