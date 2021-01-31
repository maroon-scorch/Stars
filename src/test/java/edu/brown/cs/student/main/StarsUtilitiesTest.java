package edu.brown.cs.student.main;

import edu.brown.cs.student.stars.Star;
import edu.brown.cs.student.stars.StarsUtilities;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class StarsUtilitiesTest implements StarsUtilities {

  private ArrayList<Star> starsList;

  /**
   * Because StarsUtilities is an interface, testing using an Object Class that implements it.
   * Sets up the the Constructor and the list of stars.
   */
  @Before
  public void setUp() {
    starsList = new ArrayList<Star>();
    Star[] starArray = {new Star("1", "1", 0, 0, 0), new Star("2", "2", 10, 6, 5),
        new Star("3", "3", 3.1, 1.5, -6.7), new Star("4", "4", -5, -6, -7),
        new Star("5", "5", -10.56, -13, 12), new Star("6", "6", 0.5, 0, 0),
        new Star("7", "7", -50, -50, -50), new Star("8", "8", 5, 6, 7),
        new Star("9", "9", 3, 4, 5), new Star("10", "10", 10, 20, 3)};
    starsList.addAll(Arrays.asList(starArray));
  }

  /**
   * Resets the Naive Radius and the list of stars.
   */
  @After
  public void tearDown() {
    starsList.clear();
  }

  /**
   ** Tests the findStarWithName Methods
   */
  @Test
  public void testFindStarsWithName() {
    // Stars is found
    setUp();
    Optional<Star> starFound = findStarWithName("5", starsList);
    assertEquals(starFound.get().getStarID(), "5");
    assertEquals(starFound.get().getX(), -10.56, 0.01);
    assertEquals(starFound.get().getY(), -13, 0.01);
    assertEquals(starFound.get().getZ(), 12, 0.01);

    // Star Not Found
    Optional<Star> starNotFound = findStarWithName("SOS", starsList);
    assertTrue(starNotFound.isEmpty());

    // Empty StarsList
    Optional<Star> starEmpty = findStarWithName("SOS", new ArrayList<Star>());
    assertTrue(starEmpty.isEmpty());
    tearDown();
  }

  /**
   ** Tests the findStarsWithCord Method
   */
  @Test
  public void testfindStarsWithCord() {
    setUp();
    // Stars is found
    ArrayList<Star> starFound = findStarsWithCord(-10.56, -13, 12, starsList);
    assertEquals(starFound.size(), 1);
    assertEquals(starFound.get(0).getStarID(), "5");
    assertEquals(starFound.get(0).getX(), -10.56, 0.01);
    assertEquals(starFound.get(0).getY(), -13, 0.01);
    assertEquals(starFound.get(0).getZ(), 12, 0.01);

    // No Stars found
    ArrayList<Star> starsNotFound = findStarsWithCord(0, 0, 567, starsList);
    assertEquals(starsNotFound.size(), 0);
    tearDown();
  }

  /**
   ** Tests the findStarsWithCord Method: Multiple Stars Found
   */
  @Test
  public void testfindStarsWithCordMulti() {
    setUp();
    // Stars are found
    starsList.add(new Star("Zero", "Zero", 0, 0, 0));
    ArrayList<Star> starsFound = findStarsWithCord(0, 0, 0, starsList);

    assertEquals(starsFound.size(), 2);
    assertEquals(starsFound.get(0).getStarID(), "1");
    assertEquals(starsFound.get(1).getStarID(), "Zero");
    tearDown();
  }

  /**
   ** Tests the copyWithType Method: Multiple Stars Found
   */
  @Test
  public void testCopyWithType() {
    setUp();
    // Sample List
    ArrayList<Star> sampleList = copyWithType(starsList);
    assertTrue(starsList.equals(sampleList));

    // Empty List
    ArrayList<Integer> mtList = copyWithType(new ArrayList<Integer>());
    assertEquals(mtList.size(), 0);
    tearDown();
  }
}