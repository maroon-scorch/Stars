package edu.brown.cs.mji13.main.stars;

import edu.brown.cs.mji13.stars.Star;
import edu.brown.cs.mji13.stars.StarStorage;
import edu.brown.cs.mji13.stars.UpdateStarFile;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UpdateStarFileTest {

  /**
   ** Tests the execute Methods of UpdateStarFile
   */
  @Test
  public void testExecute() {
    StarStorage storage = new StarStorage("Test");
    UpdateStarFile cmd = new UpdateStarFile(storage);

    ArrayList<String> msg = cmd.execute(new ArrayList<String>() {
      { add("data/stars/stardata.csv"); }});

    assertEquals(msg.size(), 1);
    assertEquals(msg.get(0), "Read 119617 stars from data/stars/stardata.csv");

    ArrayList<String> msg2 = cmd.execute(new ArrayList<String>() {
      { add("data/stars/no-star.csv"); }});
    assertEquals(msg2.size(), 1);
    assertEquals(msg2.get(0), "Read 0 stars from data/stars/no-star.csv");

    // Error in Reading File
    ArrayList<String> msg3 = cmd.execute(new ArrayList<String>() {
      { add("data/stars/star-improper-title.csv"); }});
    assertEquals(msg3.size(), 1);
    assertEquals(msg3.get(0),
        "ERROR: The headers of the File are not the expected headers.");
  }

  /**
   ** Tests the lineToPerson Methods of UpdateStarFile
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
   ** Tests the matchArgsToMethod Methods of UpdateStarFile
   */
  @Test
  public void testMatchArgs() {
    ArrayList<Star> lst = new ArrayList<>();
    StringBuilder currentFile = new StringBuilder();
    UpdateStarFile cmd = new UpdateStarFile();

    ArrayList<String> slst = new ArrayList<>();
    slst.add("star.csv");
    assertEquals(cmd.matchArgsToMethod(slst).get(), "stars_1");

    // Check for Erroneous Argument
    assertFalse(cmd.matchArgsToMethod(new ArrayList<String>()).isPresent());
  }


}
