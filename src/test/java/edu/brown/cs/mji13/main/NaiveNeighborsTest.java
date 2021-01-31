package edu.brown.cs.mji13.main;

import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.NaiveNeighbors;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class NaiveNeighborsTest {

  private NaiveNeighbors naive_neighbors;
  private ArrayList<Star> starsList;

  /**
   * Sets up the NaiveNeighbors and the list of stars
   */
  @Before
  public void setUp() {
    naive_neighbors  = new NaiveNeighbors();
    starsList = new ArrayList<Star>();
    Star[] starArray = {new Star("1", "1", 0, 0, 0), new Star("2", "2", 10, 6, 5),
        new Star("3", "3", 3.1, 1.5, -6.7), new Star("4", "4", -5, -6, -7),
        new Star("5", "5", -10.56, -13, 12), new Star("6", "6", 0.5, 0, 0),
        new Star("7", "7", -50, -50, -50), new Star("8", "8", 5, 6, 7),
        new Star("9", "9", 3, 4, 5), new Star("10", "10", 10, 20, 3)};
    starsList.addAll(Arrays.asList(starArray));
  }

  /**
   * Resets the NaiveNeighbors and the list of stars.
   */
  @After
  public void tearDown() {
    naive_neighbors = null;
    starsList.clear();
  }

  /**
   ** performNaiveNeighbors (name): normal test
   */
  @Test
  public void testNNNameNormal() {
    setUp();
    ArrayList<Star> result = naive_neighbors.performNaiveNeighbors(3, "1", starsList);
    assertEquals(result.size(), 3);

    assertEquals(result.get(0).getStarID(), "6");
    assertEquals(result.get(1).getStarID(), "9");
    assertEquals(result.get(2).getStarID(), "3");
    tearDown();
  }

  /**
   ** performNaiveNeighbors (name): checks for neighbors of zero
   */
  @Test
  public void testNNNameZero() {
    setUp();
    // Checking for Radius of Zero
    ArrayList<Star> result1 = naive_neighbors.performNaiveNeighbors(0, "7", starsList);
    assertEquals(result1.size(), 0);

    ArrayList<Star> result2 = naive_neighbors.performNaiveNeighbors(0, "9", starsList);
    assertEquals(result2.size(), 0);

    tearDown();
  }

  /**
   ** performNaiveNeighbors (Name): No Stars Are Found
   */
  @Test
  public void testNRCoordinateNoStars() {
    ArrayList<Star> oneStar = new ArrayList<>();
    oneStar.add(new Star("My life is a joke", "5", 1, 2, 3));
    ArrayList<Star> result = naive_neighbors.performNaiveNeighbors(10, "5", oneStar);
    assertEquals(result.size(), 0);
  }

  /**
   ** performNaiveNeighbors (Name): Multiple Stars Occupt Same Location
   */
  @Test
  public void testNNNameOccupy() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", 1, 1, 1));
    occupyList.add(new Star("B", "B", 1, 1, 1));
    occupyList.add(new Star("C", "C", 1, 1, 1));

    ArrayList<Star> result
        = naive_neighbors.performNaiveNeighbors(1, "A", occupyList);
    String[] answers = {"B", "C"};

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).distanceTo(1, 1, 1), 0, 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(0).getStarID()));
  }

  /**
   ** performNaiveNeighbors (Name): Stars Same Distance Away
   */
  @Test
  public void testNNNameSameDistance() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", 1, 1, 1));
    occupyList.add(new Star("B", "B", 0, 0, 0));
    occupyList.add(new Star("C", "C", 1, -1, -1));

    ArrayList<Star> result
        = naive_neighbors.performNaiveNeighbors(1, "B", occupyList);

    String[] answers = {"A", "C"};

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).distanceTo(0, 0, 0), Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(0).getStarID()));
  }

  /**
   ** performNaiveNeighbors (Name): Multiple Tiebreakers Needed
   */
  @Test
  public void testNNNameTieBreakers() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", 1, 1, 1));
    occupyList.add(new Star("B", "B", 0, 0, 0));
    occupyList.add(new Star("C", "C", 1, -1, -1));
    occupyList.add(new Star("D", "D", -1, -1, -1));
    occupyList.add(new Star("E", "E", 1, 1, -1));
    occupyList.add(new Star("F", "F", -1, 1, -1));

    ArrayList<Star> result
        = naive_neighbors.performNaiveNeighbors(3, "B", occupyList);
    String[] answers = {"A", "C", "D", "E", "F"};

    assertEquals(result.size(), 3);

    Random r = new Random();
    int numPick = r.nextInt(3);

    assertEquals(result.get(numPick).distanceTo(0, 0, 0), Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(numPick).getStarID()));
  }

  /**
   ** performNaiveNeighbors (Coordinate): normal test
   */
  @Test
  public void testNNCoordinateNormal() {
    setUp();
    ArrayList<Star> result = naive_neighbors.performNaiveNeighbors(5, 10, 10, 10, starsList);
    assertEquals(result.size(), 5);

    assertEquals(result.get(0).getStarID(), "2");
    assertEquals(result.get(1).getStarID(), "8");
    assertEquals(result.get(2).getStarID(), "9");
    assertEquals(result.get(3).getStarID(), "10");
    assertEquals(result.get(4).getStarID(), "6");
    tearDown();
  }

  /**
   ** performNaiveNeighbors (Coordinate): More Neighbors Asked Than List Size Available
   */
  @Test
  public void testNNCoordinateOverflow() {
    setUp();
    ArrayList<Star> result = naive_neighbors.performNaiveNeighbors(15, 0, 0, 0, starsList);
    assertEquals(result.size(), 10);

    assertEquals(result.get(0).getStarID(), "1");
    assertEquals(result.get(1).getStarID(), "6");
    assertEquals(result.get(2).getStarID(), "9");
    assertEquals(result.get(3).getStarID(), "3");
    assertEquals(result.get(4).getStarID(), "4");
    assertEquals(result.get(5).getStarID(), "8");
    assertEquals(result.get(6).getStarID(), "2");
    assertEquals(result.get(7).getStarID(), "5");
    assertEquals(result.get(8).getStarID(), "10");
    assertEquals(result.get(9).getStarID(), "7");

    tearDown();
  }

  /**
   ** performNaiveNeighbors (Coordinate): Coordinate is exactly on a star
   */
  @Test
  public void testNNCoordinateExact() {
    setUp();
    ArrayList<Star> result = naive_neighbors.performNaiveNeighbors(3, 0, 0, 0, starsList);
    assertEquals(result.size(), 3);

    assertEquals(result.get(0).getStarID(), "1");
    assertEquals(result.get(1).getStarID(), "6");
    assertEquals(result.get(2).getStarID(), "9");

    ArrayList<Star> resultNeighborsZero
        = naive_neighbors.performNaiveNeighbors(0, 0, 0, 0, starsList);
    assertEquals(resultNeighborsZero.size(), 0);
    tearDown();
  }

  /**
   ** performNaiveNeighbors (Coordinate): List of Stars is empty
   */
  @Test
  public void testNNEmpty() {
    ArrayList<Star> result
        = naive_neighbors.performNaiveNeighbors(2, 0, 0, 0, new ArrayList<Star>());
    assertEquals(result.size(), 0);
  }

  /**
   ** performNaiveNeighbors (Coordinate): Multiple Stars Occupt Same Location
   */
  @Test
  public void testNNOccupy() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", 1, 1, 1));
    occupyList.add(new Star("B", "B", 0.5, 0.5, 0.5));
    occupyList.add(new Star("C", "C", 1, 1, 1));

    ArrayList<Star> result
        = naive_neighbors.performNaiveNeighbors(2, 0, 0, 0, occupyList);
    String[] answers = {"A", "C"};

    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "B");
    assertEquals(result.get(1).distanceTo(0, 0, 0), Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(1).getStarID()));
  }

  /**
   ** performNaiveNeighbors (Coordinate): Stars Same Distance Away
   */
  @Test
  public void testNNSameDistance() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", 1, 1, 1));
    occupyList.add(new Star("B", "B", 0.5, 0.5, 0.5));
    occupyList.add(new Star("C", "C", 1, -1, -1));

    ArrayList<Star> result
        = naive_neighbors.performNaiveNeighbors(2, 0, 0, 0, occupyList);
    String[] answers = {"A", "C"};

    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "B");
    assertEquals(result.get(1).distanceTo(0, 0, 0), Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(1).getStarID()));
  }

  /**
   ** performNaiveNeighbors (Coordinate): Multiple Tie Breakers Needed
   */
  @Test
  public void testNNTieBreaker() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", 1, 1, 1));
    occupyList.add(new Star("B", "B", 0, 0, 0));
    occupyList.add(new Star("C", "C", 1, -1, -1));
    occupyList.add(new Star("D", "D", -1, -1, -1));
    occupyList.add(new Star("E", "E", 1, 1, -1));
    occupyList.add(new Star("F", "F", -1, 1, -1));

    ArrayList<Star> result
        = naive_neighbors.performNaiveNeighbors(4, 0, 0, 0, occupyList);
    String[] answers = {"A", "C", "D", "E", "F"};

    assertEquals(result.size(), 4);
    assertEquals(result.get(0).getStarID(), "B");

    Random r = new Random();
    int numPick = r.nextInt(2) + 1;
    assertEquals(result.get(numPick).distanceTo(0, 0, 0), Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(numPick).getStarID()));
  }

  /**
   ** Tests the matchArgsToMethod Methods of NaiveNeighbors
   */
  @Test
  public void testMatchArgs() {
    setUp();

    ArrayList<String> twoLst = new ArrayList<>();
    twoLst.add("12");
    twoLst.add("\"name\"");
    assertEquals(naive_neighbors.matchArgsToMethod(twoLst).get(), "naive_neighbors_2");

    ArrayList<String> fourLst = new ArrayList<>();
    fourLst.add("3");
    fourLst.add("-5.6");
    fourLst.add("7.5");
    fourLst.add("1.2");
    assertEquals(naive_neighbors.matchArgsToMethod(fourLst).get(), "naive_neighbors_4");

    tearDown();
  }

}