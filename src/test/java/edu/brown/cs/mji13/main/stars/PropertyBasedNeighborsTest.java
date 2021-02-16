package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.kdTree.KDNode;
import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.stars.NaiveNeighbors;
import edu.brown.cs.mji13.stars.Neighbors;
import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.StarStorage;
import edu.brown.cs.mji13.stars.StarsGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.*;

public class PropertyBasedNeighborsTest {
  private ArrayList<Star> testStars;
  private KDTree<Star> starTree;
  private Set<String> names;
  private NaiveNeighbors naive_neighbors;
  private Neighbors neighbors;
  private StarStorage starStorage;
  private StarsGenerator generator;

  /**
   * Sets up the generator to be passed into the two Commands
   */
  @Before
  public void setUp() {
    generator = new StarsGenerator(-100, 200, 5, 26);
    testStars = generator.generateInput(100);

    starStorage = new StarStorage("test");
    starStorage.setList(testStars);
    starStorage.setListToTree(testStars);
    starStorage.setListToStarsMap(testStars);

    starTree = starStorage.getStarsTree();
    names = starStorage.getStarNames();

    naive_neighbors  = new NaiveNeighbors(starStorage);
    neighbors = new Neighbors(starStorage);
  }

  /**
   * Resets the Configuration.
   */
  @After
  public void tearDown() {
    testStars.clear();
    naive_neighbors  = null;
    neighbors = null;
  }

  /**
   * Testing for Coordinate Implementation of Neighbors and Naive_Neighbors:
   * Check if the two lists outputted both satisfy the same properties:
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The size of both lists are the count specified
   * 3. Both lists are in ascending order of distances
   * 4. All the stars selected in the lists are all in the original list.
   */
  @Test
  public void testAtScaleForCords() {
    setUp();
    boolean isEqual = true;
    Random rand = new Random();

    for (int i = 0; i < 50; i ++) {
      int ct = 1 + rand.nextInt(50);
      double xPos = -100 + (200) * rand.nextDouble();
      double yPos = -100 + (200) * rand.nextDouble();
      double zPos = -100 + (200) * rand.nextDouble();

      ArrayList<Star> nnResult
          = naive_neighbors.performNaiveNeighbors(ct, xPos, yPos, zPos, testStars);
      ArrayList<Star> nResult
          = neighbors.performNeighbors(ct, xPos, yPos, zPos, starTree, Optional.empty());

      ArrayList<Double> cords = new ArrayList<>();
      cords.add(xPos);
      cords.add(yPos);
      cords.add(zPos);

      isEqual = isEqual
          && (generator.areNeighborsListValid(ct, testStars, cords, nnResult, nResult));
    }

    assertTrue(isEqual);
    tearDown();
  }

  /**
   * Testing for Name Implementation of Neighbors and Naive_Neighbors:
   * Check if the two lists outputted both satisfy the same properties:
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The size of both lists are the count specified
   * 3. Both lists are in ascending order of distances
   * 4. All the stars selected in the lists are all in the original list.
   * 5. The name specified isn't in the list of stars
   */
  @Test
  public void testAtScaleForNames() {
    setUp();
    boolean isEqual = true;
    Random rand = new Random();

    for (String target : names) {
      int ct = 1 + rand.nextInt(50);
      ArrayList<Star> nnResult
          = naive_neighbors.performNaiveNeighbors(ct, target, testStars);
      ArrayList<Star> nResult
          = neighbors.performNeighbors(ct, target, starTree);

      Star presentStar = starStorage.getStarFromMap(target);

      // Checking if the String is in the name outputted
      List<String> strList1 = nnResult.stream()
          .map(Star::getName)
          .collect(Collectors.toList());
      List<String> strList2 = nResult.stream()
          .map(Star::getName)
          .collect(Collectors.toList());

      isEqual = isEqual
          && (generator.areNeighborsListValid(ct, testStars,
          presentStar.getCoordinates(), nnResult, nResult) && !strList1.contains(target)
      && !strList2.contains(target));
    }

    assertTrue(isEqual);
    tearDown();
  }

}
