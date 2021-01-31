package edu.brown.cs.student.stars;

import edu.brown.cs.student.command.Command;
import edu.brown.cs.student.validations.ArgsInformation;
import edu.brown.cs.student.validations.ArgsValidator;
import edu.brown.cs.student.validations.StringValFunctions;
import edu.brown.cs.student.validations.StringValidation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static java.util.Map.entry;

/**
 * Naive Neighbors Command Object for executing the "naive_neighbors ..." command.
 */
public class NaiveNeighbors implements Command, StarsUtilities, StringValFunctions {
  /**
   * The list of Stars to store the converted lines to stars in.
   */
  private final ArrayList<Star> starsList;
  /**
   * The name of the current file the Command is operating on.
   */
  private final StringBuilder currentFile;

  /**
   * Specifications on the requirements on the argument passed to the command.
   * 2 Arguments:
   * - neighbors: non-negative integer
   * - name: surrounded by double quotes
   * 4 Arguments:
   * - neighbors: non-negative integer
   * - x: number
   * - y: number
   * - z: number
   * See custom StringValidation Method at the Bottom
   */
  private final Map<Integer, ArgsInformation[]> reqInfoMaps
      = Map.ofEntries(
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

  /**
   * The argument validator for the arguments of the mock <csv> command.
   */
  private final ArgsValidator argsValidator
      = new ArgsValidator("naive_neighbors", reqInfoMaps);

  /**
   * Creates a NaiveNeighbors object.
   *
   * @param starsList   - the list of stars the current File has
   * @param currentFile - the name of the current File
   */
  public NaiveNeighbors(ArrayList<Star> starsList, StringBuilder currentFile) {
    this.starsList = starsList;
    this.currentFile = currentFile;
  }

  /**
   * Creates a NaiveRadius object.
   */
  public NaiveNeighbors() {
    this.starsList = new ArrayList<Star>();
    this.currentFile = new StringBuilder();
  }

  /**
   * Executes the naive_neighbors Command.
   * If successful, prints out the closest n number of stars to the specified location.
   *
   * Note: TA Colton said that the randomization is meant for tiebreakers to include on the list
   * so if there are stars with same distance away but including them does not exceed that number
   * asked, they will be included just as normal.
   * @param args - the list of arguments to be operated on.
   */
  public void execute(ArrayList<String> args) {
    if (currentFile.length() == 0) {
      System.out.println("ERROR: No file has been loaded yet");
      return;
    }

    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      return;
    }

    String methodName = opMethodName.get();
    switch (methodName) {
      case "naive_neighbors_2":
        int neighbors = Integer.parseInt(args.get(0));
        String sName = args.get(1);
        String sStarNoQuotes = sName.substring(1, sName.length() - 1);
        ArrayList<Star> starsInRange2 = performNaiveNeighbors(neighbors, sStarNoQuotes, starsList);
        starsInRange2.forEach(System.out::println);
        break;
      case "naive_neighbors_4":
        int intNeighbors = Integer.parseInt(args.get(0));
        double dPosX = Double.parseDouble(args.get(1));
        double dPosY = Double.parseDouble(args.get(2));
        double dPosZ = Double.parseDouble(args.get(3));
        ArrayList<Star> starsInRange4 =
            performNaiveNeighbors(intNeighbors, dPosX, dPosY, dPosZ, starsList);
        starsInRange4.forEach(System.out::println);
        break;
      default:
        System.out.println("ERROR: Hashmap reqInfoMaps has unregistered names, "
            + "shouldn't have reached here");
        break;
    }
  }

  /**
   * Match the arguments given to which method (if any) the Command Object should execute.
   *
   * @param args the list of arguments to be operated on
   * @return Option of String - empty if the arguments are invalid, a String if match is found.
   */
  public Optional<String> matchArgsToMethod(ArrayList<String> args) {
    return argsValidator.testArgs(args);
  }

  /**
   * Finds "count" number of stars whose distance are closest to the star whose name is given.
   *
   * @param count the number of stars allowed for the list
   * @param name  the name of the destination star
   * @param alos  the list of stars to search through
   * @return the list of stars from least distance to greatest within count given
   */
  public ArrayList<Star> performNaiveNeighbors(int count, String name, ArrayList<Star> alos) {
    Optional<Star> selectedStar = findStarWithName(name, alos);
    // If the name given is empty, print an error
    if (name.isEmpty()) {
      System.out.println("ERROR: Empty String is not a valid name for stars");
      return new ArrayList<>();
    }

    // If the star is not found, print an error
    if (selectedStar.isEmpty()) {
      System.out.printf("ERROR: No Stars with name \"%s\" is found%n", name);
      return new ArrayList<>();
    } else {
      Star presentStar = selectedStar.get();

      double selectedX = presentStar.getX();
      double selectedY = presentStar.getY();
      double selectedZ = presentStar.getZ();

      ArrayList<Star> template = copyWithType(alos);
      template.removeIf(star -> star.getName().equals(name));

      return performNaiveNeighbors(count, selectedX, selectedY, selectedZ, template);
    }
  }

  /**
   * Finds "count" number of stars whose distance are closest coordinate (x, y, z).
   *
   * @param count the number of stars allowed for the list
   * @param x     the x coordinate of the point
   * @param y     the y coordinate of the point
   * @param z     the z coordinate of the point
   * @param alos  the list of stars to search through
   * @return the list of stars from least distance to greatest within count given
   */
  public ArrayList<Star> performNaiveNeighbors(int count, double x, double y, double z,
                                               ArrayList<Star> alos) {
    // If the count is zero, return an empty list
    if (count == 0) {
      return new ArrayList<>();
    }

    ArrayList<Star> template = copyWithType(alos);
    template.sort(Comparator.comparingDouble(star -> star.distanceTo(x, y, z)));

    // If the number asked to return is greater than the size of the list,
    // return the entire list
    if (count >= template.size()) {
      return template;
    }

    ArrayList<Star> truncatedStarList = new ArrayList<Star>();
    ArrayList<Star> sameValueList = new ArrayList<Star>();

    // Finds the star at the (count - 1)th position on the list and its distance
    double distAtCount = template.get(count - 1).distanceTo(x, y, z);
    int whenDistStart = 0;

    // Add stars from the list until finding a star whose distance is the same as
    // distAtCount
    for (int i = 0; i < count; i++) {
      if (template.get(i).distanceTo(x, y, z) == distAtCount) {
        whenDistStart = i;
        break;
      }
      truncatedStarList.add(template.get(i));
    }

    // Find all stars whose distance is the same as distAtCount
    for (int j = whenDistStart; j < template.size(); j++) {
      if (template.get(j).distanceTo(x, y, z) != distAtCount) {
        break;
      }
      sameValueList.add(template.get(j));
    }

    // Edge Case where the entire list is at the same distance and the number of stars
    // with the same distance equals the length of the list, in that case randomization
    // is not needed because they are used for tiebreakers when the limit exceeds.
    if ((truncatedStarList.size() == 0) && (sameValueList.size() == count)) {
      return sameValueList;
    }

    // randomly pick (count - whenDistStart + 1) out of the arraylist
    // until the truncatedStarList is filled
    Random ran = new Random();
    for (int k = 0; k < (count - whenDistStart); k++) {
      int selected = ran.nextInt(sameValueList.size());
      truncatedStarList.add(sameValueList.remove(selected));
    }

    return truncatedStarList;
  }

  /**
   * Given a string, determine if it can be converted to a non-negative integer.
   *
   * @param input - the input string
   * @return True if the string can be converted to said specification
   */
  public boolean isNonNegInt(String input) {
    return (isNonNegative(input) && isInteger(input));
  }

}
