package edu.brown.cs.student.stars;

import edu.brown.cs.student.command.Command;
import edu.brown.cs.student.csvParse.CSVParser;
import edu.brown.cs.student.validations.ArgsInformation;
import edu.brown.cs.student.validations.ArgsValidator;
import edu.brown.cs.student.validations.StringValFunctions;
import edu.brown.cs.student.validations.StringValidation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * Stars Command Object for executing the "stars <filepath>" command.
 */
public class UpdateStarFile implements Command, StringValFunctions {
  /**
   * The list of Stars to store the converted lines to stars in.
   */
  private final ArrayList<Star> starsList;

  /**
   * The name of the current file the Command is operating on.
   */
  private final StringBuilder currentFile;

  /**
   * The parser to handle parsing of the CSV File.
   */
  private final CSVParser parser = new CSVParser();

  /**
   * Expected Headers of the CSV File.
   */
  private final String[] expectedHeaders = {"StarID", "ProperName", "X", "Y", "Z"};

  /**
   * Specifications on the requirements on the argument passed to the command.
   * filepath - any string.
   */
  private final Map<Integer, ArgsInformation[]> reqInfoMaps = Map.ofEntries(
      entry(1, new ArgsInformation[] {new ArgsInformation(
          "stars_1",
          new String[] {"filepath: any"},
          new StringValidation[] {this::pass},
          new String[] {"ERROR: Filepath can be any string"}
      )}));

  /**
   * The argument validator for the arguments of the mock <csv> command.
   */
  private final ArgsValidator argsValidator
      = new ArgsValidator("stars", reqInfoMaps);

  /**
   * Creates a UpdateStarFile object.
   *
   * @param starsList   - the list of stars the current File has
   * @param currentFile - the name of the current File
   */
  public UpdateStarFile(ArrayList<Star> starsList, StringBuilder currentFile) {
    this.starsList = starsList;
    this.currentFile = currentFile;
  }

  /**
   * Executes the stars Command.
   * If successful, prints out every line of the MockPerson converted in String.
   *
   * @param args - the list of arguments to be operated on
   */
  public void execute(ArrayList<String> args) {
    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      return;
    }

    String filepath = args.get(0);
    ArrayList<Star> tempStarsList = new ArrayList<Star>();
    boolean isSuccessful =
        parser.parse(filepath, tempStarsList, expectedHeaders, this::lineToStar);
    if (isSuccessful) {
      // If parsing was successful, update the starsList and the currentFile name
      starsList.clear();
      starsList.addAll(tempStarsList);
      currentFile.replace(0, currentFile.length(), filepath);
      System.out.printf("Read %d stars from %s\n", starsList.size(), filepath);
    }
  }

  /**
   * Match the arguments given to which method (if any) the Command Object should execute,
   * normally a switch case would handle the methodName to the execution of the method,
   * but since Stars only has 1 method, this is a check for argument validation.
   *
   * @param args the list of arguments to be operated on
   * @return Optional<String> empty if the arguments are invalid, a String if a match is found.
   */
  public Optional<String> matchArgsToMethod(ArrayList<String> args) {
    return argsValidator.testArgs(args);
  }

  /**
   * Given a line of the CSV File, converts it into a MockPerson object.
   *
   * @param line - the line of the file
   * @return Star if no errors or illegal arguments were present.
   */
  public Star lineToStar(String line) {
    String[] starArgsArray = line.split(",");
    if (starArgsArray.length != 5) {
      throw new IllegalArgumentException("The file's rows are malformed, "
          + "they have inconsistent number of entries per row");
    }

    return new Star(starArgsArray[0],
        starArgsArray[1],
        starArgsArray[2],
        starArgsArray[3],
        starArgsArray[4]);
  }
}
