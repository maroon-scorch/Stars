package edu.brown.cs.student.main;

import edu.brown.cs.student.people.MockPerson;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class StringValidationsTest {

  /**
   * Because Interfaces can't be accessed directly, methods in it
   * would be tested with objects using them
   ** Tests the testIsNumeric Methods of StringValFunctions
   */
  @Test
  public void testIsNumeric() {
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");

    // Normal
    assertTrue(person1.isNumeric("14"));
    assertTrue(person1.isNumeric("18.9"));
    assertTrue(person1.isNumeric("18865.41"));
    assertTrue(person1.isNumeric("-14"));

    assertFalse(person1.isNumeric("06/31/2020"));
    assertFalse(person1.isNumeric("June 31st, 2020"));

    assertFalse(person1.isMockDate("6..7"));
  }

  /**
   ** Tests the testIsNumeric Methods of StringValFunctions
   */
  @Test
  public void testIsInteger() {
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");

    // Normal
    assertTrue(person1.isInteger("14"));
    assertTrue(person1.isInteger("0"));
    assertTrue(person1.isInteger("-14"));

    assertFalse(person1.isInteger("18.9"));
    assertFalse(person1.isInteger("18865.41"));

    assertFalse(person1.isInteger("06/31/2020"));
    assertFalse(person1.isInteger("June 31st, 2020"));

    assertFalse(person1.isInteger("6..7"));
  }

  /**
   ** Tests the isName Methods of StringValFunctions
   */
  @Test
  public void testIsName() {
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");

    // Normal
    assertTrue(person1.isName("\"14\""));
    assertTrue(person1.isName("\"fsav fee fsdf\""));

    assertFalse(person1.isName("06/31/2020"));
    assertFalse(person1.isName("14"));
    assertFalse(person1.isName("Alice"));
    assertFalse(person1.isName("'Alice'"));
  }
}