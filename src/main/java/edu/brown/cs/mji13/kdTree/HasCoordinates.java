package edu.brown.cs.mji13.kdTree;

import java.util.List;

/**
 * Interface that adds Coordinate Features to objects that implements it.
 */
public interface HasCoordinates {
  /**
   * Returns the dimension of the Coordinates.
   *
   * @return the number of dimensions the object has coordinate wise
   */
  int numDimensions();

  /**
   * Given an index d, returns the coordinate at that index.
   *
   * @param d - the index given
   * @return the coordinate at said index
   */
  double getCoordinate(int d);

  /**
   * Given another configuration of coordinates, find the distance between this
   * coordinate and that.
   *
   * @param cords - the coordinates given
   * @return the distance between the two coordinates.
   */
  double distanceTo(List<Double> cords);
}
