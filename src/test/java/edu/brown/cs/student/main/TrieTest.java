package edu.brown.cs.student.main;

import edu.brown.cs.student.stars.Star;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class TrieTest {

  @Test
  public void testStar() {
    Star star = new Star("", "", 0, 0, 0);
    String dist = star.getName();
    assertEquals(dist, "");
  }

}
