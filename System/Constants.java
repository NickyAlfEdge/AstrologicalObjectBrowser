package AOQueryRunner.System;

import java.util.Arrays;
import java.util.List;
/**
 * AOQueryRunner.System.Constants Class - stores class constants used within program
 *
 * @author Nicky Edge
 * @version 05/04/2020
 */
public final class Constants  {

    // private static variables
    private static final String[] numericVar = {"ra", "decl", "magn", "distance", "albedo"};
    private static final String[] nonNumericVar = {"number", "name", "type", "constellation", "description"};
    private static final String[] operatorVar = {">", "<", ">=", "<=", "=", "!="};
    private static final String[] descriptionVar = {"number", "ra", "decl", "magn", "distance", "constellation", "description",
                                                    ">", "<", ">=", "<=", "=", "!="};

    // public static variables
    public static final List<String> numericVarList = Arrays.asList(numericVar);
    public static final List<String> nonNumericVarList = Arrays.asList(nonNumericVar);
    public static final List<String> operatorList = Arrays.asList(operatorVar);
    public static final List<String> descriptionList = Arrays.asList(descriptionVar);
    public static final String[] starTblHead = {"Number", "Right Ascension", "Declination", "Magnitude", "Distance", "Type", "Constellation"};
    public static final String[] messierTblHead = {"Number", "Right Ascension", "Declination", "Magnitude", "Distance", "Constellation", "Description"};
    public static final String[] planetTblHead = {"Name", "Right Ascension", "Declination", "Magnitude", "Distance", "Albedo"};
    public static final String[] starGuiVar = {"", "number", "ra", "decl", "magn", "distance", "type", "constellation"};
    public static final String[] planeGuiVar = {"", "name", "ra", "decl", "magn", "distance", "albedo"};
    public static final String[] messierGuiVar = {"", "number", "ra", "decl", "magn", "distance", "constellation", "description"};
    public static final String[] operatorGuiVar = {"", ">", "<", ">=", "<=", "=", "!="};
    public static final String[] objTypes = {"Stars", "Messiers", "Planets"};

    /**
     * The caller references the constants using AOQueryRunner.System.Constants.'varName'
     * and so on. Thus, the caller should be prevented from constructing objects of
     * this class, by declaring this private constructor.
     */
    private Constants(){
        // this prevents even the native class from calling this constructor as well
        throw new AssertionError();
    }
}