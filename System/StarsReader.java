package AOQueryRunner.System;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * AOQueryRunner.System.StarsReader Class    - reads Star File, creating appropriate Star Objects and providing access
 *                                             to the various Star Object attributes within said list.
 *
 * @author Nicky Edge
 * @version 05/04/2020
 */
public class StarsReader {

    // instance variables
    private ArrayList<Stars> starsList;

    /**
     * StarsReader Object Constructor
     *
     * @param starFileName    - filename to pass for creation of Stars Objects
     */
    public StarsReader(String starFileName) throws NumberFormatException, ArrayIndexOutOfBoundsException, FileNotFoundException {

        starsList = readStarsFile(starFileName);
    }

    public ArrayList<Stars> getStarsList() {
        return starsList;
    }

    /**
     * readStarsFile method
     *
     * @param fileName - the file to read in
     * @return starsList - ArrayList<Stars>, containing Stars Objects
     */
    private ArrayList<Stars> readStarsFile(String fileName) throws NumberFormatException, ArrayIndexOutOfBoundsException, FileNotFoundException {

        ArrayList<Stars> starsList = new ArrayList<>();

        Scanner sc = new Scanner(new File(fileName));
        // read each line, splitting with each occurrence of "|"
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                String[] words = line.split("\\|");
                // manual checking of field count, as the array size is handled by .split method
                if (words.length > 7) {
                    continue;
                }
                // parse each field into proper format
                String name = words[0].trim();
                double ra = Double.parseDouble(words[1].trim());
                double declination = Double.parseDouble(words[2].trim());
                double magnitude = Double.parseDouble(words[3].trim());
                double distance = Double.parseDouble(words[4].trim());
                String type = words[5].trim();
                String constellation = words[6].trim();
                // create new Star object with gathered data
                Stars star = new Stars(name, ra, declination, magnitude, distance, type, constellation);
                // check for duplicate entries within the arraylist, if no duplicates then add to list
                // else the duplicate object is flagged and abandoned
                if (!starsList.contains(star)) {
                    starsList.add(star);
                }
            }
        }
        // close scanner
        sc.close();
        // return completed list
        return starsList;
    }
}
