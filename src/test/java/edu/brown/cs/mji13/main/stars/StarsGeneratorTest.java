package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.StarsGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class StarsGeneratorTest {

  private StarsGenerator generator;

  /**
   * Sets up the Generator
   */
  @Before
  public void setUp() {
    generator
        = new StarsGenerator(-100, 200, 5, 26);
  }

  /**
   * Resets the Configuration.
   */
  @After
  public void tearDown() {
    generator=null;
  }

  /**
   * Testing for generateInput
   * checking if its list has the same size as the input
   */
  @Test
  public void testGenerateInput() {
    setUp();
    Random rand = new Random();
    boolean isValid = true;
    for (int i = 0; i < 10; i++) {
      int size = 1 + rand.nextInt(50);
      ArrayList<Star> starsList = generator.generateInput(size);
      isValid = isValid && (starsList.size() == size);
    }

    assertTrue(isValid);
    tearDown();
  }

  /**
   * Testing for randomStar
   * checking if its coordinates are in the given range
   */
  @Test
  public void testRandomStar() {
    setUp();
    Random rand = new Random();
    boolean isValid = true;
    for (int i = 0; i < 100; i++) {
      int size = rand.nextInt(50);
      Star star = generator.randomStar(i);

      double starX = star.getCoordinate(0);
      double starY = star.getCoordinate(1);
      double starZ = star.getCoordinate(2);

      isValid = isValid && (-100 <= starX) && (starX <= 100)
          && (-100 <= starY) && (starY <= 100)
          && (-100 <= starZ) && (starZ <= 100);
    }

    assertTrue(isValid);
    tearDown();
  }

  /**
   * Testing for randomName
   * checking if its length is in range
   */
  @Test
  public void testRandomName() {
    setUp();
    Random rand = new Random();
    boolean isValid = true;
    for (int i = 0; i < 100; i++) {
      String name = generator.randomName();

      isValid = isValid && (1 <= name.length()) && (name.length() <= 5);
    }

    assertTrue(isValid);
    tearDown();
  }

}
