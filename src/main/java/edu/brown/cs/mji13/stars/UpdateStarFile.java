package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.csvParse.CSVParser;
import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValFunctions;
import edu.brown.cs.mji13.validations.StringValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * Stars Command Object for executing the "stars filepath" command.
 */
public class UpdateStarFile implements Command, StringValFunctions {

  /**
   * The common data-types shared by all the stars-related commands.
   */
  private StarStorage starStorage;

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
   * The argument validator for the arguments of the star filepath command.
   */
  private final ArgsValidator argsValidator
      = new ArgsValidator("stars", reqInfoMaps);

  /**
   * Specifications on the requirements on the argument of CSV to be valid to pass to stars
   * starID - any String
   * starName - any String
   * x - numeric
   * y - numeric
   * z - numeric.
   */
  private final Map<Integer, ArgsInformation[]> starDataValidation
      = Map.ofEntries(
      entry(5, new ArgsInformation[] {new ArgsInformation(
          "star_x_y_z",
          new String[] {"starID: any string", "starName: any string", "x: number",
              "y: number", "z: number"},
          new StringValidation[] {this::pass, this::pass, this::isNumeric, this::isNumeric,
              this::isNumeric},
          new String[] {"ERROR: Star ID of row is malformed",
              "ERROR: Proper Name of row is malformed",
              "ERROR: Coordinate X of row must be numeric.",
              "ERROR: Coordinate Y of row must be numeric.",
              "ERROR: Coordinate Z of row must be numeric."
          }
      )}));

  /**
   * The argument validator for the arguments of the stars filepath command.
   */
  private final ArgsValidator starsValidator
      = new ArgsValidator("star:", starDataValidation);

  /**
   * Creates a UpdateStarFile object.
   *
   * @param starStorage - the storage of relevant stars data shared by files
   */
  public UpdateStarFile(
      StarStorage starStorage) {
    this.starStorage = starStorage;
  }

  /**
   * Creates an empty UpdateStarFile object.
   */
  public UpdateStarFile() {
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
      starStorage.setList(tempStarsList);
      starStorage.setListToTree(tempStarsList);
      starStorage.setListToStarsMap(tempStarsList);
      starStorage.setName(filepath);
      System.out.printf("Read %d stars from %s%n", tempStarsList.size(), filepath);
    }
  }

  /**
   * Match the arguments given to which method (if any) the Command Object should execute,
   * normally a switch case would handle the methodName to the execution of the method,
   * but since Stars only has 1 method, this is a check for argument validation.
   *
   * @param args the list of arguments to be operated on
   * @return Option of String - empty if the arguments are invalid, a String if match is found.
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

    // Check to see if the arguments are valid
    // ie. first two fields are string and the last three are numeric
    ArrayList<String> stringArgs = new ArrayList<>(Arrays.asList(starArgsArray));
    Optional<String> opMethodName = starsValidator.testArgs(stringArgs);
    if (opMethodName.isEmpty()) {
      throw new IllegalArgumentException("ERROR: The fields of the row does not"
          + "match the specifications of stars");
    }

    // Adding the coordinates together as an ArrayList
    double xPos = Double.parseDouble(starArgsArray[2]);
    double yPos = Double.parseDouble(starArgsArray[3]);
    double zPos = Double.parseDouble(starArgsArray[4]);

    ArrayList<Double> coordinates = new ArrayList<>();
    coordinates.add(xPos);
    coordinates.add(yPos);
    coordinates.add(zPos);

    return new Star(starArgsArray[0],
        starArgsArray[1],
        coordinates);
  }
}
