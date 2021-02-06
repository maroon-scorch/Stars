package edu.brown.cs.mji13.main.people;

import edu.brown.cs.mji13.people.MockPerson;
import edu.brown.cs.mji13.people.MockCSV;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MockCSVTest {

  /**
   ** Tests the lineToPerson Methods of MockCSV
   */
  @Test
  public void testPersonToString() {
    MockCSV cmd = new MockCSV();
    MockPerson person1
        = cmd.lineToPerson("Bailey,Annable,6/29/2020,bannablero@bluehost.com,"
        + "Bigender,10 Pearson Park");
    assertEquals(person1.toString(), "First Name: Bailey, Last Name: Annable, "
        + "Birth Date: 6/29/2020, Email Address: bannablero@bluehost.com, Gender: Bigender,"
        + " Street Address: 10 Pearson Park");
  }

  /**
   ** Tests the matchArgsToMethod Methods of MockCSV
   */
  @Test
  public void testMatchArgs() {
    MockCSV cmd = new MockCSV();
    ArrayList<String> lst = new ArrayList<>();
    lst.add("person.csv");
    assertEquals(cmd.matchArgsToMethod(lst).get(), "mock_csv_1");
  }

}
