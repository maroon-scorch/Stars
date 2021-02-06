package edu.brown.cs.mji13.stars;

import edu.brown.cs.mji13.command.Command;
import edu.brown.cs.mji13.kdTree.KDNode;
import edu.brown.cs.mji13.kdTree.KDTree;
import edu.brown.cs.mji13.validations.ArgsInformation;
import edu.brown.cs.mji13.validations.ArgsValidator;
import edu.brown.cs.mji13.validations.StringValFunctions;
import edu.brown.cs.mji13.validations.StringValidation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Map.entry;

public class Radius implements Command, StarsUtilities, StringValFunctions {

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
   * @param starStorage  - the storage of relevant stars data shared by files
   */
  public Radius(StarStorage starStorage) {
    this.starStorage = starStorage;
  }

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
    KDTree<Star> sTree = starStorage.getStarsTree();
    switch (methodName) {
      case "radius_2":
        double radius = Double.parseDouble(args.get(0));
        String sName = args.get(1);
        String sStarNoQuotes = sName.substring(1, sName.length() - 1);
        ArrayList<Star> starsInRange2 = performRadius(radius, sStarNoQuotes, sTree);
        starsInRange2.forEach(System.out::println);
        break;
      case "radius_4":
        double dRadius = Double.parseDouble(args.get(0));
        double dPosX = Double.parseDouble(args.get(1));
        double dPosY = Double.parseDouble(args.get(2));
        double dPosZ = Double.parseDouble(args.get(3));
        ArrayList<Star> starsInRange4 = performRadius(dRadius, dPosX, dPosY, dPosZ, sTree);
        starsInRange4.forEach(System.out::println);
        break;
      default:
        System.out.println("ERROR: Hashmap reqInfoMaps has unregistered names, "
            + "shouldn't have reached here");
        break;
    }
  }

  public Optional<String> matchArgsToMethod(ArrayList<String> args) {
    return argsValidator.testArgs(args);
  }

  public ArrayList<Star> performRadius(
      double r, double x, double y, double z, KDTree<Star> tree) {
    ArrayList<Star> template = new ArrayList<Star>();
    int dimension = 3;
    ArrayList<Function<Star, Double>> distList = new ArrayList<>() {{
      add(Star::getX);
      add(Star::getY);
      add(Star::getZ);
    }};

    performRadiusHelper(r, x, y, z, 0, dimension, template, tree.getTree());
    template.sort(Comparator.comparingDouble(star -> star.distanceTo(x, y, z)));
    return template;
  }

  public void performRadiusHelper(
      double r, double x, double y, double z, int level, int dimension,
      ArrayList<Star> currentList, KDNode<Star> currentNode) {

    Star currentStar = currentNode.getItem();
    if (currentStar == null) {
      return;
    }

    if (currentStar.distanceTo(x, y, z) <= r) {
      currentList.add(currentStar);
    } else if (currentStar.getX() == x && currentStar.getY() == y && currentStar.getZ() == z) {
      currentList.add(currentStar);
    }


    KDNode<Star> leftNode = currentNode.getLeft();
    Star leftStar = leftNode.getItem();

    KDNode<Star> rightNode = currentNode.getRight();
    Star rightStar = rightNode.getItem();

    int choice = level % dimension;

//    System.out.println(currentStar);
//    System.out.println(level);
//    System.out.println(leftStar);
//    System.out.println(rightStar);
//    System.out.println("--------------");


    switch (choice) {
      case 0:
        double xDist = Math.abs(currentStar.getX() - x);
        if (xDist <= r) {
          performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, leftNode);
          performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, rightNode);
        } else {
          if (x < currentStar.getX()) {
            performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, leftNode);
          } else if (x >= currentStar.getX()) {
            performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, rightNode);
          }
        }
        break;
      case 1:
        double yDist = Math.abs(currentStar.getY() - y);
        if (yDist <= r) {
          performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, leftNode);
          performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, rightNode);
        } else {
          if (y < currentStar.getY()) {
            performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, leftNode);
          } else if (y >= currentStar.getY()) {
            performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, rightNode);
          }
        }
        break;
      case 2:
        double zDist = Math.abs(currentStar.getZ() - z);
        if (zDist <= r) {
          performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, leftNode);
          performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, rightNode);
        } else {
          if (z < currentStar.getZ()) {
            performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, leftNode);
          } else if (z >= currentStar.getZ()) {
            performRadiusHelper(r, x, y, z, level + 1, dimension, currentList, rightNode);
          }
        }
        break;
    }
  }

  public ArrayList<Star> performRadius(
      double r, String name, KDTree<Star> tree) {
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

    double selectedX = presentStar.getX();
    double selectedY = presentStar.getY();
    double selectedZ = presentStar.getZ();

    ArrayList<Star> resultList
        = performRadius(r, selectedX, selectedY, selectedZ, tree);
    resultList.removeIf(star -> star.getName().equals(name));

    return resultList;
  }


}
