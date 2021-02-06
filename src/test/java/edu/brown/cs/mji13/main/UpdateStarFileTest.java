package edu.brown.cs.mji13.main;

import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.UpdateStarFile;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UpdateStarFileTest {

  /**
   ** Tests the lineToPerson Methods of MockCSV
   */
  @Test
  public void testPersonToString() {
    ArrayList<Star> lst = new ArrayList<>();
    StringBuilder currentFile = new StringBuilder();

    UpdateStarFile cmd = new UpdateStarFile();
    Star star1
        = cmd.lineToStar("1,Lonely Star,5,-2.24,10.04");
    assertEquals(star1.getName(), "Lonely Star");
    assertEquals(star1.getCoordinate(0), 5, 0.01);
    assertEquals(star1.getCoordinate(1), -2.24, 0.01);
    assertEquals(star1.getCoordinate(2), 10.04, 0.01);
  }

  /**
   ** Tests the matchArgsToMethod Methods of MockCSV
   */
  @Test
  public void testMatchArgs() {
    ArrayList<Star> lst = new ArrayList<>();
    StringBuilder currentFile = new StringBuilder();
    UpdateStarFile cmd = new UpdateStarFile();

    ArrayList<String> slst = new ArrayList<>();
    slst.add("star.csv");
    assertEquals(cmd.matchArgsToMethod(slst).get(), "stars_1");
  }


}
