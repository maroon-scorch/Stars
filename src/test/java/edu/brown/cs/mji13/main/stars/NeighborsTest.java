package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.stars.Neighbors;
import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.StarStorage;
import edu.brown.cs.mji13.stars.UpdateStarFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NeighborsTest {
  private StarStorage starStorage;
  private Neighbors neighbors;
  private ArrayList<Star> starsList;
  private KDTree<Star> starsTree;

  /**
   * Sets up the NaiveNeighbors and the list of stars
   */
  @Before
  public void setUp() {
    starsList = new ArrayList<Star>();
    Star[] starArray = {new Star("1", "1", Arrays.asList(0.0, 0.0, 0.0)),
        new Star("2", "2", Arrays.asList(10.0, 6.0, 5.0)),
        new Star("3", "3", Arrays.asList(3.1, 1.5, -6.7)),
        new Star("4", "4", Arrays.asList(-5.0, -6.0, -7.0)),
        new Star("5", "5", Arrays.asList(-10.56, -13.0, 12.0)),
        new Star("6", "6", Arrays.asList(0.5, 0.0, 0.0)),
        new Star("7", "7", Arrays.asList(-50.0, -50.0, -50.0)),
        new Star("8", "8", Arrays.asList(5.0, 6.0, 7.0)),
        new Star("9", "9", Arrays.asList(3.0, 4.0, 5.0)),
        new Star("10", "10", Arrays.asList(10.0, 20.0, 3.0))};
    starsList.addAll(Arrays.asList(starArray));
    starStorage = new StarStorage("test");
    starStorage.setListToTree(starsList);
    starStorage.setListToStarsMap(starsList);

    starsTree = starStorage.getStarsTree();
    neighbors  = new Neighbors(starStorage);
  }

  /**
   * Resets the NaiveNeighbors and the list of stars.
   */
  @After
  public void tearDown() {
    neighbors = null;
    starsList.clear();
  }

  /**
   ** Tests Execute Methods of Neighbors
   */
  @Test
  public void testNNExecute() {
    StarStorage storage = new StarStorage("Test");
    UpdateStarFile upd = new UpdateStarFile(storage);
    Neighbors cmd = new Neighbors(storage);

    upd.execute(new ArrayList<String>() {
      { add("data/stars/ten-star.csv"); }});

    // For Coordinates
    ArrayList<String> msgCord = cmd.execute(new ArrayList<String>() {
      { add("5"); add("0"); add("0"); add("0"); }});
    assertEquals(msgCord.size(), 5);
    assertEquals(msgCord.get(0), "0");
    assertEquals(msgCord.get(1), "70667");
    assertEquals(msgCord.get(2), "71454");
    assertEquals(msgCord.get(3), "71457");
    assertEquals(msgCord.get(4), "87666");

    // For Names
    ArrayList<String> msgName = cmd.execute(new ArrayList<String>() {
      { add("5"); add("\"Sol\""); }});
    assertEquals(msgName.size(), 5);
    assertEquals(msgName.size(), 5);
    assertEquals(msgName.get(0), "70667");
    assertEquals(msgName.get(1), "71454");
    assertEquals(msgName.get(2), "71457");
    assertEquals(msgName.get(3), "87666");
    assertEquals(msgName.get(4), "118721");
  }

  /**
   ** Tests Execute for GUI Methods of Neighbors
   */
  @Test
  public void testNNExecuteGUI() {
    StarStorage storage = new StarStorage("Test");
    UpdateStarFile upd = new UpdateStarFile(storage);
    Neighbors cmd = new Neighbors(storage);

    upd.execute(new ArrayList<String>() {
      { add("data/stars/ten-star.csv"); }});

    // For Coordinates
    String msgCord = cmd.executeForGUI(new ArrayList<String>() {
      { add("1"); add("0"); add("0"); add("0"); }});
    assertEquals(msgCord, "<table><tr><th>StarID</th><th>ProperName</th>" +
        "<th>Coordinates (X, Y, Z)</th></tr><tr><td>0</td><td>Sol</td><td>[0.0, 0.0, 0.0]</td></tr>" +
        "</table>");

    // For Names
    String msgName = cmd.executeForGUI(new ArrayList<String>() {
      { add("1"); add("\"Sol\""); }});
    assertEquals(msgName, "<table><tr><th>StarID</th><th>ProperName</th>" +
        "<th>Coordinates (X, Y, Z)</th></tr><tr><td>70667</td><td>Proxima Centauri</td>" +
        "<td>[-0.47175, -0.36132, -1.15037]</td></tr></table>");
  }

  /**
   ** performNeighbors (name): normal test
   */
  @Test
  public void testNeighborsNameNormal() {
    setUp();
    ArrayList<Star> result = neighbors.performNeighbors(3, "1", starsTree);
    assertEquals(result.size(), 3);

    assertEquals(result.get(0).getStarID(), "6");
    assertEquals(result.get(1).getStarID(), "9");
    assertEquals(result.get(2).getStarID(), "3");
    tearDown();
  }

  /**
   ** performNeighbors (name): checks for neighbors of zero
   */
  @Test
  public void testNeighborsNameZero() {
    setUp();
    // Checking for Radius of Zero
    ArrayList<Star> result1 = neighbors.performNeighbors(0, "7", starsTree);
    assertEquals(result1.size(), 0);

    ArrayList<Star> result2 = neighbors.performNeighbors(0, "9", starsTree);
    assertEquals(result2.size(), 0);

    tearDown();
  }

  /**
   ** performNeighbors (Name): No Stars Are Found
   */
  @Test
  public void testNeighborsCoordinateNoStars() {
    ArrayList<Star> oneStar = new ArrayList<>();
    oneStar.add(new Star("My life is a joke", "5", Arrays.asList(1.0, 2.0, 3.0)));
    KDTree<Star> oneTree = new KDTree<>(3);
    oneTree.constructTree(oneStar);

    ArrayList<Star> result = neighbors.performNeighbors(10, "5", oneTree);
    assertEquals(result.size(), 0);
  }

  /**
   ** performNeighbors (Name): Multiple Stars Occupy Same Location
   */
  @Test
  public void testNeighborsNameOccupy() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", Arrays.asList(1.0, 1.0, 1.0)));
    occupyList.add(new Star("B", "B",  Arrays.asList(1.0, 1.0, 1.0)));
    occupyList.add(new Star("C", "C",  Arrays.asList(1.0, 1.0, 1.0)));
    starStorage.setListToStarsMap(occupyList);
    starStorage.setListToTree(occupyList);

    ArrayList<Star> result
        = neighbors.performNeighbors(1, "A", starStorage.getStarsTree());
    String[] answers = {"B", "C"};

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).distanceTo( Arrays.asList(1.0, 1.0, 1.0)), 0, 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(0).getStarID()));
  }

  /**
   ** performNeighbors (Name): Stars Same Distance Away
   */
  @Test
  public void testNeighborsNameSameDistance() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A",  Arrays.asList(1.0, 1.0, 1.0)));
    occupyList.add(new Star("B", "B",  Arrays.asList(0.0, 0.0, 0.0)));
    occupyList.add(new Star("C", "C", Arrays.asList(1.0, -1.0, -1.0)));
    starStorage.setListToStarsMap(occupyList);
    starStorage.setListToTree(occupyList);

    ArrayList<Star> result
        = neighbors.performNeighbors(1, "B", starStorage.getStarsTree());

    String[] answers = {"A", "C"};

    assertEquals(result.size(), 1);
    assertEquals(result.get(0).distanceTo(Arrays.asList(0.0, 0.0, 0.0)), Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(0).getStarID()));
  }

  /**
   ** performNeighbors (Coordinate): normal test
   */
  @Test
  public void testNeighborsCoordinateNormal() {
    setUp();
    ArrayList<Star> result = neighbors.performNeighbors(5, 10, 10, 10, starsTree, Optional.empty());
    assertEquals(result.size(), 5);

    assertEquals(result.get(0).getStarID(), "2");
    assertEquals(result.get(1).getStarID(), "8");
    assertEquals(result.get(2).getStarID(), "9");
    assertEquals(result.get(3).getStarID(), "10");
    assertEquals(result.get(4).getStarID(), "6");
    tearDown();
  }

  /**
   ** performNeighbors (Coordinate): More Neighbors Asked Than List Size Available
   */
  @Test
  public void testNeighborsCoordinateOverflow() {
    setUp();
    ArrayList<Star> result
        = neighbors.performNeighbors(15, 0, 0, 0, starsTree, Optional.empty());
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
   ** performNeighbors (Coordinate): Coordinate is exactly on a star
   */
  @Test
  public void testNeighborsCoordinateExact() {
    setUp();
    ArrayList<Star> result = neighbors.performNeighbors(3, 0, 0, 0, starsTree, Optional.empty());
    assertEquals(result.size(), 3);

    assertEquals(result.get(0).getStarID(), "1");
    assertEquals(result.get(1).getStarID(), "6");
    assertEquals(result.get(2).getStarID(), "9");

    ArrayList<Star> resultNeighborsZero
        = neighbors.performNeighbors(0, 0, 0, 0, starsTree, Optional.empty());
    assertEquals(resultNeighborsZero.size(), 0);
    tearDown();
  }

  /**
   ** performNeighbors (Coordinate): List of Stars is empty
   */
  @Test
  public void testNeighborsEmpty() {
    ArrayList<Star> result
        = neighbors.performNeighbors(2, 0, 0, 0, new KDTree<>(3), Optional.empty());
    assertEquals(result.size(), 0);
  }

  /**
   ** performNaiveNeighbors (Coordinate): Multiple Stars Occupt Same Location
   */
  @Test
  public void testNNOccupy() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", Arrays.asList(1.0, 1.0, 1.0)));
    occupyList.add(new Star("B", "B", Arrays.asList(0.5, 0.5, 0.5)));
    occupyList.add(new Star("C", "C", Arrays.asList(1.0, 1.0, 1.0)));
    starStorage.setListToStarsMap(occupyList);
    starStorage.setListToTree(occupyList);

    ArrayList<Star> result
        = neighbors.performNeighbors(2, 0, 0, 0,
        starStorage.getStarsTree(), Optional.empty());
    String[] answers = {"A", "C"};

    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "B");
    assertEquals(result.get(1).distanceTo(Arrays.asList(0.0, 0.0, 0.0)),
        Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(1).getStarID()));
  }

  /**
   ** performNaiveNeighbors (Coordinate): Stars Same Distance Away
   */
  @Test
  public void testNNSameDistance() {
    ArrayList<Star> occupyList = new ArrayList<>();
    occupyList.add(new Star("A", "A", Arrays.asList(1.0, 1.0, 1.0)));
    occupyList.add(new Star("B", "B", Arrays.asList(0.5, 0.5, 0.5)));
    occupyList.add(new Star("C", "C", Arrays.asList(1.0, -1.0, -1.0)));
    starStorage.setListToStarsMap(occupyList);
    starStorage.setListToTree(occupyList);

    ArrayList<Star> result
        = neighbors.performNeighbors(2, 0, 0, 0,
        starStorage.getStarsTree(), Optional.empty());
    String[] answers = {"A", "C"};

    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "B");
    assertEquals(result.get(1).distanceTo(Arrays.asList(0.0, 0.0, 0.0)),
        Math.sqrt(3), 0.01);
    assertTrue(Arrays.asList(answers).contains(result.get(1).getStarID()));
  }

  /**
   ** Tests the matchArgsToMethod Methods of Neighbors
   */
  @Test
  public void testMatchArgs() {
    setUp();

    ArrayList<String> twoLst = new ArrayList<>();
    twoLst.add("12");
    twoLst.add("\"name\"");
    assertEquals(neighbors.matchArgsToMethod(twoLst).get(), "neighbors_2");

    ArrayList<String> fourLst = new ArrayList<>();
    fourLst.add("3");
    fourLst.add("-5.6");
    fourLst.add("7.5");
    fourLst.add("1.2");
    assertEquals(neighbors.matchArgsToMethod(fourLst).get(), "neighbors_4");

    tearDown();
  }
}
