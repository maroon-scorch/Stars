package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.kdTree.KDNode;
import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.NaiveRadius;
import edu.brown.cs.mji13.stars.Radius;
import edu.brown.cs.mji13.stars.StarStorage;
import edu.brown.cs.mji13.stars.StarsGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.*;

public class PropertyBasedRadiusTest {
  private ArrayList<Star> testStars;
  private KDTree<Star> starTree;
  private Set<String> names;
  private NaiveRadius naive_radius;
  private Radius radius;
  private StarsGenerator generator;
  private StarStorage starStorage;
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

    naive_radius  = new NaiveRadius(starStorage);
    radius = new Radius(starStorage);
  }

  /**
   * Resets the Configuration.
   */
  @After
  public void tearDown() {
    testStars.clear();
    generator = null;
    naive_radius = null;
    radius = null;
  }


  /**
   * Testing for Coordinate Implementation of Radius
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The distances are in Ascending Order and below the radius
   * 3. All the stars selected in the lists are all in the original list.
   */
  @Test
  public void testAtScaleForCords() {
    setUp();
    boolean isEqual = true;
    Random rand = new Random();

    // Checking if they are equivalent 50 times
    for (int i = 0; i < 50; i ++) {
      double r = 1 + (50) * rand.nextDouble();
      double xPos = -100 + (200) * rand.nextDouble();
      double yPos = -100 + (200) * rand.nextDouble();
      double zPos = -100 + (200) * rand.nextDouble();

      ArrayList<Double> cords = new ArrayList<>();
      cords.add(xPos);
      cords.add(yPos);
      cords.add(zPos);

      ArrayList<Star> nrResult
          = naive_radius.performNaiveRadius(r, xPos, yPos, zPos, testStars);
      ArrayList<Star> rResult
          = radius.performRadius(r, xPos, yPos, zPos, starTree);

      assertTrue(nrResult.equals(rResult));
      isEqual = isEqual && generator.areRadiusListsValid(r, testStars, cords, nrResult, rResult);
    }

    assertTrue(isEqual);
    tearDown();
  }

  /**
   * Testing for Names Implementation of Radius
   * 1. That all stars when converted to the distance from the coordiante specified,
   * is the same
   * 2. The distances are in Ascending Order and below the radius
   * 3. All the stars selected in the lists are all in the original list.
   * 4. Name of the target is not in the list.
   */
  @Test
  public void testAtScaleForNames() {
    setUp();
    boolean isEqual = true;
    Random rand = new Random();

    // Repeat this for all names generated
    for (String target : names) {
      double r = 1 + (50) * rand.nextDouble();
      ArrayList<Star> nrResult
          = naive_radius.performNaiveRadius(r, target, testStars);
      ArrayList<Star> rResult
          = radius.performRadius(r, target, starTree);

      assertTrue(nrResult.equals(rResult));

      // Checking if the String is in the name outputted
      List<String> strList1 = nrResult.stream()
          .map(Star::getName)
          .collect(Collectors.toList());
      List<String> strList2 = rResult.stream()
          .map(Star::getName)
          .collect(Collectors.toList());

      ArrayList<Double> cords = starStorage.getStarFromMap(target).getCoordinates();
      isEqual = isEqual && generator.areRadiusListsValid(r, testStars, cords, nrResult, rResult)
          && !strList1.contains(target)
          && !strList2.contains(target);
    }

    assertTrue(isEqual);
    tearDown();
  }

}
