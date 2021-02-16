package edu.brown.cs.mji13.main.validaitons;

import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValidation;
import edu.brown.cs.mji13.validations.StringValFunctions;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

import static org.junit.Assert.*;

public class ArgsValidatorTest implements StringValFunctions{

  private ArgsValidator validator;
  private String name;
  private Map<Integer, ArgsInformation[]> reqInfoMaps;

  /**
   * Sets up the ArgsValidator and the Hashmap
   */
  @Before
  public void setUp() {
    name = "Sample_Test";
    reqInfoMaps = Map.ofEntries(
        entry(2, new ArgsInformation[] {new ArgsInformation(
            "naive_neighbors_2",
            new String[] {"neighbors: int >= 0", "\"name\""},
            new StringValidation[] {this::isNonNegInt, this::isName},
            new String[] {"ERROR: Number of Neighbors must be a Positive Integer.",
                "ERROR: Name must be surrounded by double quotes."}
        )}),
        entry(4, new ArgsInformation[] {new ArgsInformation(
            "naive_neighbors_4",
            new String[] {"neighbors: int >= 0", "x: number", "y: number", "z: number"},
            new StringValidation[] {this::isNonNegInt, this::isNumeric,
                this::isNumeric, this::isNumeric},
            new String[] {"ERROR: Number of Neighbors must be a Positive Integer.",
                "ERROR: Coordinate X must be numeric.",
                "ERROR: Coordinate Y must be numeric.",
                "ERROR: Coordinate Z must be numeric."
            }
        )})
    );
    validator = new ArgsValidator(name, reqInfoMaps);
  }

  /**
   * Resets the variables.
   */
  @After
  public void tearDown() {
    validator = null;
    name = null;
    reqInfoMaps = null;
  }


  /**
   * * Note that ArgsValidator was tested in the Commands Object as well.
   * Due to ArgsValidator tied to printing Error Messages, the Error Messages would
   * flock the terminal when testing, so all Error Cases are handled in System Testing instead.
   *
   * Testing testArgs method
   */
  @Test
  public void testTestArgs() {
    ArrayList<String> correctParameters = new ArrayList<String>();
    correctParameters.add("5");
    correctParameters.add("\"name\"");

    Optional<String> name2 = validator.testArgs(correctParameters);
    assertEquals(name2.get(), "naive_neighbors_2");

    correctParameters.clear();
    correctParameters.add("6");
    correctParameters.add("-6.7");
    correctParameters.add("17");
    correctParameters.add("0.67");
    Optional<String> name4 = validator.testArgs(correctParameters);
    assertEquals(name4.get(), "naive_neighbors_4");

    assertEquals(validator.getErrorMessage(), "");
  }

  /**
   * Test Get Set Error Method
   */
  @Test
  public void testErrorGetSet() {
    validator.setMessage("A");
    assertEquals(validator.getErrorMessage(), "A");
    validator.setMessage("B");
    assertEquals(validator.getErrorMessage(), "B");
    validator.setMessage("");
  }


  /**
   * Testing testArgsWithReq method when No Error is triggered
   */
  @Test
  public void testTestArgsWithReqNoError() {
    ArrayList<String> correctParameters = new ArrayList<String>();
    correctParameters.add("5");
    correctParameters.add("\"name\"");

    String noError2 = validator.testArgsWithReq(correctParameters, reqInfoMaps.get(2)[0]);
    assertEquals("", noError2);

    correctParameters.clear();
    correctParameters.add("6");
    correctParameters.add("-6.7");
    correctParameters.add("17");
    correctParameters.add("0.67");
    String noError4 = validator.testArgsWithReq(correctParameters, reqInfoMaps.get(4)[0]);
    assertEquals("", noError4);
  }

  /**
   * Testing testArgsWithReq method when "Type" Error is triggered
   */
  @Test
  public void testTestArgsWithReqError() {
    ArrayList<String> correctParameters = new ArrayList<String>();
    correctParameters.add("5");
    correctParameters.add("6.6");

    String error = validator.testArgsWithReq(correctParameters, reqInfoMaps.get(2)[0]);
    assertEquals(error,
        "ERROR:----------------------------------------------\n" +
        "ERROR: If you are trying to use the following format: Sample_Test " +
            "<neighbors: int >= 0> <\"name\">,\n" +
        "ERROR: then the following errors occurred:\n" +
        "ERROR: The value 6.6 is malformed\n" +
        "ERROR: Name must be surrounded by double quotes.\n" +
        "ERROR:----------------------------------------------");
  }


}
