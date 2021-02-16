package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.kdTree.HasCoordinates;
import edu.brown.cs.mji13.validations.StringValFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * The Stars Object that stores the information from the CSV File.
 */
public class Star implements StringValFunctions, HasCoordinates {
  /**
   * The list of fields Stars stores:
   * starID, star name, ArrayList of coordinates.
   */
  private String starID, starName;
  private ArrayList<Double> coordinates;

  /**
   * Creates a Star Object with the String Parameters Given.
   *
   * @param starID      id of Star
   * @param starName    name of Star
   * @param coordinates  the arbitrary list of coordinates given
   */
  public Star(String starID, String starName, List<Double> coordinates) {
    if (coordinates.size() < 1) {
      throw new IllegalArgumentException("Stars cannot have a dimension less than 1");
    }
    this.starID = starID;
    this.starName = starName;
    this.coordinates = new ArrayList<>(coordinates);
  }


  /**
   * Returns the dimension of the Coordinates in Stars.
   *
   * @return the Star's dimension (size)
   */
  public int numDimensions() {
    return coordinates.size();
  }

  /**
   * Returns the coordinate at the index of the Coordinates.
   *
   * @param d - the index of the coordinate
   * @return the value at said index if index given is not out of bounds.
   */
  public double getCoordinate(int d) {
    if (d < 0) {
      throw new IndexOutOfBoundsException("Can't Retrieve a Coordinate with Index below Zero");
    }

    if (d >= coordinates.size()) {
      throw new IndexOutOfBoundsException("Can't Retrieve a Coordinate with "
          + "Index above its Dimension");
    }
    return coordinates.get(d);
  }

  /**
   * Returns the entire coordinate list.
   *
   * @return the arrayList of doubles.
   */
  public ArrayList<Double> getCoordinates() {
    return coordinates;
  }

  /**
   * Gets the distance of the star to a coordinate given.
   *
   * @param cords - the Coordinate given
   * @return the distance if the coordinates given and the star's coordinate have the same
   * dimension (share same size).
   */
  public double distanceTo(List<Double> cords) {
    if (cords.size() != coordinates.size()) {
      throw new IllegalArgumentException("Cannot find distance between two coordinates"
          + "because of inconsistent dimensions.");
    }

    double sum = 0;
    for (int i = 0; i < cords.size(); i++) {
      double diff = coordinates.get(i) - cords.get(i);
      sum += Math.pow(diff, 2);
    }

    return Math.sqrt(sum);
  }

  /**
   * Gets the name of the star.
   *
   * @return name
   */
  public String getName() {
    return starName;
  }

  /**
   * Gets the id of the star.
   *
   * @return name
   */
  public String getStarID() {
    return starID;
  }

  /**
   * Enables the star object to be printable.
   *
   * @return the String Literal of the star (which is its id)
   */
  public String toString() {
    return starID;
  }

  /**
   * Enables the star object to be printable.
   *
   * @return the HTML Literal of the Star, which is a row of its information.
   */
  public String toHTML() {
    return "<tr>" + "<td>" + starID + "</td>" + "<td>" + starName + "</td>"
        + "<td>" + coordinates.toString() + "</td>" + "</tr>";
  }

}
