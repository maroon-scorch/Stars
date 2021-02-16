package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.StarStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StarsStorageTest {
  // The main focus of starsStorageTest are for methods that are undercovered
  // in the execution of the Commands that rely on other functionalities in starsStorage,
  private StarStorage storage;

  /**
   * Sets up the Generator
   */
  @Before
  public void setUp() {
    storage = new StarStorage("Test");
    ArrayList<Star> starsList = new ArrayList<Star>();
    Star[] starArray = {new Star("1", "1", Arrays.asList(0.0, 0.0, 0.0)),
        new Star("2", "2", Arrays.asList(10.0, 6.0, 5.0)),
        new Star("3", "3", Arrays.asList(3.1, 1.5, -6.7)),
        new Star("4", "4", Arrays.asList(-5.0, -6.0, -7.0)),
        new Star("5", "5", Arrays.asList(-10.56, -13.0, 12.0)),
        new Star("6", "6", Arrays.asList(0.5, 0.0, 0.0)),
        new Star("7", "7", Arrays.asList(-50.0, -50.0, -50.0)),
        new Star("8", "8", Arrays.asList(5.0, 6.0, 7.0)),
        new Star("9", "B", Arrays.asList(3.0, 4.0, 5.0)),
        new Star("10", "A", Arrays.asList(10.0, 20.0, 3.0))};
    starsList.addAll(Arrays.asList(starArray));
    storage.setListToStarsMap(starsList);
  }

  /**
   * Resets the Configuration.
   */
  @After
  public void tearDown() {
    storage=null;
  }

  /**
   ** Testing Map Functionalities
   */
  @Test
  public void testStarsMap() {
    setUp();
    // Testing if name is in storage
    assertTrue(storage.isNameInStorage("2"));
    assertTrue(storage.isNameInStorage("B"));
    assertTrue(storage.isNameInStorage("A"));
    assertFalse(storage.isNameInStorage("67475"));
    // Empty String
    assertFalse(storage.isNameInStorage(""));

    // Getting Stars from the Map
    assertEquals(storage.getStarFromMap("A").getStarID(), "10");
    assertEquals(storage.getStarFromMap("B").getStarID(), "9");
    assertNull(storage.getStarFromMap("C"));

    // Finding the Set of Names
    Set<String> nameSet = storage.getStarNames();
    assertTrue(nameSet.contains("2"));
    assertTrue(nameSet.contains("4"));
    assertTrue(nameSet.contains("A"));

    tearDown();
  }

  /**
   ** Testing HTML Tab;e
   */
  @Test
  public void testStarsHTML() {
    setUp();
    ArrayList<Star> starsList = new ArrayList<Star>();
    Star[] starArray = {new Star("1", "1", Arrays.asList(0.0, 0.0, 0.0)),
        new Star("2", "2", Arrays.asList(10.0, 6.0, 5.0)),
        new Star("3", "3", Arrays.asList(3.1, 1.5, -6.7))};
    starsList.addAll(Arrays.asList(starArray));
    String table = storage.starListToHTML(starsList);

    assertEquals(table, "<table><tr><th>StarID</th><th>ProperName</th>" +
        "<th>Coordinates (X, Y, Z)</th>" +
        "</tr><tr><td>1</td><td>1</td><td>[0.0, 0.0, 0.0]</td></tr>" +
        "<tr><td>2</td><td>2</td><td>[10.0, 6.0, 5.0]</td></tr>" +
        "<tr><td>3</td><td>3</td><td>[3.1, 1.5, -6.7]</td></tr></table>");

    // Empty Table
    String emptyTable = storage.starListToHTML(new ArrayList<>());
    assertEquals(emptyTable,
        "<table><tr><th>StarID</th><th>ProperName</th><th>Coordinates (X, Y, Z)</th>" +
            "</tr></table>");

    tearDown();
  }
}
