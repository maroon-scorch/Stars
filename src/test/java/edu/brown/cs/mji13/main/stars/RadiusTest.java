package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.stars.Radius;
import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.StarStorage;
import edu.brown.cs.mji13.stars.UpdateStarFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RadiusTest {
  private StarStorage starStorage;
  private Radius radius;
  private ArrayList<Star> starsList;
  private KDTree<Star> starsTree;

  /**
   * Sets up the Naive Radius and the list of stars
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
    radius  = new Radius(starStorage);
  }

  /**
   * Resets the Naive Radius and the list of stars.
   */
  @After
  public void tearDown() {
    radius = null;
    starsList.clear();
  }

  /**
   ** Tests Execute Methods of Radius
   */
  @Test
  public void testRadiusExecute() {
    StarStorage storage = new StarStorage("Test");
    UpdateStarFile upd = new UpdateStarFile(storage);
    Radius radCmd = new Radius(storage);

    upd.execute(new ArrayList<String>() {
      { add("data/stars/ten-star.csv"); }});

    // For Coordinates
    ArrayList<String> radMsgCord = radCmd.execute(new ArrayList<String>() {
      { add("5"); add("0"); add("0"); add("0"); }});
    assertEquals(radMsgCord.size(), 6);
    assertEquals(radMsgCord.get(0), "0");
    assertEquals(radMsgCord.get(1), "70667");
    assertEquals(radMsgCord.get(2), "71454");
    assertEquals(radMsgCord.get(3), "71457");
    assertEquals(radMsgCord.get(4), "87666");
    assertEquals(radMsgCord.get(5), "118721");

    // For Names
    ArrayList<String> radMsgName = radCmd.execute(new ArrayList<String>() {
      { add("5"); add("\"Sol\""); }});
    assertEquals(radMsgName.size(), 5);
    assertEquals(radMsgName.get(0), "70667");
    assertEquals(radMsgName.get(1), "71454");
    assertEquals(radMsgName.get(2), "71457");
    assertEquals(radMsgName.get(3), "87666");
    assertEquals(radMsgName.get(4), "118721");
  }

  /**
   ** Tests Execute for GUI Methods of Radius
   */
  @Test
  public void testRadiusExecuteGUI() {
    StarStorage storage = new StarStorage("Test");
    UpdateStarFile upd = new UpdateStarFile(storage);
    Radius radCmd = new Radius(storage);

    upd.execute(new ArrayList<String>() {
      { add("data/stars/ten-star.csv"); }});

    // For Coordinates
    String radMsgCord = radCmd.executeForGUI(new ArrayList<String>() {
      { add("1"); add("0"); add("0"); add("0"); }});
    assertEquals(radMsgCord,
        "<table><tr><th>StarID</th><th>ProperName</th><th>Coordinates (X, Y, Z)</th></tr>" +
            "<tr><td>0</td><td>Sol</td><td>[0.0, 0.0, 0.0]</td></tr></table>");

    // For Names
    String radMsgName = radCmd.executeForGUI(new ArrayList<String>() {
      { add("1"); add("\"Sol\""); }});
    assertEquals(radMsgName,
        "<table><tr><th>StarID</th><th>ProperName</th>" +
            "<th>Coordinates (X, Y, Z)</th></tr></table>");
  }

  /**
   ** performRadius (name): normal test
   */
  @Test
  public void testRadiusNameNormal() {
    setUp();
    ArrayList<Star> result = radius.performRadius(10, "1", starsTree);
    assertEquals(result.size(), 3);
    assertEquals(result.get(0).getStarID(), "6");
    assertEquals(result.get(1).getStarID(), "9");
    assertEquals(result.get(2).getStarID(), "3");
    tearDown();
  }

  /**
   ** performRadius (name): checks for radius of zero
   */
  @Test
  public void testRadiusNameZero() {
    setUp();
    // Checking for Radius of Zero
    ArrayList<Star> result1 = radius.performRadius(0, "7", starsTree);
    assertEquals(result1.size(), 0);

    tearDown();
  }

  /**
   ** performRadius (Coordinate): normal test
   */
  @Test
  public void testRadiusCoordinateNormal() {
    setUp();
    ArrayList<Star> result = radius.performRadius(10, 10, 10, 10, starsTree);
    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "2");
    assertEquals(result.get(1).getStarID(), "8");
    tearDown();
  }

  /**
   ** performRadius (Coordinate): Coordinate is exactly on a star
   */
  @Test
  public void testRadiusCoordinateExact() {
    setUp();
    ArrayList<Star> result = radius.performRadius(3, 0, 0, 0, starsTree);
    assertEquals(result.size(), 2);
    assertEquals(result.get(0).getStarID(), "1");
    assertEquals(result.get(1).getStarID(), "6");

    ArrayList<Star> resultRadiusZero = radius.performRadius(0, 0, 0, 0, starsTree);
    assertEquals(resultRadiusZero.size(), 1);
    assertEquals(resultRadiusZero.get(0).getStarID(), "1");
    tearDown();
  }

  /**
   ** performRadius (Coordinate): No Stars Are Found
   */
  @Test
  public void testRadiusCoordinateNoStars() {
    setUp();
    ArrayList<Star> result = radius.performRadius(3, 100, 100, 100, starsTree);
    assertEquals(result.size(), 0);
    tearDown();
  }

  /**
   ** performNaiveRadius (Coordinate): List of Stars is empty
   */
  @Test
  public void testNREmpty() {
    KDTree<Star> mtTree = new KDTree<>(3);
    ArrayList<Star> result = radius.performRadius(3, 0, 0, 0, mtTree);
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
    assertEquals(radius.matchArgsToMethod(twoLst).get(), "radius_2");

    ArrayList<String> fourLst = new ArrayList<>();
    fourLst.add("32");
    fourLst.add("-5.2");
    fourLst.add("-0.75");
    fourLst.add("10.2");
    assertEquals(radius.matchArgsToMethod(fourLst).get(), "radius_4");

    tearDown();
  }
}
