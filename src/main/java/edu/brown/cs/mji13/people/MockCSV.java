package edu.brown.cs.mji13.people;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.csvParse.CSVParser;
import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValFunctions;
import edu.brown.cs.mji13.validations.StringValidation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * MockCSV Command Object for executing the "mock csv" command.
 */
public class MockCSV implements Command, StringValFunctions {
  /**
   * The list of MockPerson to store the lines of csv file in.
   */
  private final ArrayList<MockPerson> peopleList = new ArrayList<>();

  /**
   * The parser to handle parsing of the CSV File.
   */
  private final CSVParser parser = new CSVParser();

  /**
   * Expected Headers of the CSV File.
   */
  private final String[] expectedHeaders
      = {"FirstName", "LastName", "BirthDate", "Email", "Gender", "StreetAddress"};

  /**
   * Specifications on the requirements on the argument passed to the command.
   * filepath - any string.
   */
  private final Map<Integer, ArgsInformation[]> reqInfoMaps
      = Map.ofEntries(
      entry(1, new ArgsInformation[] {new ArgsInformation(
          "mock_csv_1",
          new String[] {"filepath: any"},
          new StringValidation[] {this::pass},
          new String[] {"ERROR: Filepath can be any string"}
      )}));

  /**
   * The argument validator for the arguments of the "mock csv" command.
   */
  private final ArgsValidator argsValidator
      = new ArgsValidator("mock <csv>", reqInfoMaps);

  /**
   * Creates an empty MOCKCSV object.
   */
  public MockCSV() {
  }

  /**
   * Executes the MockCSV Command.
   * If successful, prints out every line of the MockPerson converted in String.
   *
   * @param args - the list of arguments to be operated on
   * @return the list of toString of MockPerson to be printed out; returns the error message
   * if unsuccessful.
   */
  public ArrayList<String> execute(ArrayList<String> args) {
    ArrayList<String> messages = new ArrayList<>();
    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      messages.add(argsValidator.getErrorMessage());
      return messages;
    }

    String filepath = args.get(0);
    boolean isSuccessful =
        parser.parse(filepath, peopleList, expectedHeaders, this::lineToPerson);

    if (isSuccessful) {
      peopleList.forEach((people) -> messages.add(people.toString()));
      return messages;
    }

    messages.addAll(parser.getMessages());
    return messages;
  }

  /**
   * Match the arguments given to which method (if any) the Command Object should execute,
   * normally a switch case would handle the methodName to the execution of the method,
   * but since MockCSV only has 1 method, this is a check for argument validation.
   *
   * @param args the list of arguments to be operated on
   * @return Option of String - empty if the arguments are invalid, a String if a match is found.
   */
  public Optional<String> matchArgsToMethod(ArrayList<String> args) {
    return argsValidator.testArgs(args);
  }

  /**
   * Given a line of the CSV File, converts it into a MockPerson object.
   *
   * @param line - the line of the file
   * @return MockPerson if no errors or illegal arguments were present.
   */
  public MockPerson lineToPerson(String line) {
    String[] personAttributes = line.split(",", -1);
    if (personAttributes.length != 6) {
      throw new IllegalArgumentException("The file's rows are malformed, "
          + "they have inconsistent number of entries per row");
    }
    String firstName = personAttributes[0];
    String lastName = personAttributes[1];
    String birthDate = personAttributes[2];
    String email = personAttributes[3];
    String gender = personAttributes[4];
    String streetAddress = personAttributes[5];

    return new MockPerson(firstName, lastName, birthDate, email, gender, streetAddress);
  }
}
