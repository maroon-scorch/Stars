package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValFunctions;
import edu.brown.cs.mji13.validations.StringValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

/**
 * Naive Radius Command Object for executing the "naive_radius ..." command.
 */
public class NaiveRadius implements Command, StringValFunctions {

  /**
   * The common data-types shared by all the stars-related commands.
   */
  private StarStorage starStorage;

  /**
   * Specifications on the requirements on the argument passed to the command.
   * 2 Arguments:
   *   - radius: non-negative number
   *   - name: surrounded by double quotes
   * 4 Arguments:
   *   - radius: non-negative number
   *   - x: number
   *   - y: number
   *   - z: number
   */
  private final Map<Integer, ArgsInformation[]> reqInfoMaps
      = Map.ofEntries(
      entry(2, new ArgsInformation[] {new ArgsInformation(
          "naive_radius_2",
          new String[] {"radius >= 0", "\"name\""},
          new StringValidation[] {this::isNonNegative, this::isName},
          new String[] {"ERROR: Radius must be non-negative.",
              "ERROR: Name must be surrounded by double quotes"}
      )}),
      entry(4, new ArgsInformation[] {new ArgsInformation(
          "naive_radius_4",
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
      = new ArgsValidator("naive_radius", reqInfoMaps);

  /**
   * Creates a NaiveRadius object.
   *
   * @param starStorage  - the storage of relevant stars data shared by files
   */
  public NaiveRadius(StarStorage starStorage) {
    this.starStorage = starStorage;
  }

  /**
   * Creates a NaiveRadius object.
   */
  public NaiveRadius() {
  }

  /**
   * Executes the naive_radius Command.
   * If successful, prints out every stars with radius less than or equal to that of
   * the specified radius.
   *
   * Note: TA Colton said that the randomization is meant for tiebreakers to include on the list
   * since naive_radius gets all the stars within range in, there are no tiebreakers to decide
   * which "star" gets in.
   * @param args - the list of arguments to be operated on.
   */
  public void execute(ArrayList<String> args) {

    // If the current file is empty, print an error
    if (starStorage.getFileName().length() == 0) {
      System.out.println("ERROR: No file has been loaded yet");
      return;
    }

    // If no methods are matched, exits the command
    Optional<String> opMethodName = matchArgsToMethod(args);
    if (opMethodName.isEmpty()) {
      return;
    }

    // If a method name has been found, determines which method it maps to and executes it
    String methodName = opMethodName.get();
    ArrayList<Star> slist = starStorage.getStarsList();
    switch (methodName) {
      case "naive_radius_2":
        double radius = Double.parseDouble(args.get(0));
        String sName = args.get(1);
        String sStarNoQuotes = sName.substring(1, sName.length() - 1);
        ArrayList<Star> starsInRange2 = performNaiveRadius(radius, sStarNoQuotes, slist);
        starsInRange2.forEach(System.out::println);
        break;
      case "naive_radius_4":
        double dRadius = Double.parseDouble(args.get(0));
        double dPosX = Double.parseDouble(args.get(1));
        double dPosY = Double.parseDouble(args.get(2));
        double dPosZ = Double.parseDouble(args.get(3));
        ArrayList<Star> starsInRange4 = performNaiveRadius(dRadius, dPosX, dPosY, dPosZ, slist);
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
   * @return Optional of String - empty if the arguments are invalid, a String if match is found.
   */
  public Optional<String> matchArgsToMethod(ArrayList<String> args) {
    return argsValidator.testArgs(args);
  }


  /**
   * Finds all stars whose distance to the star specified with the name is less than or equal to
   * the radius given.
   *
   * @param r the radius given
   * @param name the name of the star
   * @param alos the list of stars to search through
   * @return the list of stars within range inclusive.
   */
  public ArrayList<Star> performNaiveRadius(double r, String name, ArrayList<Star> alos) {
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

    ArrayList<Star> template = new ArrayList<>(alos);
    template.removeIf(star -> star.getName().equals(name));

    return performNaiveRadius(r, selectedX, selectedY, selectedZ, template);
  }

  /**
   * Finds all stars whose distance to the coordinate (x, y, z) is less than or equal to
   * the radius given.
   *
   * @param r    the radius given
   * @param x    the x coordinate of the point
   * @param y    the y coordinate of the point
   * @param z    the z coordinate of the point
   * @param alos the list of stars to search through
   * @return the list of stars within range inclusive.
   */
  public ArrayList<Star> performNaiveRadius(
      double r, double x, double y, double z, ArrayList<Star> alos) {
    ArrayList<Star> template = new ArrayList<>(alos);
    template.removeIf(star -> (star.distanceTo(Arrays.asList(x, y, z)) > r));
    template.sort(Comparator.comparingDouble(star -> star.distanceTo(Arrays.asList(x, y, z))));
    return template;
  }
}
