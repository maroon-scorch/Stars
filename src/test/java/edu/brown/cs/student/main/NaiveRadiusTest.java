package edu.brown.cs.student.main;

import edu.brown.cs.student.stars.Star;
import edu.brown.cs.student.stars.NaiveRadius;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class NaiveRadiusTest {

  private NaiveRadius naive_radius;
  private ArrayList<Star> starsList;

  /**
   * Sets up the Naive Radius and the list of stars
   */
  @Before
  public void setUp() {
    naive_radius  = new NaiveRadius();
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
    naive_radius = null;
    starsList.clear();
  }

  /**
   ** performNaiveRadius (name): normal test
   */
  @Test
  public void testNRNameNormal() {
    setUp();
    ArrayList<Star> result = naive_radius.performNaiveRadius(10, "1", starsList);
    assertEquals(result.size(), 3);
    assertEquals(result.get(0).getStarID(), "6");
    assertEquals(result.get(1).getStarID(), "9");
    assertEquals(result.get(2).getStarID(), "3");
    tearDown();
  }

  /**
   ** performNaiveRadius (name): checks for radius of zero
   */
  @Test
  public void testNRNameZero() {
    setUp();
    // Checking for Radius of Zero
    ArrayList<Star> result1 = naive_radius.performNaiveRadius(0, "7", starsList);
    assertEquals(result1.size(), 0);

    // Adding a duplicate star to "7"
    starsList.add(new Star("Duplicate 7", "Dup 7", -50, -50, -50));
    starsList.add(new Star("Duplicate 7 Again", "Dup 7 again", -50, -50, -50));

    ArrayList<Star> result2 = naive_radius.performNaiveRadius(0, "7", starsList);
    assertEquals(result2.size(), 2);
    assertEquals(result2.get(0).getStarID(), "Duplicate 7");
    assertEquals(result2.get(1).getStarID(), "Duplicate 7 Again");

    tearDown();
  }

  /**
   ** performNaiveRadius (Coordinate): normal test
   */
  @Test
  public void testNRCoordinateNormal() {
    setUp();
    ArrayList<Star> result = naive_radius.performNaiveRadius(10, 10, 10, 10, starsList);
    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "2");
    assertEquals(result.get(1).getStarID(), "8");
    tearDown();
  }

  /**
   ** performNaiveRadius (Coordinate): Coordinate is exactly on a star
   */
  @Test
  public void testNRCoordinateExact() {
    setUp();
    ArrayList<Star> result = naive_radius.performNaiveRadius(3, 0, 0, 0, starsList);
    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "1");
    assertEquals(result.get(1).getStarID(), "6");

    ArrayList<Star> resultRadiusZero = naive_radius.performNaiveRadius(0, 0, 0, 0, starsList);
    assertEquals(resultRadiusZero.size(), 1);
    assertEquals(resultRadiusZero.get(0).getStarID(), "1");
    tearDown();
  }

  /**
   ** performNaiveRadius (Coordinate): No Stars Are Found
   */
  @Test
  public void testNRCoordinateNoStars() {
    setUp();
    ArrayList<Star> result = naive_radius.performNaiveRadius(3, 100, 100, 100, starsList);
    assertEquals(result.size(), 0);
    tearDown();
  }

  /**
   ** performNaiveRadius (Coordinate): List of Stars is empty
   */
  @Test
  public void testNREmpty() {
    ArrayList<Star> result = naive_radius.performNaiveRadius(3, 0, 0, 0, new ArrayList<Star>());
    assertEquals(result.size(), 0);
  }

  /**
   ** Tests the matchArgsToMethod Methods of NaiveRadius
   */
  @Test
  public void testMatchArgs() {
    setUp();

    ArrayList<String> twoLst = new ArrayList<>();
    twoLst.add("32");
    twoLst.add("\"name\"");
    assertEquals(naive_radius.matchArgsToMethod(twoLst).get(), "naive_radius_2");

    ArrayList<String> fourLst = new ArrayList<>();
    fourLst.add("32");
    fourLst.add("-5.2");
    fourLst.add("-0.75");
    fourLst.add("10.2");
    assertEquals(naive_radius.matchArgsToMethod(fourLst).get(), "naive_radius_4");

    tearDown();
  }

}