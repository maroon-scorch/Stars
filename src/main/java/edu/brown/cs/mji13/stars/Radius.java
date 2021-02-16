package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValFunctions;
import edu.brown.cs.mji13.validations.StringValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * Radius Command Object for executing the "radius ..." command.
 */
public class Radius implements Command, StringValFunctions {

  /**
   * The common data-types shared by all the stars-related commands.
   */
  private StarStorage starStorage;

  /**
   * Specifications on the requirements on the argument passed to the command.
   * 2 Arguments:
   * - radius: non-negative number
   * - name: surrounded by double quotes
   * 4 Arguments:
   * - radius: non-negative number
   * - x: number
   * - y: number
   * - z: number
   */
  private final Map<Integer, ArgsInformation[]> reqInfoMaps
      = Map.ofEntries(
      entry(2, new ArgsInformation[] {new ArgsInformation(
          "radius_2",
          new String[] {"radius >= 0", "\"name\""},
          new StringValidation[] {this::isNonNegative, this::isName},
          new String[] {"ERROR: Radius must be non-negative.",
              "ERROR: Name must be surrounded by double quotes"}
      )}),
      entry(4, new ArgsInformation[] {new ArgsInformation(
          "radius_4",
          new String[] {"radius >= 0", "x: number", "y: number", "z: number"},
          new StringValidation[] {this::isNonNegative, this::isNumeric,
              this::isNumeric, this::isNumeric},
          new String[] {"ERROR: Radius must be non-negative.",
              "ERROR: Coordinate X must be numeric",
              "ERROR: Coordinate Y must be numeric",
              "ERROR: Coordinate Z must be numeric"
          }
      )})
  );


  /**
   * The argument validator for the arguments of the mock <csv> command.
   */
  private final ArgsValidator argsValidator
      = new ArgsValidator("radius", reqInfoMaps);

  /**
   * Creates a Radius object.
   *
   * @param starStorage - the storage of relevant stars data shared by files
   */
  public Radius(StarStorage starStorage) {
    this.starStorage = starStorage;
  }

  /**
   * Execute Specific Variables
   * The list of answers (Stars) the command line finds during its execution.
   * And the boolean hasErrorOccurred checking if error occurred during execution
   */
  private ArrayList<Star> answers = new ArrayList<>();
  private boolean hasErrorOccurred = false;

  /**
   * Executes the radius Command.
   * If successful, finds out every stars with radius less than or equal to that of
   * the specified radius.
   *
   * @param args - the list of arguments to be operated on.
   * @return the Arraylist of starIDs to be print out if Successful. If unSuccessful,
   * prints the error messages instead.
   */
  public ArrayList<String> execute(ArrayList<String> args) {
    ArrayList<String> messages = new ArrayList<>();
    hasErrorOccurred = false;
    answers.clear();

    // If the current file is empty, print an error
    if (starStorage.getFileName().length() == 0) {
      hasErrorOccurred = true;
      messages.add("ERROR: No file has been loaded yet");
      return messages;
    }

    // If no methods are matched, exits the command
    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      hasErrorOccurred = true;
      messages.add(argsValidator.getErrorMessage());
      return messages;
    }

    // If a method name has been found, determines which method it maps to and executes it
    String methodName = opMethodName.get();
    KDTree<Star> sTree = starStorage.getStarsTree();
    switch (methodName) {
      case "radius_2":
        double radius = Double.parseDouble(args.get(0));
        String sName = args.get(1);
        String sStarNoQuotes = sName.substring(1, sName.length() - 1);
        if (starStorage.isNameInStorage(sStarNoQuotes)) {
          answers = performRadius(radius, sStarNoQuotes, sTree);
          answers.forEach((str) -> messages.add(str.getStarID()));
        } else {
          hasErrorOccurred = true;
          messages.add(String.format("ERROR: No Stars with name \"%s\" "
              + "is found or name is empty", sStarNoQuotes));
        }
        return messages;

      case "radius_4":
        double dRadius = Double.parseDouble(args.get(0));
        double dPosX = Double.parseDouble(args.get(1));
        double dPosY = Double.parseDouble(args.get(2));
        double dPosZ = Double.parseDouble(args.get(3));
        answers = performRadius(dRadius, dPosX, dPosY, dPosZ, sTree);
        answers.forEach((str) -> messages.add(str.getStarID()));
        return messages;
      default:
        // Only occurs if Programmer made an Error
        System.out.println("ERROR: Hashmap reqInfoMaps has unregistered names, "
            + "shouldn't have reached here");
        return new ArrayList<>();
    }
  }

  /**
   * Executes the radius Command for the GUI.
   *
   * @param args - the list of arguments to be operated on.
   * @return the String of the HTML Table formed by the list of Stars.
   */
  public String executeForGUI(ArrayList<String> args) {
    String result = String.join("\n", execute(args));
    ArrayList<Star> answerCopy = new ArrayList<Star>(answers);
    return hasErrorOccurred ? result : starStorage.starListToHTML(answerCopy);
  }

  /**
   * Match the arguments given to which method (if any) the Command Object should execute.
   *
   * @param args the list of arguments to be operated on
   * @return Optional of String - empty if the arguments are invalid, a String if match is found.
   */
  public Optional<String> matchArgsToMethod(ArrayList<String> args) {
    return argsValidator.testArgs(args);
  }

  /**
   * Finds all stars whose distance to the coordinate (x, y, z) is less than or equal to
   * the radius given.
   *
   * @param r    the radius given
   * @param x    the x coordinate of the point
   * @param y    the y coordinate of the point
   * @param z    the z coordinate of the point
   * @param tree the KD Tree of stars to search through
   * @return the list of stars within range inclusive.
   */
  public ArrayList<Star> performRadius(
      double r, double x, double y, double z, KDTree<Star> tree) {
    List<Double> cords = Arrays.asList(x, y, z);
    return tree.findNodesInRadius(r, cords);
  }

  /**
   * Finds all stars whose distance to the star specified with the name is less than or equal to
   * the radius given.
   *
   * @param r    the radius given
   * @param name the name of the star
   * @param tree the KD Tree of stars to search through
   * @return the list of stars within range inclusive.
   */
  public ArrayList<Star> performRadius(double r, String name, KDTree<Star> tree) {
    // Gets the Star from StarStorage, because the program checked if the name is in the
    // storage before, error handling is taken care of already.
    Star presentStar = starStorage.getStarFromMap(name);

    double selectedX = presentStar.getCoordinate(0);
    double selectedY = presentStar.getCoordinate(1);
    double selectedZ = presentStar.getCoordinate(2);

    ArrayList<Star> resultList
        = performRadius(r, selectedX, selectedY, selectedZ, tree);
    // Remove the name from the list generated
    resultList.removeIf(star -> star.getName().equals(name));

    return resultList;
  }

}
