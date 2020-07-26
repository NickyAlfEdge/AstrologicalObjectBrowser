package AOQueryRunner.System;

import java.util.Objects;
/**
 * AOQueryRunner.System.Stars Class - Child class of AOQueryRunner.System.AO
 *
 * @author Nicky Edge
 * @version 05/03/2020
 */
public class Stars extends AO {

    // instance variables
    private String type;
    private String constellation;

    // constructor
    public Stars(String name, double ra, double declination, double magnitude, double distance, String type, String constellation) {
        super(name, ra, declination, magnitude, distance);
        this.type = type;
        this.constellation = constellation;
    }

    // accessors methods
    public String getType() {
        return this.type;
    }

    public String getConstellation() {
        return this.constellation;
    }

    /**
     * generated equals method override, used for .contains method
     *
     * @param o - AOQueryRunner.System.Stars class object
     * @return - equivalence of AOQueryRunner.System.Stars class object properties
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stars stars = (Stars) o;
        return Objects.equals(super.getName(), stars.getName()) &&
                Objects.equals(super.getRa(), stars.getRa()) &&
                Objects.equals(super.getDeclination(), stars.getDeclination()) &&
                Objects.equals(super.getMagnitude(), stars.getMagnitude()) &&
                Objects.equals(super.getDistance(), stars.getDistance()) &&
                Objects.equals(type, stars.type) &&
                Objects.equals(constellation, stars.constellation);
    }

    /**
     * generated hashCode method override, used for .contains method
     *
     * @return - hash value of AOQueryRunner.System.Stars class object properties
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.getName(), super.getRa(), super.getDeclination(), super.getMagnitude(), super.getDistance(), type, constellation);
    }
}