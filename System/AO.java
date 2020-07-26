package AOQueryRunner.System;
/**
 * AOQueryRunner.System.AO Class -  Parent class of AOQueryRunner.System.Stars, AOQueryRunner.System.Planets and Messier.
 *
 * @author Nicky Edge
 * @version 05/03/2020
 */
public class AO {

    // instance variables
    private String name;
    private double ra;
    private double declination;
    private double magnitude;
    private double distance;

    // constructor
    public AO(String name, double ra, double declination, double magnitude, double distance) {
        this.name = name;
        this.ra = ra;
        this.declination = declination;
        this.magnitude = magnitude;
        this.distance = distance;
    }

    // accessors and mutator methods
    public String getName() {
        return name;
    }

    public double getRa() {
        return this.ra;
    }

    public double getDeclination() {
        return this.declination;
    }

    public double getMagnitude() {
        return this.magnitude;
    }

    public double getDistance() {
        return this.distance;
    }
}
