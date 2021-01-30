package edu.brown.cs.student.main;

import edu.brown.cs.student.people.MockPerson;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class MockPersonTest {

  /**
   ** Tests the toString Methods of MockPerson
   */
  @Test
  public void testPersonToString() {
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "fbaracloughrr@webmd.com" , "Male", "9613 Schiller Hill");
    assertEquals(person1.toString(), "First Name: Frants, Last Name: Baraclough, "
        + "Birth Date: 12/4/2020, Email Address: fbaracloughrr@webmd.com, Gender: Male, "
        + "Street Address: 9613 Schiller Hill");

    // Blank Fields
    MockPerson person2 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");
    assertEquals(person2.toString(), "First Name: Frants, Last Name: Baraclough, "
        + "Birth Date: 12/4/2020, Email Address: [Undeclared/Empty], Gender: [Undeclared/Empty], "
        + "Street Address: 9613 Schiller Hill");
  }

  /**
   ** Tests the formatEmpty Methods of MockPerson
   */
  @Test
  public void testFormatEmpty() {
    // Simple Get
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");
    assertEquals(person1.formatEmpty(""), "[Undeclared/Empty]");
    assertEquals(person1.formatEmpty("ABC"), "ABC");
  }

  /**
   ** Tests the MockDate Methods of MockPerson
   */
  @Test
  public void testMockDate() {
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");

    // MockDates - Normal
    assertTrue(person1.isMockDate("06/30/2020"));
    assertTrue(person1.isMockDate("6/30/2020"));
    assertTrue(person1.isMockDate("6/3/2020"));

    assertFalse(person1.isMockDate("06/31/2020"));
    assertFalse(person1.isMockDate("June 31st, 2020"));
    assertFalse(person1.isMockDate("23432432"));

    // MockDates - Leap Year
    assertTrue(person1.isMockDate("02/29/2020"));
    assertFalse(person1.isMockDate("02/29/2019"));

  }

  /**
   ** Tests the MockEmail Methods of MockPerson
   */
  @Test
  public void testMockEmail() {
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");

    // MockEmail - Normal
    assertTrue(person1.isMockEmail("mj123@gmail.com"));
    assertTrue(person1.isMockEmail("kfeeham1c@discuz.net"));
    assertTrue(person1.isMockEmail("is_123@gmail.com"));

    assertFalse(person1.isMockDate("iamastring"));

    // MockEmail - Specific Domains
    assertTrue(person1.isMockEmail("klacy@guardian.co.uk"));
  }

  /**
   ** Tests the MockAddress Methods of MockPerson
   */
  @Test
  public void testMockAddress() {
    MockPerson person1 = new MockPerson("Frants", "Baraclough", "12/4/2020",
        "" , "", "9613 Schiller Hill");
    // MockAddress - Normal
    assertTrue(person1.isMockStreetAddress("81 David Plaza"));
    assertTrue(person1.isMockStreetAddress("180 CD Shasta Junction"));

    assertFalse(person1.isMockStreetAddress("Macquarie 2801"));
    assertFalse(person1.isMockStreetAddress("81-David-Plaza"));
  }
}
