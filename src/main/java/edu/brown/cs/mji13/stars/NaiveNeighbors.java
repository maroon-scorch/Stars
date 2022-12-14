package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValFunctions;
import edu.brown.cs.mji13.validations.StringValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static java.util.Map.entry;

/**
 * Naive Neighbors Command Object for executing the "naive_neighbors ..." command.
 */
public class NaiveNeighbors implements Command, StringValFunctions {
  /**
   * The common data-types shared by all the stars-related commands.
   */
  private StarStorage starStorage;

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
   * @param starStorage - the storage of relevant stars data shared by files
   */
  public NaiveNeighbors(StarStorage starStorage) {
    this.starStorage = starStorage;
  }

  /**
   * Creates a NaiveNeighbors object.
   */
  public NaiveNeighbors() {
  }

  /**
   * Execute Specific Variables
   * The list of answers (Stars) the command line finds during its execution.
   * And the boolean hasErrorOccurred checking if error occurred during execution
   */
  private ArrayList<Star> answers = new ArrayList<>();
  private boolean hasErrorOccurred = false;

  /**
   * Executes the naive_neighbors Command.
   * If successful, prints out the closest n number of stars to the specified location.
   * Note: TA Colton said that the randomization is meant for tiebreakers to include on the list
   * so if there are stars with same distance away but including them does not exceed that number
   * asked, they will be included just as normal.
   *
   * @param args - the list of arguments to be operated on.
   * @return the Arraylist of starIDs to be print out if Successful. If unSuccessful,
   * prints the error messages instead.
   */
  public ArrayList<String> execute(ArrayList<String> args) {
    ArrayList<String> messages = new ArrayList<>();
    hasErrorOccurred = false;
    answers.clear();

    // If no File has been loaded, signal an error
    if (starStorage.getFileName().length() == 0) {
      hasErrorOccurred = true;
      messages.add("ERROR: No file has been loaded yet");
      return messages;
    }

    // If the arguments failed the validation check, exit out of execute.
    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      hasErrorOccurred = true;
      messages.add(argsValidator.getErrorMessage());
      return messages;
    }

    // If a valid method name has been found, then operate:
    String methodName = opMethodName.get();
    ArrayList<Star> slist = starStorage.getStarsList();
    switch (methodName) {
      case "naive_neighbors_2":
        int neighbors = Integer.parseInt(args.get(0));
        String sName = args.get(1);
        String sStarNoQuotes = sName.substring(1, sName.length() - 1);
        if (starStorage.isNameInStorage(sStarNoQuotes)) {
          answers = performNaiveNeighbors(neighbors, sStarNoQuotes, slist);
          answers.forEach((str) -> messages.add(str.getStarID()));
        } else {
          hasErrorOccurred = true;
          messages.add(String.format("ERROR: No Stars with name \"%s\" "
              + "is found or name is empty", sStarNoQuotes));
        }
        return messages;

      case "naive_neighbors_4":
        int intNeighbors = Integer.parseInt(args.get(0));
        double dPosX = Double.parseDouble(args.get(1));
        double dPosY = Double.parseDouble(args.get(2));
        double dPosZ = Double.parseDouble(args.get(3));
        answers = performNaiveNeighbors(intNeighbors, dPosX, dPosY, dPosZ, slist);
        answers.forEach((str) -> messages.add(str.getStarID()));
        return messages;

      default:
        System.out.println("ERROR: Hashmap reqInfoMaps has unregistered names, "
            + "shouldn't have reached here");
        return new ArrayList<>();
    }
  }

  /**
   * Executes the naive_neighbors Command for the GUI.
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
    Star presentStar = starStorage.getStarFromMap(name);

    double selectedX = presentStar.getCoordinate(0);
    double selectedY = presentStar.getCoordinate(1);
    double selectedZ = presentStar.getCoordinate(2);

    ArrayList<Star> template = new ArrayList<>(alos);
    template.removeIf(star -> star.getName().equals(name));

    return performNaiveNeighbors(count, selectedX, selectedY, selectedZ, template);
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

    ArrayList<Star> template = new ArrayList<>(alos);
    List<Double> cordList = Arrays.asList(x, y, z);
    template.sort(Comparator.comparingDouble(star -> star.distanceTo(cordList)));

    // If the number asked to return is greater than the size of the list,
    // return the entire list
    if (count >= template.size()) {
      return template;
    }

    ArrayList<Star> truncatedStarList = new ArrayList<Star>();
    ArrayList<Star> sameValueList = new ArrayList<Star>();

    // Finds the star at the (count - 1)th position on the list and its distance
    double distAtCount = template.get(count - 1).distanceTo(cordList);
    int whenDistStart = 0;

    // Add stars from the list until finding a star whose distance is the same as
    // distAtCount
    for (int i = 0; i < count; i++) {
      if (template.get(i).distanceTo(cordList) == distAtCount) {
        whenDistStart = i;
        break;
      }
      truncatedStarList.add(template.get(i));
    }

    // Find all stars whose distance is the same as distAtCount
    for (int j = whenDistStart; j < template.size(); j++) {
      if (template.get(j).distanceTo(cordList) != distAtCount) {
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

}
