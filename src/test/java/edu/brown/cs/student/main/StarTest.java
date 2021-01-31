package edu.brown.cs.student.main;

import edu.brown.cs.student.stars.Star;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class StarTest {

  /**
   ** Tests the distanceTo Methods of Star
   */
  @Test
  public void testStarDistanceTo() {
    Star star1 = new Star("Abc", "My life is a joke", "2", "3", "4");
    // Distance Normal
    assertEquals(star1.distanceTo(0, 13, 4), 10.198, 0.01);
    // Distance to itself
    assertEquals(star1.distanceTo(2, 3, 4), 0, 0.01);
    // Distance to Negatives
    assertEquals(star1.distanceTo(-1, 2.0, -3), 7.6811, 0.01);
    // Distance to Zero
    assertEquals(star1.distanceTo(0, 0, 0), 5.38516, 0.01);
  }

  /**
   ** Tests the accessor Methods of Star
   */
  @Test
  public void testStarGet() {
    // Simple Get
    Star star1 = new Star("Abc", "My life is a joke", 1.9, 2.0, 3.1);
    assertEquals(star1.getStarID(), "Abc");
    assertEquals(star1.getName(), "My life is a joke");
    assertEquals(star1.getX(), 1.9, 0.01);
    assertEquals(star1.getY(), 2.0, 0.01);
    assertEquals(star1.getZ(), 3.1, 0.01);

    // Construct With String
    Star star2 = new Star("A", "B", "1.2", "-5", "6");
    assertEquals(star2.getStarID(), "A");
    assertEquals(star2.getName(), "B");
    assertEquals(star2.getX(), 1.2, 0.01);
    assertEquals(star2.getY(), -5, 0.01);
    assertEquals(star2.getZ(), 6, 0.01);

    // Empty String
    Star star3 = new Star("Abc", "", 1.9, 2.0, 3.1);
    assertEquals(star3.getStarID(), "Abc");
    assertEquals(star3.getName(), "");
  }

  /**
   ** Tests the toString Methods of Star
   */
  @Test
  public void testStarToString() {
    // Simple Get
    Star star1 = new Star("Abc", "My life is a joke", 1.9, 2.0, 3.1);
    assertEquals(star1.toString(), "Abc");

    // Construct With String
    Star star2 = new Star("A", "B", "1.2", "-5", "6");
    assertEquals(star2.toString(), "A");
  }

}
