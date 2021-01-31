package edu.brown.cs.student.main;

import edu.brown.cs.student.validations.StringValFunctions;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class StringValidationsTest implements StringValFunctions{

  /**
   ** Tests the testIsNumeric Methods of StringValFunctions
   */
  @Test
  public void testIsNumeric() {
    // Normal
    assertTrue(isNumeric("14"));
    assertTrue(isNumeric("18.9"));
    assertTrue(isNumeric("18865.41"));
    assertTrue(isNumeric("-14"));

    assertFalse(isNumeric("06/31/2020"));
    assertFalse(isNumeric("June 31st, 2020"));

    assertFalse(isNumeric("6..7"));
  }

  /**
   ** Tests the testIsNumeric Methods of StringValFunctions
   */
  @Test
  public void testIsInteger() {
    // Normal
    assertTrue(isInteger("14"));
    assertTrue(isInteger("0"));
    assertTrue(isInteger("-14"));

    assertFalse(isInteger("18.9"));
    assertFalse(isInteger("18865.41"));

    assertFalse(isInteger("06/31/2020"));
    assertFalse(isInteger("June 31st, 2020"));

    assertFalse(isInteger("6..7"));
  }

  /**
   ** Tests the isName Methods of StringValFunctions
   */
  @Test
  public void testIsName() {
    // Normal
    assertTrue(isName("\"14\""));
    assertTrue(isName("\"fsav fee fsdf\""));

    assertFalse(isName("06/31/2020"));
    assertFalse(isName("14"));
    assertFalse(isName("Alice"));
    assertFalse(isName("'Alice'"));
  }
}