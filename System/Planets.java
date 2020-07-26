package AOQueryRunner.System;

import java.util.Objects;
/**
 * AOQueryRunner.System.Planets Class -  Child class of AOQueryRunner.System.AO
 *
 * @author Nicky Edge
 * @version 05/03/2020
 */
public class Planets extends AO {

    // instance variables
    private double albedo;

    // constructor
    public Planets(String name, double ra, double declination, double magnitude, double distance, double albedo) {
        super(name, ra, declination, magnitude, distance);
        this.albedo = albedo;
    }

    // accessors and mutator methods
    public double getAlbedo() {
        return this.albedo;
    }

    /**
     * generated equals method override, used for .contains method
     *
     * @param o - AOQueryRunner.System.Planets class object
     * @return - equivalence of AOQueryRunner.System.Planets class object properties
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planets planets = (Planets) o;
        return Objects.equals(super.getName(), planets.getName()) &&
                Objects.equals(super.getRa(), planets.getRa()) &&
                Objects.equals(super.getDeclination(), planets.getDeclination()) &&
                Objects.equals(super.getMagnitude(), planets.getMagnitude()) &&
                Objects.equals(super.getDistance(), planets.getDistance()) &&
                Objects.equals(planets.albedo, albedo);
    }

    /**
     * generated hashCode method override, used for .contains method
     *
     * @return - hash value of Planets class object properties
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.getName(), super.getRa(), super.getDeclination(), super.getMagnitude(), super.getDistance(), albedo);
    }
}
