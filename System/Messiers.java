package AOQueryRunner.System;

import java.util.Objects;
/**
 * Messier Class -  Child class of AOQueryRunner.System.AO
 *
 * @author Nicky Edge
 * @version 05/03/2020
 */
public class Messiers extends AO {

    // instance variables
    private String constellation;
    private String description;

    // constructor
    public Messiers(String name, double ra, double declination, double magnitude, double distance, String constellation, String description) {
        super(name, ra, declination, magnitude, distance);
        this.constellation = constellation;
        this.description = description;
    }

    // accessors and mutator methods
    public String getConstellation() {
        return this.constellation;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * generated equals method override, used for .contains method
     *
     * @param o - Messier class object
     * @return - equivalence of Messier class object properties
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messiers messiers = (Messiers) o;
        return Objects.equals(super.getName(), messiers.getName()) &&
                Objects.equals(super.getRa(), messiers.getRa()) &&
                Objects.equals(super.getDeclination(), messiers.getDeclination()) &&
                Objects.equals(super.getMagnitude(), messiers.getMagnitude()) &&
                Objects.equals(super.getDistance(), messiers.getDistance()) &&
                Objects.equals(constellation, messiers.constellation) &&
                Objects.equals(description, messiers.description);
    }

    /**
     * generated hashCode method override, used for .contains method
     *
     * @return - hash value of Messier class object properties
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.getName(), super.getRa(), super.getDeclination(), super.getMagnitude(), super.getDistance(), constellation, description);
    }
}
