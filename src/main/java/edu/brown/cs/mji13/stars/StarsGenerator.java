package edu.brown.cs.mji13.stars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Stars Generator Object used to generate random test cases and handle Model Based
 * and Property Based Testing.
 */
public class StarsGenerator {
  /**
   * The Random Library below is the main mechanism of randomness behind this Generator Object.
   */
  private Random generator = new Random();

  /**
   * The fields for StarsGenerator.
   */
  private double min, range;
  private int maxNameLength, wordRange;

  /**
   * Constructs A StarsGenerator Object.
   *
   * @param min           - The Lowest Bound Value of Coordinates
   * @param range         - The range of coordinates
   * @param maxNameLength - the maximum length of a random name
   * @param wordRange     - the range of ASCILL Value to take from (usually 26 for alphabet)
   */
  public StarsGenerator(double min, double range, int maxNameLength, int wordRange) {
    this.min = min;
    this.range = range;
    this.maxNameLength = maxNameLength;
    this.wordRange = wordRange;
  }

  /**
   * Generates an ArrayList of randomly generated Stars of size n.
   *
   * @param n - The number of stars in the generated list.
   * @return  - A list of size n of randomly generated stars.
   */
  public ArrayList<Star> generateInput(int n) {
    if (n <= 0) {
      return new ArrayList<>();
    }

    ArrayList<Star> template = new ArrayList<>();
    Set<String> nameSet = new HashSet<String>();

    for (int i = 0; i < n; i++) {
      Star randStar = randomStar(i);
      if (nameSet.contains(randStar.getName())) {
        i--;
      } else {
        nameSet.add(randStar.getName());
        template.add(randStar);
      }
    }
    return template;
  }

  /**
   * Generates a random Star with a given id.
   *
   * @param id - The id of the star
   * @return  - A randomly generated star with that id
   */
  public Star randomStar(int id) {
    String starID = Integer.toString(id);
    String name = randomName();

    double xPos = min + (range) * generator.nextDouble();
    double yPos = min + (range) * generator.nextDouble();
    double zPos = min + (range) * generator.nextDouble();

    return new Star(starID, name, Arrays.asList(xPos, yPos, zPos));
  }

  /**
   * Generates a random name.
   *
   * @return  - A randomly generated name.
   */
  public String randomName() {
    String name = "";
    int nameLength = generator.nextInt(maxNameLength) + 1;

    for (int i = 0; i < nameLength; i++) {
      name += (char) (generator.nextInt(wordRange) + 'a');
    }
    return name;
  }

  /**
   * Determines if the two given stars lists both satisfy the same properties:
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The distances are in Ascending Order and below the radius
   * 3. All the stars selected in the lists are all in the original list.
   * @param radius - the radius of the search
   * @param originalList - the original list
   * @param cord - the coordinates of the search
   * @param starList1 - the first list generated
   * @param starList2 - the second list generated
   * @return  - A randomly generated name.
   */
  public Boolean areRadiusListsValid(
      double radius, ArrayList<Star> originalList, ArrayList<Double> cord,
      ArrayList<Star> starList1, ArrayList<Star> starList2) {

    List<Double> distList1 = starList1.stream()
        .map(star -> star.distanceTo(cord))
        .collect(Collectors.toList());
    List<Double> distList2 = starList2.stream()
        .map(star -> star.distanceTo(cord))
        .collect(Collectors.toList());

    return distList1.equals(distList2)
        && isListInRange(distList1, radius)
        && isListInRange(distList2, radius)
        && isAscending(distList1)
        && isAscending(distList2)
        && isSubList(starList1, originalList)
        && isSubList(starList2, originalList);

  }

  /**
   * Determines if the two given stars lists both satisfy the same properties:
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The size of both lists are the count specified
   * 3. Both lists are in ascending order of distances
   * 4. All the stars selected in the lists are all in the original list.
   * @param count - the count of the search
   * @param originalList - the original list
   * @param cord - the coordinates of the search
   * @param starList1 - the first list generated
   * @param starList2 - the second list generated
   * @return  - A randomly generated name.
   */
  public boolean areNeighborsListValid(
      int count, ArrayList<Star> originalList, ArrayList<Double> cord,
      ArrayList<Star> starList1, ArrayList<Star> starList2) {

    List<Double> distList1 = starList1.stream()
        .map(star -> star.distanceTo(cord))
        .collect(Collectors.toList());
    List<Double> distList2 = starList2.stream()
        .map(star -> star.distanceTo(cord))
        .collect(Collectors.toList());

    return (distList1.size() == count)
        && (distList2.size() == count)
        && distList1.equals(distList2)
        && isAscending(distList1)
        && isAscending(distList2)
        && isSubList(starList1, originalList)
        && isSubList(starList2, originalList);
  }

  /**
   * Checks if the first list is a subset of the second list.
   *
   * @param <T> - the generic type of the list
   * @param sublist - the sublist
   * @param list    - the main list
   * @return if the list is a sublist of the second or not.
   */
  public <T> boolean isSubList(List<T> sublist, List<T> list) {
    return list.containsAll(sublist);
  }

  /**
   * Checks if all values in the list of doubles or less than or equal to the bound radius.
   *
   * @param list   - the list
   * @param radius - the bound
   * @return if the list is a sublist of the second or not.
   */
  public boolean isListInRange(List<Double> list, double radius) {
    boolean result = true;

    for (double dist : list) {
      result = result && (Math.abs(dist) <= radius);
    }

    return result;
  }

  /**
   * Checks if the given list of doubles is in ascending order.
   *
   * @param list - the list of doubles given
   * @return if the list is ascending or not
   */
  public boolean isAscending(List<Double> list) {
    boolean result = true;

    for (int i = 0; i < list.size() - 1; i++) {
      double currentItem = list.get(i);
      double nextItem = list.get(i + 1);
      result = result && (currentItem <= nextItem);
    }

    return result;
  }
}
