package AOQueryRunner.System;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * AOQueryRunner.System.MessiersReader Class -  reads Messier File, creating appropriate Messier Objects and providing access
 *                         to the various Messier Object attributes within said list.
 *
 * @author Nicky Edge
 * @version 05/04/2020
 */
public class MessiersReader {

    // instance variables
    private ArrayList<Messiers> messierList;

    /**
     * MessiersReader Object Constructor
     *
     * @param messierFileName    - filename to pass for creation of AOQueryRunner.System.Stars Objects
     */
    public MessiersReader(String messierFileName) throws NumberFormatException, ArrayIndexOutOfBoundsException, FileNotFoundException  {

        messierList = readMessierFile(messierFileName);
    }

    public ArrayList<Messiers> getMessiersList() {
        return messierList;
    }

    /**
     * readMessierFile method
     *
     * @param fileName - the file to read in
     * @return messierList - ArrayList<Messier>, containing Messier Objects
     */
    private ArrayList<Messiers> readMessierFile(String fileName) throws NumberFormatException, ArrayIndexOutOfBoundsException, FileNotFoundException  {

        ArrayList<Messiers> messierList = new ArrayList<>();

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
                String constellation = words[5].trim();
                String description = "";
                // if a description is read in, description equals this data
                if (words.length == 7) {
                    description = words[6].trim();
                }
                // create new Messier object with gathered data
                Messiers messier = new Messiers(name, ra, declination, magnitude, distance, constellation, description);
                // check for duplicate entries within the arraylist, if no duplicates then add to list
                // else the duplicate object is flagged and abandoned
                if (!messierList.contains(messier)) {
                    messierList.add(messier);
                }
            }
        }
        // close scanner
        sc.close();
        // return completed list
        return messierList;
    }
}
