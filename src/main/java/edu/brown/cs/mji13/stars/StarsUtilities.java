package edu.brown.cs.mji13.stars;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Interface for the Utilities Functions in the star package.
 */
public interface StarsUtilities {

  /**
   * Finds the Star with the name specified.
   *
   * @param name  the name of the star
   * @param stars the list of stars to seach through
   * @return Option of empty if not found, Option of the star if found
   */
  default Optional<Star> findStarWithName(String name, ArrayList<Star> stars) {
    for (Star eachStar : stars) {
      if (eachStar.getName().equals(name)) {
        return Optional.of(eachStar);
      }
    }
    return Optional.empty();
  }

  /**
   * Finds the Stars with the coordinate specified.
   *
   * @param x     the x coordinate of the star
   * @param y     the y coordinate of the star
   * @param z     the z coordinate of the star
   * @param stars the list of stars to seach through
   * @return The list of stars found (could be empty).
   */
  default ArrayList<Star> findStarsWithCord(double x, double y, double z, ArrayList<Star> stars) {
    ArrayList<Star> template = new ArrayList<Star>();
    for (Star eachStar : stars) {
      if ((x == eachStar.getX()) && (y == eachStar.getY()) && (z == eachStar.getZ())) {
        template.add(eachStar);
      }
    }
    return template;
  }

  /**
   * Makes a shallow copy of the ArrayList given and preserves type.
   *
   * @param <A> The genertic Type A of the ArrayList
   * @param lst The ArrayList given
   * @return A copy of the ArrayList
   */
  default <A> ArrayList<A> copyWithType(ArrayList<A> lst) {
    return new ArrayList<>(lst);
  }
}
