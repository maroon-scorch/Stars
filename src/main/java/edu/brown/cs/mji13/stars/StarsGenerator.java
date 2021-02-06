package edu.brown.cs.mji13.stars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The Stars Generator Object used to generate random test cases.
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
}
