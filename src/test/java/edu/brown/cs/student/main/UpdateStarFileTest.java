package edu.brown.cs.student.main;

import edu.brown.cs.student.stars.Star;
import edu.brown.cs.student.stars.UpdateStarFile;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class UpdateStarFileTest {

  /**
   ** Tests the lineToPerson Methods of MockCSV
   */
  @Test
  public void testPersonToString() {
    ArrayList<Star> lst = new ArrayList<>();
    StringBuilder currentFile = new StringBuilder();

    UpdateStarFile cmd = new UpdateStarFile(lst, currentFile);
    Star star1
        = cmd.lineToStar("1,Lonely Star,5,-2.24,10.04");
    assertEquals(star1.getName(), "Lonely Star");
    assertEquals(star1.getX(), 5, 0.01);
    assertEquals(star1.getY(), -2.24, 0.01);
    assertEquals(star1.getZ(), 10.04, 0.01);
  }

  /**
   ** Tests the matchArgsToMethod Methods of MockCSV
   */
  @Test
  public void testMatchArgs() {
    ArrayList<Star> lst = new ArrayList<>();
    StringBuilder currentFile = new StringBuilder();
    UpdateStarFile cmd = new UpdateStarFile(lst, currentFile);

    ArrayList<String> slst = new ArrayList<>();
    slst.add("star.csv");
    assertEquals(cmd.matchArgsToMethod(slst).get(), "stars_1");
  }

}
