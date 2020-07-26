package AOQueryRunner.System;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * AOQueryRunner.System.PlanetsReader Class  - reads Planet File, creating appropriate Planet Objects and providing access
 *                        to the various Planet Object attributes within said list.
 *
 * @author Nicky Edge
 * @version 05/04/2020
 */
public class PlanetsReader {

    // instance variables
    private ArrayList<Planets> planetsList;

    /**
     * PlanetsReader Object Constructor
     *
     * @param planetFileName    - filename to pass for creation of Planets Objects
     */
    public PlanetsReader(String planetFileName) throws NumberFormatException, ArrayIndexOutOfBoundsException, FileNotFoundException  {

        planetsList = readPlanetsFile(planetFileName);
    }

    public ArrayList<Planets> getPlanetsList() {
        return planetsList;
    }

    /**
     * readPlanetsFile method
     *
     * @param fileName - the file to read in
     * @return planetsList - ArrayList<Planets>, containing Planets Objects
     */
    private ArrayList<Planets> readPlanetsFile(String fileName) throws NumberFormatException, ArrayIndexOutOfBoundsException, FileNotFoundException  {

        ArrayList<Planets> planetsList = new ArrayList<>();

        Scanner sc = new Scanner(new File(fileName));
        // read each line, splitting with each occurrence of empty space
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                // "\\s+" means reluctant multi-space
                String[] words = line.split("\\s+");
                // manual checking of field count, as the array size is handled by .split method
                if (words.length > 6) {
                    continue;
                }
                // parse each field into proper format
                String name = words[0].trim();
                double ra = Double.parseDouble(words[1]);
                double declination = Double.parseDouble(words[2]);
                double magnitude = Double.parseDouble(words[3]);
                double distance = Double.parseDouble(words[4]);
                double albedo = Double.parseDouble(words[5]);
                // create new AOQueryRunner.System.Planets object with gathered data
                Planets planet = new Planets(name, ra, declination, magnitude, distance, albedo);
                // check for duplicate entries within the arraylist, if no duplicates then add to list
                // else the duplicate object is flagged and abandoned
                if (!planetsList.contains(planet)) {
                    planetsList.add(planet);
                }
            }
        }
        // close scanner
        sc.close();
        // return completed list
        return planetsList;
    }
}
