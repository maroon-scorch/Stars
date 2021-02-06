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

public class ModelRadiusTest {
  private ArrayList<Star> testStars;
  private KDTree<Star> starTree;
  private Set<String> names;
  private NaiveRadius naive_radius;
  private Radius radius;
  /**
   * Sets up the generator to be passed into the two Commands
   */
  @Before
  public void setUp() {
    StarsGenerator generator = new StarsGenerator(-100, 200, 5, 26);
    testStars = generator.generateInput(100);

    StarStorage starStorage = new StarStorage("test");
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
    naive_radius = null;
    radius = null;
  }


  /**
   * Testing for Coordinate Implementation of Radius
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

      ArrayList<Star> nrResult
          = naive_radius.performNaiveRadius(r, xPos, yPos, zPos, testStars);
      ArrayList<Star> rResult
          = radius.performRadius(r, xPos, yPos, zPos, starTree);

      isEqual = isEqual && (nrResult.equals(rResult));
    }

    assertTrue(isEqual);
    tearDown();
  }

  /**
   * Testing for Names Implementation of Radius
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

      isEqual = isEqual && (nrResult.equals(rResult));
    }

    assertTrue(isEqual);
    tearDown();
  }

}
