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
import java.util.Random;

import static java.util.Map.entry;

/**
 * Neighbors Command Object for executing the "neighbors ..." command.
 */
public class Neighbors implements Command, StringValFunctions {

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
          "neighbors_2",
          new String[] {"neighbors: int >= 0", "\"name\""},
          new StringValidation[] {this::isNonNegInt, this::isName},
          new String[] {"ERROR: Number of Neighbors must be a Positive Integer.",
              "ERROR: Name must be surrounded by double quotes."}
      )}),
      entry(4, new ArgsInformation[] {new ArgsInformation(
          "neighbors_4",
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
      = new ArgsValidator("neighbors", reqInfoMaps);

  /**
   * Creates a Neighbors object.
   *
   * @param starStorage - the storage of relevant stars data shared by files
   */
  public Neighbors(StarStorage starStorage) {
    this.starStorage = starStorage;
  }

  /**
   * Creates a Neighbors object.
   */
  public Neighbors() {
  }

  /**
   * Executes the naive_neighbors Command.
   * If successful, prints out the closest n number of stars to the specified location.
   *
   * @param args - the list of arguments to be operated on.
   */
  public void execute(ArrayList<String> args) {
    // If no File has been loaded, signal an error
    if (starStorage.getFileName().length() == 0) {
      System.out.println("ERROR: No file has been loaded yet");
      return;
    }

    // If the arguments failed the validation check, exit out of execute.
    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      return;
    }

    // If a valid method name has been found, then operate:
    String methodName = opMethodName.get();
    KDTree<Star> sTree = starStorage.getStarsTree();
    switch (methodName) {
      case "neighbors_2":
        int neighbors = Integer.parseInt(args.get(0));
        String sName = args.get(1);
        String sStarNoQuotes = sName.substring(1, sName.length() - 1);
        ArrayList<Star> starsInRange2 = performNeighbors(neighbors, sStarNoQuotes, sTree);
        starsInRange2.forEach(System.out::println);
        break;
      case "neighbors_4":
        int intNeighbors = Integer.parseInt(args.get(0));
        double dPosX = Double.parseDouble(args.get(1));
        double dPosY = Double.parseDouble(args.get(2));
        double dPosZ = Double.parseDouble(args.get(3));
        ArrayList<Star> starsInRange4 =
            performNeighbors(intNeighbors, dPosX, dPosY, dPosZ, sTree, Optional.empty());
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
   * Finds "count" number of stars whose distance are closest coordinate (x, y, z). If
   * an optional of name is present, then the specified name will be removed from the
   * resulting list.
   *
   * @param count the number of stars allowed for the list
   * @param x     the x coordinate of the point
   * @param y     the y coordinate of the point
   * @param z     the z coordinate of the point
   * @param tree  the KD Tree of stars to search through
   * @param name  an optional field to support "neighbors count name" commands
   * @return the list of stars from least distance to greatest within count given
   */
  public ArrayList<Star> performNeighbors(
      int count, double x, double y, double z, KDTree<Star> tree, Optional<String> name) {

    boolean filterName = name.isPresent();
    // Setup for findNearestNeighbors from the KD Tree
    List<Double> cordsList = Arrays.asList(x, y, z);
    ArrayList<Star> resultList = tree.findNearestNeighbors(count, cordsList);

    // If we are removing the name, then proceed to remove the star with said name from
    // the list.
    if (filterName) {
      String pName = name.get();
      resultList.removeIf(star -> star.getName().equals(pName));
      count = count - 1;
    }

    // If the size of the resultlist is the same as the count, then there are no ties,
    // so we can safely return the list.
    if (resultList.size() == count) {
      return resultList;
    }

    // If the list is tied, find the distance at which the stars are tied.
    ArrayList<Star> fullTiedList = new ArrayList<>(resultList);
    double distAtCount = resultList.get(count - 1).distanceTo(cordsList);
    resultList.removeIf(star -> star.distanceTo(cordsList) == distAtCount);
    fullTiedList.removeIf(star -> star.distanceTo(cordsList) != distAtCount);

    // For the remaining gap in the result list, randomly remove stars from the fully
    // tied list until the result list is full.
    Random ran = new Random();
    for (int i = 0; i < (count - resultList.size() + 1); i++) {
      int selected = ran.nextInt(fullTiedList.size());
      resultList.add(fullTiedList.remove(selected));
    }

    return resultList;
  }

  /**
   * Finds "count" number of stars whose distance are closest to the star whose name is given,
   * excluding the star with the name itself.
   *
   * @param count the number of stars allowed for the list
   * @param name  the name of the destination star
   * @param tree  the KD Tree of stars to search through
   * @return the list of stars from least distance to greatest within count given
   */
  public ArrayList<Star> performNeighbors(
      int count, String name, KDTree<Star> tree) {
    // If the name is empty, print an error
    if (name.isEmpty()) {
      System.out.println("ERROR: Empty String is not a valid name for stars");
      return new ArrayList<>();
    }

    // If the selected star is not found, print an error
    Optional<Star> selectedStar = starStorage.getStarFromMap(name);
    if (selectedStar.isEmpty()) {
      System.out.printf("ERROR: No Stars with name \"%s\" is found%n", name);
      return new ArrayList<>();
    }

    Star presentStar = selectedStar.get();

    double selectedX = presentStar.getCoordinate(0);
    double selectedY = presentStar.getCoordinate(1);
    double selectedZ = presentStar.getCoordinate(2);

    // Calls performNeighbors in the coordinate form.
    ArrayList<Star> resultList
        = performNeighbors(count + 1, selectedX, selectedY, selectedZ, tree,
        Optional.of(name));

    return resultList;
  }
}
