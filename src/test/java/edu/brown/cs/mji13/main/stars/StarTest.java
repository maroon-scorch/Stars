package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.stars.Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StarTest {

  /**
   ** Tests the distanceTo Methods of Star
   */
  @Test
  public void testStarDistanceTo() {
    ArrayList<Double> starCords = new ArrayList<>();
    starCords.add(2.0);
    starCords.add(3.0);
    starCords.add(4.0);

    Star star1 = new Star("Abc", "My life is a joke", starCords);
    // Distance Normal
    assertEquals(star1.distanceTo(Arrays.asList(0.0, 13.0, 4.0)), 10.198, 0.01);
    // Distance to itself
    assertEquals(star1.distanceTo(Arrays.asList(2.0, 3.0, 4.0)), 0, 0.01);
    // Distance to Negatives
    assertEquals(star1.distanceTo(Arrays.asList(-1.0, 2.0, -3.0)), 7.6811, 0.01);
    // Distance to Zero
    assertEquals(star1.distanceTo(Arrays.asList(0.0, 0.0, 0.0)), 5.38516, 0.01);
  }

  /**
   ** Tests the numDimensions Method of Star
   */
  @Test
  public void testStarDim() {
    ArrayList<Double> starCords = new ArrayList<>();
    starCords.add(2.0);
    starCords.add(3.0);
    starCords.add(4.0);

    Star star1 = new Star("Abc", "My life is a joke", starCords);
    assertEquals(star1.numDimensions(), 3);

    starCords.add(5.7);
    starCords.add(4.6);
    Star star2 = new Star("A", "A", starCords);
    assertEquals(star2.numDimensions(), 5);

    starCords.clear();
    starCords.add(1.1);
    Star star3 = new Star("A--", "A", starCords);
    assertEquals(star3.numDimensions(), 1);
  }

  /**
   ** Tests the accessor Methods of Star
   */
  @Test
  public void testStarGet() {
    // Simple Get
    ArrayList<Double> starCords = new ArrayList<>();
    starCords.add(2.0);
    starCords.add(3.0);
    starCords.add(4.0);

    Star star1 = new Star("Abc", "My life is a joke", starCords);
    assertEquals(star1.getStarID(), "Abc");
    assertEquals(star1.getName(), "My life is a joke");

    // Empty String
    Star star3 = new Star("Abc", "", starCords);
    assertEquals(star3.getStarID(), "Abc");
    assertEquals(star3.getName(), "");
  }

  /**
   ** Tests the toString Methods of Star
   */
  @Test
  public void testStarToString() {
    ArrayList<Double> starCords = new ArrayList<>();
    starCords.add(1.9);
    starCords.add(2.0);
    starCords.add(3.1);

    // Simple Get
    Star star1 = new Star("Abc", "My life is a joke", starCords);
    assertEquals(star1.toString(), "Abc");

    // Construct With String
    Star star2 = new Star("A", "B", starCords);
    assertEquals(star2.toString(), "A");
  }

}
